/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package dao;

import model.Movie;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    public boolean addMovie(Movie movie) {

        String sql =
                "INSERT INTO movies(title,genre,duration_minutes,rating) "
                + "VALUES(?,?,?,?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, movie.getTitle());
            pst.setString(2, movie.getGenre());
            pst.setInt(3, movie.getDurationMinutes());
            pst.setString(4, movie.getRating());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Add Movie Error : "
                    + e.getMessage());

            return false;
        }
    }

    public List<Movie> getAllMovies() {

        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM movies";

        try (
                Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {

                Movie movie = new Movie();

                movie.setMovieId(
                        rs.getInt("movie_id"));

                movie.setTitle(
                        rs.getString("title"));

                movie.setGenre(
                        rs.getString("genre"));

                movie.setDurationMinutes(
                        rs.getInt("duration_minutes"));

                movie.setRating(
                        rs.getString("rating"));

                movies.add(movie);
            }

        } catch (SQLException e) {

            System.out.println("Get Movies Error : "
                    + e.getMessage());
        }

        return movies;
    }

    public Movie getMovieById(int movieId) {

        String sql =
                "SELECT * FROM movies "
                + "WHERE movie_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(1, movieId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Movie movie = new Movie();

                movie.setMovieId(
                        rs.getInt("movie_id"));

                movie.setTitle(
                        rs.getString("title"));

                movie.setGenre(
                        rs.getString("genre"));

                movie.setDurationMinutes(
                        rs.getInt("duration_minutes"));

                movie.setRating(
                        rs.getString("rating"));

                return movie;
            }

        } catch (SQLException e) {

            System.out.println("Search Error : "
                    + e.getMessage());
        }

        return null;
    }

    public boolean updateMovie(Movie movie) {

        String sql =
                "UPDATE movies SET "
                + "title=?,"
                + "genre=?,"
                + "duration_minutes=?,"
                + "rating=? "
                + "WHERE movie_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, movie.getTitle());
            pst.setString(2, movie.getGenre());
            pst.setInt(3, movie.getDurationMinutes());
            pst.setString(4, movie.getRating());
            pst.setInt(5, movie.getMovieId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Update Error : "
                    + e.getMessage());

            return false;
        }
    }

    public boolean deleteMovie(int movieId) {

        String sql =
                "DELETE FROM movies "
                + "WHERE movie_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(1, movieId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Delete Error : "
                    + e.getMessage());

            return false;
        }
    }

    public List<Movie> searchMovies(String keyword) {

        List<Movie> movies = new ArrayList<>();

        String sql =
                "SELECT * FROM movies "
                + "WHERE title LIKE ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1,
                    "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Movie movie = new Movie();

                movie.setMovieId(
                        rs.getInt("movie_id"));

                movie.setTitle(
                        rs.getString("title"));

                movie.setGenre(
                        rs.getString("genre"));

                movie.setDurationMinutes(
                        rs.getInt("duration_minutes"));

                movie.setRating(
                        rs.getString("rating"));

                movies.add(movie);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Search Error : "
                            + e.getMessage());
        }

        return movies;
    }
    
    public int getTotalMovies(){

    String sql =
            "SELECT COUNT(*) FROM movies";

    try(
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery()
    ){

        if(rs.next()){
            return rs.getInt(1);
        }

    }catch(SQLException e){
        System.out.println(e.getMessage());
    }

    return 0;
}
}
