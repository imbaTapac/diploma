package com.student.rating.controller;

import com.student.rating.dto.*;
import com.student.rating.entity.Rating;
import com.student.rating.entity.log.LogType;
import com.student.rating.logging.Logged;
import com.student.rating.mapper.StudentRatingDTOMapper;
import com.student.rating.service.GroupService;
import com.student.rating.service.RatingService;
import com.student.rating.service.SpecialtyService;
import com.student.rating.service.StudentService;
import com.student.rating.validator.LDAPValidator;
import com.student.rating.validator.StudentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Тарас on 19.02.2018.
 */
@Controller
public class UserController {

    private final MessageSource messageSource;
    private final StudentService studentService;
    private final GroupService groupService;
    private final StudentValidator studentValidator;
    private final LDAPValidator ldapValidator;
    private final SpecialtyService specialtyService;
    private final RatingService ratingService;
    private final StudentRatingDTOMapper studentRatingDTOMapper;

    private List<String> groupNames;
    private List<String> specialtyNames;

    @Value("${application.version}")
    private String version;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(MessageSource messageSource, StudentService studentService, GroupService groupService,
                          SpecialtyService specialtyService, StudentValidator studentValidator, LDAPValidator ldapValidator,
                          RatingService ratingService, StudentRatingDTOMapper studentRatingDTOMapper) {
        this.messageSource = messageSource;
        this.studentService = studentService;
        this.groupService = groupService;
        this.specialtyService = specialtyService;
        this.studentValidator = studentValidator;
        this.ldapValidator = ldapValidator;
        this.ratingService = ratingService;
        this.studentRatingDTOMapper = studentRatingDTOMapper;
    }


    @GetMapping(value = "/login")
    public String login(Locale locale, Model model, String error, String session, String maxSession) {
        //LOG.info(messageSource.getMessage("locale", new String[]{locale.getDisplayName(locale)}, locale));

        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("error", new String[]{}, locale));
        } else if (session != null) {
            model.addAttribute("session", messageSource.getMessage("sessionExpired", new String[]{}, locale));
        } else if (maxSession != null) {
            model.addAttribute("sessionError", "Max count of session");
        }

        return "login";
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("LDAPUser", new LDAPUserDTO());
        return "registration";
    }

    @PostMapping(value = "/registration")
    @Logged(LogType.SIGN_UP)
    public String register(@ModelAttribute("LDAPUser") LDAPUserDTO ldapUserDTO, BindingResult result, Model model, Locale locale) {
        String userName = ldapUserDTO.getLogin();
        String email = ldapUserDTO.getEmail();

        ldapValidator.validate(ldapUserDTO, result);
        if (result.hasErrors()) {
            return "registration";
        }

        boolean founded = studentService.searchInLDAP(userName, email);

        if (!founded) {
            model.addAttribute("error", messageSource.getMessage("ldap_error", new String[]{}, locale));
            return "registration";
        }
        studentService.createNewStudentFromLDAPAttribute();

        return "redirect:/finish-registration";
    }

    @GetMapping(value = "/finish-registration")
    public String finishRegistration(Model model) {
        groupNames = groupService.getAllFacultyGroupNamesByFacultyName();
        specialtyNames = specialtyService.getAllSpecialtyNamesByFacultyName();
        model.addAttribute("groups", groupNames);
        model.addAttribute("specialties", specialtyNames);
        model.addAttribute("UserDTO", new UserDTO());
        return "finish-registration";
    }

    @PostMapping(value = "/save-user")
    public String finishRegister(@ModelAttribute("UserDTO") UserDTO userDTO, BindingResult result, Model model) {
        studentValidator.validate(userDTO, result);
        if (result.hasErrors()) {
            model.addAttribute("specialties", specialtyNames);
            model.addAttribute("groups", groupNames);
            return "finish-registration";
        }

        studentService.saveFromDTO(userDTO);

        return "redirect:/login";
    }

    @GetMapping(value = "/")
    public String redirect() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser") ?
                "redirect:/welcome" : "redirect:/login";
    }

    @GetMapping(value = "/welcome")
    public String welcome(HttpSession session) {
        studentService.addToSession(session);
        return "welcome";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_STUDENT"})
    @GetMapping("/profile")
    public String profile(Model model) {
        StudentProfileDTO profileDTO = studentService.getStudentProfile();
        model.addAttribute("profile", profileDTO);
        return "profile";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @GetMapping("/students")
    public String students(Model model) {
        List<GroupDTO> groups = groupService.findAllGroupsWithStudents();
        model.addAttribute("groups", groups);
        return "students";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_STUDENT"})
    @GetMapping("/my-rating")
    public String studentRating(Model model) {
        List<Rating> ratings = ratingService.findAllMonthStudentRatings(new Date());
        model.addAttribute("ratings", ratings);
        return "my-rating";
    }


    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_STUDENT"})
    @PostMapping(value = "/my-rating")
    public ResponseEntity<List<StudentRatingDTO>> getStudentRating(@RequestBody Date date) {
        List<Rating> ratings = ratingService.findAllMonthStudentRatings(date);
        List<StudentRatingDTO> studentRatings = studentRatingDTOMapper.toDTOs(ratings);
        return ResponseEntity.ok()
                .body(studentRatings);
    }

    @GetMapping(value = "/version")
    public ResponseEntity getApplicationVersion() {
        String ver = "Current version is " + version;
        return ResponseEntity.ok(ver);
    }
}