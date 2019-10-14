package com.student.rating.service.impl;

import static com.student.rating.constants.Constants.APPROVED_BY_HEAD_OF_GROUP;
import static com.student.rating.constants.Constants.APPROVED_BY_HEAD_OF_SO;
import static com.student.rating.entity.Role.HEAD_OF_GROUP;
import static com.student.rating.entity.Role.HEAD_OF_SO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.student.rating.dto.AvgReportByGroupDTO;
import com.student.rating.dto.OverallReportByGroupDTO;
import com.student.rating.entity.Group;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Student;
import com.student.rating.exception.StudentRatingBaseException;
import com.student.rating.repository.GroupRepository;
import com.student.rating.repository.RatingRepository;
import com.student.rating.service.ReportService;
import com.student.rating.service.StudentService;

/**
 * Created by Тарас on 11.06.2018.
 */
@Service
public class ReportServiceImpl implements ReportService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);
	private static final String ERROR_WRITING_TO_FILE = "Error during writing data to file ";

	private final RatingRepository ratingRepository;
	private final StudentService studentService;
	private final GroupRepository groupRepository;

	@Autowired
	public ReportServiceImpl(RatingRepository ratingRepository,StudentService studentService, GroupRepository groupRepository) {
		this.ratingRepository = ratingRepository;
		this.studentService = studentService;
		this.groupRepository = groupRepository;
	}

	//TODO : in ver 0.9 code refactoring

	@Override
	public Resource reportByGroup() {
		XSSFWorkbook book = new XSSFWorkbook();

		XSSFSheet sheet = book.createSheet("Звіт по всіх групах та їх студентах");
		book.getProperties().getCoreProperties().setCreator("Tarasii");

		int rowNum = 0;
		Student currentStudent = studentService.getCurrentUser();
		List<Group> groups = new ArrayList<>();
		if(currentStudent.getRole() == HEAD_OF_GROUP) {
			groups = Collections.singletonList(groupRepository.findGroupById(currentStudent.getGroup().getId()).orElseThrow(() -> new StudentRatingBaseException(404, "Undefined group")));
		}
		else if(currentStudent.getRole() == HEAD_OF_SO) {
			groups = groupRepository.findAll();
		}
		for(Group group : groups) {
			if(groups.indexOf(group) != 0) {
				rowNum++;
			}
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			Cell cell0 = row.createCell(colNum++);
			cell0.setCellValue("Група " + group.getName());
			sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, colNum - 1, 2));
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

			for(Student student : group.getStudents()) {
				colNum = 0;
				double sum = 0;
				Row row2 = sheet.createRow(rowNum++);

				Cell cell4 = row2.createCell(colNum++);
				cell4.setCellValue(student.getStudentSurname());

				Cell cell5 = row2.createCell(colNum++);
				cell5.setCellValue(student.getStudentName());

				for(Rating rating : student.getRatings()) {
					sum = sum + rating.getScore();
				}

				if(sum > 10) {
					sum = 10;
				}
				if(sum < 0) {
					sum = 0;
				}

				Cell cell6 = row2.createCell(colNum++);
				cell6.setCellValue(sum);
			}

		}

		return createExcelFile(book);
	}

	@Override
	public Resource avgReportByGroups() {
		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet("Звіт по групах загальний");
		book.getProperties().getCoreProperties().setCreator("Tarasii");

		Student currentStudent = studentService.getCurrentUser();
		List<Group> groups = new ArrayList<>();
		if(currentStudent.getRole() == HEAD_OF_GROUP) {
			groups = Collections.singletonList(currentStudent.getGroup());
		}
		else if (currentStudent.getRole() == HEAD_OF_SO) {
			groups = groupRepository.findAll();
		}
		int rowNum = 0;
		int colNum = 0;
		Row row = sheet.createRow(rowNum++);

		Cell cell0 = row.createCell(colNum++);
		cell0.setCellValue("Група");

		Cell cell1 = row.createCell(colNum++);
		cell1.setCellValue("Середня оцінка");
		sheet.autoSizeColumn(cell1.getColumnIndex());

		for(Group group : groups) {
			colNum = 0;
			double groupSum = 0;
			int students = 0;
			Row row1 = sheet.createRow(rowNum++);
			Cell cell2 = row1.createCell(colNum++);
			cell2.setCellValue(group.getName());

			for(Student student : group.getStudents()) {
				double sum = 0;
				for(Rating rating : student.getRatings()) {
					sum = sum + rating.getScore();
				}
				students++;
				if(sum > 10) {
					sum = 10;
				}
				if(sum < 0) {
					sum = 0;
				}
				groupSum += sum;
			}

			double summary = (groupSum / students) > 0 ? Math.round(groupSum / students) : 0;
			//LOG.debug("Students : {}", students);
			//LOG.debug("Summary : {}", (summary > 0) ? Math.round(summary) : 0);
			Cell cell3 = row1.createCell(colNum++);
			cell3.setCellValue(summary);
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

		return createExcelFile(book);
	}

	@Override
	public List<AvgReportByGroupDTO> avgReportByGroup() {
		List<AvgReportByGroupDTO> avgReportByGroupDTOList = new ArrayList<>();
		int students;
		int counter = 0;
		List<Group> groups = groupRepository.findAll();
		for(Group group : groups) {
			counter++;
			double sum = 0;
			students = 0;
			for(Student student : group.getStudents()) {
				for(Rating rating : student.getRatings()) {
					sum = sum + rating.getScore();
				}
				students++;
			}
			if(sum > 10) {
				sum = 10;
			}
			if(sum < 0) {
				sum = 0;
			}
			double summary = sum / students;
			AvgReportByGroupDTO avgReportByGroupDTO = new AvgReportByGroupDTO();
			avgReportByGroupDTO.setNumber(counter);
			avgReportByGroupDTO.setGroupName(group.getName());
			avgReportByGroupDTO.setScore((double) (summary > 0 ? Math.round(summary) : 0));
			avgReportByGroupDTOList.add(avgReportByGroupDTO);
		}
		return avgReportByGroupDTOList;
	}

	//TODO : in ver_1.1 make report by month
	@Override
	public List<OverallReportByGroupDTO> overallByGroup() {
		List<OverallReportByGroupDTO> ratings = new ArrayList<>();
		Student currentStudent = studentService.getCurrentUser();
		if(currentStudent.getRole() == HEAD_OF_GROUP) {
			List<Student> studentList = studentService.findAllStudentsByStudentGroupId(currentStudent);
			for(Student student : studentList) {
				student.setRatings(ratingRepository.findAllRatingsByStudentIdAndStageOfApprove(student.getId(), APPROVED_BY_HEAD_OF_GROUP));
				for(Rating rating : student.getRatings()) {
					OverallReportByGroupDTO reportByGroup = buildOverallReport(rating);
					ratings.add(reportByGroup);
				}

			}
		}
		else if((currentStudent.getRole() == HEAD_OF_SO)) {
			List<Rating> ratingList = ratingRepository.findAllRatingsByStageOfApprove(APPROVED_BY_HEAD_OF_SO);
			for(Rating rating : ratingList) {
				OverallReportByGroupDTO reportByGroup = buildOverallReport(rating);
				ratings.add(reportByGroup);
			}
		}
		return ratings;
	}

	private OverallReportByGroupDTO buildOverallReport(Rating rating) {
		OverallReportByGroupDTO reportByGroup = new OverallReportByGroupDTO();
		reportByGroup.setIdStudent(rating.getStudent().getId());
		reportByGroup.setStudentName(rating.getStudent().getStudentName());
		reportByGroup.setStudentSurname(rating.getStudent().getStudentSurname());
		reportByGroup.setGroupName(rating.getStudent().getGroup().getName());
		reportByGroup.setBlockName(rating.getParagraph().getSubblock().getBlock().getName());
		reportByGroup.setSubblockName(rating.getParagraph().getSubblock().getName());
		reportByGroup.setParagraphName(rating.getParagraph().getName());
		reportByGroup.setScore(rating.getScore());
		reportByGroup.setComment(rating.getComment());
		return reportByGroup;
	}

	private Resource createExcelFile(Workbook workbook) {
		try(ByteArrayOutputStream out = new ByteArrayOutputStream();
		    Workbook excel = workbook) {
			excel.write(out);
			return new ByteArrayResource(out.toByteArray());
		} catch(IOException e) {
			LOG.error("Error while writing excel file");
			throw new StudentRatingBaseException(500, ERROR_WRITING_TO_FILE + "for " + workbook.getSheetName(0),e);
		}
	}
}

