package org.kles.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "devicetype")
public class DeviceTypeWrapper {

    private List<DeviceType> listType;

    @XmlElement(name = "type")
    public List<DeviceType> getDeviceType() {
        return this.listType;
    }

    public void setDeviceType(List<DeviceType> stat) {
        this.listType = stat;
    }
}
