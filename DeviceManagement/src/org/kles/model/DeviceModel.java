package org.kles.model;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.kles.jaxb.adapter.ImageAdapter;
import org.kles.jaxb.adapter.LocalDateAdapter;

public class DeviceModel extends AbstractDataModel {

    private final StringProperty name;
    private final ObjectProperty<DeviceType> type;
    private final FloatProperty price;
    private final ObjectProperty<Image> image;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty description;

    public DeviceModel() {
        this("DeviceModel");
    }

    public DeviceModel(String name) {
        super("DeviceModel");
        this.name = new SimpleStringProperty();
        this.type = new SimpleObjectProperty<>();
        this.price = new SimpleFloatProperty();
        this.image = new SimpleObjectProperty<>();
        this.image.set(new Image("/resources/images/device.png"));
        this.date = new SimpleObjectProperty<>();
        this.description = new SimpleStringProperty();

    }

    @Override
    public ArrayList<?> extractData() {
        ArrayList a = new ArrayList();
        a.add(name.get());
        a.add(type.get());
        a.add(price.get());
        a.add(image.get());
        a.add(date.get());
        return a;
    }

    @Override
    public void populateData(ArrayList<?> data) {
        if (data != null) {
            if (data.size() == 5) {
                name.set((String) data.get(0));
                type.set((DeviceType) data.get(1));
                price.set((Float) data.get(2));
                image.set((Image) data.get(3));
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
        return new DeviceModel();
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
    
     public String getDescription() {
        return description.get();
    }

    public void setDescription(String name) {
        this.description.set(name);
    }

    public StringProperty getDescriptionProperty() {
        return this.description;
    }

    public DeviceType getType() {
        return type.get();
    }

    public void setType(DeviceType type) {
        this.type.set(type);
    }

    public ObjectProperty<DeviceType> getTypeProperty() {
        return this.type;
    }

    public float getPrice() {
        return price.get();
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public FloatProperty getPriceProperty() {
        return this.price;
    }

    @XmlJavaTypeAdapter(ImageAdapter.class)
    public Image getImage() {
        return image.get();
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public ObjectProperty<Image> getImageProperty() {
        return this.image;
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
