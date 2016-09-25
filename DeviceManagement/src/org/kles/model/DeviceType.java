package org.kles.model;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeviceType extends AbstractDataModel {

    private final StringProperty name;

    public DeviceType() {
        this("DeviceType");
    }

    public DeviceType(String name) {
        super(name);
        this.name = new SimpleStringProperty("");
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
        a.add(name.get());
        return a;
    }

    @Override
    public void populateData(ArrayList<?> data) {
        if (data != null) {
            if (data.size() == 1) {
                name.set(data.get(0).toString());
            }
        }
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public AbstractDataModel newInstance() {
        return new DeviceType();
    }
}
