package rvmm.transactions;

import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;

/**
 *
 * @author DayeE
 */
public class ChangeRadialGradient_Transaction implements jTPS_Transaction {
    MapMakerData data;
    RadialGradient oldGradient;
    RadialGradient newGradient;
    
    public ChangeRadialGradient_Transaction(MapMakerData initData, RadialGradient oG, RadialGradient nG) {        
        data = initData;
        oldGradient = oG;
        newGradient = nG;
    }

    @Override
    public void doTransaction() {
        data.setOceanGradient(newGradient);
        data.setSavedSliders();
    }

    @Override
    public void undoTransaction() {
        data.setOceanGradient(oldGradient);
        data.setSavedSliders();
    }
}
