package rvmm.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Daye Eun
 */
public class SubregionPrototype implements Cloneable {
    final StringProperty name;
    final StringProperty capital;
    final StringProperty leader;
          Color color;
    final ObservableList<Polygon> area;
          Image flag;
       
    public SubregionPrototype(String initName, Color c, Image img) {
        name = new SimpleStringProperty(initName);
        capital = new SimpleStringProperty("");
        leader = new SimpleStringProperty("");
        color = c;
        area = FXCollections.observableArrayList();
        flag = img;
    }
    
    public SubregionPrototype
        (String initName, String initCapital, String initLeader, Color initColor, Image img) {
        this(initName, initColor, img);
        capital.set(initCapital);
        leader.set(initLeader);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCapital() {
        return capital.get();
    }

    public void setCapital(String value) {
        capital.set(value);
    }

    public StringProperty capitalProperty() {
        return capital;
    }
    
    public String getLeader() {
        return leader.get();
    }

    public void setLeader(String value) {
        leader.set(value);
    }

    public StringProperty leaderProperty() {
        return leader;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        color = c;
    }
    
    public ObservableList<Polygon> getArea() {
        return area;
    }
    
    public void setAreaInfo(Color c, double t) {
        for(int i = 0; i < area.size(); i++) {
            area.get(i).setFill(color);
            area.get(i).setStroke(c);
            area.get(i).setStrokeWidth(t);
        }
    }
        
    public void addArea(Polygon newPolygon) {
        area.add(newPolygon);
    }
    
    public void setArea(ObservableList<Polygon> initArea) {
        area.clear();
        area.addAll(initArea);
    }
    
    public Image getImage() {
        return flag;
    }
    
    public void setImage(Image img) {
        flag = img;
    }
        
    public Object clone() {
        SubregionPrototype clone = new SubregionPrototype(   name.getValue(), 
                                        capital.getValue(), 
                                        leader.getValue(), 
                                        color, flag);
        clone.setArea(this.getArea());
        return clone;
    }
    
    public boolean equals(Object obj) {
        return this == obj;
    }
}