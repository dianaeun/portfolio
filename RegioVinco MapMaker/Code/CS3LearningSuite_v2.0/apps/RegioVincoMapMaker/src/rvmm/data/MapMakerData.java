package rvmm.data;

import djf.components.AppDataComponent;
import djf.modules.AppGUIModule;
import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import static rvmm.MapMakerPropertyType.RVMM_CENTER_X_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_CENTER_Y_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_BORDER_COLOR_PICKER;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_BORDER_THICKNESS_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_CYCLE_METHOD_COMBO_BOX;
import static rvmm.MapMakerPropertyType.RVMM_FOCUS_ANGLE_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_FOCUS_DISTENCE_SLIDER;
import rvmm.RegioVincoMapMakerApp;
import static rvmm.MapMakerPropertyType.RVMM_ITEMS_TABLE_VIEW;
import static rvmm.MapMakerPropertyType.RVMM_MAP_PANE;
import static rvmm.MapMakerPropertyType.RVMM_NAME_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_PROPORTIONAL_CHECK_BOX;
import static rvmm.MapMakerPropertyType.RVMM_RADIUS_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_STOP_0_COLOR_PICKER;
import static rvmm.MapMakerPropertyType.RVMM_STOP_1_COLOR_PICKER;
import rvmm.workspace.MapMakerWorkspace;

/**
 *
 * @author McKillaGorilla, Daye Eun
 */
public class MapMakerData implements AppDataComponent {
    RegioVincoMapMakerApp app;
    
    Pane map;
    
    Color borderColor = Color.BLACK;
    double borderThickness = 1.0;
    
    ObservableList<SubregionPrototype> subregions;
    TableViewSelectionModel subregionSelectionModel;
    String nameProperty;
    String exportPath;
    boolean capitalExists = false;
    boolean flagExists = false;
    boolean leaderExists = false;
    
    double mapHeight = 536;
    double mapWidth = 802;
    
    ObservableList<ImageView> images;
    ObservableList<StringProperty> imagePaths;
    
    double angle = 0;
    double distance = 0;
    boolean proportional = false;
    double centerX = 900;
    double centerY = 600;
    double radius = 960;
    CycleMethod cycleMethod = CycleMethod.NO_CYCLE;
    Stop stop0 = new Stop(0, Color.web("#5b92e5"));
    Stop stop1 = new Stop(1, Color.BLACK);
    RadialGradient oceanGradient;
    
    int subregionId = 0;
    
    final double DEFAULT_LINE_THICKNESS = 1.0;
    
    boolean imgSelected = false;
    
    double scale = 1.0;
    double translateX = 0;
    double translateY = 0;
    
    public MapMakerData(RegioVincoMapMakerApp initApp) {
        app = initApp;
        
        // GET ALL THE THINGS WE'LL NEED TO MANIUPLATE THE TABLE
        TableView tableView = (TableView)app.getGUIModule().getGUINode(RVMM_ITEMS_TABLE_VIEW);
        subregions = tableView.getItems();
        subregionSelectionModel = tableView.getSelectionModel();
        subregionSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        
        images = FXCollections.observableArrayList();
        imagePaths = FXCollections.observableArrayList();
        
        // AND FOR LIST NAME AND OWNER DATA
        nameProperty = "Unknown";
        exportPath = "./export/";
        map = (Pane)app.getGUIModule().getGUINode(RVMM_MAP_PANE);
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        oceanGradient = new RadialGradient(angle, distance, centerX, centerY, radius, proportional, cycleMethod, stop0, stop1);
        ocean.setFill(oceanGradient);    
        setSavedSliders();
        setBorderData();
    }
    
    public RadialGradient newOceanGradient() {
        RadialGradient newOcean = new RadialGradient(angle, distance, centerX, centerY, radius, proportional, cycleMethod, stop0, stop1);
        return newOcean;
    }
    
