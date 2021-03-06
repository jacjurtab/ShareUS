package com.shareus;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class ShareUSApplication {

    public static DataSource DS;

    public static void main(String[] args) throws SQLException, IOException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        Map<String, String> env = System.getenv();
        String url;
        String user;
        String pass;
        
        if(!env.containsKey("URL")) {
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream is = classloader.getResourceAsStream("local.properties");
        	Properties prop = new Properties();
        	prop.load(is);
        	url = prop.getProperty("URL");
        	user = prop.getProperty("USER");
        	pass = prop.getProperty("PASS");
        } else {
        	url = env.get("URL");
            user = env.get("USER");
            pass = env.get("PASS");
        }
        ds.setUrl(url);
        ds.setUser(user);
        ds.setPassword(pass);
        DS = ds;
        SpringApplication.run(ShareUSApplication.class, args);
    }

}
