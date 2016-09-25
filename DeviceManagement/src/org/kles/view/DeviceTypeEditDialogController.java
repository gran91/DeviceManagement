package org.kles.view;

import org.kles.fx.custom.TextFieldValidator;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.kles.DeviceManagement;
import org.kles.model.AbstractDataModel;
import org.kles.model.DeviceType;

/**
 * Dialog to edit details of a status.
 *
 * @author Jérémy Chaut
 */
public class DeviceTypeEditDialogController extends AbstractDataModelEditController {

    @FXML
    private TextField nameField;

    private BooleanBinding nameBoolean;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        nameBoolean = TextFieldValidator.emptyTextFieldBinding(nameField, DeviceManagement.resourceMessage.getString("message.firstname"), messages);
    }

    /**
     * Sets the developer to be edited in the dialog.
     *
     * @param device
     */
    @Override
    public void setDataModel(AbstractDataModel device) {
        datamodel = device;
        nameField.setText(((DeviceType) device).getName());
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            ((DeviceType) datamodel).setName(nameField.getText());
            okClicked = true;
            dialogStage.close();
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
}
