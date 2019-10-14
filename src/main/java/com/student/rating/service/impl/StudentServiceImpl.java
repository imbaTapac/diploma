package com.student.rating.service.impl;

import static com.student.rating.constants.Constants.APPROVED_BY_HEAD_OF_GROUP;
import static com.student.rating.constants.Constants.EMAIL_ATTRIBUTE;
import static com.student.rating.constants.Constants.ID_ATTRIBUTE;
import static com.student.rating.constants.Constants.OKR_ATTRIBUTE;
import static com.student.rating.constants.Constants.ROLE_ATTRIBUTE;
import static com.student.rating.constants.Constants.STUDENT_FULL_NAME_ATTRIBUTE;
import static com.student.rating.constants.Constants.UNAPPROVED;
import static com.student.rating.constants.Constants.USERNAME_ATTRIBUTE;
import static com.student.rating.entity.Role.HEAD_OF_GROUP;
import static com.student.rating.entity.Role.HEAD_OF_SO;
import static com.student.rating.utils.StaticDataEngine.OKR_LIST;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.student.rating.context.LDAPAttributesContextHolder;
import com.student.rating.context.StudentContextHolder;
import com.student.rating.dto.StudentProfileDTO;
import com.student.rating.dto.UserDTO;
import com.student.rating.encoder.ShaPasswordEncoder;
import com.student.rating.entity.Group;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Role;
import com.student.rating.entity.Specialty;
import com.student.rating.entity.Student;
import com.student.rating.exception.StudentRatingBaseException;
import com.student.rating.mapper.StudentProfileDTOMapper;
import com.student.rating.repository.GroupRepository;
import com.student.rating.repository.RatingRepository;
import com.student.rating.repository.SpecialtyRepository;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.StudentService;
import com.student.rating.utils.DateTimeUtils;

/**
 * Created by Тарас on 01.03.2018.
 */
@Service
public class StudentServiceImpl implements StudentService {

	@Value("${role.head-of-group.code}")
	private String headOfGroup;
	@Value("${role.head-of-so.code}")
	private String headOfSo;

	private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

