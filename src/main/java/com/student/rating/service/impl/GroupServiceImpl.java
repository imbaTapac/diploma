package com.student.rating.service.impl;

import static com.student.rating.constants.Constants.FACULTY_ATTRIBUTE;
import static com.student.rating.constants.Constants.IT_FACULTY;
import static com.student.rating.constants.Constants.ORGANIZATIONAL_PERSON;
import static com.student.rating.constants.Constants.ROLE_ATTRIBUTE;
import static com.student.rating.constants.Constants.USERNAME_ATTRIBUTE;
import static com.student.rating.entity.Role.HEAD_OF_GROUP;
import static com.student.rating.utils.StaticDataEngine.FACULTY_LIST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.rating.context.LDAPAttributesContextHolder;
import com.student.rating.dto.GroupDTO;
import com.student.rating.dto.StudentDTO;
import com.student.rating.entity.Faculty;
import com.student.rating.entity.Group;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Student;
import com.student.rating.exception.StudentRatingBaseException;
import com.student.rating.mapper.StudentDTOMapper;
import com.student.rating.repository.GroupRepository;
import com.student.rating.repository.RatingRepository;
import com.student.rating.service.GroupService;
import com.student.rating.service.StudentService;
import com.student.rating.utils.DateTimeUtils;

@Service
public class GroupServiceImpl implements GroupService {

	private static final Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);

	private final GroupRepository groupRepository;
	private final StudentService studentService;
	private final StudentDTOMapper studentDTOMapper;
	private final RatingRepository ratingRepository;
	private final LDAPAttributesContextHolder ldapAttributesContextHolder;

	@Autowired
	public GroupServiceImpl(GroupRepository groupRepository, StudentService studentService, LDAPAttributesContextHolder ldapAttributesContextHolder,
	                        RatingRepository ratingRepository, StudentDTOMapper studentDTOMapper) {
		this.groupRepository = groupRepository;
		this.ratingRepository = ratingRepository;
		this.ldapAttributesContextHolder = ldapAttributesContextHolder;
		this.studentDTOMapper = studentDTOMapper;
		this.studentService = studentService;
	}

	@Override
	public List<GroupDTO> findAllGroupsWithStudents() {
		Student currentStudent = studentService.getCurrentUser();
		Date monthStart = DateTimeUtils.getCurrentMonthStart();
		Date monthEnd = DateTimeUtils.getCurrentMonthEnd();
		if(currentStudent.getRole() == HEAD_OF_GROUP) {
			List<Student> students = studentService.findAllStudentsByStudentGroupId(currentStudent);
			Group group = groupRepository.findGroupById(currentStudent.getGroup().getId()).orElseThrow(() -> new StudentRatingBaseException(404, "Wrong group id"));
			group.setStudents(students);
			for(Student student : group.getStudents()) {
				fetchStudentRatings(student, monthStart, monthEnd);
			}
			GroupDTO groupDTO = mapGroupToDTO(group);
			return Collections.singletonList(groupDTO);
		} else {
			List<Group> groups = groupRepository.findAll();
			List<GroupDTO> groupDTOS = new ArrayList<>();
			for(Group group : groups) {
				for(Student student : group.getStudents()) {
					fetchStudentRatings(student, monthStart, monthEnd);
				}
				GroupDTO groupDTO = mapGroupToDTO(group);
				groupDTOS.add(groupDTO);
			}
			return groupDTOS;
		}
	}

	@Override
	public List<String> getAllFacultyGroupNamesByFacultyName() {
		String studentFacultyName = "";
		LOG.debug("Current thread is {}", Thread.currentThread().getName());
		LOG.debug("LDAPContextHolder size {}", ldapAttributesContextHolder.getAttributes().size());
		List<Attributes> attributeList = ldapAttributesContextHolder.getAttributes();
		for(Attributes attributes : attributeList) {
			try {
				if(attributes.get(USERNAME_ATTRIBUTE) != null) {
					LOG.debug("In thread {} LDAP user name is {}", Thread.currentThread().getName(), attributes.get(USERNAME_ATTRIBUTE).get());
				}
				if(attributes.get(FACULTY_ATTRIBUTE) != null &&
						attributes.get(ROLE_ATTRIBUTE) != null &&
						!attributes.get(ROLE_ATTRIBUTE).get().toString().equals(ORGANIZATIONAL_PERSON)
				) {
					studentFacultyName = attributes.get(FACULTY_ATTRIBUTE).get().toString();
					studentFacultyName = studentFacultyName.contains(IT_FACULTY.toLowerCase()) ? IT_FACULTY : studentFacultyName;
					LOG.debug("Student faculty is {}", studentFacultyName);
				}
			} catch(NamingException e) {
				LOG.error("Error while parsing LDAP attributes. {}", e);
			}
		}

		Faculty studentFaculty = new Faculty();
		for(Faculty faculty : FACULTY_LIST) {
			if(faculty.getName().equals(studentFacultyName)) {
				studentFaculty = faculty;
			}
		}

		List<String> groupNames = new ArrayList<>();
		for(Group group : studentFaculty.getGroup()) {
			groupNames.add(group.getName());
		}

		return groupNames;
	}

	private GroupDTO mapGroupToDTO(Group group) {
		List<StudentDTO> studentDTOS = studentDTOMapper.toDTOs(group.getStudents());
		for(StudentDTO s : studentDTOS) {
			s.filterRatings();
		}
		return new GroupDTO(group, studentDTOS);
	}

	private void fetchStudentRatings(Student student, Date monthStart, Date monthEnd) {
		List<Rating> ratings = ratingRepository.findAllRatingsByStudentIdAndDateBetween(student.getId(), monthStart, monthEnd);
		student.setRatings(ratings);
	}
}
