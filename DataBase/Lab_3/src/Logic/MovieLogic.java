/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Database.Table;
import Models.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author andrew
 */
public class MovieLogic extends Table<Movie> {
    
    public MovieLogic(Connection conn, String name){
        super(conn, name);
    }
    
    public List<Movie> GetAll() {
        return super.getAll("*", this::SelectAll);
    }
    
    public List<Movie> FindMany(String condition){
        return super.getWhere("*", condition, this::SelectAll);
    }
    
    public Movie InsertOne(Movie movie){
        int rows = super.insertOne("mID, title, year, director", (PreparedStatement statement) -> {
            return SetAll(statement, movie);
        });
        if(rows == -1)
            return null;
        
        return movie;
    }
    
    public boolean UpdateOne(Integer id, Movie movie) {
        int result = super.update("mID=?, title=?, year=?, director=?", "mID=?", (PreparedStatement statement) -> {
            statement = SetAll(statement, movie);
            try {
                statement.setInt(5, id);
            } catch (SQLException ex) {
                return null;
            }
            return statement;
        });
        return result > 0;
    }
    
    public boolean DeleteOne(Integer id) {
        int result = super.delete("mID=?", (PreparedStatement statement) -> {
            try {
                statement.setInt(1, id);
            } catch (SQLException ex) {
                return null;
            }
            return statement;
        });
        return true;
    }
    
    private Movie SelectAll(ResultSet res) {
        try {
            Movie m = new Movie();
            m.setmID(res.getInt("mID"));
            m.setTitle(res.getString("title"));
            m.setYear(res.getInt("year"));
            m.setDirector(res.getString("director"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    /**
     * Set value by order -- mID, title, year, director
     * @param statement
     * @return 
     */
    private PreparedStatement SetAll(PreparedStatement statement, Movie movie) {
        try {
            statement.setInt(1, movie.getmID());
            statement.setString(2, movie.getTitle());
            statement.setInt(3, movie.getYear());
            statement.setString(4, movie.getDirector());
        } catch (SQLException ex) {
            return null;
        }
        return statement;
    }
}
