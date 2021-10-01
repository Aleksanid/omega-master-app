package ua.aleksanid.omegaapp.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.aleksanid.omegaapp.entities.User;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDAO extends JpaRepository<User,Long> {

    User getByLogin(String login);
}
