package dao;

import model.ShowTime;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowTimeDAO {

    public boolean addShowTime(ShowTime showTime){

        String sql =
                "INSERT INTO showtimes(movie_id,show_date,show_time,hall_no) "
                + "VALUES(?,?,?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1,
                    showTime.getMovieId());

            pst.setDate(2,
                    Date.valueOf(
                            showTime.getShowDate()));

            pst.setTime(3,
                    Time.valueOf(
                            showTime.getShowTime()));

            pst.setInt(4,
                    showTime.getHallNo());

            return pst.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(
                    "Add Showtime Error : "
                            + e.getMessage());

            return false;
        }
    }

    public List<ShowTime> getAllShowTimes(){

        List<ShowTime> list =
                new ArrayList<>();

        String sql =
        "SELECT s.showtime_id, s.movie_id, m.title, s.show_date, s.show_time, s.hall_no FROM showtimes s "
                + "JOIN movies m ON s.movie_id = m.movie_id";

        try(
                Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery(sql)
        ){

            while(rs.next()){

                ShowTime s =
                        new ShowTime();

                s.setShowtimeId(
                        rs.getInt("showtime_id"));

                s.setMovieId(
                        rs.getInt("movie_id"));
                
                s.setMovieTitle(
                        rs.getString("title"));
                
                s.setShowDate(
                        rs.getDate("show_date").toString());

                s.setShowTime(
                        rs.getTime("show_time").toString());

                s.setHallNo(
                        rs.getInt("hall_no"));

                list.add(s);
            }

        }catch(Exception e){

            System.out.println(
                    "Load Error : "
                            + e.getMessage());
        }

        return list;
    }
    
    public List<ShowTime> getShowTimesByMovie(int movieId){

    List<ShowTime> list =
            new ArrayList<>();

    String sql =
            "SELECT * FROM showtimes "
            + "WHERE movie_id=?";

    try(
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql)
    ){

        pst.setInt(1,movieId);

        ResultSet rs =
                pst.executeQuery();

        while(rs.next()){

            ShowTime s =
                    new ShowTime();

            s.setShowtimeId(
                    rs.getInt("showtime_id"));

            s.setMovieId(
                    rs.getInt("movie_id"));

            s.setShowDate(
                    rs.getDate("show_date").toString());

            s.setShowTime(
                    rs.getTime("show_time").toString());

            s.setHallNo(
                    rs.getInt("hall_no"));

            list.add(s);
        }

    }catch(Exception e){

        System.out.println(e.getMessage());
    }

    return list;
}
    
    public int getTotalShowTimes(){

    String sql =
            "SELECT COUNT(*) FROM showtimes";

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

    }catch(Exception e){
        System.out.println(e.getMessage());
    }

    return 0;
}
}