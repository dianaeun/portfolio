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
public class ReassignColors_Transaction implements jTPS_Transaction {
    MapMakerData data;
    ObservableList<Color> oldColors;
    ObservableList<Color> newColors;
    
    public ReassignColors_Transaction(MapMakerData initData, 
                ObservableList<Color> oC, ObservableList<Color> nC) {
        data = initData;
        oldColors = oC;
        newColors = nC;
    }

    @Override
    public void doTransaction() {
        ObservableList<SubregionPrototype> s = data.getSubregions();
        for(int i = 0; i < s.size(); i++) {
            s.get(i).setColor(newColors.get(i));
            s.get(i).setAreaInfo(data.getBorderColor(), data.getBorderThickness()/data.getScale());
        }
        data.refreshSubregion();
    }

    @Override
    public void undoTransaction() {
        ObservableList<SubregionPrototype> s = data.getSubregions();
        for(int i = 0; i < s.size(); i++) {
            s.get(i).setColor(oldColors.get(i));
            s.get(i).setAreaInfo(data.getBorderColor(), data.getBorderThickness()/data.getScale());
        }
        data.refreshSubregion();
    }
}
