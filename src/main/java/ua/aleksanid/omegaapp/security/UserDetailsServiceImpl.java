package ua.aleksanid.omegaapp.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.aleksanid.omegaapp.daos.UserDAO;
import ua.aleksanid.omegaapp.entities.User;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    private final UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userDAO.getByLogin(login);
        if (user == null) {
            logger.error("Failed auth with username: {}", login);
            throw new UsernameNotFoundException(login);
        }
        return new AuthUser(user.getLogin(), user.getPassword(), Collections.emptyList(), user.getId());
    }
}
