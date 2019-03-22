package com.student.rating.service.impl;

import com.student.rating.entity.Faculty;
import com.student.rating.entity.Group;
import com.student.rating.entity.Student;
import com.student.rating.repository.GroupRepository;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.GroupService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.student.rating.utility.StaticDataEngine.FACULTY_LIST;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Group> groupList() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        DateTime currentDate = new DateTime();
        DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Student student = studentRepository.findByUsername(name);
            Long groupId = student.getGroup().getId();
            List<Student> list = studentRepository.findAllStudentsInGroupWithRatings(groupId, monthStart.toDate(), monthEnd.toDate());
            Group group = student.getGroup();
            group.setStudents(list);
            return Collections.singletonList(group);
        } else {
            List<Group> groups = groupRepository.findAll();
            for (Group group : groups) {
                List<Student> students = studentRepository.findAllStudentsInGroupWithRatings(group.getId(), monthStart.toDate(), monthEnd.toDate());
                group.setStudents(students);
            }
            return groups;
        }
    }

    @Override
    public List<String> getAllFacultyGroupNamesByFacultyName(String facultyName) {
        Faculty studentFaculty = new Faculty();
        for (Faculty faculty : FACULTY_LIST) {
            if (faculty.getName().equals(facultyName)) {
                studentFaculty = faculty;
            }
        }
        LOG.debug("Founded faculty {}" , studentFaculty);
        List<String> groupNames = new ArrayList<>();
        for (Group group : studentFaculty.getGroup()) {
            groupNames.add(group.getName());
        }
        LOG.debug("Founded groups {}" , groupNames);
        return groupNames;
    }

    @Override
    public Group findGroupByName(String groupName) {
        return groupRepository.findGroupByName(groupName);
    }

}
