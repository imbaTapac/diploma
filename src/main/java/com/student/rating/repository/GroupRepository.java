package com.student.rating.repository;

import com.student.rating.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

/**
 * Created by Тарас on 31.05.2018.
 */
@Repository
@PersistenceContext
public interface GroupRepository extends JpaRepository<Group, Long> {
    /**
     * @param groupName
     * @return
     */
    Group findGroupByName(String groupName);
}
