package rvmm.files;

import static djf.AppPropertyType.APP_PATH_WORK;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.swing.text.html.HTML;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import properties_manager.PropertiesManager;
import rvmm.RegioVincoMapMakerApp;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;

/**
 *
 * @author Daye Eun
 */
public class MapMakerFiles implements AppFileComponent {
    RegioVincoMapMakerApp app;
    
    // FOR JSON SAVING AND LOADING
    static final String JSON_PROPORTIONAL = "PROPORTIONAL";
    static final String JSON_FOCUS_ANGLE = "FOCUS_ANGLE";
    static final String JSON_FOCUS_DISTANCE = "FOCUS_DISTANCE";
    static final String JSON_CENTER_X = "CENTER_X";
    static final String JSON_CENTER_Y = "CENTER_Y";
    static final String JSON_RADIUS = "RADIUS";
    static final String JSON_CYCLE_METHOD = "CYCLE_METHOD";
    static final String JSON_STOP_0_COLOR = "STOP_0_COLOR";
    static final String JSON_STOP_1_COLOR = "STOP_1_COLOR";
    
    static final String JSON_BORDER_COLOR = "BORDER_COLOR";
    static final String JSON_BORDER_THICKNESS = "BORDER_THICKNESS";
    
    static final String JSON_MAP_DIMENSION_WIDTH = "MAP_WIDTH";
    static final String JSON_MAP_DIMENSION_HEIGHT = "MAP_HEIGHT";
    
    static final String JSON_WORKSPACE_IMAGES = "WORKSPACE_IMAGES";
    static final String JSON_IMAGE_PATH = "IMAGE_PATH";
    static final String JSON_IMAGE_X = "IMAGE_X";
    static final String JSON_IMAGE_Y = "IMAGE_Y";
    
    static final String JSON_NAME = "REGION_NAME";
    static final String JSON_PARENT_PATH = "PARENT_DIRECTORY";
    static final String JSON_HAVE_CAPITALS = "SUBREGIONS_HAVE_CAPITALS";
    static final String JSON_HAVE_FLAGS = "SUBREGIONS_HAVE_FLAGS";
    static final String JSON_HAVE_LEADERS = "SUBREGIONS_HAVE_LEADERS";
    static final String JSON_SCALE = "SCALE";
    static final String JSON_TRANSLATE_X = "TRANSLATE_X";
    static final String JSON_TRANSLATE_Y = "TRANSLATE_Y";
    static final String JSON_NUMBER_OF_SUBREGIONS = "NUMBER_OF_SUBREGIONS";
    
    static final String JSON_SUBREGIONS = "SUBREGIONS";
    static final String JSON_SUBREGION_NAME = "NAME";
    static final String JSON_CAPITAL = "CAPITAL";
    static final String JSON_LEADER = "LEADER";
    static final String JSON_COLOR_RED = "RED";
    static final String JSON_COLOR_GREEN = "GREEN";
    static final String JSON_COLOR_BLUE = "BLUE";
    static final String JSON_NUMBER_OF_SUBREGION_POLYGONS = "NUMBER_OF_SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGONS = "SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGON = "SUBREGION_POLYGON";
    static final String JSON_POLYGON_POINTS = "VERTICES";
    static final String JSON_POLYGON_POINT_X = "X";
    static final String JSON_POLYGON_POINT_Y = "Y";
    
    static final String RVM_NAME = "name";
    static final String RVM_HAVE_CAPITALS = "subregions_have_capitals";
    static final String RVM_HAVE_FLAGS = "subregions_have_flags";
    static final String RVM_HAVE_LEADERS = "subregions_have_leaders";
    static final String RVM_SUBREGIONS = "subregions";
    static final String RVM_SUBREGION_NAME = "name";
    static final String RVM_CAPITAL = "capital";
    static final String RVM_LEADER = "leader";
    static final String RVM_COLOR_RED = "red";
    static final String RVM_COLOR_GREEN = "green";
    static final String RVM_COLOR_BLUE = "blue";
    
