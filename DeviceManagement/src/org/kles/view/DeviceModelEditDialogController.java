package org.kles.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.prefs.Preferences;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableNumberValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.kles.DeviceManagement;
import org.kles.model.AbstractDataModel;
import org.kles.model.DeviceModel;
import org.kles.model.DeviceType;

/**
 * Dialog to edit details of a status.
 *
 * @author Jérémy Chaut
 */
public class DeviceModelEditDialogController extends AbstractDataModelEditController {

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ComboBox<DeviceType> typeField;
    @FXML
    private TextField pricehtField;
    @FXML
    private TextField pricettcField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ImageView imageField;

    private BooleanBinding nameBoolean;
    private ObservableNumberValue htBind,ttbind;
    private static final String[] imgExt = {".jpg", ".jpeg", "png", ".bpm"};

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
     * @param devicemodel
     */
    @Override
    public void setDataModel(AbstractDataModel devicemodel) {
        datamodel = devicemodel;
        nameField.setText(((DeviceModel) devicemodel).getName());
        typeField.getSelectionModel().select(((DeviceModel) devicemodel).getType());
        pricehtField.setText("" + ((DeviceModel) devicemodel).getPrice());
        pricettcField.setText("" + ((DeviceModel) devicemodel).getPrice() * 1.2);
        Bindings.multiply(htBind, 1.2);
        dateField.setValue(((DeviceModel) devicemodel).getDate());
        if (((DeviceModel) devicemodel).getImage() != null) {
            imageField.setImage(((DeviceModel) devicemodel).getImage());
        }
        descriptionField.setText(((DeviceModel) devicemodel).getDescription());
        Bindings.bindBidirectional(imageField.imageProperty(), ((DeviceModel) datamodel).getImageProperty());
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            ((DeviceModel) datamodel).setName(nameField.getText());
            ((DeviceModel) datamodel).setType(typeField.getSelectionModel().getSelectedItem());
            ((DeviceModel) datamodel).setPrice(Float.parseFloat(pricehtField.getText()));
            ((DeviceModel) datamodel).setDate(dateField.getValue());
            ((DeviceModel) datamodel).setImage(imageField.getImage());
            ((DeviceModel) datamodel).setDescription(descriptionField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void addType() {
        DeviceType devicetype = new DeviceType();
        if (mainApp.getRootController().showDataModelEditDialog(devicetype)) {
            mainApp.getDeviceTypeData().add(devicetype);
        }
    }

    @FXML
    private void changeImage() {
        FileChooser f = new FileChooser();
        f.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image", Arrays.asList(imgExt)));
        File file = f.showOpenDialog(dialogStage);
        if (file != null) {
            DeviceManagement.prefs = Preferences.userNodeForPackage(DeviceManagement.class);
            String filePath = DeviceManagement.prefs.get("filePath", null);
            File fileTarg;
            if (filePath != null) {
                fileTarg = new File(filePath + System.getProperty("file.separator") + file.getName());
                try {
                    Files.copy(Paths.get(file.toURI()), Paths.get(fileTarg.toURI()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    fileTarg = file;
                }
            } else {
                fileTarg = file;
            }
            ((DeviceModel) datamodel).setImage(new Image(fileTarg.toURI().toString()));
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
        typeField.setItems(main.getDeviceTypeData());
    }
}