    public void setSavedSliders() {
        ((Slider)app.getGUIModule().getGUINode(RVMM_FOCUS_ANGLE_SLIDER)).setValue(angle);
        ((Slider)app.getGUIModule().getGUINode(RVMM_FOCUS_DISTENCE_SLIDER)).setValue(distance);
        ((Slider)app.getGUIModule().getGUINode(RVMM_CENTER_X_SLIDER)).setValue(centerX);
        ((Slider)app.getGUIModule().getGUINode(RVMM_CENTER_Y_SLIDER)).setValue(centerY);
        ((Slider)app.getGUIModule().getGUINode(RVMM_RADIUS_SLIDER)).setValue(radius);
        ((CheckBox)app.getGUIModule().getGUINode(RVMM_PROPORTIONAL_CHECK_BOX)).setSelected(proportional);
        ((ComboBox)app.getGUIModule().getGUINode(RVMM_CYCLE_METHOD_COMBO_BOX)).setValue(cycleMethod.toString());
        ((ColorPicker)app.getGUIModule().getGUINode(RVMM_STOP_0_COLOR_PICKER)).setValue(stop0.getColor());
        ((ColorPicker)app.getGUIModule().getGUINode(RVMM_STOP_1_COLOR_PICKER)).setValue(stop1.getColor());    
    }
    
    public void setBorderData() {
        ((ColorPicker)app.getGUIModule().getGUINode(RVMM_CHANGE_BORDER_COLOR_PICKER)).setValue(borderColor);
        ((Slider)app.getGUIModule().getGUINode(RVMM_CHANGE_BORDER_THICKNESS_SLIDER)).setValue(borderThickness);
    }
    
    public void setOceanGradient(RadialGradient p) {
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        angle = p.getFocusAngle();
        distance = p.getFocusDistance();
        centerX = p.getCenterX();
        centerY = p.getCenterY();
        radius = p.getRadius();
        proportional = p.isProportional();
        cycleMethod = p.getCycleMethod();
        stop0 = p.getStops().get(0);
        stop1 = p.getStops().get(1);
        ocean.setFill(p);
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public void setCenterX(double x) {
        centerX = x;
    }
    
    public void setCenterY(double y) {
        centerY = y;
    }
    
    public void setRadius(double r) {
        radius = r;
    }
    
    public void setProportional(boolean b) {
        proportional = b;
    } 
    
    public void setCycleMethod(String s) {
        for(int i = 0; i < CycleMethod.values().length; i++) {
            if(CycleMethod.values()[i].name().equals(s)) {
                cycleMethod = CycleMethod.values()[i];
                return;
            }
        }
    }
    
    public void setStop0(Color c) {
        stop0 = new Stop(0, c);
    }
    
    public void setStop1(Color c) {
        stop1 = new Stop(1, c);
    }
    
    public void setBorders(double t, Color c) {
        borderColor = c;
        borderThickness = t;
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public double getBorderThickness() {
        return borderThickness;
    }
           
    public ObservableList<SubregionPrototype> reassignColors() {
        ObservableList<SubregionPrototype> newColoredSubregion = FXCollections.observableArrayList();
        ArrayList<Integer> randomColor = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < subregions.size(); i++) {
            int random = r.nextInt(250) + 1;
            while(randomColor.contains(random)) {
                random = r.nextInt(250) + 1;
            }
            randomColor.add(random);
            Color color = Color.rgb(random, random, random);
            SubregionPrototype subregion = (SubregionPrototype)subregions.get(i).clone();
            subregion.setColor(color);
            subregion.setAreaInfo(borderColor, borderThickness/map.getScaleX());
            newColoredSubregion.add(subregion);
        }
        return newColoredSubregion;
    }
    
    public ObservableList<Color> getNewColors() {
        ObservableList<Color> newColors = FXCollections.observableArrayList();
        ArrayList<Integer> randomColor = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < subregions.size(); i++) {
            int random = r.nextInt(250) + 1;
            while(randomColor.contains(random)) {
                random = r.nextInt(250) + 1;
            }
            randomColor.add(random);
            Color color = Color.rgb(random, random, random);
            newColors.add(color);
        }
        return newColors;
    }
    
    public void setSubregions(ObservableList<SubregionPrototype> initSubregion) {
        subregions.clear();
        subregions.addAll(initSubregion);
    }
    
    public ObservableList<SubregionPrototype> getSubregions() {
          return subregions;
    }
    
    public ObservableList<SubregionPrototype> copySubregions() {
        ObservableList<SubregionPrototype> copiedSubregion = FXCollections.observableArrayList();
        for(int i = 0; i < subregions.size(); i++) {
            copiedSubregion.add((SubregionPrototype)subregions.get(i).clone());
        }
        
          return copiedSubregion;
    }
    
    public void addImages(ImageView img) {
        images.add(img);
        ((Pane)map.getParent()).getChildren().add(img);
        ((MapMakerWorkspace)app.getWorkspaceComponent()).setImageViewFunctions(img);
    }
    
