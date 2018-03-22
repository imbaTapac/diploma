package student_rating.DAO;

/**
 * Created by Тарас on 01.03.2018.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import student_rating.entity.Roles;

public interface RoleDAO extends JpaRepository<Roles, Long> {
}

