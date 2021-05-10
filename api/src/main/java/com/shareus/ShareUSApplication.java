package com.shareus;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class ShareUSApplication {

    public static DataSource DS;

    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://api.share-us.tech:5434/shareus");
        ds.setUser("dam");
        ds.setPassword("damshareus");
        DS = ds;
        SpringApplication.run(ShareUSApplication.class, args);
    }

}
