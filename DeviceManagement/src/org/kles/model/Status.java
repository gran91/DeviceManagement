package org.kles.model;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Status extends AbstractDataModel {

    private final IntegerProperty id;
    private final StringProperty name;

    public Status() {
        this("Status");
    }

    public Status(String name) {
        super(name);
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty("");
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty getIdProperty() {
        return this.id;
    }

    public String getName() {
        return (String) this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getNameProperty() {
        return this.name;
    }

    @Override
    public ArrayList<?> extractData() {
        ArrayList a = new ArrayList();
        a.add(id.get());
        a.add(name.get());
        return a;
    }

    @Override
    public void populateData(ArrayList<?> data) {
        if (data != null) {
            if (data.size() == 2) {
                id.set((Integer)data.get(0));
                name.set(data.get(1).toString());
            }
        }
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public AbstractDataModel newInstance() {
        return new Status();
    }
}
