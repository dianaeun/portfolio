package rvmm.transactions;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;

/**
 *
 * @author DayeE
 */
public class ChangeMapDimension_Transaction implements jTPS_Transaction {
    MapMakerData data;
    double oldWidth, oldHeight;
    double newWidth, newHeight;
    
    public ChangeMapDimension_Transaction(MapMakerData initData, double oW, double oH, double nW, double nH) {        
        data = initData;
        oldWidth = oW;
        oldHeight = oH;
        newWidth = nW;
        newHeight = nH;
    }

    @Override
    public void doTransaction() {
        ObservableList<ArrayList<ArrayList<Double>>> oldData = data.getRelocatePolygons();
        data.changeDimension(newWidth, newHeight);
        data.setRelocatePolygons(oldData);
    }

    @Override
    public void undoTransaction() {
        ObservableList<ArrayList<ArrayList<Double>>> oldData = data.getRelocatePolygons();
        data.changeDimension(oldWidth, oldHeight);
        data.setRelocatePolygons(oldData);
    }
}
