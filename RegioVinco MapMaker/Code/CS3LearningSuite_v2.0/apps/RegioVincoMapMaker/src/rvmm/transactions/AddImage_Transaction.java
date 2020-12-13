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
public class AddImage_Transaction implements jTPS_Transaction {
    MapMakerData data;
    ImageView imgView;
    Image img;
    StringProperty path = new SimpleStringProperty();
    
    public AddImage_Transaction(MapMakerData initData, ImageView initView, StringProperty imgPath) {
        data = initData;
        imgView = initView;
        img = initView.getImage();
        path = imgPath;
    }

    @Override
    public void doTransaction() {
        imgView.setImage(img);
        data.addImages(imgView);
        data.addImagePath(path);
    }

    @Override
    public void undoTransaction() {
        imgView.setImage(null);
        data.removeImages(imgView);
        data.getImagePaths().remove(path);
    }
}
