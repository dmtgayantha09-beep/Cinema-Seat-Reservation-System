package dao;

import model.Seat;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    public List<Seat> getSeatsByHall(int hallId){

        List<Seat> seats =
                new ArrayList<>();

        String sql =
                "SELECT * FROM seats "
                + "WHERE hall_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1,hallId);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()){

                Seat seat =
                        new Seat();

                seat.setSeatId(
                        rs.getInt("seat_id"));

                seat.setHallId(
                        rs.getInt("hall_id"));

                seat.setSeatRow(
                        rs.getString("seat_row"));

                seat.setSeatNumber(
                        rs.getInt("seat_number"));

                seat.setSeatType(
                        rs.getString("seat_type"));

                seats.add(seat);
            }

        }catch(Exception e){

            System.out.println(e.getMessage());
        }

        return seats;
    }

    public List<Seat> getAvailableSeats(int showtimeId, int hallId){

        List<Seat> seats =
                new ArrayList<>();

        String sql =
                "SELECT s.* FROM seats s "
                + "WHERE s.hall_id=? "
                + "AND NOT EXISTS ("
                + "SELECT 1 FROM booking_seats bs "
                + "INNER JOIN bookings b "
                + "ON b.booking_id = bs.booking_id "
                + "WHERE bs.seat_id = s.seat_id "
                + "AND b.showtime_id = ? "
                + "AND b.status = 'CONFIRMED'"
                + ") "
                + "ORDER BY s.seat_row, s.seat_number";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1,hallId);
            pst.setInt(2,showtimeId);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()){

                Seat seat =
                        new Seat();

                seat.setSeatId(
                        rs.getInt("seat_id"));

                seat.setHallId(
                        rs.getInt("hall_id"));

                seat.setSeatRow(
                        rs.getString("seat_row"));

                seat.setSeatNumber(
                        rs.getInt("seat_number"));

                seat.setSeatType(
                        rs.getString("seat_type"));

                seats.add(seat);
            }

        }catch(Exception e){

            System.out.println(e.getMessage());
        }

        return seats;
    }
    
    public int getTotalShowSeats(){

        String sql =
                "SELECT COUNT(*) FROM seats";

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
    
    public boolean addSeat(Seat seat){

        String sql =
                "INSERT INTO seats "
                + "(hall_id, seat_row, seat_number, seat_type) "
                + "VALUES (?,?,?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1, seat.getHallId());
            pst.setString(2, seat.getSeatRow());
            pst.setInt(3, seat.getSeatNumber());
            pst.setString(4, seat.getSeatType());

            return pst.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean updateSeat(Seat seat){

        String sql =
                "UPDATE seats SET "
                + "hall_id=?, "
                + "seat_row=?, "
                + "seat_number=?, "
                + "seat_type=? "
                + "WHERE seat_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1, seat.getHallId());
            pst.setString(2, seat.getSeatRow());
            pst.setInt(3, seat.getSeatNumber());
            pst.setString(4, seat.getSeatType());
            pst.setInt(5, seat.getSeatId());

            return pst.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean deleteSeat(int seatId){

        String sql =
                "DELETE FROM seats "
                + "WHERE seat_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ){

            pst.setInt(1, seatId);

            return pst.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(e.getMessage());
        }

        return false;
    }

    public List<Seat> getAllSeats(){

        List<Seat> seats =
                new ArrayList<>();

        String sql =
                "SELECT * FROM seats "
                + "ORDER BY hall_id, seat_row, seat_number";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ){

            while(rs.next()){

                Seat seat =
                        new Seat();

                seat.setSeatId(
                        rs.getInt("seat_id"));

                seat.setHallId(
                        rs.getInt("hall_id"));

                seat.setSeatRow(
                        rs.getString("seat_row"));

                seat.setSeatNumber(
                        rs.getInt("seat_number"));

                seat.setSeatType(
                        rs.getString("seat_type"));

                seats.add(seat);
            }

        }catch(Exception e){

            System.out.println(e.getMessage());
        }

        return seats;
    }
}
