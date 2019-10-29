package com.student.rating.service.impl;

import static com.student.rating.constants.Constants.FACULTY_ATTRIBUTE;
import static com.student.rating.constants.Constants.IT_FACULTY;
import static com.student.rating.constants.Constants.ORGANIZATIONAL_PERSON;
import static com.student.rating.constants.Constants.ROLE_ATTRIBUTE;
import static com.student.rating.utils.StaticDataEngine.FACULTY_LIST;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.rating.context.LDAPAttributesContextHolder;
import com.student.rating.entity.Faculty;
import com.student.rating.entity.Specialty;
import com.student.rating.repository.SpecialtyRepository;
import com.student.rating.service.SpecialtyService;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

	private static final Logger LOG = LoggerFactory.getLogger(SpecialtyServiceImpl.class);

	private final LDAPAttributesContextHolder ldapAttributesContextHolder;
	private final SpecialtyRepository specialtyRepository;

	@Autowired
	public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, LDAPAttributesContextHolder ldapAttributesContextHolder) {
		this.specialtyRepository = specialtyRepository;
		this.ldapAttributesContextHolder = ldapAttributesContextHolder;
	}

	@Override
	public List<String> getAllSpecialtyNamesByFacultyName() {
		List<Attributes> attributeList = ldapAttributesContextHolder.getAttributes();
		String studentFacultyName = "";
		for(Attributes attributes : attributeList) {
			try {
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
		List<Specialty> facultySpecialties = specialtyRepository.findByFacultyId(studentFaculty.getId());
		List<String> specialtyNames = new ArrayList<>();
		for(Specialty specialty : facultySpecialties) {
			specialtyNames.add(specialty.getName());
		}
		return specialtyNames;
	}
}
