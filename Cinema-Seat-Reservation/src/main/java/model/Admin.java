package model;

public class Admin extends User {

    @Override
    public void openDashboard() {

        System.out.println(
                "Admin Dashboard Opened"
        );
    }
}