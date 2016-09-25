package org.kles.view;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import org.kles.model.DeviceModel;
import org.kles.model.DeviceType;

public class DeviceModelOverviewController extends ModelManagerTableViewController implements IModelManagerView {

    @FXML
    private TableColumn<DeviceModel, String> nameColumn;
    @FXML
    private TableColumn<DeviceModel, DeviceType> typeColumn;
    @FXML
    private TableColumn<DeviceModel, Float> pricehtColumn;
    @FXML
    private TableColumn<DeviceModel, Float> pricettcColumn;
    @FXML
    private TableColumn<DeviceModel, LocalDate> dateColumn;

    public DeviceModelOverviewController() {
        super("DeviceModel");
    }

    public DeviceModelOverviewController(String dataname) {
        super("DeviceModel");
    }

    @Override
    public void loadColumnTable() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        pricehtColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        pricettcColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
    }

    @FXML
    @Override
    public void handleNew() {
        datamodel = new DeviceModel();
        super.handleNew();
    }
}
