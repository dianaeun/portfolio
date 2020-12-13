/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvmm.workspace.controllers;

import djf.AppTemplate;
import djf.modules.AppGUIModule;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rvmm.RegioVincoMapMakerApp;
import static rvmm.MapMakerPropertyType.RVMM_ITEMS_TABLE_VIEW;
import rvmm.data.SubregionPrototype;
/**
 *
 * @author DayeE
 */

public class SubRegionTableController {
    RegioVincoMapMakerApp app;

    public SubRegionTableController(AppTemplate initApp) {
        app = (RegioVincoMapMakerApp)initApp;
    }

    public void processChangeTableSize() {
        AppGUIModule gui = app.getGUIModule();
        TableView<SubregionPrototype> itemsTable = (TableView)gui.getGUINode(RVMM_ITEMS_TABLE_VIEW);
        ObservableList columns = itemsTable.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            TableColumn column = (TableColumn)columns.get(i);
            column.setMinWidth(itemsTable.widthProperty().getValue()/columns.size());
            column.setMaxWidth(itemsTable.widthProperty().getValue()/columns.size());
        }
    }  
}
