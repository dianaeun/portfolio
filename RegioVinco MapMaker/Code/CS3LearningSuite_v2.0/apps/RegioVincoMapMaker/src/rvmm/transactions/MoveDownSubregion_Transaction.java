package rvmm.transactions;

import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;

/**
 *
 * @author DayeE
 */
public class MoveDownSubregion_Transaction implements jTPS_Transaction {
    
    MapMakerData data;
    SubregionPrototype itemToMove;
    
    public MoveDownSubregion_Transaction(MapMakerData initData, SubregionPrototype initItemToMove) {
        data = initData;
        itemToMove = initItemToMove;
    }

    @Override
    public void doTransaction() {
        int index = data.getSubregionIndex(itemToMove);
        data.moveSubregion(index, index+1);
        data.clearSelected();
        data.selectSubregion(itemToMove);
        data.refreshSubregion();
    }

    @Override
    public void undoTransaction() {
        int index = data.getSubregionIndex(itemToMove);
        data.moveSubregion(index, index-1);
        data.clearSelected();
        data.selectSubregion(itemToMove);
        data.refreshSubregion();
    }
}
