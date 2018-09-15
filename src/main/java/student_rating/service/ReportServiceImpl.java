package student_rating.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import student_rating.DTO.AvgReportByGroupDTO;
import student_rating.DTO.OverallReportByGroup;
import student_rating.entity.Group;
import student_rating.entity.Rating;
import student_rating.entity.Student;
import student_rating.repository.GroupRepository;
import student_rating.repository.RatingRepository;
import student_rating.repository.StudentRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 11.06.2018.
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final GroupRepository groupRepository;

    @Autowired
    public ReportServiceImpl(RatingRepository ratingRepository, StudentRepository studentRepository, StudentService studentService,
                             GroupRepository groupRepository) {
        this.ratingRepository = ratingRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.groupRepository = groupRepository;
    }


    @Override
    public ByteArrayInputStream reportByGroup() {
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("Звіт по всіх групах та їх студентах");
        List<Student> studentList = studentService.findAll();
        List<Group> groups = groupRepository.findAll();

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


            for (Student student : studentList) {
                colNum = 0;
                double sum = 0;
                if (student.getGroup().getId() == group.getId()) {
                    Row row2 = sheet.createRow(rowNum++);

                    Cell cell4 = row2.createCell(colNum++);
                    cell4.setCellValue(student.getStudentSurname());

                    Cell cell5 = row2.createCell(colNum++);
                    cell5.setCellValue(student.getStudentName());

                    List<Rating> ratings = ratingRepository.findByStudentId(student.getId());
                    for (Rating rating : ratings) {
                        System.out.println(student.getRatings().size());
                        sum = sum + rating.getScore();
                    }

                    Cell cell6 = row2.createCell(colNum++);
                    cell6.setCellValue(sum);

                }
            }

        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            book.write(os);
            book.write(new FileOutputStream("C://Users/Тарас/Downloads/repot.xls"));
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
        List<Student> studentList = studentService.findAll();
        List<Group> groups = groupRepository.findAll();

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
            double sum = 0;
            students = 0;
            Row row1 = sheet.createRow(rowNum++);
            Cell cell2 = row1.createCell(colNum++);
            cell2.setCellValue(group.getName());

            for (Student student : group.getStudent()) {
                List<Rating> ratings = ratingRepository.findByStudentId(student.getId());
                for (Rating rating : ratings) {
                    sum = sum + rating.getScore();
                }
                students++;
            }
            if (sum > 10) {
                sum = 10;
            }
            Cell cell3 = row1.createCell(colNum++);
            cell3.setCellValue(sum / students);
        }

        /*Name rangeCell = book.getName("Група");
        String reference = sheet.getSheetName()+"!$A$"+ (2) + ":$A$"+(rowNum);
        rangeCell.setRefersToFormula(reference);

        rangeCell = book.getName("Середня оцінка");
        reference = sheet.getSheetName()+"!$B$"+ (2) + ":$B$"+(rowNum);
        rangeCell.setRefersToFormula(reference);*/
        //////build graphic

                        /* At the end of this step, we have a worksheet with test data, that we want to write into a chart */
                        /* Create a drawing canvas on the worksheet */
        XSSFDrawing xlsx_drawing = sheet.createDrawingPatriarch();
                        /* Define anchor points in the worksheet to position the chart */
        XSSFClientAnchor anchor = xlsx_drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);
                        /* Create the chart object based on the anchor point */
        XSSFChart my_line_chart = xlsx_drawing.createChart(anchor);
        my_line_chart.setTitleText("Середнє значення рейтингу по групах");
                        /* Define legends for the line chart and set the position of the legend */
        XSSFChartLegend legend = my_line_chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);

                        /* Create data for the chart */
        LineChartData data = my_line_chart.getChartDataFactory().createLineChartData();

                        /* Define chart AXIS */
        ChartAxis bottomAxis = my_line_chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = my_line_chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
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
        my_line_chart.plot(data, new ChartAxis[]{bottomAxis, leftAxis});

        try {
            book.write(os);
            book.write(new FileOutputStream("C://Users/Тарас/Downloads/repot1.xls"));
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
        List<Student> studentList = studentService.findAll();
        List<Group> groups = groupRepository.findAll();
        List<AvgReportByGroupDTO> avgReportByGroupDTOList = new ArrayList();
        int students = 0;
        int counter = 0;
        for (Group group : groups) {
            counter++;
            double sum = 0;
            students = 0;
            for (Student student : group.getStudent()) {
                List<Rating> ratings = ratingRepository.findByStudentId(student.getId());
                for (Rating rating : ratings) {
                    sum = sum + rating.getScore();
                }
                students++;
            }
            if (sum > 10) {
                sum = 10;
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
    public List<OverallReportByGroup> overallByGroup() {
        List<OverallReportByGroup> ratings = new ArrayList();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Student headOfGroup = studentRepository.findByUsername(name);
            List<Student>studentList = studentRepository.findAllStudentsByGroupId(headOfGroup.getGroup().getId());
            for (Student student : studentList){
                List<Rating>ratingList = ratingRepository.findByStudentId(student.getId());
                for(Rating rating : ratingList){
                    OverallReportByGroup reportByGroup = new OverallReportByGroup();
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
            List<Rating>ratingList = ratingRepository.findAll();
                for(Rating rating : ratingList){
                    OverallReportByGroup reportByGroup = new OverallReportByGroup();
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

