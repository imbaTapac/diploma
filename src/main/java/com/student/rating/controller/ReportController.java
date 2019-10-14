package com.student.rating.controller;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.student.rating.dto.AvgReportByGroupDTO;
import com.student.rating.dto.OverallReportByGroupDTO;
import com.student.rating.service.ReportService;

@Controller
public class ReportController {

	private final ReportService reportService;

	private static final String APPLICATION_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
	@GetMapping(value = "/reports")
	public String reports() {
		return "reports";
	}

	@Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
	@GetMapping(value = "/reportByGroup")
	public ResponseEntity<Resource> reportByGroup() {
		Resource report = reportService.reportByGroup();
		String contentDisposition = buildContentDisposition("groupReport");

		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION, contentDisposition)
				.header(CONTENT_TYPE, APPLICATION_EXCEL)
				.body(report);
	}

	@Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
	@GetMapping(value = "/avgReportByGroups")
	public ResponseEntity<Resource> avgReportByGroups() {
		Resource report = reportService.avgReportByGroups();
		String contentDisposition = buildContentDisposition("avgReportByGroups");

		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION, contentDisposition)
				.header(CONTENT_TYPE, APPLICATION_EXCEL)
				.body(report);
	}

	private String buildContentDisposition(String reportType) {
		return "attachment; filename=\"" + reportType + ".xlsx\"";
	}

	@Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
	@PostMapping(value = "/statistic_report1")
	public ResponseEntity<List<AvgReportByGroupDTO>> statisticReportByGroups() {
		List<AvgReportByGroupDTO> avgReportByGroupDTO = reportService.avgReportByGroup();

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(avgReportByGroupDTO);
	}

	@Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
	@PostMapping(value = "/statistic_report2")
	public ResponseEntity<List<OverallReportByGroupDTO>> statisticReportOverallByGroup() {
		List<OverallReportByGroupDTO> overallReportByGroupDTO = reportService.overallByGroup();

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(overallReportByGroupDTO);
	}
}