    ArrayList<Integer> randomColor = new ArrayList<>();
    // FOR EXPORTING TO HTML;
    
    public MapMakerFiles(RegioVincoMapMakerApp initApp) {
        app = initApp;
    }
    
    /**
     * This method is for saving user work.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        //  GET THE DATA
        MapMakerData mapData = (MapMakerData)data;
        
        boolean proportional = mapData.getProportional();
        double focusAngle = mapData.getFocusAngle();
        double focusDistance = mapData.getFocusDistance();
        double centerX = mapData.getCenterX();
        double centerY = mapData.getCenterY();
        double radius = mapData.getRadius();
        CycleMethod method = mapData.getCycleMethod();
        Stop stop0 = mapData.getStop0();
        Stop stop1 = mapData.getStop1();
        
        double thickness = mapData.getBorderThickness();
        String borderColor = mapData.getBorderColor().toString();
        
        double mapWidth = mapData.getMapDimensionWidth();
        double mapHeight = mapData.getMapDimensionHeight();
        
        String name = mapData.getName();
        String parent = mapData.getExportPath();
        boolean haveCapital = mapData.doesCapitalExists();
        boolean haveFlags = mapData.doesFlagExists();
        boolean haveLeaders = mapData.doesLeaderExists();
        double scale = mapData.getScale();
        double x = mapData.getTranslateX();
        double y = mapData.getTranslateY();
        int numOfSubregion = mapData.getNumSubregions();
        
        JsonArrayBuilder imgArrayBuilder = Json.createArrayBuilder();
        ObservableList<ImageView> workspaceImages = mapData.getImages();
        ObservableList<StringProperty> workspaceImagePaths = mapData.getImagePaths();
        for(int i = 0; i < workspaceImages.size(); i++) {
            JsonObjectBuilder images = Json.createObjectBuilder()
                    .add(JSON_IMAGE_PATH, workspaceImagePaths.get(i).getValue())
                    .add(JSON_IMAGE_X, workspaceImages.get(i).getTranslateX())
                    .add(JSON_IMAGE_Y, workspaceImages.get(i).getTranslateY());
            imgArrayBuilder.add(images);
            images.build();
        }
        JsonArray imgArray = imgArrayBuilder.build();
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Iterator<SubregionPrototype> itemsIt = mapData.subregionsIterator();
	while (itemsIt.hasNext()) {	
            SubregionPrototype subregion = itemsIt.next();
	    JsonObjectBuilder itemJson = Json.createObjectBuilder()
		    .add(JSON_SUBREGION_NAME, subregion.getName());
            if(haveCapital) {
                itemJson.add(JSON_CAPITAL, subregion.getCapital());
            }
            if(haveLeaders) {
                itemJson.add(JSON_LEADER, subregion.getLeader());
            }
            itemJson.add(JSON_COLOR_RED, (int)(subregion.getColor().getRed()*255))
                    .add(JSON_COLOR_GREEN, (int)(subregion.getColor().getGreen()*255))
                    .add(JSON_COLOR_BLUE, (int)(subregion.getColor().getBlue()*255))
		    .add(JSON_NUMBER_OF_SUBREGION_POLYGONS, subregion.getArea().size());
            
            JsonArrayBuilder polygons = Json.createArrayBuilder();
            ArrayList<ArrayList<Double>> polygonPoints = mapData.saveSubregion(subregion);
            int numOfPolygons = polygonPoints.size();
            for(int j = 0; j < numOfPolygons; j++) {
                JsonArrayBuilder points = Json.createArrayBuilder();
                ArrayList<Double> rawPoints = polygonPoints.get(j);
                int numOfPoints = rawPoints.size();
                for(int k = 0; k < numOfPoints; k+=2) {
                    double rawX = rawPoints.get(k);
                    double rawY = rawPoints.get(k+1);
                    JsonObjectBuilder point = Json.createObjectBuilder()
                            .add(JSON_POLYGON_POINT_X, rawX)
                            .add(JSON_POLYGON_POINT_Y, rawY);
                    points.add(point);
                    point.build();
                }
                polygons.add(points);
                points.build();
            } 
            itemJson.add(JSON_SUBREGION_POLYGONS, polygons);
            polygons.build();
            // add polygon points!
	    arrayBuilder.add(itemJson.build());
	}
	JsonArray subregionArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject toDoDataJSO = Json.createObjectBuilder()
                .add(JSON_PROPORTIONAL, proportional)
                .add(JSON_FOCUS_ANGLE, focusAngle)
                .add(JSON_FOCUS_DISTANCE, focusDistance)
                .add(JSON_CENTER_X, centerX)
                .add(JSON_CENTER_Y, centerY)
                .add(JSON_RADIUS, radius)
                .add(JSON_CYCLE_METHOD, method.name())
                .add(JSON_STOP_0_COLOR, stop0.getColor().toString())
                .add(JSON_STOP_1_COLOR, stop1.getColor().toString())
                .add(JSON_BORDER_COLOR, borderColor)
                .add(JSON_BORDER_THICKNESS, thickness)
                .add(JSON_MAP_DIMENSION_WIDTH, mapWidth)
                .add(JSON_MAP_DIMENSION_HEIGHT, mapHeight)
                .add(JSON_WORKSPACE_IMAGES, imgArray)
                .add(JSON_NAME, name)
                .add(JSON_PARENT_PATH, parent)
                .add(JSON_HAVE_CAPITALS, haveCapital)
		.add(JSON_HAVE_FLAGS, haveFlags)
                .add(JSON_HAVE_LEADERS, haveLeaders)
                .add(JSON_SCALE, scale)
                .add(JSON_TRANSLATE_X, x)
                .add(JSON_TRANSLATE_Y, y)
                .add(JSON_NUMBER_OF_SUBREGIONS, numOfSubregion)
                .add(JSON_SUBREGIONS, subregionArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(toDoDataJSO);
	jsonWriter.close();

	// INIT THE WRITER
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String newFilePath = props.getProperty(APP_PATH_WORK) + mapData.getName();
        File saveDir = new File(newFilePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        String fileToSave = mapData.getName() + ".json";
        newFilePath = newFilePath + "/" + fileToSave;
        File file = new File(newFilePath);
        if(!file.exists()) {filePath = newFilePath; }
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(toDoDataJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
        app.getRecentWorkModule().startWork(file);
    }
    
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error
     * reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	MapMakerData mapMakerData = (MapMakerData)data;
	mapMakerData.reset();
        
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        boolean proportional = json.getBoolean(JSON_PROPORTIONAL);
        mapMakerData.setProportional(proportional);
        double focusAngle = getDataAsDouble(json, JSON_FOCUS_ANGLE);
        mapMakerData.setAngle(focusAngle);
        double focusDistance = getDataAsDouble(json, JSON_FOCUS_DISTANCE);
        mapMakerData.setDistance(focusDistance);
        double centerX = getDataAsDouble(json, JSON_CENTER_X);
        mapMakerData.setCenterX(centerX);
        double centerY = getDataAsDouble(json, JSON_CENTER_Y);
        mapMakerData.setCenterY(centerY);
        double radius = getDataAsDouble(json, JSON_RADIUS);
        mapMakerData.setRadius(radius);
        String method = json.getString(JSON_CYCLE_METHOD);
        String stop0Color = json.getString(JSON_STOP_0_COLOR);
        String stop1Color = json.getString(JSON_STOP_1_COLOR);
        Stop stop0 = new Stop(0, Color.valueOf(stop0Color));
        mapMakerData.setStop0(Color.valueOf(stop0Color));
        Stop stop1 = new Stop(1, Color.valueOf(stop1Color));
        mapMakerData.setStop1(Color.valueOf(stop1Color));
        mapMakerData.setOceanGradient(focusAngle, focusDistance, centerX, centerY, radius, proportional, CycleMethod.valueOf(method), stop0, stop1);
        mapMakerData.setSavedSliders();
        
        String color = json.getString(JSON_BORDER_COLOR);
        double thickness = getDataAsDouble(json, JSON_BORDER_THICKNESS);
        mapMakerData.setBorders(thickness, Color.valueOf(color));
        mapMakerData.setBorderData();
        
        double mapW = getDataAsDouble(json, JSON_MAP_DIMENSION_WIDTH);
        double mapH = getDataAsDouble(json, JSON_MAP_DIMENSION_HEIGHT);
        mapMakerData.setMapDimensionWidth(mapW);
        mapMakerData.setMapDimensionHeight(mapH);
        mapMakerData.changeDimension(mapW, mapH);
        
        JsonArray jsonImageArray = json.getJsonArray(JSON_WORKSPACE_IMAGES);
        for(int i = 0; i < jsonImageArray.size(); i++) {
            JsonObject image = jsonImageArray.getJsonObject(i);
            String imagePath = image.getString(JSON_IMAGE_PATH);
            double x = getDataAsDouble(image, JSON_IMAGE_X);
            double y = getDataAsDouble(image, JSON_IMAGE_Y);
            mapMakerData.addImagePath(new SimpleStringProperty(imagePath));
            Image newImage = new Image("file:" + imagePath);
            ImageView newImageView = new ImageView(newImage);
            newImageView.setTranslateX(x);
            newImageView.setTranslateY(y);
            mapMakerData.addImages(newImageView);
        }
        String name = "";
        String parent = "";
        boolean haveCapital = false;
        boolean haveFlags = false;
        boolean haveLeaders = false;
        double scale = 1.0;
        double x = 0;
        double y = 0;
	// LOAD LIST NAME AND OWNER
        name = json.getString(JSON_NAME);
        parent = json.getString(JSON_PARENT_PATH);
        haveCapital = json.getBoolean(JSON_HAVE_CAPITALS);
        haveFlags = json.getBoolean(JSON_HAVE_FLAGS);
        haveLeaders = json.getBoolean(JSON_HAVE_LEADERS);
        scale = getDataAsDouble(json, JSON_SCALE);
        x = getDataAsDouble(json, JSON_TRANSLATE_X);
        y = getDataAsDouble(json, JSON_TRANSLATE_Y);
        mapMakerData.setName(name);
        mapMakerData.setExportPath(parent);
        mapMakerData.setCapitalExists(haveCapital);
        mapMakerData.setFlagExists(haveFlags);
        mapMakerData.setLeaderExists(haveLeaders);
        mapMakerData.setScale(scale);
        mapMakerData.setTranslateX(x);
        mapMakerData.setTranslateY(y);
	// AND NOW LOAD ALL THE ITEMS
        int numSubregions = getDataAsInt(json, JSON_NUMBER_OF_SUBREGIONS);
        JsonArray jsonSubregionsArray = json.getJsonArray(JSON_SUBREGIONS);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String path = mapMakerData.getExportPath() + name + "/";
        File file = new File(path);
        URL folderURL = file.toURI().toURL();
	for (int i = 0; i < numSubregions; i++) {
	    JsonObject jsonSubregion = jsonSubregionsArray.getJsonObject(i);
            SubregionPrototype subregion = loadSubregion(jsonSubregion, haveCapital, haveFlags, haveLeaders, folderURL.toString());
            int numSubregionPolygons = getDataAsInt(jsonSubregion, JSON_NUMBER_OF_SUBREGION_POLYGONS);
            ArrayList<ArrayList<Double>> subregionPolygonPoints = new ArrayList();
            // GO THROUGH ALL OF THIS SUBREGION'S POLYGONS
            for (int polygonIndex = 0; polygonIndex < numSubregionPolygons; polygonIndex++) {
                // GET EACH POLYGON (IN LONG/LAT GEOGRAPHIC COORDINATES)
                JsonArray jsonPolygon = jsonSubregion.getJsonArray(JSON_SUBREGION_POLYGONS);
                JsonArray pointsArray = jsonPolygon.getJsonArray(polygonIndex);
                ArrayList<Double> polygonPointsList = new ArrayList();
                for (int pointIndex = 0; pointIndex < pointsArray.size(); pointIndex++) {
                    JsonObject point = pointsArray.getJsonObject(pointIndex);
                    double pointX = point.getJsonNumber(JSON_POLYGON_POINT_X).doubleValue();
                    double pointY = point.getJsonNumber(JSON_POLYGON_POINT_Y).doubleValue();
                    polygonPointsList.add(pointX);
                    polygonPointsList.add(pointY);
                }
                subregionPolygonPoints.add(polygonPointsList);
            }
	    mapMakerData.addSubregion(subregion, subregionPolygonPoints);
	}
        randomColor.clear();
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    public int getDataAsInt(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber)value;
        return number.bigIntegerValue().intValue();
    }
    
    public SubregionPrototype loadSubregion
        (JsonObject jsonSubregion, boolean haveCapital, boolean haveFlag, boolean haveLeader, String path) {
	// GET THE DATA
        
	String name = "";
        // How to not add the same color?
        name = jsonSubregion.getString(JSON_SUBREGION_NAME);
        int red = getDataAsInt(jsonSubregion, JSON_COLOR_RED);
        int green = getDataAsInt(jsonSubregion, JSON_COLOR_GREEN);
        int blue = getDataAsInt(jsonSubregion, JSON_COLOR_BLUE);
        Color color = Color.rgb(red, green, blue);
        
	// THEN USE THE DATA TO BUILD AN ITEM
        Image flag = null;
        if(haveFlag) {            
            flag = new Image(path + name + " Flag.png");
        }
        SubregionPrototype subregion = new SubregionPrototype(name, color, flag);
        if(haveCapital) {
            String capital = jsonSubregion.getString(JSON_CAPITAL);
            subregion.setCapital(capital);
        }
        if(haveLeader) {
            String leader = jsonSubregion.getString(JSON_LEADER);
            subregion.setLeader(leader);
        }
        
	// ALL DONE, RETURN IT
	return subregion;
    }
        
    public SubregionPrototype loadSubregion(JsonObject jsonSubregion) {
	// GET THE DATA
        
	String name = "";
        // How to not add the same color?
        Random r = new Random();
        int random = r.nextInt(250) + 1;
        while(randomColor.contains(random)) {
            random = r.nextInt(250) + 1;
        }
        randomColor.add(random);
        Color color = Color.rgb(random, random, random);
        
	// THEN USE THE DATA TO BUILD AN ITEM
        Image flag = null;
        SubregionPrototype subregion = new SubregionPrototype(name, color, flag);

	// ALL DONE, RETURN IT
	return subregion;
    }    

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method would be used to export data to another format,
     * which we're not doing in this assignment.
     */
    @Override
    public void exportData(AppDataComponent data, String savedFileName) throws IOException {
        MapMakerData mapData = (MapMakerData)data;
        String mapName = mapData.getName();
        boolean haveCapital = mapData.doesCapitalExists();
        boolean haveFlags = mapData.doesFlagExists();
        boolean haveLeaders = mapData.doesLeaderExists();
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String exportDirPath = mapData.getExportPath() + "/" + mapName + "/";
            File exportDir = new File(exportDirPath);
            if (!exportDir.exists()) {
                exportDir.mkdir();
            }
            Pane mapPane = mapData.getOuterPane();
//            WritableImage defaultImage = new WritableImage(802, 536);
            WritableImage image = mapPane.snapshot(new SnapshotParameters(), null);
                        
            String ImageName = mapName + ".png";
            File imgFile = new File(exportDirPath + ImageName);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imgFile);
            
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            Iterator<SubregionPrototype> itemsIt = mapData.subregionsIterator();
            while (itemsIt.hasNext()) {	
                SubregionPrototype subregion = itemsIt.next();
                JsonObjectBuilder itemJson = Json.createObjectBuilder()
		    .add(RVM_SUBREGION_NAME, subregion.getName());
                if(haveCapital) {
                    itemJson.add(RVM_CAPITAL, subregion.getCapital());
                }
                if(haveLeaders) {
                    itemJson.add(RVM_LEADER, subregion.getLeader());
                }
                itemJson.add(RVM_COLOR_RED, (int)(subregion.getColor().getRed()*255))
                        .add(RVM_COLOR_GREEN, (int)(subregion.getColor().getGreen()*255))
                        .add(RVM_COLOR_BLUE, (int)(subregion.getColor().getBlue()*255));
                arrayBuilder.add(itemJson.build());
            }
            
