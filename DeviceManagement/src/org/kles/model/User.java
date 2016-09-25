package org.kles.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User extends AbstractDataModel {

    private final StringProperty code;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;

    public User() {
        super("User");
        this.code = new SimpleStringProperty("");
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public StringProperty getCodeProperty() {
        return code;
    }

    public String getFirstName() {
        return (String) this.firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty getFirstNameProperty() {
        return this.firstName;
    }

    public String getLastName() {
        return (String) this.lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty getLastNameProperty() {
        return this.lastName;
    }

    public String getEmail() {
        return (String) this.email.get();
    }

    public void setEmail(String street) {
        this.email.set(street);
    }

    public StringProperty getEmailProperty() {
        return this.email;
    }

    @Override
    public ArrayList<String> extractData() {
        ArrayList<String> a = new ArrayList();
        a.add(code.get());
        a.add(firstName.get());
        a.add(lastName.get());
        a.add(email.get());
        return a;
    }

    @Override
    public void populateData(ArrayList<?> data) {
        if (data != null) {
            if (data.size() == 5) {
                code.set(data.get(0).toString());
                firstName.set(data.get(1).toString());
                lastName.set(data.get(2).toString());
                email.set(data.get(3).toString());
            }
        }
    }

    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }

    @Override
    public AbstractDataModel newInstance() {
        return new User();
    }
}
