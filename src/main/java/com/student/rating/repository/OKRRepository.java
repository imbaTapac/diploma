package com.student.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.student.rating.entity.OKR;

import javax.persistence.PersistenceContext;

/**
 * Created by Тарас on 31.05.2018.
 */
@Repository
@PersistenceContext
public interface OKRRepository extends JpaRepository<OKR,Long> {
}
