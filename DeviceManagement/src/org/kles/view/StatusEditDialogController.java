package org.kles.view;

import org.kles.fx.custom.TextFieldValidator;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.kles.DeviceManagement;
import org.kles.model.AbstractDataModel;
import org.kles.model.Status;

/**
 * Dialog to edit details of a status.
 *
 * @author Jérémy Chaut
 */
public class StatusEditDialogController extends AbstractDataModelEditController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;

    private BooleanBinding idBoolean, nameBoolean;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        idBoolean = TextFieldValidator.emptyTextFieldBinding(idField, DeviceManagement.resourceMessage.getString("message.code"), messages);
        nameBoolean = TextFieldValidator.emptyTextFieldBinding(nameField, DeviceManagement.resourceMessage.getString("message.firstname"), messages);
        BooleanBinding[] mandotariesBinding = new BooleanBinding[]{idBoolean, nameBoolean};
        BooleanBinding mandatoryBinding = TextFieldValidator.any(mandotariesBinding);
        LongBinding nbMandatoryBinding = TextFieldValidator.count(mandotariesBinding);
    }

    /**
     * Sets the developer to be edited in the dialog.
     *
     * @param status
     */
    @Override
    public void setDataModel(AbstractDataModel status) {
        datamodel = status;
        idField.setText(""+((Status) status).getId());
        nameField.setText(((Status) status).getName());
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            ((Status) datamodel).setId(Integer.parseInt(idField.getText()));
            ((Status) datamodel).setName(nameField.getText());
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
