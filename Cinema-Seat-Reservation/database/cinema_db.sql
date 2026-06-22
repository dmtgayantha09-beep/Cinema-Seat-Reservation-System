DROP DATABASE IF EXISTS cinema_db;

CREATE DATABASE cinema_db;
USE cinema_db;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('CUSTOMER','ADMIN') DEFAULT 'CUSTOMER'
);

CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    duration_minutes INT NOT NULL,
    rating VARCHAR(10) NOT NULL
);

CREATE TABLE showtimes (
    showtime_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    hall_no INT NOT NULL,

    FOREIGN KEY (movie_id)
    REFERENCES movies(movie_id)
    ON DELETE CASCADE
);

CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    hall_id INT NOT NULL,
    seat_row VARCHAR(2) NOT NULL,
    seat_number INT NOT NULL,
    seat_type ENUM('REGULAR','PREMIUM','VIP') DEFAULT 'REGULAR',

    UNIQUE(hall_id, seat_row, seat_number)
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    showtime_id INT NOT NULL,
    total_price DECIMAL(8,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY(user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE,

    FOREIGN KEY(showtime_id)
    REFERENCES showtimes(showtime_id)
    ON DELETE CASCADE
);

CREATE TABLE booking_seats (
    booking_id INT NOT NULL,
    seat_id INT NOT NULL,

    PRIMARY KEY(booking_id, seat_id),

    FOREIGN KEY(booking_id)
    REFERENCES bookings(booking_id)
    ON DELETE CASCADE,

    FOREIGN KEY(seat_id)
    REFERENCES seats(seat_id)
    ON DELETE CASCADE
);

INSERT INTO users(name,email,username,password,role)
VALUES
('System Admin','admin@cinema.com','Admin001','admin123','ADMIN'),
('John Doe','john@example.com','John001','john123','CUSTOMER');

INSERT INTO movies(title,genre,duration_minutes,rating)
VALUES
('Galactic Horizon','Sci-Fi',142,'PG-13'),
('The Last Sonata','Drama',118,'PG'),
('Shadow Pursuit','Action',105,'R');

INSERT INTO showtimes(movie_id,show_date,show_time,hall_no)
VALUES
(1,'2026-06-20','18:00:00',1),
(1,'2026-06-20','21:00:00',1),
(2,'2026-06-21','17:30:00',1),
(3,'2026-06-21','20:00:00',1);

INSERT INTO seats(hall_id,seat_row,seat_number,seat_type)
VALUES
(1,'A',1,'REGULAR'),
(1,'A',2,'REGULAR'),
(1,'A',3,'REGULAR'),
(1,'A',4,'REGULAR'),
(1,'A',5,'REGULAR'),
(1,'B',1,'PREMIUM'),
(1,'B',2,'PREMIUM'),
(1,'B',3,'PREMIUM'),
(1,'B',4,'PREMIUM'),
(1,'B',5,'PREMIUM'),
(1,'C',1,'VIP'),
(1,'C',2,'VIP'),
(1,'C',3,'VIP'),
(1,'C',4,'VIP'),
(1,'C',5,'VIP');
