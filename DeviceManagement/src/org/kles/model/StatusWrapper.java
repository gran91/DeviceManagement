package org.kles.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "status")
public class StatusWrapper {

    private List<Status> status;

    @XmlElement(name = "stat")
    public List<Status> getStatus() {
        return this.status;
    }

    public void setStatus(List<Status> stat) {
        this.status = stat;
    }
}
