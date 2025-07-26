package edu.ijse.BakeTrack.dao;

import edu.ijse.BakeTrack.db.DBobject;

import java.sql.*;

public class SqlExecute {

    public static  <T>T SqlExecute(String sqlLine,Object... objects){
        PreparedStatement statement= null;
        try {
            Connection connection= DBobject.getInstance().getConnection();
            statement = connection.prepareStatement(sqlLine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(int i=0;i<objects.length;i++){
            try {
                if(objects[i]==null){
                    statement.setNull(i+1, Types.NULL);
                }else{
                    statement.setObject(i+1,objects[i]);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

         if(sqlLine.startsWith("SELECT") || sqlLine.startsWith("select")){
             ResultSet resultSet= null;
             try {
                 resultSet = statement.executeQuery();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
             return (T) resultSet;
         }
         else{
             boolean done= false;
             try {
                 done = statement.executeUpdate()>0;
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
             return (T)(Boolean) done;
         }
    }
}
