package org.kles.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import org.kles.model.DeviceType;


public class DeviceTypeOverviewController extends ModelManagerTableViewController implements IModelManagerView {

    @FXML
    private TableColumn<DeviceType, String> nameColumn;

    public DeviceTypeOverviewController() {
        super("DeviceType");
    }

    public DeviceTypeOverviewController(String dataname) {
        super("DeviceType");
    }

    @Override
    public void loadColumnTable() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    }

    @FXML
    @Override
    public void handleNew() {
        datamodel = new DeviceType();
        super.handleNew();
    }
}
