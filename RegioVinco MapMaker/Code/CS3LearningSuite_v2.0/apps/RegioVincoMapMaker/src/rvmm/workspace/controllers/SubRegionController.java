package rvmm.workspace.controllers;

import rvmm.RegioVincoMapMakerApp;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;
import rvmm.workspace.dialogs.SubregionDialog;
import rvmm.transactions.MoveUpSubregion_Transaction;
import rvmm.transactions.MoveDownSubregion_Transaction;

/**
 *
 * @author Daye Eun
 */
public class SubRegionController {
    RegioVincoMapMakerApp app;
    SubregionDialog subregionDialog;
    
    public SubRegionController(RegioVincoMapMakerApp initApp) {
        app = initApp;
        
        subregionDialog = new SubregionDialog(app);
        subregionDialog.setOnCloseRequest(e->{
            subregionDialog.clearEditedSubregion();
        });
    }
    
    public void processEditSubregions() {
        //Have to work on it!
        MapMakerData data = (MapMakerData)app.getDataComponent();      
        if(data.isSubregionSelected()) {
            subregionDialog.showSubregionDialog(data.getSelectedSubregion());
            app.getFileModule().markAsEdited(true);
        }
    }
    
    public void processMoveUpSubregions() {
        MapMakerData data = (MapMakerData)app.getDataComponent();
        if(data.isSubregionSelected()) {
            SubregionPrototype item = data.getSelectedSubregion();
            if(data.getSubregionIndex(item) != 0) {
                MoveUpSubregion_Transaction transaction = new MoveUpSubregion_Transaction(data, item);
                app.processTransaction(transaction);
                app.getFileModule().markAsEdited(true);
                app.getFoolproofModule().updateAll();
            }
        }
    }
    
    public void processMoveDownSubregions() {
        MapMakerData data = (MapMakerData)app.getDataComponent();
        if(data.isSubregionSelected()) {
            SubregionPrototype item = data.getSelectedSubregion();
            if(data.getSubregionIndex(item) != (data.getNumSubregions() - 1)) {
                MoveDownSubregion_Transaction transaction = new MoveDownSubregion_Transaction(data, item);
                app.processTransaction(transaction);
                app.getFileModule().markAsEdited(true);
                app.getFoolproofModule().updateAll();
            }
        }
    }
}
