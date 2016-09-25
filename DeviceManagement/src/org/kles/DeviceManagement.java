package org.kles;

import org.kles.fx.custom.DigitalClock;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import org.kles.model.Device;

import org.kles.model.DeviceModel;
import org.kles.model.DeviceModelWrapper;
import org.kles.model.User;
import org.kles.model.DeviceType;
import org.kles.model.DeviceTypeWrapper;
import org.kles.model.DeviceWrapper;
import org.kles.model.Status;
import org.kles.model.StatusWrapper;
import org.kles.model.UserWrapper;
import org.kles.view.ModelManagerTableViewController;
import org.kles.view.RootLayoutController;
import resources.Resource;

public class DeviceManagement extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Locale locale;
    private Properties configProp;
    private final StringProperty title = new SimpleStringProperty(Resource.TITLE);
    private final StringProperty dirData = new SimpleStringProperty();
    public static final String configFileName = "config";
    public static ResourceBundle resourceMessage;
    public static String SKIN = "skin";
    public static String LANGUAGE = "lang";
    public static Preferences prefs = Preferences.userNodeForPackage(DeviceManagement.class);
    private RootLayoutController rootController;
    private HashMap<String, ObservableList> dataMap;
    private final ObservableList<Status> statusData = FXCollections.observableArrayList();
    private final ObservableList<User> userData = FXCollections.observableArrayList();
    private final ObservableList<Device> deviceData = FXCollections.observableArrayList();
    private final ObservableList<DeviceType> deviceTypeData = FXCollections.observableArrayList();
    private final ObservableList<DeviceModel> deviceModelData = FXCollections.observableArrayList();
    private final DigitalClock clock = new DigitalClock(DigitalClock.CLOCK);

    private final LinkedHashMap<String, String> listSkin = new LinkedHashMap<>();

    public static final Image LOGO_IMAGE = new Image(RootLayoutController.class.getResourceAsStream("/resources/images/logo.png"));

    /**
     * Constructor
     */
    public DeviceManagement() {
        clock.start();
        if (prefs.get(LANGUAGE, null) == null) {
            prefs.put(LANGUAGE, Locale.getDefault().toString());
        } else {
            Locale.setDefault(new Locale(prefs.get(LANGUAGE, null).split("_")[0], prefs.get(LANGUAGE, null).split("_")[1]));
        }
        locale = Locale.getDefault();
        resourceMessage = ResourceBundle.getBundle("resources/language", Locale.getDefault());
        createDataMap();

        loadSkins();
        if (prefs.get(SKIN, null) == null) {
            prefs.put(SKIN, STYLESHEET_MODENA);
        }
        Application.setUserAgentStylesheet(prefs.get(SKIN, null));
    }

    @Override
    public void start(Stage primaryStage) {
        title.bind(Bindings.concat(Resource.TITLE).concat(dirData).concat("\t").concat(clock.getTimeText()));
        this.primaryStage = primaryStage;
        this.primaryStage.titleProperty().bind(title);
        this.primaryStage.getIcons().add(new Image("file:/resources/images/logo.png"));
        initRootLayout();
        showDevice();
    }

    public void showDevice() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("resources.language", locale));
            loader.setLocation(DeviceManagement.class.getResource("view/DeviceOverview.fxml"));
            rootLayout.setCenter(loader.load());
            ModelManagerTableViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSkins() {
        listSkin.put("CASPIAN", STYLESHEET_CASPIAN);
        listSkin.put("MODENA", STYLESHEET_MODENA);
        listSkin.put("DarkTheme", "org/kles/css/DarkTheme.css");
        listSkin.put("Windows 7", "org/kles/css/Windows7.css");
        listSkin.put("JMetroDarkTheme", "org/kles/css/JMetroDarkTheme.css");
        listSkin.put("JMetroLightTheme", "org/kles/css/JMetroLightTheme.css");

    }

    public void clearData() {
        statusData.clear();
        userData.clear();
        deviceData.clear();
        deviceModelData.clear();
        deviceTypeData.clear();
    }

    private void createDataMap() {
        dataMap = new HashMap();
        dataMap.put("Status", statusData);
        dataMap.put("User", userData);
        dataMap.put("Device", deviceData);
        dataMap.put("DeviceModel", deviceModelData);
        dataMap.put("DeviceType", deviceTypeData);

    }

    /**
     * Initializes the root layout and tries to load the last opened person
     * file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("resources.language", locale));
            loader.setLocation(DeviceManagement.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            rootController = loader.getController();
            rootController.setMainApp(this);
            scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
                System.out.println("Width: " + newSceneWidth);
            });
            scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
                System.out.println("Height: " + newSceneHeight);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getDataDirectoryPath();
        if (file != null) {
            loadDataDirectory(file);
        }
    }

    public File getDataDirectoryPath() {
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setRegistryFilePath(File file) {
        if (file != null) {
            prefs.put("filePath", file.getPath());
            dirData.set(" - " + file.getAbsolutePath());
        } else {
            prefs.remove("filePath");
            dirData.set("");
        }
    }

    public void loadDataDirectory(File directory) {
        JAXBContext context;
        Unmarshaller um;
        try {
            if (directory.exists() && directory.isDirectory()) {
                File[] listFile = directory.listFiles();
                for (File f : listFile) {
                    if (f.getName().startsWith("user")) {
                        context = JAXBContext.newInstance(UserWrapper.class);
                        um = context.createUnmarshaller();
                        UserWrapper wrapper = (UserWrapper) um.unmarshal(f);
                        userData.clear();
                        userData.addAll(wrapper.getUsers());
                    } else if (f.getName().startsWith("status")) {
                        context = JAXBContext.newInstance(StatusWrapper.class);
                        um = context.createUnmarshaller();
                        StatusWrapper wrapper = (StatusWrapper) um.unmarshal(f);
                        statusData.clear();
                        statusData.addAll(wrapper.getStatus());
                    } else if (f.getName().startsWith("device.xml")) {
                        context = JAXBContext.newInstance(DeviceWrapper.class);
                        um = context.createUnmarshaller();
                        DeviceWrapper wrapper = (DeviceWrapper) um.unmarshal(f);
                        deviceData.clear();
                        deviceData.addAll(wrapper.getDevice());
                    } else if (f.getName().startsWith("devicetype.xml")) {
                        context = JAXBContext.newInstance(DeviceTypeWrapper.class);
                        um = context.createUnmarshaller();
                        DeviceTypeWrapper wrapper = (DeviceTypeWrapper) um.unmarshal(f);
                        deviceTypeData.clear();
                        deviceTypeData.addAll(wrapper.getDeviceType());
                    } else if (f.getName().startsWith("devicemodel.xml")) {
                        context = JAXBContext.newInstance(DeviceModelWrapper.class);
                        um = context.createUnmarshaller();
                        DeviceModelWrapper wrapper = (DeviceModelWrapper) um.unmarshal(f);
                        deviceModelData.clear();
                        deviceModelData.addAll(wrapper.getDeviceModel());
                    }
                }
            }
            setRegistryFilePath(directory);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void saveDataToFile(File file) {
        try {
            saveUser(file);
            saveStatus(file);
            saveDeviceType(file);
            saveDeviceModel(file);
            saveDevice(file);
            setRegistryFilePath(file);
        } catch (Exception e) {
        }
    }

    private void saveUser(File file) throws PropertyException, JAXBException {
        if (userData.size() > 0) {
            JAXBContext context = JAXBContext.newInstance(UserWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            UserWrapper wrapper = new UserWrapper();
            wrapper.setUsers(userData);
            File fileData = new File(file.getAbsoluteFile() + System.getProperty("file.separator") + "user.xml");
            m.marshal(wrapper, fileData);
        }
    }

    private void saveStatus(File file) throws PropertyException, JAXBException {
        if (statusData.size() > 0) {
            JAXBContext context = JAXBContext.newInstance(StatusWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StatusWrapper wrapper = new StatusWrapper();
            wrapper.setStatus(statusData);
            File fileData = new File(file.getAbsoluteFile() + System.getProperty("file.separator") + "status.xml");
            m.marshal(wrapper, fileData);
        }
    }

    private void saveDevice(File file) throws PropertyException, JAXBException {
        if (deviceTypeData.size() > 0) {
            JAXBContext context = JAXBContext.newInstance(DeviceWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            DeviceWrapper wrapper = new DeviceWrapper();
            wrapper.setDevice(deviceData);
            File fileData = new File(file.getAbsoluteFile() + System.getProperty("file.separator") + "device.xml");
            m.marshal(wrapper, fileData);
        }
    }

    private void saveDeviceType(File file) throws PropertyException, JAXBException {
        if (deviceTypeData.size() > 0) {
            JAXBContext context = JAXBContext.newInstance(DeviceTypeWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            DeviceTypeWrapper wrapper = new DeviceTypeWrapper();
            wrapper.setDeviceType(deviceTypeData);
            File fileData = new File(file.getAbsoluteFile() + System.getProperty("file.separator") + "devicetype.xml");
            m.marshal(wrapper, fileData);
        }
    }

    private void saveDeviceModel(File file) throws PropertyException, JAXBException {
        if (deviceModelData.size() > 0) {
            JAXBContext context = JAXBContext.newInstance(DeviceModelWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            DeviceModelWrapper wrapper = new DeviceModelWrapper();
            wrapper.setDeviceModel(deviceModelData);
            File fileData = new File(file.getAbsoluteFile() + System.getProperty("file.separator") + "devicemodel.xml");
            m.marshal(wrapper, fileData);
        }
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the data as an observable list of Persons.
     *
     * @return
     */
    public ObservableList<User> getUserData() {
        return userData;
    }

    public ObservableList<Status> getStatusData() {
        return statusData;
    }

    public ObservableList<Device> getDeviceData() {
        return deviceData;
    }

    public ObservableList<DeviceModel> getDeviceModelData() {
        return deviceModelData;
    }

    public ObservableList<DeviceType> getDeviceTypeData() {
        return deviceTypeData;
    }

    public ResourceBundle getResourceMessage() {
        return resourceMessage;
    }

    public void setResourceMessage(ResourceBundle resourceMessage) {
        DeviceManagement.resourceMessage = resourceMessage;
    }

    public HashMap<String, ObservableList> getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap<String, ObservableList> dataMap) {
        this.dataMap = dataMap;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public RootLayoutController getRootController() {
        return rootController;
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public LinkedHashMap<String, String> getListSkin() {
        return listSkin;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
