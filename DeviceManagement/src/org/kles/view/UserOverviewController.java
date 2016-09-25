package org.kles.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import org.kles.model.User;

public class UserOverviewController extends ModelManagerTableViewController implements IModelManagerView {

    @FXML
    private TableColumn<User, String> codeColumn;
    @FXML
    private TableColumn<User, String> firstnameColumn;
    @FXML
    private TableColumn<User, String> lastnameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    public UserOverviewController() {
        super("User");
    }

    public UserOverviewController(String dataname) {
        super("User");
    }

    @Override
    public void loadColumnTable() {
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastnameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
    }
    
    @FXML
    @Override
    public void handleNew() {
        datamodel = new User();
        super.handleNew();
    }
}