	private final StudentRepository studentRepository;
	private final LdapTemplate ldapTemplate;
	private final ShaPasswordEncoder passwordEncoder;
	private final GroupRepository groupRepository;
	private final SpecialtyRepository specialtyRepository;
	private final LDAPAttributesContextHolder ldapAttributesContextHolder;
	private final StudentContextHolder studentContextHolder;
	private final StudentProfileDTOMapper studentProfileDTOMapper;
	private final RatingRepository ratingRepository;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, LdapTemplate ldapTemplate, ShaPasswordEncoder shaPasswordEncoder, GroupRepository groupRepository,
	                          SpecialtyRepository specialtyRepository, LDAPAttributesContextHolder ldapAttributesContextHolder, StudentContextHolder studentContextHolder,
	                          StudentProfileDTOMapper studentProfileDTOMapper,RatingRepository ratingRepository) {
		this.studentRepository = studentRepository;
		this.ldapTemplate = ldapTemplate;
		this.passwordEncoder = shaPasswordEncoder;
		this.groupRepository = groupRepository;
		this.specialtyRepository = specialtyRepository;
		this.ldapAttributesContextHolder = ldapAttributesContextHolder;
		this.studentContextHolder = studentContextHolder;
		this.studentProfileDTOMapper = studentProfileDTOMapper;
		this.ratingRepository = ratingRepository;
	}

	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public void addToSession(HttpSession session) {
		Student student = getCurrentUser();
		LOG.debug("Add student {} to session", student);
		session.setAttribute("student", student);
		LOG.debug("Student successfully added to session");
	}

	@Override
	public Student update(Student student) {
		return studentRepository.saveAndFlush(student);
	}

	@Override
	public Student findByUsername(String username) {
		return studentRepository.findByUsername(username);
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student findById(Long id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Override
	public List<Student> findAllStudentsByStudentGroupId(Student student) {
		List<Student> students = new ArrayList<>();
		if(student.getRole() == HEAD_OF_GROUP) {
			Group group = student.getGroup();
			students = studentRepository.findAllStudentsByGroupId(group.getId());
		} else if(student.getRole() == HEAD_OF_SO) {
			students = studentRepository.findAll();
		}
		return students;
	}

	@Override
	public List<Student> findStudentRatings() {
		Student student = getCurrentUser();
		List<Student> students = new ArrayList<>();
		DateTime currentDate = new DateTime();
		DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
		DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
		if(student.getRole() == HEAD_OF_GROUP) {
			Group group = student.getGroup();
			students = studentRepository.findAllStudentsByGroupIdAndRatingsStageOfApprove(group.getId(), UNAPPROVED, monthStart.toDate(), monthEnd.toDate());
		} else if(student.getRole() == HEAD_OF_SO) {
			students = studentRepository.findAllStudentsRatingsStageOfApprove(APPROVED_BY_HEAD_OF_GROUP, monthStart.toDate(), monthEnd.toDate());
		}
		return students;
	}

	@Override
	public boolean searchInLDAP(String userName, String email) {
		LOG.debug("Current thread is {}", Thread.currentThread().getName());
		LOG.debug("Start search user with login {} and email {} in LDAP directory", userName, email);
		List<Attributes> attributeList = new ArrayList<>();
		LOG.debug("Session id is {}", RequestContextHolder.currentRequestAttributes().getSessionId());
		if(isEmpty(ldapAttributesContextHolder.getAttributes())) {
			attributeList = ldapTemplate.search(query().base("ou=people").where("uid").is(userName).and("mail").is(email),
					(AttributesMapper<Attributes>) attributes -> {
						if(attributes == null) {
							return null;
						}
						return attributes;
					});
			ldapAttributesContextHolder.setAttributes(attributeList);
		}
		LOG.debug("Status of searching user with login {} and email {} in LDAP is {}", userName, email, (!isEmpty(attributeList)) ? "success" : "failed");
		if(nonNull(attributeList) && !isEmpty(attributeList)) {
			logAttributes(userName, attributeList);
		}

		return !isEmpty(ldapAttributesContextHolder.getAttributes());
	}

	@Override
	public Student createNewStudentFromLDAPAttribute() {
		Student student = new Student();
		List<Attributes> attributeList = ldapAttributesContextHolder.getAttributes();
		for(Attributes attributes : attributeList) {
			try {
				if(attributes.get(ID_ATTRIBUTE) != null) {
					student.setId(Long.parseLong(attributes.get(ID_ATTRIBUTE).get().toString()));
				}

				if(attributes.get(EMAIL_ATTRIBUTE) != null) {
					student.setEmail(attributes.get(EMAIL_ATTRIBUTE).get().toString());
				}

				if(attributes.get(USERNAME_ATTRIBUTE) != null) {
					student.setUsername(attributes.get(USERNAME_ATTRIBUTE).get().toString());
				}

				if(attributes.get(OKR_ATTRIBUTE) != null) {
					String okr = attributes.get(OKR_ATTRIBUTE).get().toString();
					boolean isBachelor = okr.equals("Бакалавр");
					boolean isMagister = okr.equals("Магістр");
					if(isBachelor) {
						student.setOkr(OKR_LIST.get(0));
					}
					else if(isMagister) {
						student.setOkr(OKR_LIST.get(1));
					}
				}

				if(attributes.get(STUDENT_FULL_NAME_ATTRIBUTE) != null) {
					String[] studentFullName = attributes.get(STUDENT_FULL_NAME_ATTRIBUTE).get().toString().split("\\s");
					student.setStudentSurname(studentFullName[0]);
					student.setStudentName(studentFullName[1]);
				}

				if(attributes.get(ROLE_ATTRIBUTE) != null) {
					String role = attributes.get(ROLE_ATTRIBUTE).get().toString();
					LOG.debug("Role is {}", role);
				}
			} catch(NamingException e) {
				LOG.error("Error while parsing LDAP attributes {}", e);
			}
		}

		LOG.debug("Result student is [{}]", student);
		studentContextHolder.setStudent(student);
		return student;
	}

	@Override
	public Student saveFromDTO(UserDTO userDTO) {
		Student student = studentContextHolder.getStudent();
		LOG.debug("Trying to save student {}", student);
		Specialty specialty = specialtyRepository.findSpecialtyByName(userDTO.getSpecialtyName());
		LOG.debug("Specialty is {}", specialty);
		Group group = groupRepository.findGroupByName(userDTO.getGroupName());
		LOG.debug("Group is {}", group);
		String roleCode = userDTO.getRoleCode();
		if(roleCode.equals(headOfGroup)) {
			student.setRole(HEAD_OF_GROUP);
		} else if(roleCode.equals(headOfSo)) {
			student.setRole(HEAD_OF_SO);
		} else {
			student.setRole(Role.STUDENT);
		}
		student.setSpecialty(specialty);
		student.setGroup(group);
		student.setCourse(parseInt(userDTO.getCourse()));
		student.setPhone(userDTO.getPhone());
		student.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		Student resultStudent = studentRepository.save(student);
		if(resultStudent.getPhone().equals(userDTO.getPhone())) {
			LOG.debug("Student {} \n was successfully saved to DB", resultStudent);
			LOG.debug("Destroying LDAP context holder");
			ldapAttributesContextHolder.destroy();
			if(isEmpty(ldapAttributesContextHolder.getAttributes())) {
				LOG.debug("LDAP context holder was successfully destroyed");
			}
			LOG.debug("Destroying Student context holder");
			studentContextHolder.destroy();
			if(isNull(studentContextHolder.getStudent())) {
				LOG.debug("Student context holder was successfully destroyed");
			}
		}
		return resultStudent;
	}

	@Override
	public Student getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(nonNull(authentication)) {
			return Optional.of(studentRepository.findByUsername(authentication.getName()))
					.orElseThrow(() -> new StudentRatingBaseException(404, String.format("User %s not found", authentication.getName())));
		} else {
			throw new StudentRatingBaseException(404, "No authenticated student exists");
		}
	}

	@Override
	public StudentProfileDTO getStudentProfile() {
		Student student = getCurrentUser();
		List<Rating> ratings = ratingRepository.findAllRatingsByStudentIdAndDateBetween(student.getId(), DateTimeUtils.getCurrentMonthStart(),DateTimeUtils.getCurrentMonthEnd());
		student.setRatings(ratings);
		//TODO need another algorithm
		StudentProfileDTO profileDTO = studentProfileDTOMapper.toDto(student);
		profileDTO.setFacultyName(student.getGroup().getFaculty().getName());
		return profileDTO;
	}

	private void logAttributes(String userName, List<Attributes> attributesList) {
		LOG.debug("Start parsing LDAP attributes of user {}", userName);
		for(Attributes attributes : attributesList) {
			for(Enumeration e = attributes.getAll(); e.hasMoreElements(); ) {
				LOG.debug("{}", e.nextElement());
			}
		}
		LOG.debug("All attribute was parsed.");
	}
}
