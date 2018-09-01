/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Interfaces.IGet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Interfaces.ISet;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author andrew
 * @param <T>
 */
public class Table<T> {
    private final String tableName;
    private final Connection connection;
    
    /**
     * 
     * @param conn
     * @param name
     */
    
    public Table(Connection conn, String name) {
        this.connection = conn;
        this.tableName = name;
    }
    
    public List<T> getAll(String columns, IGet<T> getter) {
        try (Statement stm = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, 
                        ResultSet.CONCUR_READ_ONLY)) {
            ResultSet res = stm.executeQuery("SELECT " + columns
                + " FROM " + this.tableName);
            List<T> list = FatchData(res, getter);
            stm.close();
            return list;
        } catch (SQLException ex) {
            return null;
        }
    }
    public List<T> getWhere(String columns, String condition, IGet<T> getter) {
        try (Statement stm = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, 
                        ResultSet.CONCUR_READ_ONLY)) {
            ResultSet res = stm.executeQuery("SELECT " + columns
                + " FROM " + this.tableName + " WHERE " + condition);
            List<T> list = FatchData(res, getter);
            stm.close();
            return list;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public int insertOne (String columns, ISet setter) {
        String sql = "INSERT INTO "+ tableName +" ("+ columns +")"
                + " VALUES "+ generateStringValue(columns);
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement = setter.setValue(statement);
            
            if(statement == null)
                return -1;
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted;
        } catch (SQLException ex) {
            return -1;
        }
    }
    
    //Update
    public int update (String columns, String condition,  ISet setter) {
        String sql = "UPDATE "+ tableName +" SET "+ columns
                + " WHERE "+ condition;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement = setter.setValue(statement);
            
            if(statement == null)
                return -1;
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted;
        } catch (SQLException ex) {
            return -1;
        }
    }
    
    //Delete
    public int delete (String condition, ISet setter) {
        String sql = "DELETE FROM "+ tableName +" WHERE "+ condition;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement = setter.setValue(statement);
            
            if(statement == null)
                return -1;
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted;
        } catch (SQLException ex) {
            return -1;
        }
    }
    
    private String generateStringValue(String columns){
        int count = (int) columns.chars().filter(c -> c == ',').count();
        String values = "(?";
        for(int i = 0; i < count; i++){
            values += ", ?";
        }
        values += ")";
        return values;
    }
    
    private List<T> FatchData(ResultSet res, IGet<T> getter) throws SQLException{
        List<T> list = new ArrayList<>();

        while (res.next()) {
            T row = getter.getValue(res);

            if(row != null)
                list.add(row);
        }
        
        return list;
    }
}
