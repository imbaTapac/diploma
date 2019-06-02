package com.student.rating.service.impl;

import static com.student.rating.constants.Constants.FACULTY_ATTRIBUTE;
import static com.student.rating.constants.Constants.IT_FACULTY;
import static com.student.rating.constants.Constants.ORGANIZATIONAL_PERSON;
import static com.student.rating.constants.Constants.ROLE_ATTRIBUTE;
import static com.student.rating.constants.Constants.USERNAME_ATTRIBUTE;
import static com.student.rating.utility.StaticDataEngine.FACULTY_LIST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.student.rating.context.LDAPAttributesContextHolder;
import com.student.rating.entity.Faculty;
import com.student.rating.entity.Group;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Role;
import com.student.rating.entity.Student;
import com.student.rating.exception.StudentRatingBaseException;
import com.student.rating.repository.GroupRepository;
import com.student.rating.repository.RatingRepository;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	private static final Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);

	private final GroupRepository groupRepository;
	private final StudentRepository studentRepository;
	private final RatingRepository ratingRepository;
	private final LDAPAttributesContextHolder ldapAttributesContextHolder;

	@Autowired
	public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository, LDAPAttributesContextHolder ldapAttributesContextHolder,
	                        RatingRepository ratingRepository) {
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
		this.ratingRepository = ratingRepository;
		this.ldapAttributesContextHolder = ldapAttributesContextHolder;
	}

	@Override
	public List<Group> groupList() {
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		DateTime currentDate = new DateTime();
		DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
		DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
		if(role.equals(Role.HEAD_OF_GROUP.getFullAuthority())) {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Student headOfGroup = studentRepository.findByUsername(name);
			Group group = groupRepository.findGroupById(headOfGroup.getGroup().getId()).orElseThrow(() -> new StudentRatingBaseException(404, "Wrong group id"));
			for(Student student : group.getStudents()) {
				List<Rating> ratings = ratingRepository.findAllRatingsByStudentIdAndDateBetween(student.getId(), monthStart.toDate(), monthEnd.toDate());
				student.setRatings(ratings);
			}
			return Collections.singletonList(group);
		} else {
			List<Group> groups = groupRepository.findAll();
			for(Group group : groups) {
				for(Student student : group.getStudents()) {
					List<Rating> ratings = ratingRepository.findAllRatingsByStudentIdAndDateBetween(student.getId(), monthStart.toDate(), monthEnd.toDate());
					student.setRatings(ratings);
				}
			}
			return groups;
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

}
