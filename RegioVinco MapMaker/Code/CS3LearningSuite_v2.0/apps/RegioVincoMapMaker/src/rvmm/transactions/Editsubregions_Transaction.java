package rvmm.transactions;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;

/**
 *
 * @author DayeE
 */
public class Editsubregions_Transaction implements jTPS_Transaction {
    MapMakerData data;
    ArrayList<SubregionPrototype> oldSubregions;
    ArrayList<SubregionPrototype> newSubregions;
    
    public Editsubregions_Transaction
        (MapMakerData initData, ArrayList<SubregionPrototype> oS, ArrayList<SubregionPrototype> nS) {
        data = initData;
        oldSubregions = oS;
        newSubregions = nS;
    }

    @Override
    public void doTransaction() {
        for(int i = 0; i < newSubregions.size(); i++) {
                int place = data.getSubregionIndex(oldSubregions.get(i));
                SubregionPrototype toAdd = newSubregions.get(i);
                data.removeSubregion(place);
                data.addSubregionAt(toAdd, place);
        }
        data.selectSubregion(newSubregions.get(0));
        data.refreshSubregion();
    }

    @Override
    public void undoTransaction() {
        for(int i = 0; i < newSubregions.size(); i++) {
                int place = data.getSubregionIndex(newSubregions.get(i));
                SubregionPrototype toAdd = oldSubregions.get(i);
                data.removeSubregion(place);
                data.addSubregionAt(toAdd, place);
        }
        data.selectSubregion(oldSubregions.get(0));
        data.refreshSubregion();
    }
}
