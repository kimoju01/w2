package com.study.jdbcex2.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

// HikariConfig를 이용해서 하나의 HikariDataSource를 구성한다.
// 구성된 HikariDataSource는 getConnection()을 통해 사용한다.
// ConnectionUtil.INSTANCE.getConnection()을 통해 Connection을 얻는다.
public enum ConnectionUtil {

    INSTANCE;

    private HikariDataSource ds;

    ConnectionUtil() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver"); // 이 코드 없으니까 안되네
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("webuser");
        config.setPassword("webuser");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);

    }

    public Connection getConnection() throws Exception {
        return ds.getConnection();
    }
}
