package com.student.rating.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.student.rating.dto.AvgReportByGroupDTO;
import com.student.rating.dto.OverallReportByGroupDTO;
import com.student.rating.entity.Group;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Student;
import com.student.rating.repository.RatingRepository;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.ReportService;
import com.student.rating.utility.StaticDataEngine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 11.06.2018.
 */
@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;
    private List<Group> groups;

    @Autowired
    public ReportServiceImpl(RatingRepository ratingRepository, StudentRepository studentRepository) {
        this.ratingRepository = ratingRepository;
        this.studentRepository = studentRepository;
        this.groups = StaticDataEngine.GROUP_LIST;
    }

    @Override
    public ByteArrayInputStream reportByGroup() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Звіт по всіх групах та їх студентах");
        book.getProperties().getCoreProperties().setCreator("Tarasii");

        int rowNum = 0;

        for (Group group : groups) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            Cell cell0 = row.createCell(colNum++);
            cell0.setCellValue("Група " + group.getName());
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, colNum - 1, 3));
            CellUtil.setAlignment(cell0, HorizontalAlignment.CENTER);

            colNum = 0;
            Row row1 = sheet.createRow(rowNum++);
            Cell cell1 = row1.createCell(colNum++);
            cell1.setCellValue("Прізвище");
            sheet.autoSizeColumn(cell1.getColumnIndex());

            Cell cell2 = row1.createCell(colNum++);
            cell2.setCellValue("Ім'я");

            Cell cell3 = row1.createCell(colNum++);
            cell3.setCellValue("Балів");


            for (Student student : group.getStudents()) {
                colNum = 0;
                double sum = 0;
                Row row2 = sheet.createRow(rowNum++);

                Cell cell4 = row2.createCell(colNum++);
                cell4.setCellValue(student.getStudentSurname());

                Cell cell5 = row2.createCell(colNum++);
                cell5.setCellValue(student.getStudentName());

                for (Rating rating : student.getRatings()) {
                    sum = sum + rating.getScore();
                }

                if (sum > 10) {
                    sum = 10;
                }
                if (sum < 0) {
                    sum = 0;
                }

                Cell cell6 = row2.createCell(colNum++);
                cell6.setCellValue(sum);
            }

        }

        try {
            book.write(os);
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] bytes = os.toByteArray();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        return inputStream;
    }

    @Override
    public ByteArrayInputStream avgReportByGroups() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Звіт по групах загальний");
        book.getProperties().getCoreProperties().setCreator("Tarasii");

        int rowNum = 0;
        int colNum = 0;
        int students = 0;
        Row row = sheet.createRow(rowNum++);

        Cell cell0 = row.createCell(colNum++);
        cell0.setCellValue("Група");

        Cell cell1 = row.createCell(colNum++);
        cell1.setCellValue("Середня оцінка");
        sheet.autoSizeColumn(cell1.getColumnIndex());

        for (Group group : groups) {
            colNum = 0;
            double groupSum = 0;
            students = 0;
            Row row1 = sheet.createRow(rowNum++);
            Cell cell2 = row1.createCell(colNum++);
            cell2.setCellValue(group.getName());

            for (Student student : group.getStudents()) {
                double sum = 0;
                for (Rating rating : student.getRatings()) {
                    sum = sum + rating.getScore();
                }
                students++;
                if (sum > 10) {
                    sum = 10;
                }
                if (sum < 0) {
                    sum = 0;
                }
                groupSum += sum;
            }

            LOG.info("Students : {}",students);
            LOG.info("Summary : {}", groupSum / students);
            Cell cell3 = row1.createCell(colNum++);
            cell3.setCellValue(groupSum / students);
        }

        /* At the end of this step, we have a worksheet with test data, that we want to write into a chart */
        /* Create a drawing canvas on the worksheet */
        XSSFDrawing xlsxDrawing = sheet.createDrawingPatriarch();
        /* Define anchor points in the worksheet to position the chart */
        XSSFClientAnchor anchor = xlsxDrawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);
        /* Create the chart object based on the anchor point */
        XSSFChart myLineChart = xlsxDrawing.createChart(anchor);
        myLineChart.setTitleText("Середнє значення рейтингу по групах");
        /* Define legends for the line chart and set the position of the legend */
        XSSFChartLegend legend = myLineChart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);

        /* Create data for the chart */
        LineChartData data = myLineChart.getChartDataFactory().createLineChartData();

        /* Define chart AXIS */
        ChartAxis bottomAxis = myLineChart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = myLineChart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        /* Define Data sources for the chart */
        /* Set the right cell range that contain values for the chart */
        /* Pass the worksheet and cell range address as inputs */
        /* Cell Range Address is defined as First row, last row, first column, last column */

        ChartDataSource<String> xs = DataSources.fromStringCellRange(sheet, new CellRangeAddress(1, (rowNum), 0, 0));
        ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, (rowNum), 1, 1));

        /* Add chart data sources as data to the chart */
        LineChartSeries series = data.addSeries(xs, ys1);
        series.setTitle("Середнє значення по групі");

        /* Plot the chart with the inputs from data and chart axis */
        myLineChart.plot(data, bottomAxis, leftAxis);

        try {
            book.write(os);
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] bytes = os.toByteArray();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        return inputStream;
    }

    @Override
    public List<AvgReportByGroupDTO> avgReportByGroup() {
        List<AvgReportByGroupDTO> avgReportByGroupDTOList = new ArrayList<>();
        int students ;
        int counter = 0;
        for (Group group : groups) {
            counter++;
            double sum = 0;
            students = 0;
            for (Student student : group.getStudents()) {
                for (Rating rating : student.getRatings()) {
                    sum = sum + rating.getScore();
                }
                students++;
            }
            if (sum > 10) {
                sum = 10;
            }
            if (sum < 0) {
                sum = 0;
            }
            AvgReportByGroupDTO avgReportByGroupDTO = new AvgReportByGroupDTO();
            avgReportByGroupDTO.setNumber(counter);
            avgReportByGroupDTO.setGroupName(group.getName());
            avgReportByGroupDTO.setScore(sum / students);
            avgReportByGroupDTOList.add(avgReportByGroupDTO);
        }
        return avgReportByGroupDTOList;
    }

    @Override
    public List<OverallReportByGroupDTO> overallByGroup() {
        List<OverallReportByGroupDTO> ratings = new ArrayList<>();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Student headOfGroup = studentRepository.findByUsername(name);
            List<Student> studentList = studentRepository.findAllStudentsByGroupId(headOfGroup.getGroup().getId());
            for (Student student : studentList) {
                for (Rating rating : student.getRatings()) {
                    OverallReportByGroupDTO reportByGroup = new OverallReportByGroupDTO();
                    reportByGroup.setIdStudent(student.getId());
                    reportByGroup.setStudentName(student.getStudentName());
                    reportByGroup.setStudentSurname(student.getStudentSurname());
                    reportByGroup.setGroupName(student.getGroup().getName());
                    reportByGroup.setBlockName(rating.getParagraph().getSubblock().getBlock().getName());
                    reportByGroup.setSubblockName(rating.getParagraph().getSubblock().getName());
                    reportByGroup.setParagraphName(rating.getParagraph().getName());
                    reportByGroup.setScore(rating.getScore());
                    ratings.add(reportByGroup);
                }

            }
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            List<Rating> ratingList = ratingRepository.findAll();
            for (Rating rating : ratingList) {
                OverallReportByGroupDTO reportByGroup = new OverallReportByGroupDTO();
                reportByGroup.setIdStudent(rating.getStudent().getId());
                reportByGroup.setStudentName(rating.getStudent().getStudentName());
                reportByGroup.setStudentSurname(rating.getStudent().getStudentSurname());
                reportByGroup.setGroupName(rating.getStudent().getGroup().getName());
                reportByGroup.setBlockName(rating.getParagraph().getSubblock().getBlock().getName());
                reportByGroup.setSubblockName(rating.getParagraph().getSubblock().getName());
                reportByGroup.setParagraphName(rating.getParagraph().getName());
                reportByGroup.setScore(rating.getScore());
                ratings.add(reportByGroup);
            }
        }
        return ratings;
    }
}