    public void removeImages(ImageView img) {
        images.remove(img);
        ((Pane)map.getParent()).getChildren().remove(img);
    }
    
    public ObservableList<ImageView> getImages() {
        return images;
    }
    
    public void addImagePath(StringProperty path) {
        imagePaths.add(path);
    }
    
    public ObservableList<StringProperty> getImagePaths() {
        return imagePaths;
    }
    
    public double getMapDimensionWidth() {
        return mapWidth;
    }
    
    public void setMapDimensionWidth(double w) {
        mapWidth = w;
    }
    
    public double getMapDimensionHeight() {
        return mapHeight;
    }
    
    public void setMapDimensionHeight(double h) {
        mapHeight = h;
    }
    
    public boolean getProportional() {
        return proportional;
    }
    
    public double getFocusAngle() {
        return angle;
    }
    
    public double getFocusDistance() {
        return distance;
    }
    
    public double getCenterX() {
        return centerX;
    }
    
    public double getCenterY() {
        return centerY;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public CycleMethod getCycleMethod() {
        return cycleMethod;
    }
    
    public Stop getStop0() {
        return stop0;
    }
    
    public Stop getStop1() {
        return stop1;
    }

    public void setOceanGradient(double fa, double fd, double x, double y, double r, boolean p, CycleMethod c, Stop st1, Stop st2) {
        oceanGradient = new RadialGradient(fa, fd, x, y, r, p, c, st1, st2);   
        angle = fa;
        distance = fd;
        centerX = x;
        centerY = y;
        radius = r;
        proportional = p;
        cycleMethod = c;
        stop0 = st1;
        stop1 = st2;
        ((Rectangle)map.getChildren().get(0)).setFill(oceanGradient);
    }
    
    public double getMapHeight() {
        return getOuterPane().getHeight();
    }
    
    public double getMapWidth() {
        return getOuterPane().getWidth();
    }
    
    public String getName() {
        return nameProperty;
    }
    
    public BorderPane getOuterPane() {
        return (BorderPane)map.getParent().getParent();
    }
    
    public void changeDimension(double w, double h) {
        BorderPane outerPane = getOuterPane();
        mapHeight = h;
        mapWidth = w;
        ((Rectangle)map.getChildren().get(0)).setHeight(h);
        ((Rectangle)map.getChildren().get(0)).setWidth(h*2);
        outerPane.setMinWidth(w);
        outerPane.setMaxWidth(w);
        outerPane.setMinHeight(h);
        outerPane.setMaxHeight(h);  
    }
    
    public void setExportPath(String path) {
        exportPath = path;
    }
    
    public String getExportPath() {
        return exportPath;
    }
    
    public Iterator<SubregionPrototype> subregionsIterator() {
        return this.subregions.iterator();
    }
    
    public void setName(String initName) {
        nameProperty = initName;
        ((Label)app.getGUIModule().getGUINode(RVMM_NAME_LABEL)).setText(initName);
    }
    
    public boolean doesCapitalExists() {
        for(SubregionPrototype s: subregions) {
            if(s.getCapital().equals("")) {
                capitalExists = false;
                return capitalExists;
            }
        }
        capitalExists = true;
        return capitalExists;
    }
    
    public boolean doesFlagExists() {
        String path = getExportPath() + nameProperty + "/";
        for(SubregionPrototype s: subregions) {
            String name = s.getName();
            path = path + name + " Flag.png";
            File img = new File(path);
            if(!img.exists()) {
                flagExists = false;
                return flagExists;
            }
        }
        flagExists = true;
        return flagExists;
    }
    
    public boolean doesLeaderExists() {
        for(SubregionPrototype s: subregions) {
            if(s.getLeader().equals("")) {
                leaderExists = false;
                return leaderExists;
            }
        }
        leaderExists = true;
        return leaderExists;
    }
    
    public void setCapitalExists(boolean exist) {
        capitalExists = exist;
    }
    
    public void setFlagExists(boolean exist) {
        flagExists = exist;
    }
    
    public void setLeaderExists(boolean exist) {
        leaderExists = exist;
    }
    
    public double getScale() {
        scale = map.getScaleX();
        return scale;
    }
    
    public void setScale(double s) {
        scale = s;
        map.setScaleX(s);
        map.setScaleY(s);
    }
    
    public double getTranslateX() {
        translateX = map.getTranslateX();
        return translateX;
    }
    
    public void setTranslateX(double x) {
        translateX = x;
        map.setTranslateX(x);
    }
    
    public double getTranslateY() {
        translateY = map.getTranslateY();
        return translateY;
    }
    
    public void setTranslateY(double y) {
        translateY = y;
        map.setTranslateY(y);
    }

    @Override
    public void reset() {
        AppGUIModule gui = app.getGUIModule();
        
        scale = 1.0;
        translateX = 0;
        translateY = 0;
        
        map.setScaleX(scale);
        map.setScaleY(scale);
        map.setTranslateX(translateX);
        map.setTranslateY(translateY);
                
        imagePaths.clear();
        images.clear();

        subregions.clear();
        
        // AND THE POLYGONS THEMSELVES
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        map.getChildren().clear();
        map.getChildren().add(ocean);
        
        Pane clipped = (Pane)map.getParent();
        clipped.getChildren().clear();
        clipped.getChildren().add(map);
        
        // CLEAR OUT THE ITEMS FROM THE TABLE
        TableView tableView = (TableView)gui.getGUINode(RVMM_ITEMS_TABLE_VIEW);
        subregions = tableView.getItems();
        subregions.clear();
    }
    
    double minX;
    double maxX;
    double minY;
    double maxY;
    
    public void findMinMax() {
        ObservableList<Double> init = subregions.get(0).getArea().get(0).getPoints();
        minX = init.get(0);
        maxX = init.get(0);
        minY = init.get(1);
        maxY = init.get(1);
        for(int i = 0; i < subregions.size(); i++) {
            ObservableList<Polygon> subregion = subregions.get(i).getArea();
            for(int j = 0; j < subregion.size(); j++) {
                Polygon p = subregion.get(j);
                ObservableList<Double> points = p.getPoints();
                for(int k = 0; k < points.size(); k+=2) {
                    double x = points.get(k);
                    double y = points.get(k+1);
                    if(minX > x) {minX = x;}
                    if(maxX < x) {maxX = x;}
                    if(minY > y) {minY = y;}
                    if(maxY < y) {maxY = y;}
                }
            }
        }
    }
    
    public double getMaxX() {
        return maxX;
    }
    
    public double getMaxY() {
        return maxY;
    }
    
    public double getMinX() {
        return minX;
    }
        
    public double getMinY() {
        return minY;
    }    

    public void resetViewport() {
        scaleMap(1.0);
        moveMap(0, 0);
    }

    private void scaleMap(double zoomScale) {
        map.scaleXProperty().setValue(zoomScale);
        map.scaleYProperty().setValue(zoomScale);
    }
    
    private void moveMap(double x, double y) {
        map.translateXProperty().set(x);
        map.translateYProperty().set(y);
    }
                    
    public void adjustLines(double thickness, Color c) {
        Iterator<SubregionPrototype> sub = subregions.iterator();
        while (sub.hasNext()) {
            SubregionPrototype subregion = (SubregionPrototype)sub.next().clone();
            Iterator<Polygon> polyIt = subregion.getArea().iterator();
            while (polyIt.hasNext()) {
                Polygon poly = polyIt.next();
                poly.setStrokeWidth(thickness/map.getScaleX());
                poly.setStroke(c);
            }
        }
        refreshSubregion();
    }
     
    public void adjustLineThickness() {
        double scale = map.scaleXProperty().doubleValue();
        Iterator<SubregionPrototype> sub = subregions.iterator();
        while (sub.hasNext()) {
            SubregionPrototype subregion = sub.next();
            Iterator<Polygon> polyIt = subregion.getArea().iterator();
            while (polyIt.hasNext()) {
                Polygon poly = polyIt.next();
                poly.setStrokeWidth(DEFAULT_LINE_THICKNESS/map.getScaleX());
                poly.setStroke(Color.BLACK);
            }
        }
        refreshSubregion();
    }
            
    public boolean isSubregionSelected() {
        ObservableList<SubregionPrototype> selectedItems = this.getSelectedSubregions();
        return (selectedItems != null) && (selectedItems.size() == 1);
    }
    
    public boolean areSubretgionsSelected() {
        ObservableList<SubregionPrototype> selectedItems = this.getSelectedSubregions();
        return (selectedItems != null) && (selectedItems.size() > 1);        
    }

    public boolean isValidSubregionEdit(SubregionPrototype itemToEdit, String category, String description, LocalDate startDate, LocalDate endDate, String assignedTo, boolean completed) {
        return isValidNewSubregion(category, description, startDate, endDate, assignedTo, completed);
    }

    public boolean isValidNewSubregion(String category, String description, LocalDate startDate, LocalDate endDate, String assignedTo, boolean completed) {
        if (category.trim().length() == 0)
            return false;
        if (description.trim().length() == 0)
            return false;
        if (startDate.isAfter(endDate))
            return false;
        return true;
    }
    
    public void removeMapPolygons() {
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        map.getChildren().clear();
        map.getChildren().add(ocean);
    }
        
    public void addMapPolygons() {
        for(int i = 0; i < subregions.size(); i++) {
            subregions.get(i).setAreaInfo(borderColor, borderThickness/map.getScaleX());
            map.getChildren().addAll(subregions.get(i).getArea());
        }
    }
            
    public void refreshSubregion() {
        removeMapPolygons();
        addMapPolygons();
    }
        
    public void addSubregion(SubregionPrototype itemToAdd, ArrayList<ArrayList<Double>> rawPolygons) {
        for (int i = 0; i < rawPolygons.size(); i++) {
            ArrayList<Double> rawPolygonPoints = rawPolygons.get(i);
            Polygon polygonToAdd = new Polygon();
            ObservableList<Double> transformedPolygonPoints = polygonToAdd.getPoints();
            for (int j = 0; j < rawPolygonPoints.size(); j+=2) {
                double longX = rawPolygonPoints.get(j);
                double latY = rawPolygonPoints.get(j+1);
                double x = longToX(longX);
                double y = latToY(latY);
                transformedPolygonPoints.addAll(x, y);
            }
            polygonToAdd.setFill(itemToAdd.getColor());
            itemToAdd.addArea(polygonToAdd);
            map.getChildren().add(polygonToAdd);
            polygonToAdd.setStrokeWidth(borderThickness/map.getScaleX());
            polygonToAdd.setStroke(borderColor);
        }
        subregions.add(itemToAdd);
        subregionId++; 
    }
    
    public ArrayList<ArrayList<Double>> saveSubregion(SubregionPrototype subregionToSave) {
        ArrayList<ArrayList<Double>> polygons = new ArrayList<ArrayList<Double>>();
        ObservableList<Polygon> sub = subregionToSave.getArea();
        for (int num = 0; num < sub.size(); num++) {
            Polygon p = sub.get(num);
            ArrayList<Double> rawPoints = new ArrayList<Double>();
            ObservableList<Double> points = p.getPoints();
                for(int k = 0; k < points.size(); k+=2) {
                    double x = points.get(k);
                    double y = points.get(k+1);
                    double longX = xToLong(x);
                    double latY = yToLat(y);
                    rawPoints.add(longX);
                    rawPoints.add(latY);
                }
                polygons.add(rawPoints);
            }
        return polygons;
    }
    
    public ObservableList<ArrayList<ArrayList<Double>>> getRelocatePolygons() {
        ObservableList<ArrayList<ArrayList<Double>>> newArea = FXCollections.observableArrayList();
        for(SubregionPrototype s : subregions) {
            ObservableList<Polygon> area = s.getArea();
            ArrayList<ArrayList<Double>> rawPoints = new ArrayList<ArrayList<Double>>();
            for (int num = 0; num < area.size(); num++) {
                Polygon p = area.get(num);
                ArrayList<Double> rawPoint = new ArrayList<Double>();
                ObservableList<Double> points = p.getPoints();
                for(int k = 0; k < points.size(); k+=2) {
                    double x = points.get(k);
                    double y = points.get(k+1);
                    double longX = xToLong(x);
                    double latY = yToLat(y);
                    rawPoint.add(longX);
                    rawPoint.add(latY);
                }
                rawPoints.add(rawPoint);
            }
            newArea.add(rawPoints);
        }    
        return newArea;    
    }
    
    public void setRelocatePolygons(ObservableList<ArrayList<ArrayList<Double>>> newArea) {
        for(int j = 0; j < subregions.size(); j++) {
            SubregionPrototype s = subregions.get(j);
            ObservableList<Polygon> area = FXCollections.observableArrayList();
            ArrayList<ArrayList<Double>> rawPoints = newArea.get(j);
            for (int i = 0; i < rawPoints.size(); i++) {
                ArrayList<Double> rawPoint = rawPoints.get(i);
                Polygon polygonToAdd = new Polygon();
                ObservableList<Double> transformedPolygonPoints = polygonToAdd.getPoints();
                for (int k = 0; k < rawPoint.size(); k+=2) {
                    double longX = rawPoint.get(k);
                    double latY = rawPoint.get(k+1);
                    double x = longToX(longX);
                    double y = latToY(latY);
                    transformedPolygonPoints.addAll(x, y);
                }
                polygonToAdd.setFill(s.getColor());
                polygonToAdd.setStrokeWidth(borderThickness/map.getScaleX());
                polygonToAdd.setStroke(borderColor);
                area.add(polygonToAdd);
            }
            s.setArea(area);
        }
        refreshSubregion();
    }
    
    private double longToX(double longCoord) {
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        double paneHeight = ocean.getHeight();
        double unitDegree = paneHeight/180;
        double newLongCoord = (longCoord + 180) * unitDegree;
        return newLongCoord;
    }

    private double latToY(double latCoord) {
        // DEFAULT WILL SCALE TO THE HEIGHT OF THE MAP PANE
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        double paneHeight = ocean.getHeight();
        
        // WE ONLY WANT POSITIVE COORDINATES, SO SHIFT BY 90
        double unitDegree = paneHeight/180;
        double newLatCoord = (latCoord + 90) * unitDegree;
        return paneHeight - newLatCoord;
    }
    
    private double xToLong(double x) {
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        double paneHeight = ocean.getHeight();
        double unitDegree = paneHeight/180;
        double longCoord = (x/unitDegree) - 180;
        return longCoord;
    }
    
    private double yToLat(double y) {
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        double paneHeight = ocean.getHeight();
        double unitDegree = paneHeight/180;
        double latCoord = ((paneHeight - y) / unitDegree) - 90;
        return latCoord;
    }
    
    public void removeSubregion(int index) {
        subregions.remove(index);
    }

    public SubregionPrototype getSelectedSubregion() {
        if (!isSubregionSelected()) {
            return null;
        }
        return getSelectedSubregions().get(0);
    }
    
    public ObservableList<SubregionPrototype> getSelectedSubregions() {
        return (ObservableList<SubregionPrototype>)this.subregionSelectionModel.getSelectedItems();
    }
    
    public void setSelectedSubregion(SubregionPrototype sub) {
        this.subregionSelectionModel.clearAndSelect(getSubregionIndex(sub));
    }

    public int getSubregionIndex(SubregionPrototype subregion) {
        return subregions.indexOf(subregion);
    }
    
    public SubregionPrototype getSubregionAtIndex(int i) {
        if((i >= 0) && (i < subregions.size())) 
            return subregions.get(i);
        return null;
    }

    public void addSubregionAt(SubregionPrototype subregion, int index) {
        subregions.add(index, subregion);
    }

    public void moveSubregion(int oldIndex, int newIndex) {
        SubregionPrototype subregionToMove = subregions.remove(oldIndex);
        subregions.add(newIndex, subregionToMove);
    }

    public int getNumSubregions() {
        return subregions.size();
    }

    public void selectSubregion(SubregionPrototype subregionToSelect) {
        this.subregionSelectionModel.clearSelection();
        this.subregionSelectionModel.select(subregionToSelect);
    }

    public ArrayList<SubregionPrototype> getCurrentSubregionsOrder() {
        ArrayList<SubregionPrototype> orderedSubregions = new ArrayList();
        for (SubregionPrototype subregion : subregions) {
            orderedSubregions.add(subregion);
        }
        return orderedSubregions;
    }

    public void clearSelected() {
        this.subregionSelectionModel.clearSelection();
    }

    public void sortSubregions(Comparator sortComparator) {
        Collections.sort(subregions, sortComparator);
    }
    
    public void setImageSelected(boolean b) {
        imgSelected = b;
    }
    
    public boolean getImageSelected() {
        return imgSelected;
    }

    public void rearrangeSubregion(ArrayList<SubregionPrototype> oldListOrder) {
        subregions.clear();
        for (SubregionPrototype subregion : oldListOrder) {
            subregions.add(subregion);
        }
    }
    
    public SubregionPrototype findSubregion(Polygon p) {
        for(SubregionPrototype s : subregions) {
            if(s.getArea().contains(p)) {
                return s;
            }
        }
        return null;
    }
    
    
}