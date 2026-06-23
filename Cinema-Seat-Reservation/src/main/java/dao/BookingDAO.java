package dao;

import model.Booking;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public int createBooking(
            Booking booking){

        String sql =
                "INSERT INTO bookings"
                + "(user_id,"
                + "showtime_id,"
                + "total_price,"
                + "status)"
                + " VALUES(?,?,?,?)";

        try(
                Connection con = DBConnection.getConnection();

                PreparedStatement pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)
        ){

            pst.setInt(
                    1,
                    booking.getUserId());

            pst.setInt(
                    2,
                    booking.getShowtimeId());

            pst.setDouble(
                    3,
                    booking.getTotalPrice());

            pst.setString(
                    4,
                    booking.getStatus());

            if (pst.executeUpdate() == 0) {
                return 0;
            }

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return 0;

        }catch(Exception e){

            System.out.println(
                    e.getMessage());

            return 0;
        }
    }

    public boolean addBookingSeat(int bookingId, int seatId) {

        String sql =
                "INSERT INTO booking_seats"
                + "(booking_id,seat_id) "
                + "VALUES(?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1, bookingId);
            pst.setInt(2, seatId);

            return pst.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(e.getMessage());

            return false;
        }
    }

    public boolean isSeatBooked(int showtimeId, int seatId) {

        String sql =
                "SELECT 1 FROM bookings b "
                + "INNER JOIN booking_seats bs "
                + "ON b.booking_id = bs.booking_id "
                + "WHERE b.showtime_id = ? "
                + "AND bs.seat_id = ? "
                + "AND b.status = 'CONFIRMED'";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1, showtimeId);
            pst.setInt(2, seatId);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        }catch(Exception e){

            System.out.println(e.getMessage());

            return false;
        }
    }
    
    public List<Booking> getBookingsByUser(int userId){

    List<Booking> bookings =
            new ArrayList<>();

    String sql =
            "SELECT b.booking_id, "
            + "m.title, "
            + "s.show_date, "
            + "s.show_time, "
            + "CONCAT(st.seat_row,st.seat_number) seat, "
            + "b.total_price, "
            + "b.status "
            + "FROM bookings b "
            + "INNER JOIN showtimes s "
            + "ON b.showtime_id=s.showtime_id "
            + "INNER JOIN movies m "
            + "ON s.movie_id=m.movie_id "
            + "INNER JOIN booking_seats bs "
            + "ON b.booking_id=bs.booking_id "
            + "INNER JOIN seats st "
            + "ON bs.seat_id=st.seat_id "
            + "WHERE b.user_id=?";

    try(
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql)
    ){

        pst.setInt(1, userId);

        try(ResultSet rs =
                pst.executeQuery()){

            while(rs.next()){

                Booking booking =
                        new Booking();

                booking.setBookingId(
                        rs.getInt("booking_id"));

                booking.setMovieTitle(
                        rs.getString("title"));

                booking.setShowDate(
                        rs.getString("show_date"));

                booking.setShowTime(
                        rs.getString("show_time"));

                booking.setSeat(
                        rs.getString("seat"));

                booking.setTotalPrice(
                        rs.getDouble("total_price"));

                booking.setStatus(
                        rs.getString("status"));

                bookings.add(booking);
            }
        }

    }catch(Exception e){

        System.out.println(
                "Load Booking Error : "
                        + e.getMessage());
    }

    return bookings;
}
    public boolean cancelBooking(int bookingId) {

    String sql =
            "UPDATE bookings " +
            "SET status='CANCELLED' " +
            "WHERE booking_id=?";

    try(
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql)
    ){

        pst.setInt(1, bookingId);

        return pst.executeUpdate() > 0;

    }catch(Exception e){

        System.out.println(e.getMessage());

        return false;
    }
}
    
    public int getTotalBookings(){

    String sql =
            "SELECT COUNT(*) total "
            + "FROM bookings "
            + "WHERE status='CONFIRMED'";

    try(
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery()
    ){

        if(rs.next()){

            return rs.getInt("total");
        }

    }catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}
    
    public double getTotalRevenue(){

        String sql =
                "SELECT SUM(total_price) revenue "
                + "FROM bookings "
                + "WHERE status='CONFIRMED'";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ){

            if(rs.next()){

                return rs.getDouble("revenue");
            }

        }catch(Exception e){

            System.out.println(e.getMessage());
        }

        return 0;
    }
    
    public int getBookedSeatsCount(){

        String sql =
                "SELECT COUNT(*) "
                + "FROM booking_seats bs "
                + "INNER JOIN bookings b "
                + "ON bs.booking_id = b.booking_id "
                + "WHERE b.status='CONFIRMED'";

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
