package org.kles.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.prefs.Preferences;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.kles.DeviceManagement;
import org.kles.model.AbstractDataModel;
import org.kles.model.Device;
import org.kles.model.DeviceModel;
import org.kles.model.DeviceType;
import org.kles.model.Status;
import org.kles.model.User;

/**
 * Dialog to edit details of a status.
 *
 * @author Jérémy Chaut
 */
public class DeviceEditDialogController extends AbstractDataModelEditController {

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<DeviceModel> modelField;
    @FXML
    private ComboBox<Status> statusField;
    @FXML
    private ComboBox<User> userField;
    @FXML
    private DatePicker dateField;

    private BooleanBinding nameBoolean;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        //nameBoolean = TextFieldValidator.emptyTextFieldBinding(nameField, DeviceManagement.resourceMessage.getString("message.firstname"), messages);
    }

    /**
     * Sets the developer to be edited in the dialog.
     *
     * @param device
     */
    @Override
    public void setDataModel(AbstractDataModel device) {
        datamodel = device;
        nameField.setText(((Device) device).getName());
        modelField.getSelectionModel().select(((Device) device).getModel());
        statusField.getSelectionModel().select(((Device) device).getStatus());
        userField.getSelectionModel().select(((Device) device).getUser());
        dateField.setValue(((Device) device).getDate());
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            ((Device) datamodel).setName(nameField.getText());
            ((Device) datamodel).setModel(modelField.getSelectionModel().getSelectedItem());
            ((Device) datamodel).setStatus(statusField.getSelectionModel().getSelectedItem());
            ((Device) datamodel).setUser(userField.getSelectionModel().getSelectedItem());
            ((Device) datamodel).setDate(dateField.getValue());
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void addModel() {
        DeviceModel devicemodel = new DeviceModel();
        if (mainApp.getRootController().showDataModelEditDialog(devicemodel)) {
            mainApp.getDeviceModelData().add(devicemodel);
        }
    }

    @FXML
    private void addStatus() {
        Status status = new Status();
        if (mainApp.getRootController().showDataModelEditDialog(status)) {
            mainApp.getStatusData().add(status);
        }
    }

    @FXML
    private void addUser() {
        User user = new User();
        if (mainApp.getRootController().showDataModelEditDialog(user)) {
            mainApp.getUserData().add(user);
        }
    }

    /**
     * Validates the status input in the text fields.
     *
     * @return true if the input is valid
     */
    @Override
    public boolean isInputValid() {
        errorMessage = "";
        return super.isInputValid();
    }

    @Override
    public void setMainApp(DeviceManagement main) {
        super.setMainApp(main);
        modelField.setItems(main.getDeviceModelData());
        statusField.setItems(main.getStatusData());
        userField.setItems(main.getUserData());
    }
}
