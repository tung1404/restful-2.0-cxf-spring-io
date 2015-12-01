package vn.store.web.init;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@Profile("default")
@PropertySources({
    @PropertySource("classpath:application.properties")
})
public class MySQLConfig {

	@Autowired
	private Environment env;
	
	@Bean
    public DataSource mysqlDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.resolvePlaceholders("${jdbc.driverClassName}"));
        dataSource.setUrl(env.resolvePlaceholders("${jdbc.url}"));
        dataSource.setUsername(env.resolvePlaceholders("${jdbc.username}"));
        dataSource.setPassword(env.resolvePlaceholders("${jdbc.password}"));

        return dataSource;
    }
}
