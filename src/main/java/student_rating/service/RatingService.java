package student_rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import student_rating.DAO.BlockDAO;
import student_rating.DAO.RatingDAO;
import student_rating.DAO.StaticDataDAO;
import student_rating.entity.Block;
import student_rating.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Тарас on 06.03.2018.
 */
@Service
public class RatingService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    BlockDAO blockDAO;
    @Autowired
    RatingDAO ratingDAO;
    @Autowired
    StaticDataDAO staticDataDAO;

    @Transactional
    public void add(Block block){
        staticDataDAO.addBlock(block);
    }

    @Transactional
     public void addRating(Rating rating){
        ratingDAO.addRating(rating);
    }
}
