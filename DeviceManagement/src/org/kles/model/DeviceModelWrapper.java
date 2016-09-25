 package org.kles.model;
 
 import java.util.List;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 
 @XmlRootElement(name="devicemodel")
 public class DeviceModelWrapper
 {
   private List<DeviceModel> devicemodel;
 
   @XmlElement(name="model")
   public List<DeviceModel> getDeviceModel()
   {
     return this.devicemodel;
   }
 
   public void setDeviceModel(List<DeviceModel> devicemodel) {
     this.devicemodel = devicemodel;
   }
 }
