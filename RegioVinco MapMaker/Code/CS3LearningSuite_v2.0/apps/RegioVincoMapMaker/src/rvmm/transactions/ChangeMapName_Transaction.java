package rvmm.transactions;

import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;

/**
 *
 * @author DayeE
 */
public class ChangeMapName_Transaction implements jTPS_Transaction {
    MapMakerData data;
    String oldTitle;
    String newTitle;
    
    public ChangeMapName_Transaction(MapMakerData initData, String oldName, String newName) {        
        data = initData;
        newTitle = newName;
        oldTitle = oldName;
    }

    @Override
    public void doTransaction() {
        data.setName(newTitle);
    }

    @Override
    public void undoTransaction() {
        data.setName(oldTitle);
    }
}
