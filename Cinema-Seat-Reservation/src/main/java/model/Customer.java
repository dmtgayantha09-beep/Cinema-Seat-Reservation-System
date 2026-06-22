package model;

public class Customer extends User {

    @Override
    public void openDashboard() {

        System.out.println(
                "Customer Dashboard Opened"
        );
    }
}