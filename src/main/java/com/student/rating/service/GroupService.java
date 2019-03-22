package com.student.rating.service;

import com.student.rating.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> groupList();
    List<String> getAllFacultyGroupNamesByFacultyName(String facultyName);
    Group findGroupByName(String groupName);
}
