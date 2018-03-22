package student_rating.DAO;

import org.springframework.stereotype.Repository;
import student_rating.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Тарас on 06.03.2018.
 */
@Repository
public class RatingDAOImp implements RatingDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addRating(Rating rating) {
        entityManager.merge(rating);
    }
}
