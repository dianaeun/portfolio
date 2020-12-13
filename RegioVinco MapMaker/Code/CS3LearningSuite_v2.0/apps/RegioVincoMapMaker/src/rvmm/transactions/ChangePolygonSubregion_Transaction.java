/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvmm.transactions;

import javafx.scene.shape.Polygon;
import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;

/**
 *
 * @author DayeE
 */
public class ChangePolygonSubregion_Transaction implements jTPS_Transaction {
    MapMakerData data;
    Polygon polygonToMove;
    SubregionPrototype containsP;
    SubregionPrototype willHaveP;
    int indexOfOld;
    
    public ChangePolygonSubregion_Transaction(MapMakerData initData, Polygon p, SubregionPrototype o, SubregionPrototype n) {
        data = initData;
        polygonToMove = p;
        containsP = o;
        willHaveP = n;
        indexOfOld = data.getSubregionIndex(containsP);
    }

    @Override
    public void doTransaction() {
        polygonToMove.setFill(willHaveP.getColor());
        containsP.getArea().remove(polygonToMove);
        if(containsP.getArea().isEmpty()) {
            data.removeSubregion(indexOfOld);
        }
        willHaveP.addArea(polygonToMove);
        willHaveP.setAreaInfo(data.getBorderColor(), data.getBorderThickness()/data.getScale());
    }

    @Override
    public void undoTransaction() {
        polygonToMove.setFill(containsP.getColor());
        willHaveP.getArea().remove(polygonToMove);
        if(containsP.getArea().isEmpty()) {
            data.addSubregionAt(containsP, indexOfOld);
        }
        containsP.addArea(polygonToMove);
        containsP.setAreaInfo(data.getBorderColor(), data.getBorderThickness()/data.getScale());
    }
}
