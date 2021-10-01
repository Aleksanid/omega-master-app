package ua.aleksanid.omegaapp.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:security.properties")
@Getter
public class SecurityProperties {
    @Value("${security.secret}")
    private String secret;
    @Value("${security.expirationTime}")
    private long expirationTime;
    @Value("${security.tokenPrefix}")
    private String tokenPrefix;
    @Value("${security.headerString}")
    private String headerString;
}
