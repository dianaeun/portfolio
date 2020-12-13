/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvmm.transactions;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import rvmm.data.MapMakerData;

/**
 *
 * @author DayeE
 */
public class MoveImage_Transaction implements jTPS_Transaction {
    ImageView imgView;
    double[] newTranslate = new double[2];
    double[] oldTranslate = new double[2];
    
    public MoveImage_Transaction(ImageView initView, double oldX, double oldY) {
        imgView = initView;
        oldTranslate[0] = oldX;
        oldTranslate[1] = oldY;
        newTranslate[0] = imgView.getTranslateX();
        newTranslate[1] = imgView.getTranslateY();
    }

    @Override
    public void doTransaction() {
        imgView.setTranslateX(newTranslate[0]);
        imgView.setTranslateY(newTranslate[1]);
    }

    @Override
    public void undoTransaction() {
        imgView.setTranslateX(oldTranslate[0]);
        imgView.setTranslateY(oldTranslate[1]);
    }
}
