package com.student.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.student.rating.entity.Specialty;

import javax.persistence.PersistenceContext;

/**
 * Created by Тарас on 31.05.2018.
 */
@Repository
@PersistenceContext
public interface SpecialtyRepository extends JpaRepository<Specialty,Long> {
	/**
	 *
	 * @param specialtyName
	 * @return
	 */
	Specialty findSpecialtyByName(String specialtyName);

	/**
	 *
	 * @param id
	 * @return
	 */
	List<Specialty> findByFacultyId(Long id);
}
