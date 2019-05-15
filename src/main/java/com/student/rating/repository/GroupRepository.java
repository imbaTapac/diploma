package com.student.rating.repository;

import java.util.Optional;

import com.student.rating.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT g FROM Group g WHERE g.id = :id")
    Optional<Group> findGroupById(@Param("id") Long id);
}
