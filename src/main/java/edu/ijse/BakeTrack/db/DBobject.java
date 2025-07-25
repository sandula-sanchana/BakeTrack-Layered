package edu.ijse.BakeTrack.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBobject{
     private static DBobject obo;
     private Connection connection;

     private DBobject() {
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PROJECT_BAKERY", "sandula", "92540010");
         } catch (ClassNotFoundException | SQLException e) {
             System.err.println(e.getMessage());
             throw new RuntimeException(e);


         }
     }
         public static DBobject getInstance () {
             if (obo == null) {
                 obo = new DBobject();
             }
             return obo;
         }

         public Connection getConnection () {
             return this.connection;
         }

     }