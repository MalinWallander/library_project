package com.library.config;

import com.library.db.UserDao;
import com.library.db.impl.ItemDAOImpl;
import com.library.db.impl.UserDaoImpl;
import com.library.db.ItemDao;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.library.service.SearchService;
import com.library.service.UserService;

import javax.sql.DataSource;

public class AppContext {

    private static final DataSource dataSource = buildDataSource();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    // DAOs
    public final UserDao userDao = new UserDaoImpl(jdbcTemplate);
    public final ItemDao itemDao= new ItemDAOImpl(jdbcTemplate);


    // Services
    public final UserService userService = new UserService(userDao);
    // I AppContext.java
     public final SearchService searchService = new SearchService(itemDao);

    // Singleton instance
    private static AppContext instance;

    public static AppContext getInstance() {
        if (instance == null)
            instance = new AppContext();
        return instance;
    }

    private static DataSource buildDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://aws-1-eu-west-1.pooler.supabase.com:6543/postgres");
        config.setUsername("postgres.urozmzhqlitadoryylfv");
        config.setPassword("Reapprove-Stamp-Scariness3");
        config.setMaximumPoolSize(5);
        config.addDataSourceProperty("sslmode", "require");
        return new HikariDataSource(config);
    }
}
