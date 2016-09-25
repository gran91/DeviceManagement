package org.kles.view;

import org.kles.fx.custom.TextFieldValidator;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.kles.DeviceManagement;
import org.kles.model.AbstractDataModel;
import org.kles.model.User;

/**
 * Dialog to edit details of a developer.
 *
 * @author Jérémy Chaut
 */
public class UserEditDialogController extends AbstractDataModelEditController {

    @FXML
    private TextField codeField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField workspaceField;

    private BooleanBinding codeM3Boolean, firstNameBoolean, lastNameBoolean, emailBoolean;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        codeM3Boolean = TextFieldValidator.emptyTextFieldBinding(codeField, DeviceManagement.resourceMessage.getString("message.code"), messages);
        firstNameBoolean = TextFieldValidator.emptyTextFieldBinding(firstNameField, DeviceManagement.resourceMessage.getString("message.firstname"), messages);
        lastNameBoolean = TextFieldValidator.emptyTextFieldBinding(lastNameField, DeviceManagement.resourceMessage.getString("message.lastname"), messages);
        emailBoolean = TextFieldValidator.patternTextFieldBinding(emailField, TextFieldValidator.emailPattern, DeviceManagement.resourceMessage.getString("message.email"), messages);
        BooleanBinding[] mandotariesBinding = new BooleanBinding[]{codeM3Boolean, firstNameBoolean, lastNameBoolean, emailBoolean};
        BooleanBinding mandatoryBinding = TextFieldValidator.any(mandotariesBinding);
        LongBinding nbMandatoryBinding = TextFieldValidator.count(mandotariesBinding);
    }

    /**
     * Sets the developer to be edited in the dialog.
     *
     * @param developer
     */
    @Override
    public void setDataModel(AbstractDataModel developer) {
        datamodel = developer;
        codeField.setText(((User) developer).getCode());
        firstNameField.setText(((User) developer).getFirstName());
        lastNameField.setText(((User) developer).getLastName());
        emailField.setText(((User) developer).getEmail());
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            ((User) datamodel).setCode(codeField.getText());
            ((User) datamodel).setFirstName(firstNameField.getText());
            ((User) datamodel).setLastName(lastNameField.getText());
            ((User) datamodel).setEmail(emailField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    @Override
    public boolean isInputValid() {
        errorMessage = "";
        return super.isInputValid();
    }
}
