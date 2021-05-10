package com.shareus;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@SpringBootApplication
public class ShareUSApplication {

    public static DataSource DS;

    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        Map<String, String> env = System.getenv();
        ds.setUrl(env.get("URL"));
        ds.setUser(env.get("USER"));
        ds.setPassword(env.get("PASS"));
//        ds.setUrl("jdbc:postgresql://api.share-us.tech:5434/shareus");
//        ds.setUser("dam");
//        ds.setPassword("damshareus");
        DS = ds;
        SpringApplication.run(ShareUSApplication.class, args);
    }

}
