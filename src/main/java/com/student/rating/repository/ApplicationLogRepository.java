package com.student.rating.repository;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.rating.entity.log.ApplicationLog;


/**
 * since @1.0 ver
 */

@Repository
@PersistenceContext
public interface ApplicationLogRepository extends JpaRepository<ApplicationLog,Long> {
}
