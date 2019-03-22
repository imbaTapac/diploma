package com.student.rating.controller;

import com.student.rating.dto.LDAPUserDTO;
import com.student.rating.dto.UserDTO;
import com.student.rating.entity.Group;
import com.student.rating.entity.Student;
import com.student.rating.service.GroupService;
import com.student.rating.service.StudentService;
import com.student.rating.validator.LDAPValidator;
import com.student.rating.validator.StudentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.directory.Attributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.student.rating.constants.Constants.*;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Created by Тарас on 19.02.2018.
 */
@Controller
@SessionAttributes(names = "student", types = Student.class)
public class UserController {

    private final MessageSource messageSource;
    private final StudentService studentService;
    private final GroupService groupService;
    private final LdapTemplate template;
    private final StudentValidator studentValidator;
    private final LDAPValidator ldapValidator;

    private Student student;
    private String studentFacultyName;
    private List<String> groupNames;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(MessageSource messageSource, StudentService studentService, GroupService groupService, LdapTemplate ldapTemplate,
                          StudentValidator studentValidator, LDAPValidator ldapValidator) {
        this.messageSource = messageSource;
        this.studentService = studentService;
        this.groupService = groupService;
        this.template = ldapTemplate;
        this.studentValidator = studentValidator;
        this.ldapValidator = ldapValidator;
    }


    @GetMapping(value = "/login")
    public String login(Locale locale, Model model, String error) {
        LOG.info(messageSource.getMessage("locale", new String[]{locale.getDisplayName(locale)}, locale));

        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("error", new String[]{}, locale));
        }

        return "login";
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("LDAPUser", new LDAPUserDTO());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String register(@ModelAttribute("LDAPUser") LDAPUserDTO ldapUserDTO, BindingResult result, Model model, Locale locale) {
        String username = ldapUserDTO.getLogin();
        String email = ldapUserDTO.getEmail();

        ldapValidator.validate(ldapUserDTO, result);
        if (result.hasErrors()) {
            return "registration";
        }
        //TODO: in ver_0.8 remove this logic to the service
        boolean founded = template.search(query().base("ou=people").where("uid").is(username).and("mail").is(email),
                (AttributesMapper<Attributes>) attributes -> {
                    if (attributes == null) {
                        return null;
                    }
                    for (Enumeration e = attributes.getAll(); e.hasMoreElements(); ) {
                        LOG.debug("{}", e.nextElement());
                    }
                    if (attributes.get(FACULTY_ATTRIBUTE) != null &&
                            attributes.get(ROLE_ATTRIBUTE) != null &&
                            !attributes.get(ROLE_ATTRIBUTE).get().toString().equals(ORGANIZATIONAL_PERSON)
                    ) {
                        studentFacultyName = attributes.get(FACULTY_ATTRIBUTE).get().toString();
                        studentFacultyName = studentFacultyName.contains(IT_FACULTY.toLowerCase()) ? IT_FACULTY : studentFacultyName;
                        LOG.info("Student faculty is {}", studentFacultyName);
                    }

                    student = studentService.createNewStudentFromLDAPAttribute(attributes);
                    return attributes;
                }).isEmpty();

        LOG.debug("Status of searching user with login {} and email {} in LDAP is {}", username,email,founded);

        if (founded) {
            model.addAttribute("error", messageSource.getMessage("ldap_error", new String[]{}, locale));
            return "registration";
        }

        return "redirect:/finish-registration";
    }

    @GetMapping(value = "/finish-registration")
    public String finishRegistration(Model model) {
        groupNames = groupService.getAllFacultyGroupNamesByFacultyName(studentFacultyName);
        model.addAttribute("groups", groupNames);
        model.addAttribute("UserDTO", new UserDTO());
        return "finish-registration";
    }

    @PostMapping(value = "/save-user")
    public String finishRegister(@ModelAttribute("UserDTO") UserDTO userDTO, BindingResult result, Model model) {
        studentValidator.validate(userDTO, result);
        if (result.hasErrors()) {
            model.addAttribute("groups", groupNames);
            return "finish-registration";
        }
        //TODO: in ver_0.8 remove this logic to the service
        if (!studentFacultyName.isEmpty()) {
            LOG.info("Student faculty is {}", studentFacultyName);
            Group group = groupService.findGroupByName(userDTO.getGroupName());
            LOG.info("Group name is {}", userDTO.getGroupName());
            LOG.info("Group is {}", group);
            student.setGroup(group);
        }
        LOG.info("Trying to save student {}", student);

        studentService.saveFromDTO(student, userDTO);

        return "redirect:/login";
    }

    @GetMapping(value = "/")
    public String redirect() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser") ?
                "redirect:/welcome" : "redirect:/login";
    }

    @GetMapping(value = "/welcome")
    public String welcome(Model model, HttpSession session) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        studentService.addToSession(session);
        model.addAttribute("role", role);
        return "welcome";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_STUDENT"})
    @GetMapping("/profile")
    public String profile(Student student, Model model) {
        model.addAttribute("student", student);
        return "profile";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @GetMapping("/students")
    public String students(Model model) {
        List<Group> groups = groupService.groupList();
        model.addAttribute("groups", groups);
        return "students";
    }

    @GetMapping(value = "error")
    public ModelAndView errors(HttpServletRequest request) {
        String pageURL = "";
        int httpErrorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (Objects.equals(httpErrorCode, 403)) {
            pageURL = "error403";
        }
        if (Objects.equals(httpErrorCode, 404)) {
            pageURL = "error404";
        }

        ModelAndView errorPage = new ModelAndView(pageURL);
        errorPage.addObject("msg", "У вас недостатньо прав для перегляду цієї сторіки!");

        return errorPage;
    }
}