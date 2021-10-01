package ua.aleksanid.omegaapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.aleksanid.omegaapp.configurations.SecurityProperties;

@Service
public class SecurityService {
    private final Logger logger = LogManager.getLogger(SecurityService.class.getName());

    private final SecurityProperties securityProperties;

    public SecurityService(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public boolean validateToken(String token) {
        if (token != null) {
            try {
                JWT.require(Algorithm.HMAC512(securityProperties.getSecret().getBytes()))
                        .build()
                        .verify(token.replace(securityProperties.getTokenPrefix(), ""));
                return true;
            } catch (TokenExpiredException exp) {
                logger.error("Token expired ", exp);
                return false;
            }
        }
        return false;
    }
}
