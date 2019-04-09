package com.student.rating.service;

import com.student.rating.dto.AvgReportByGroupDTO;
import com.student.rating.dto.OverallReportByGroupDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Тарас on 11.06.2018.
 */
public interface ReportService {
    ByteArrayInputStream reportByGroup();
    ByteArrayInputStream avgReportByGroups();
    List<AvgReportByGroupDTO> avgReportByGroup();
    List<OverallReportByGroupDTO> overallByGroup();
}
