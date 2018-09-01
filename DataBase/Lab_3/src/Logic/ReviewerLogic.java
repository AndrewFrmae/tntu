/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Database.Table;
import Models.Reviewer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author andrew
 */
public class ReviewerLogic extends Table<Reviewer> {
    
    public ReviewerLogic(Connection conn, String name){
        super(conn, name);
    }
    
    public List<Reviewer> GetAll() {
        return super.getAll("*", this::SelectAll);
    }
    
    public List<Reviewer> FindMany(String condition){
        return super.getWhere("*", condition, this::SelectAll);
    }
    
    public Reviewer InsertOne(Reviewer reviewer){
        int rows = super.insertOne("rID, name", (PreparedStatement statement) -> {
            return SetAll(statement, reviewer);
        });
        if(rows == -1)
            return null;
        
        return reviewer;
    }
    
    public boolean UpdateOne(Integer id, Reviewer reviewer) {
        int result = super.update("rID=?, name=?", "rID=?", (PreparedStatement statement) -> {
            statement = SetAll(statement, reviewer);
            try {
                statement.setInt(3, id);
            } catch (SQLException ex) {
                return null;
            }
            return statement;
        });
        return result > 0;
    }
    
    public boolean DeleteOne(Integer id) {
        int result = super.delete("rID=?", (PreparedStatement statement) -> {
            try {
                statement.setInt(1, id);
            } catch (SQLException ex) {
                return null;
            }
            return statement;
        });
        return true;
    }
    
    private Reviewer SelectAll(ResultSet res) {
        try {
            Reviewer r = new Reviewer();
            r.setrID(res.getInt("rID"));
            r.setName(res.getString("name"));
            return r;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    /**
     * Set value by order -- mID, title, year, director
     * @param statement
     * @return 
     */
    private PreparedStatement SetAll(PreparedStatement statement, Reviewer reviewer) {
        try {
            statement.setInt(1, reviewer.getrID());
            statement.setString(2, reviewer.getName());
        } catch (SQLException ex) {
            return null;
        }
        return statement;
    }
}
