package org.kles.model;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.kles.jaxb.adapter.LocalDateAdapter;

public class Device extends AbstractDataModel {

    private final StringProperty name;
    private final ObjectProperty<DeviceModel> model;
    private final ObjectProperty<Status> status;
    private final ObjectProperty<User> user;
    private final ObjectProperty<LocalDate> date;

    public Device() {
        this("Device");
    }

    public Device(String name) {
        super(name);
        this.name = new SimpleStringProperty();
        this.model = new SimpleObjectProperty<>();
        this.status = new SimpleObjectProperty<>();
        this.user = new SimpleObjectProperty<>();
        this.date = new SimpleObjectProperty<>();

    }

    @Override
    public ArrayList<?> extractData() {
        ArrayList a = new ArrayList();
        a.add(name.get());
        a.add(model.get());
        a.add(status.get());
        a.add(user.get());
        a.add(date.get());
        return a;
    }

    @Override
    public void populateData(ArrayList<?> data) {
        if (data != null) {
            if (data.size() == 5) {
                name.set((String) data.get(0));
                model.set((DeviceModel) data.get(1));
                status.set((Status) data.get(2));
                user.set((User) data.get(3));
                date.set((LocalDate) data.get(4));
            }
        }
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public AbstractDataModel newInstance() {
        return new Device();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getNameProperty() {
        return this.name;
    }

    public DeviceModel getModel() {
        return model.get();
    }

    public void setModel(DeviceModel model) {
        this.model.set(model);
    }

    public ObjectProperty<DeviceModel> getModelProperty() {
        return this.model;
    }

    public Status getStatus() {
        return status.get();
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    public ObjectProperty<Status> getStatusProperty() {
        return this.status;
    }

    public User getUser() {
        return user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public ObjectProperty<User> getUserProperty() {
        return this.user;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return this.date;
    }
}
