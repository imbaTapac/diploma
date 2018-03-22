package student_rating.DAO;

import org.springframework.stereotype.Repository;
import student_rating.entity.Block;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Тарас on 07.03.2018.
 */
@Repository
public class StaticDataDAOImpl implements StaticDataDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addBlock(Block block) {
        entityManager.persist(block);
    }

}
