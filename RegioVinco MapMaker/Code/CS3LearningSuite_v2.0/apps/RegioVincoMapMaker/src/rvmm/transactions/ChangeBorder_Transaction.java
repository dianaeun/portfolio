package rvmm.transactions;

import djf.AppTemplate;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;

/**
 *
 * @author Daye Eun
 */
public class ChangeBorder_Transaction implements jTPS_Transaction {
    MapMakerData data;
    double oldThickness, newThickness;
    Color oldColor, newColor;
    
    public ChangeBorder_Transaction(MapMakerData initData, 
            double oT, Color oC, double nT, Color nC) {
        data = initData;
        oldThickness = oT;
        newThickness = nT;
        oldColor = oC;
        newColor = nC;
    }

    @Override
    public void doTransaction() {
        data.setBorders(newThickness, newColor);
        data.refreshSubregion();
        data.setBorderData();
    }

    @Override
    public void undoTransaction() {
        data.setBorders(oldThickness, oldColor);
        data.refreshSubregion();
        data.setBorderData();
    }
}
