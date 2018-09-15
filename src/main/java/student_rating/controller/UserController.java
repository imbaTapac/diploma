package student_rating.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import student_rating.DTO.AvgReportByGroupDTO;
import student_rating.DTO.RatingDTO;
import student_rating.DTO.RejectRatingDTO;
import student_rating.entity.Block;
import student_rating.entity.Group;
import student_rating.entity.Rating;
import student_rating.entity.Student;
import student_rating.repository.GroupRepository;
import student_rating.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Тарас on 19.02.2018.
 */
@Controller
@ComponentScan("student_rating")
public class UserController {

    private final MessageSource messageSource;
    private final BlockService blockService;
    private final StudentService studentService;
    private final RatingService ratingService;
    private final EmailService emailService;
    private final ReportService reportService;
    private final GroupRepository groupRepository;
    private List<Rating> ratingList;

    private static final String APPLICATION_EXCEL = "application/vnd.ms-excel";

    @Autowired
    public UserController(MessageSource messageSource, BlockService blockService,
                          StudentService studentService, RatingService ratingService, EmailService emailService,
                          ReportService reportService,GroupRepository groupRepository) {
        this.messageSource = messageSource;
        this.blockService = blockService;
        this.studentService = studentService;
        this.ratingService = ratingService;
        this.emailService = emailService;
        this.reportService = reportService;
        this.groupRepository = groupRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Locale locale, Model model, String error, String logout, String message) {

        System.out.println(locale.getDisplayLanguage());
        System.out.println(messageSource.getMessage("locale", new String[]{locale.getDisplayName(locale)}, locale));

        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("error", new String[]{}, locale));
        }

        if (logout != null) {
            model.addAttribute("message", "Успешный выход");
        }

        return "login";
    }


    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        model.addAttribute("role", role);
        return "welcome";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.findByUsername(studentName);
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        model.addAttribute("student", student);
        model.addAttribute("role", role);
        return "profile";
    }

    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public String rating(Model model) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        model.addAttribute("role", role);
        return "rating";
    }

    @RequestMapping(value = "/rating/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveRating(@RequestBody List<RatingDTO> ratingDTO) {
        ratingService.save(ratingDTO);
        return "rating";
    }

    @RequestMapping(value = "/staticData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getData(@RequestBody List<Block> blocks) throws IOException {
        try {
            for (Block block : blocks) {
                blockService.save(block);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return "KEK";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String students(Model model) {
        List<Student> students = studentService.findAllStudentsByGroupId();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Group> groups = groupRepository.findAll();

        model.addAttribute("students", students);
        model.addAttribute("role", role);
        model.addAttribute("groups",groups);
        return "students";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @RequestMapping(value = "/check_rating", method = RequestMethod.GET)
    public String check(Model model) {
        List<Student> students = studentService.findAllStudentsByGroupIdAndStageOfApprove();
        model.addAttribute("students", students);
        return "check_rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @RequestMapping(value = "/check_rating/{id}", method = RequestMethod.POST)
    public String check_rating(@PathVariable("id") Long id) {
        List<Rating> rating = ratingService.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(id);
        ratingList = new ArrayList();
        ratingList = rating;
        return "redirect:/students_rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @RequestMapping(value = "/students_rating", method = RequestMethod.GET)
    public String student_rating(Model model) {
        model.addAttribute("rating", ratingList);
        return "students_rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @RequestMapping(value = "/students_rating/accept", method = RequestMethod.PUT)
    public String accept(@RequestBody Long studentId) {
        ratingService.approveRating(studentId);
        return "students_rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @RequestMapping(value = "/students_rating/reject", method = RequestMethod.POST)
    @ResponseBody
    public String reject(@RequestBody RejectRatingDTO rejectRatingDTO) {
        emailService.sendMail(rejectRatingDTO);
        return "students_rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String reports() {
        return "reports";
    }


    @RequestMapping(value = "/report_by_group", method = RequestMethod.GET)
    public @ResponseBody void reportByGroup(HttpServletResponse response) throws IOException {
        InputStream inputStream = reportService.reportByGroup();
        response.setContentType(APPLICATION_EXCEL);
        response.setHeader("Content-disposition","attachment;filename=ReportByGroup.xls");
        response.setHeader("Content-Length", String.valueOf(inputStream.read()));
        FileCopyUtils.copy(inputStream,response.getOutputStream());
    }

    @RequestMapping(value = "/avg_report_by_groups", method = RequestMethod.GET)
    public @ResponseBody void avgReportByGroups(HttpServletResponse response) throws IOException {
        InputStream inputStream = reportService.avgReportByGroups();
        response.setContentType(APPLICATION_EXCEL);
        response.setHeader("Content-disposition","attachment;filename=ReportByGroup1.xls");
        response.setHeader("Content-Length", String.valueOf(inputStream.read()));
        FileCopyUtils.copy(inputStream,response.getOutputStream());
    }

    @RequestMapping(value = "/statistic_report1",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String statisticReportByGroups() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
       return mapper.writeValueAsString(reportService.avgReportByGroup());
    }

    @RequestMapping(value = "/statistic_report2",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String statisticReportOverallByGroup() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(reportService.overallByGroup());
    }
}