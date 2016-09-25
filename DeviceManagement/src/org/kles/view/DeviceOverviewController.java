package org.kles.view;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import org.kles.model.Device;
import org.kles.model.DeviceModel;
import org.kles.model.Status;
import org.kles.model.User;

public class DeviceOverviewController extends ModelManagerTableViewController implements IModelManagerView {

    @FXML
    private TableColumn<Device, String> nameColumn;
    @FXML
    private TableColumn<Device, DeviceModel> modelColumn;
    @FXML
    private TableColumn<Device, Status> statusColumn;
    @FXML
    private TableColumn<Device, User> userColumn;
    @FXML
    private TableColumn<Device, LocalDate> dateColumn;

    public DeviceOverviewController() {
        super("Device");
    }

    public DeviceOverviewController(String dataname) {
        super("Device");
    }

    @Override
    public void loadColumnTable() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().getModelProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().getUserProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
    }

    @FXML
    @Override
    public void handleNew() {
        datamodel = new Device();
        super.handleNew();
    }
}
