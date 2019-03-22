package com.student.rating.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.student.rating.service.ReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ReportController {

    private final ReportService reportService;

    private static final String APPLICATION_EXCEL = "application/vnd.ms-excel";

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @GetMapping(value = "/reports")
    public String reports() {
        return "reports";
    }

    @GetMapping(value = "/report_by_group")
    public @ResponseBody
    void reportByGroup(HttpServletResponse response) throws IOException {
        InputStream inputStream = reportService.reportByGroup();
        response.setContentType(APPLICATION_EXCEL);
        response.setHeader("Content-disposition", "attachment;filename=ReportByGroup.xlsx");
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping(value = "/avg_report_by_groups")
    public @ResponseBody
    void avgReportByGroups(HttpServletResponse response) throws IOException {
        InputStream inputStream = reportService.avgReportByGroups();
        response.setContentType(APPLICATION_EXCEL);
        response.setHeader("Content-disposition", "attachment;filename=ReportByGroup1.xlsx");
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @PostMapping(value = "/statistic_report1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    String statisticReportByGroups() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(reportService.avgReportByGroup());
    }

    @PostMapping(value = "/statistic_report2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    String statisticReportOverallByGroup() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(reportService.overallByGroup());
    }
}
