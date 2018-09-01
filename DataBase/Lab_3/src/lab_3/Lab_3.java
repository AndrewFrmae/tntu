/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_3;

import Database.DataBase;
import Logic.*;
import Models.*;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author andrew
 */
public class Lab_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DataBase db = new DataBase("andrew", "andrew", "lab3", "3306");
        MovieLogic movies = new MovieLogic(db.getConnection(), "Movie");
        RatingLogic ratings = new RatingLogic(db.getConnection(), "Rating");
        ReviewerLogic reviewers = new ReviewerLogic(db.getConnection(), "Reviewer");
        
        
        
        
        System.out.println("======= Movie =======");
        Movie mov = new Movie();
        mov.setmID(1);
        mov.setTitle("title");
        mov.setDirector("director");
        mov.setYear(2018);
        
        List<Movie> resM = movies.GetAll();
        System.out.println("Size: " + resM.size());
        movies.InsertOne(mov);
        resM = movies.GetAll();
        System.out.println("Director: " + resM.get(0).getDirector());
        mov.setDirector("Andrew");
        movies.UpdateOne(1, mov);
        resM = movies.GetAll();
        System.out.println("Director: " + resM.get(0).getDirector());
        movies.DeleteOne(1);
        resM = movies.GetAll();
        System.out.println("Size: " + resM.size());
        
        System.out.println("======= Reviewer =======");
        Reviewer rev = new Reviewer();
        rev.setrID(2);
        rev.setName("Andrew");
        
        List<Reviewer> resRev = reviewers.GetAll();
        System.out.println("Size: " + resRev.size());
        reviewers.InsertOne(rev);
        resRev = reviewers.GetAll();
        System.out.println("Name: " + resRev.get(0).getName());
        rev.setName("Name");
        boolean t = reviewers.UpdateOne(2, rev);
        resRev = reviewers.GetAll();
        System.out.println("Name: " + resRev.get(0).getName());
        reviewers.DeleteOne(2);
        resRev = reviewers.GetAll();
        System.out.println("Size: " + resRev.size());
        
        System.out.println("======= Rating =======");
        Rating rat = new Rating();
        rat.setrID(2);
        rat.setmID(1);
        rat.setStars(5);
        rat.setRatingDate(new Date(2018, 1, 29));
        
        List<Rating> resRat = ratings.GetAll();
        System.out.println("Size: " + resRat.size());
        ratings.InsertOne(rat);
        resRat = ratings.GetAll();
        System.out.println("Stars: " + resRat.get(0).getStars());
        rat.setStars(3);
        ratings.UpdateOne(1, 2, rat);
        resRat = ratings.GetAll();
        System.out.println("Stars: " + resRat.get(0).getStars());
        ratings.DeleteOne(1, 2);
        resRat = ratings.GetAll();
        System.out.println("Size: " + resRat.size());
        
        
        
        System.out.print("END.\n\r");
    }
    
}
