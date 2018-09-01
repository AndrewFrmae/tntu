/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Database.Table;
import Models.Movie;
import Models.Rating;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author andrew
 */
public class RatingLogic extends Table<Rating> {
    
    public RatingLogic(Connection conn, String name){
        super(conn, name);
    }
    
    public List<Rating> GetAll() {
        return super.getAll("*", this::SelectAll);
    }
    
    public List<Rating> FindMany(String condition){
        return super.getWhere("*", condition, this::SelectAll);
    }
    
    public Rating InsertOne(Rating rating){
        int rows = super.insertOne("rID, mID, stars, ratingDate", (PreparedStatement statement) -> {
            return SetAll(statement, rating);
        });
        if(rows == -1)
            return null;
        
        return rating;
    }
    
    public boolean UpdateOne(Integer mID, Integer rID, Rating rating) {
        int result = super.update("rID=?, mID=?, stars=?, ratingDate=?", "mID=? and rID=?", (PreparedStatement statement) -> {
            statement = SetAll(statement, rating);
            try {
                statement.setInt(5, mID);                
                statement.setInt(6, rID);
            } catch (SQLException ex) {
                return null;
            }
            return statement;
        });
        return result > 0;
    }
    
    public boolean DeleteOne(Integer mID, Integer rID) {
        int result = super.delete("mID=? and rID=?", (PreparedStatement statement) -> {
            try {
                statement.setInt(1, mID);
                statement.setInt(2, rID);                
            } catch (SQLException ex) {
                return null;
            }
            return statement;
        });
        return true;
    }
    
    private Rating SelectAll(ResultSet res) {
        try {
            Rating r = new Rating();
            r.setmID(res.getInt("mID"));
            r.setrID(res.getInt("rID"));
            r.setStars(res.getInt("stars"));
            r.setRatingDate(res.getDate("ratingDate"));
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
    private PreparedStatement SetAll(PreparedStatement statement, Rating rating) {
        try {
            statement.setInt(1, rating.getrID());
            statement.setInt(2, rating.getmID());
            statement.setInt(3, rating.getStars());
            statement.setDate(4, rating.getRatingDate());
        } catch (SQLException ex) {
            return null;
        }
        return statement;
    }
}
