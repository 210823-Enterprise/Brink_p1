package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {{
    if (System.getenv("RDS_HOSTNAME") != null) {
        try {
        Class.forName("org.postgresql.Driver");
        String dbName = System.getenv("postgres 3");
        String userName = System.getenv("postgres");
        String password = System.getenv("postgres");
        String hostname = System.getenv("team-6-ncc.cvtq9j4axrge.us-east-1.rds.amazonaws.com");
        String port = System.getenv("5432");
        String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
       // logger.trace("Getting remote connection with connection string from environment variables.");
        Connection con = DriverManager.getConnection(jdbcUrl);
       // logger.info("Remote connection successful.");
      }
      catch (ClassNotFoundException e) {
    	  System.out.println("fail");
    	  }// logger.warn(e.toString());}
      catch (SQLException e) {
    	  System.out.println("fail");// logger.warn(e.toString());}
      }

    }
}}