            JsonArray subregionArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
            JsonObject toDoDataJSO = Json.createObjectBuilder()
                    .add(RVM_NAME, mapName)
                    .add(RVM_HAVE_CAPITALS, haveCapital)
                    .add(RVM_HAVE_FLAGS, haveFlags)
                    .add(RVM_HAVE_LEADERS, haveLeaders)
                    .add(RVM_SUBREGIONS, subregionArray)
                    .build();

            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(toDoDataJSO);
            jsonWriter.close();

            // INIT THE WRITER
            String fileToSave = mapData.getName() + ".rvm";
            String filePath = exportDirPath + fileToSave;
            File file = new File(filePath);
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(toDoDataJSO);
            String prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        
        }
        catch(IOException e) {
            throw new IOException("Error");
        }
    }
    
    private void addCellToRow(Document doc, Node rowNode, String text) {
        Element tdElement = doc.createElement(HTML.Tag.TD.toString());
        tdElement.setTextContent(text);
        rowNode.appendChild(tdElement);
    }
    private Node getNodeWithId(Document doc, String tagType, String searchID) {
        NodeList testNodes = doc.getElementsByTagName(tagType);
        for (int i = 0; i < testNodes.getLength(); i++) {
            Node testNode = testNodes.item(i);
            Node testAttr = testNode.getAttributes().getNamedItem(HTML.Attribute.ID.toString());
            if ((testAttr != null) && testAttr.getNodeValue().equals(searchID)) {
                return testNode;
            }
        }
        return null;
    }
    private void saveDocument(Document doc, String outputFilePath)
            throws TransformerException, TransformerConfigurationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        Result result = new StreamResult(new File(outputFilePath));
        Source source = new DOMSource(doc);
        transformer.transform(source, result);
    }

    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        MapMakerData mapMakerData = (MapMakerData)data;
	mapMakerData.reset();
        
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        int numSubregions = getDataAsInt(json, JSON_NUMBER_OF_SUBREGIONS);
        JsonArray jsonSubregionsArray = json.getJsonArray(JSON_SUBREGIONS);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	for (int i = 0; i < numSubregions; i++) {
	    JsonObject jsonSubregion = jsonSubregionsArray.getJsonObject(i);
            SubregionPrototype subregion = loadSubregion(jsonSubregion);
            int numSubregionPolygons = getDataAsInt(jsonSubregion, JSON_NUMBER_OF_SUBREGION_POLYGONS);
            ArrayList<ArrayList<Double>> subregionPolygonPoints = new ArrayList();
            // GO THROUGH ALL OF THIS SUBREGION'S POLYGONS
            for (int polygonIndex = 0; polygonIndex < numSubregionPolygons; polygonIndex++) {
                // GET EACH POLYGON (IN LONG/LAT GEOGRAPHIC COORDINATES)
                JsonArray jsonPolygon = jsonSubregion.getJsonArray(JSON_SUBREGION_POLYGONS);
                JsonArray pointsArray = jsonPolygon.getJsonArray(polygonIndex);
                ArrayList<Double> polygonPointsList = new ArrayList();
                for (int pointIndex = 0; pointIndex < pointsArray.size(); pointIndex++) {
                    JsonObject point = pointsArray.getJsonObject(pointIndex);
                    double pointX = point.getJsonNumber(JSON_POLYGON_POINT_X).doubleValue();
                    double pointY = point.getJsonNumber(JSON_POLYGON_POINT_Y).doubleValue();
                    polygonPointsList.add(pointX);
                    polygonPointsList.add(pointY);
                }
                subregionPolygonPoints.add(polygonPointsList);
            }
	    mapMakerData.addSubregion(subregion, subregionPolygonPoints);
	}
        randomColor.clear();
    }
}
