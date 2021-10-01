package ua.aleksanid.omegaapp.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.aleksanid.omegaapp.entities.Giveaway;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface GiveawayDAO extends JpaRepository<Giveaway, Long> {
    boolean existsByName(String name);
}
