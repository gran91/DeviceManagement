package org.kles.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import org.kles.model.Status;


public class StatusOverviewController extends ModelManagerTableViewController implements IModelManagerView {

    @FXML
    private TableColumn<Status, Integer> idColumn;
    @FXML
    private TableColumn<Status, String> nameColumn;

    public StatusOverviewController() {
        super("Status");
    }

    public StatusOverviewController(String dataname) {
        super("Status");
    }

    @Override
    public void loadColumnTable() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    }

    @FXML
    @Override
    public void handleNew() {
        datamodel = new Status();
        super.handleNew();
    }
}
