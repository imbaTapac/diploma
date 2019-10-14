package com.student.rating.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.student.rating.dto.AvgReportByGroupDTO;
import com.student.rating.dto.OverallReportByGroupDTO;

/**
 * Created by Тарас on 11.06.2018.
 */
public interface ReportService {
	Resource reportByGroup();

	Resource avgReportByGroups();

	List<AvgReportByGroupDTO> avgReportByGroup();

	List<OverallReportByGroupDTO> overallByGroup();
}
