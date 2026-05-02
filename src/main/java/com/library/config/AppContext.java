package com.library.config;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.library.db.ItemDao;
import com.library.db.LoanDao;
import com.library.db.ReservationDao;
import com.library.db.ReturnDao;
import com.library.db.SearchItemDao;
import com.library.db.UserDao;
import com.library.db.impl.ItemDaoImpl;
import com.library.db.impl.LoanDaoImpl;
import com.library.db.impl.ReservationDaoImpl;
import com.library.db.impl.SearchItemDaoImpl;
import com.library.db.impl.ReturnDaoImpl;
import com.library.db.impl.UserDaoImpl;
import com.library.service.ItemService;
import com.library.service.LoanService;
import com.library.service.ReservationService;
import com.library.service.ReturnCopyService;
import com.library.service.SearchService;
import com.library.service.UserService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class AppContext {

    private static final DataSource dataSource = buildDataSource();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    // DAOs
    public final UserDao userDao = new UserDaoImpl(jdbcTemplate);
    public final ItemDao itemDao = new ItemDaoImpl(jdbcTemplate);
    public final SearchItemDao searchItemDao = new SearchItemDaoImpl(jdbcTemplate);
    public final ReservationDao reservationDao = new ReservationDaoImpl(jdbcTemplate);
    public final LoanDao loanDao = new LoanDaoImpl(jdbcTemplate);
    public final ReturnDao returnDao = new ReturnDaoImpl(jdbcTemplate);

    // Services
    public final UserService userService = new UserService(userDao);
    public final SearchService searchService = new SearchService(searchItemDao);
    public final ItemService itemService = new ItemService(itemDao);
    public final ReservationService reservationService = new ReservationService(reservationDao);
    public final ReturnCopyService returnCopyService = new ReturnCopyService(returnDao);
    public final LoanService loanService = new LoanService(loanDao);
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
