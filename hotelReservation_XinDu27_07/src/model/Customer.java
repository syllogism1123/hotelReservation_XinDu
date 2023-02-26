package model;

import java.io.Serializable;

import static model.Color.*;

public class Customer implements Serializable {
    private final String firstname;
    private final String lastName;
    private final String email;

    public Customer(String firstname, String lastName, String email) {

        if (!email.matches("^(.+)@(.+).(.+)$")) {
            throw new IllegalArgumentException("Email does not match the format: name@domain.com");
        }
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "FirstName: " + this.firstname + " LastName: " + this.lastName + " Email: "
                + ANSI_CYAN + this.email + ANSI_RESET;
    }
}
