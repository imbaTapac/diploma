package student_rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import student_rating.entity.Paragraph;

import javax.persistence.PersistenceContext;

/**
 * Created by Тарас on 31.05.2018.
 */
@Repository
@PersistenceContext
public interface ParagraphRepository extends JpaRepository<Paragraph,Long> {
}