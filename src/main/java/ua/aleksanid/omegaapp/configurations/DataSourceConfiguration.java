package ua.aleksanid.omegaapp.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfiguration {
}
