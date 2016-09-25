 package org.kles.model;
 
 import java.util.List;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 
 @XmlRootElement(name="devices")
 public class DeviceWrapper
 {
   private List<Device> device;
 
   @XmlElement(name="device")
   public List<Device> getDevice()
   {
     return this.device;
   }
 
   public void setDevice(List<Device> device) {
     this.device = device;
   }
 }
