package student_rating.service;

import student_rating.DTO.AvgReportByGroupDTO;
import student_rating.DTO.OverallReportByGroup;
import student_rating.entity.Rating;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Тарас on 11.06.2018.
 */
public interface ReportService {
    ByteArrayInputStream reportByGroup();
    ByteArrayInputStream avgReportByGroups();
    List<AvgReportByGroupDTO> avgReportByGroup();
    List<OverallReportByGroup> overallByGroup();
}
