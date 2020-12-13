package rvmm.workspace;

import static djf.AppPropertyType.APP_PATH_IMAGES;
import static djf.AppPropertyType.NEW_BUTTON;
import djf.components.AppWorkspaceComponent;
import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.DISABLED;
import static djf.modules.AppGUIModule.ENABLED;
import static djf.modules.AppGUIModule.FOCUS_TRAVERSABLE;
import static djf.modules.AppGUIModule.HAS_KEY_HANDLER;
import djf.ui.AppNodesBuilder;
import djf.ui.dialogs.AppDialogsFacade;
import static djf.ui.style.DJFStyle.CLASS_DJF_ICON_BUTTON;
import static djf.ui.style.DJFStyle.CLASS_DJF_TOOLBAR_PANE;
import java.io.File;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import static rvmm.MapMakerPropertyType.CYCLE_METHOD_DEFAULT;
import static rvmm.MapMakerPropertyType.CYCLE_METHOD_OPTIONS;
import static rvmm.MapMakerPropertyType.RVMM_ADD_IMAGE_BUTTON;
import rvmm.RegioVincoMapMakerApp;
import rvmm.workspace.controllers.SubRegionController;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;
import rvmm.transactions.ChangeMapName_Transaction;
import rvmm.workspace.foolproof.MapMakerFoolproofDesign;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_BOX;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_PROMPT;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_TABLE;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_COLUMN;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_MAP_OCEAN;
import static rvmm.MapMakerPropertyType.RVMM_ITEMS_TABLE_VIEW;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_COLUMN;
import static rvmm.MapMakerPropertyType.RVMM_CAPITAL_COLUMN;
import static rvmm.MapMakerPropertyType.RVMM_CENTER_X_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_CENTER_X_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_CENTER_Y_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_CENTER_Y_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_BORDER_COLOR_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_BORDER_COLOR_PICKER;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_BORDER_THICKNESS_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_BORDER_THICKNESS_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_MAP_DIMENSION_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_MAP_NAME_CONTENT_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_MAP_NAME_TITLE_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_POLYGON_CONTENT_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_POLYGON_SELECT_SUBREGION_CONTENT_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_POLYGON_SELECT_SUBREGION_TITLE_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_POLYGON_TITLE_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CYCLE_METHOD_COMBO_BOX;
import static rvmm.MapMakerPropertyType.RVMM_CYCLE_METHOD_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_FIT_TO_POLYGON_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_FOCUS_ANGLE_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_FOCUS_ANGLE_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_FOCUS_DISTENCE_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_FOCUS_DISTENCE_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_FOOLPROOF_SETTINGS;
import static rvmm.MapMakerPropertyType.RVMM_GRADIENT_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_GRADIENT_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_LEADER_COLUMN;
import static rvmm.MapMakerPropertyType.RVMM_MAP_PANE;
import static rvmm.MapMakerPropertyType.RVMM_MOVE_DOWN_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_MOVE_POLYGON_MODE_CHECK_BOX;
import static rvmm.MapMakerPropertyType.RVMM_MOVE_POLYGON_MODE_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_MOVE_UP_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_NAME_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_OUTER_PANE;
import static rvmm.MapMakerPropertyType.RVMM_PROPORTIONAL_CHECK_BOX;
import static rvmm.MapMakerPropertyType.RVMM_PROPORTIONAL_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_RADIUS_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_RADIUS_SLIDER;
import static rvmm.MapMakerPropertyType.RVMM_RANDOMIZE_MAP_COLOR_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_REMOVE_IMAGE_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_RENAME_MAP_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_RESET_VIEWPORT_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_SNAP_IMAGE_BOTTOM_LEFT_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_SNAP_IMAGE_TOP_LEFT_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_STOP_0_COLOR_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_STOP_0_COLOR_PICKER;
import static rvmm.MapMakerPropertyType.RVMM_STOP_1_COLOR_LABEL;
import static rvmm.MapMakerPropertyType.RVMM_STOP_1_COLOR_PICKER;
import static rvmm.MapMakerPropertyType.RVMM_TOGGLE_MAP_CHECK_BOX;
import static rvmm.MapMakerPropertyType.RVMM_TOGGLE_MAP_LABEL;
import rvmm.transactions.AddImage_Transaction;
import rvmm.transactions.ChangeBorder_Transaction;
import rvmm.transactions.ChangePolygonSubregion_Transaction;
import rvmm.transactions.ChangeRadialGradient_Transaction;
import rvmm.transactions.ReassignColors_Transaction;
import rvmm.transactions.RemoveImage_Transaction;
import rvmm.transactions.MoveImage_Transaction;
import rvmm.workspace.controllers.SubRegionTableController;
import rvmm.workspace.dialogs.ChangeDimensionDialog;
import rvmm.workspace.dialogs.NewMapDialog;
import rvmm.workspace.dialogs.SubregionDialog;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_TITLE;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_ICON_BUTTON;

/**
 *
 * @author McKillaGorilla, Daye Eun
 */
public class MapMakerWorkspace extends AppWorkspaceComponent {

    double mX, mY;
    double tempX, tempY;
    
    ImageView selectedImg;
    double oldTransX, oldTransY;
    RadialGradient oceanGradient;
    
    Polygon chosen;
    boolean movePolyProcess = false;
    
    double oldThickness = 1.0;
    boolean cycleSelected = false;
    
    public MapMakerWorkspace(RegioVincoMapMakerApp app) {
        super(app);

        // LAYOUT THE APP
        initLayout();
        
        initFoolproofDesign();
    }
    
    @Override
    public void openNewDialog() {
        NewMapDialog newMapDialog = new NewMapDialog((RegioVincoMapMakerApp)app);
        newMapDialog.showNewMapDialog();
        this.deactivate();
    }
        
    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private void initLayout() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
                
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder mmBuilder = app.getGUIModule().getNodesBuilder();
        FlowPane topToolBar = app.getGUIModule().getTopToolbarPane();
        topToolBar.setAlignment(Pos.CENTER);
        
        workspace = new BorderPane();
        
        // Create New Map
        Button newButton = (Button)app.getGUIModule().getGUINode(NEW_BUTTON);
        newButton.setOnAction(e -> {
            NewMapDialog newMap = new NewMapDialog((RegioVincoMapMakerApp)app);
            newMap.showNewMapDialog();
        });
        
        // Creating mapToolbar
        ToolBar mapToolbar = new ToolBar();
        mapToolbar.getStyleClass().add(CLASS_DJF_TOOLBAR_PANE);
        topToolBar.getChildren().add(mapToolbar);
        Button resetViewportButton = mmBuilder.buildIconButton(RVMM_RESET_VIEWPORT_BUTTON, null, mapToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button fitToPolyButton = mmBuilder.buildIconButton(RVMM_FIT_TO_POLYGON_BUTTON, null, mapToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        ToolBar imageToolbar = new ToolBar();
        imageToolbar.getStyleClass().add(CLASS_DJF_TOOLBAR_PANE);
        topToolBar.getChildren().add(imageToolbar);
        Button renameMapButton = mmBuilder.buildIconButton(RVMM_RENAME_MAP_BUTTON, null, imageToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button addImageButton = mmBuilder.buildIconButton(RVMM_ADD_IMAGE_BUTTON, null, imageToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button removeImageButton = mmBuilder.buildIconButton(RVMM_REMOVE_IMAGE_BUTTON, null, imageToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button snapImageTopLeftButton = mmBuilder.buildIconButton(RVMM_SNAP_IMAGE_TOP_LEFT_BUTTON, null, imageToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button snapImageButtomLeftButton = mmBuilder.buildIconButton(RVMM_SNAP_IMAGE_BOTTOM_LEFT_BUTTON, null, imageToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button reassignColorsButton = mmBuilder.buildIconButton(RVMM_RANDOMIZE_MAP_COLOR_BUTTON, null, imageToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button changeMapDimensionButton = mmBuilder.buildIconButton(RVMM_CHANGE_MAP_DIMENSION_BUTTON, null, imageToolbar, CLASS_DJF_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        changeMapDimensionButton.setOnAction(e-> {
            ChangeDimensionDialog newChangeMapDimensionDialog = new ChangeDimensionDialog((RegioVincoMapMakerApp)app);
            newChangeMapDimensionDialog.showChangeDimensionDialog();
        });
        
        ToolBar checkBoxToolbar = new ToolBar();
        checkBoxToolbar.getStyleClass().add(CLASS_DJF_TOOLBAR_PANE);
        topToolBar.getChildren().add(checkBoxToolbar);
        Label toggleFrame = mmBuilder.buildLabel(RVMM_TOGGLE_MAP_LABEL, null, checkBoxToolbar, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        CheckBox toggleFrameCheckBox = mmBuilder.buildCheckBox(RVMM_TOGGLE_MAP_CHECK_BOX, null, checkBoxToolbar, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label movePolygonMode = mmBuilder.buildLabel(RVMM_MOVE_POLYGON_MODE_LABEL, null, checkBoxToolbar, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        CheckBox movePolygonModeCheckBox = mmBuilder.buildCheckBox(RVMM_MOVE_POLYGON_MODE_CHECK_BOX, null, checkBoxToolbar, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        ToolBar borderToolbar = new ToolBar();
        borderToolbar.getStyleClass().add(CLASS_DJF_TOOLBAR_PANE);
        topToolBar.getChildren().add(borderToolbar);
        Label borderColor = mmBuilder.buildLabel(RVMM_CHANGE_BORDER_COLOR_LABEL, null, borderToolbar, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        ColorPicker borderColorPicker = mmBuilder.buildColorPicker(RVMM_CHANGE_BORDER_COLOR_PICKER, null, borderToolbar, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        borderColorPicker.setValue(Color.BLACK);
        Label borderThickness = mmBuilder.buildLabel(RVMM_CHANGE_BORDER_THICKNESS_LABEL, null, borderToolbar, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider borderThicknessSlider = mmBuilder.buildSlider(RVMM_CHANGE_BORDER_THICKNESS_SLIDER, null, borderToolbar, null, 0, 1, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        setSliderValues(borderThicknessSlider, 0.5, 3);
        borderThicknessSlider.setBlockIncrement(0.01);
        borderThicknessSlider.setValue(1);
        borderColorPicker.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            double oldT = mmData.getBorderThickness();
            Color oldColor = mmData.getBorderColor();
            double newT = borderThicknessSlider.getValue();
            Color newColor = borderColorPicker.getValue();
            ChangeBorder_Transaction transaction = 
                    new ChangeBorder_Transaction(mmData, oldT, oldColor, newT, newColor);
            app.processTransaction(transaction);
        });
        setBorderSliderFunction(borderThicknessSlider);
        
        Pane mapPane = new Pane();
        app.getGUIModule().addGUINode(RVMM_MAP_PANE, mapPane);
        BorderPane outerMapPane = new BorderPane();
        app.getGUIModule().addGUINode(RVMM_OUTER_PANE, outerMapPane);
        Rectangle clippingRectangle = new Rectangle();
        outerMapPane.setClip(clippingRectangle);
        Pane clippedPane = new Pane();
        outerMapPane.setCenter(clippedPane);
        clippedPane.getChildren().add(mapPane);
        Rectangle ocean = new Rectangle();
        mapPane.getChildren().add(ocean);
        Rectangle toggleRectangle = new Rectangle();
        toggleRectangle.setFill(null);
        toggleRectangle.setStroke(null);
        outerMapPane.getChildren().add(toggleRectangle);
        BorderPane leftFrame = new BorderPane();
        leftFrame.setCenter(outerMapPane);
        mapPane.minWidthProperty().bind(outerMapPane.widthProperty());
        mapPane.maxWidthProperty().bind(outerMapPane.widthProperty());
        mapPane.minHeightProperty().bind(outerMapPane.heightProperty());
        mapPane.maxHeightProperty().bind(outerMapPane.heightProperty());
        outerMapPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clippingRectangle.setWidth(newValue.getWidth());
            clippingRectangle.setHeight(newValue.getHeight());
            toggleRectangle.setWidth(newValue.getWidth());
            toggleRectangle.setHeight(newValue.getHeight());
            ocean.setWidth(newValue.getHeight()*2);
            ocean.setHeight(newValue.getHeight());
        });
        resetViewportButton.setOnAction(e->{
            app.getFileModule().markAsEdited(true);
            mapPane.setTranslateX(0);
            mapPane.setTranslateY(0);
            mapPane.setScaleX(1);
            mapPane.setScaleY(1);
        });
        fitToPolyButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            mmData.findMinMax();
            double lengthX = mmData.getMaxX() - mmData.getMinX();
            double lengthY = mmData.getMaxY() - mmData.getMinY();
            double scaleX  = mapPane.getWidth()/lengthX;
            double scaleY = mapPane.getHeight()/lengthY;
            double newScale = scaleX/1.2;
            double transX = newScale*(mapPane.getWidth()/2-lengthX*0.6-mmData.getMinX()+lengthX*1.2*0.1);
            double transY = newScale*(mapPane.getHeight()/2 - (mmData.getMinY()+lengthY/2));
            if(scaleX > scaleY) { 
                newScale = scaleY/1.2;
                transX = newScale*(mapPane.getWidth()/2 - (mmData.getMinX()+lengthX/2));
                transY = newScale*(mapPane.getHeight()/2-lengthY*0.6-mmData.getMinY()+lengthY*1.2*0.1);
            }
            mapPane.setScaleX(newScale);
            mapPane.setScaleY(newScale);
            mapPane.setTranslateX(transX);
            mapPane.setTranslateY(transY);
            mmData.adjustLineThickness();
        });
        renameMapButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            String newName = AppDialogsFacade.showTextInputDialog(app.getGUIModule().getWindow(), RVMM_CHANGE_MAP_NAME_TITLE_TEXT, RVMM_CHANGE_MAP_NAME_CONTENT_TEXT);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            if(!newName.isEmpty())
                changeName(mmData, mmData.getName(), newName);
        });
        addImageButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            FileChooser fC = new FileChooser();
            String path = mmData.getExportPath();
            fC.setInitialDirectory(new File(path));
            File selectedFile = fC.showOpenDialog(app.getGUIModule().getWindow());
            if(selectedFile != null) {
                String imagePath = selectedFile.toURI().toString();
                String[] relPath = imagePath.split("/export");
                String relativePath = "./export" + relPath[1];
                StringProperty imgPath = new SimpleStringProperty(relativePath);
                Image img = new Image(imagePath);
                ImageView regionImgView = new ImageView(img);
                regionImgView.setTranslateX(0);
                regionImgView.setTranslateY(0);
                AddImage_Transaction transaction = new AddImage_Transaction(mmData, regionImgView, imgPath);
                app.processTransaction(transaction);
            }
        });
        removeImageButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            if(selectedImg != null) {
                int index = mmData.getImages().indexOf(selectedImg);
                RemoveImage_Transaction transaction = new RemoveImage_Transaction(mmData, selectedImg, mmData.getImagePaths().get(index));
                app.processTransaction(transaction);
                selectedImg = null;
                mmData.setImageSelected(false);
                app.getFoolproofModule().updateAll();
            }
        });
        snapImageTopLeftButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            if(selectedImg != null) {
                oldTransX = selectedImg.getTranslateX();
                oldTransY = selectedImg.getTranslateY();
                selectedImg.setTranslateX(20);
                selectedImg.setTranslateY(20);
                
                if((oldTransX != selectedImg.getTranslateX()) && (oldTransY != selectedImg.getTranslateY())) {
                    MoveImage_Transaction transaction = new MoveImage_Transaction(selectedImg, oldTransX, oldTransY);
                    app.processTransaction(transaction);
                }
            }
        });
        snapImageButtomLeftButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            if(selectedImg != null) {
                oldTransX = selectedImg.getTranslateX();
                oldTransY = selectedImg.getTranslateY();
                selectedImg.setTranslateX(20);
                selectedImg.setTranslateY(clippedPane.getHeight() - selectedImg.getImage().getHeight() - 20);
                if((oldTransX != selectedImg.getTranslateX()) && (oldTransY != selectedImg.getTranslateY())) {
                    MoveImage_Transaction transaction = new MoveImage_Transaction(selectedImg, oldTransX, oldTransY);
                    app.processTransaction(transaction);
                }
            }
        });
        reassignColorsButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            ObservableList<Color> oldColors = FXCollections.observableArrayList();
            for(int i = 0; i < mmData.getSubregions().size(); i++) {
                oldColors.add(mmData.getSubregions().get(i).getColor());
            }
            ReassignColors_Transaction transaction = 
                    new ReassignColors_Transaction(mmData, oldColors, mmData.getNewColors());
            app.processTransaction(transaction);
        });
        toggleFrameCheckBox.setOnAction(e-> {
            if(toggleFrameCheckBox.isSelected()) {
                toggleRectangle.setStroke(Color.BLACK);
                toggleRectangle.setStrokeWidth(20);
            }
            else {
                toggleRectangle.setStroke(null);
            }
        });
        movePolygonModeCheckBox.setOnAction(e-> {
            if(movePolygonModeCheckBox.isSelected()) {
                app.getGUIModule().getPrimaryScene().setCursor(Cursor.HAND);
            }
            else {
                app.getGUIModule().getPrimaryScene().setCursor(Cursor.DEFAULT);
                movePolyProcess = false;
                if(chosen != null) {
                    MapMakerData mmData = (MapMakerData)app.getDataComponent();
                    chosen.setFill(mmData.findSubregion(chosen).getColor());
                }
            }
        });
        BorderPane description = new BorderPane();
        GridPane namePane = new GridPane();
        namePane.setAlignment(Pos.CENTER);
        description.setTop(namePane);
        SplitPane body = new SplitPane();
        body.getItems().addAll(leftFrame, description);
        body.setDividerPosition(0, 0.5f);
        Label mapNameLabel = mmBuilder.buildLabel(RVMM_NAME_LABEL, null, null, CLASS_RVMM_TITLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        namePane.add(mapNameLabel, 0, 0);
        Button moveSubregionUpButton = mmBuilder.buildIconButton(RVMM_MOVE_UP_BUTTON, null, null, CLASS_RVMM_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);        
        Button moveSubregionDownButton = mmBuilder.buildIconButton(RVMM_MOVE_DOWN_BUTTON, null, null, CLASS_RVMM_ICON_BUTTON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        namePane.add(moveSubregionUpButton, 1, 0);
        namePane.add(moveSubregionDownButton, 2, 0);
        // AND NOW THE TABLE
        TableView<SubregionPrototype> subregionTable  = mmBuilder.buildTableView(RVMM_ITEMS_TABLE_VIEW, null, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, true);
        description.setCenter(subregionTable);
        TableColumn subregionColumn      = mmBuilder.buildTableColumn(RVMM_SUBREGION_COLUMN, subregionTable, CLASS_RVMM_COLUMN);
        TableColumn capitalColumn   = mmBuilder.buildTableColumn(RVMM_CAPITAL_COLUMN, subregionTable, CLASS_RVMM_COLUMN);
        TableColumn leaderColumn     = mmBuilder.buildTableColumn(RVMM_LEADER_COLUMN,  subregionTable, CLASS_RVMM_COLUMN);
        // SPECIFY THE TYPES FOR THE COLUMNS
        subregionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("Name"));
        capitalColumn.setCellValueFactory(new PropertyValueFactory<String, String>("Capital"));
        leaderColumn.setCellValueFactory(new PropertyValueFactory<String, String>("Leader"));
        SubRegionController sC = new SubRegionController((RegioVincoMapMakerApp) app);
        SubRegionTableController sTC = new SubRegionTableController(app);
        moveSubregionUpButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            sC.processMoveUpSubregions();
        });
        moveSubregionDownButton.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            sC.processMoveDownSubregions();
        });
        subregionTable.widthProperty().addListener(e->{
            sTC.processChangeTableSize();
        });
        subregionTable.setRowFactory(it-> {
            TableRow<SubregionPrototype> rows = new TableRow<>();
            rows.setOnMouseClicked(e -> {
                app.getFoolproofModule().updateAll();
                if(!rows.isEmpty() && e.getButton()==MouseButton.PRIMARY && e.getClickCount()==1) {      
                    MapMakerData mmData = (MapMakerData)app.getDataComponent();
                    highlightSubregion(mmData.getSelectedSubregion(), mapPane, mmData);
                    if(movePolyProcess == true) {
                        SubregionPrototype toAdd = mmData.getSelectedSubregion();
                        if(toAdd != null) {
                            if(!toAdd.getArea().contains(chosen)) {
                                ButtonType change = AppDialogsFacade.showYesNoDialog(app.getGUIModule().getWindow(), RVMM_CHANGE_POLYGON_TITLE_TEXT, RVMM_CHANGE_POLYGON_CONTENT_TEXT);
                                if(change.equals(ButtonType.YES)) {
                                    ChangePolygonSubregion_Transaction transaction = new ChangePolygonSubregion_Transaction(mmData, chosen, mmData.findSubregion(chosen), toAdd);
                                    app.processTransaction(transaction);
                                    app.getFileModule().markAsEdited(true);
                                }
                                else {
                                    for(int i = 0; i < toAdd.getArea().size() ;i++) {
                                        if(mapPane.getChildren().contains(toAdd.getArea().get(i))) {
                                            int j = mapPane.getChildren().indexOf(toAdd.getArea().get(i));
                                            ((Polygon)mapPane.getChildren().get(j)).setFill(toAdd.getColor());
                                        }
                                    } 
                                }
                            movePolyProcess = false;
                            chosen = null;
                            }
                            else {
                                AppDialogsFacade.showStringMessageDialog(app.getGUIModule().getWindow(), "Choose a Different Subregion", "Selected Polygon is already inside the Subregion");
                            }
                        }
                    }
                }
                if(!rows.isEmpty() && e.getButton()==MouseButton.PRIMARY && e.getClickCount()==2) {      
                    sC.processEditSubregions();
                }
            });
            
            return rows;
        });
        mapPane.setOnMouseClicked(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            for(int i = 1; i < mapPane.getChildren().size(); i++) {
                Polygon p = (Polygon)mapPane.getChildren().get(i);
                SubregionPrototype prototype = mmData.findSubregion(p);
                if(p.contains(e.getX(), e.getY())) {
                    if((movePolyProcess!= true) && movePolygonModeCheckBox.isSelected()) {
                        chosen = p; 
                        chosen.setFill(Color.RED);
                        movePolyProcess = true;
                    }
                    mmData.setSelectedSubregion(prototype);
                    if(prototype != null && !movePolygonModeCheckBox.isSelected()) {
                        highlightSubregion(prototype, mapPane, mmData);
                    }
                    if(e.getButton()==MouseButton.PRIMARY && e.getClickCount()==2) {  
                        SubregionDialog subDialog = new SubregionDialog((RegioVincoMapMakerApp)app);
                        subDialog.showSubregionDialog(prototype);
                    }
                }
            }
            if(movePolyProcess == true) {
                AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(), RVMM_CHANGE_POLYGON_SELECT_SUBREGION_TITLE_TEXT, RVMM_CHANGE_POLYGON_SELECT_SUBREGION_CONTENT_TEXT);
            }
        });
        GridPane uiController = new GridPane();
        uiController.getStyleClass().add(CLASS_RVMM_BOX);
        description.setBottom(uiController);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);
        uiController.getColumnConstraints().addAll(col1,col2,col3,col4);
        Label gradientLabel = mmBuilder.buildLabel(RVMM_GRADIENT_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(0, gradientLabel);
        Label empty = new Label();
        uiController.addColumn(1, empty);
        Label angleLabel = mmBuilder.buildLabel(RVMM_FOCUS_ANGLE_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(0, angleLabel);
        Slider angleSlider = mmBuilder.buildSlider(RVMM_FOCUS_ANGLE_SLIDER, null, null, null, 0, 360, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(1, angleSlider);
        setSliderValues(angleSlider, 25, 3);
        Label centerXLabel = mmBuilder.buildLabel(RVMM_CENTER_X_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(0, centerXLabel);
        Slider centerXSlider = mmBuilder.buildSlider(RVMM_CENTER_X_SLIDER, null, null, null, 0, 1920, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(1, centerXSlider);
        setSliderValues(centerXSlider, 500, 3);
        Label radiusLabel = mmBuilder.buildLabel(RVMM_RADIUS_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(0, radiusLabel);
        Slider radiusSlider = mmBuilder.buildSlider(RVMM_RADIUS_SLIDER, null, null, null, 0, 960, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(1, radiusSlider);
        setSliderValues(radiusSlider, 200, 3);
        Label stop0Label = mmBuilder.buildLabel(RVMM_STOP_0_COLOR_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(0, stop0Label);
        ColorPicker stop0Picker = mmBuilder.buildColorPicker(RVMM_STOP_0_COLOR_PICKER, null, null, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(1, stop0Picker);
        Label proportionalLabel = mmBuilder.buildLabel(RVMM_PROPORTIONAL_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(2, proportionalLabel);
        CheckBox proportionalCheckBox = mmBuilder.buildCheckBox(RVMM_PROPORTIONAL_CHECK_BOX, null, null, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(3, proportionalCheckBox);
        Label focusDistanceLabel = mmBuilder.buildLabel(RVMM_FOCUS_DISTENCE_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(2, focusDistanceLabel);
        Slider focusDistanceSlider = mmBuilder.buildSlider(RVMM_FOCUS_DISTENCE_SLIDER, null, null, null, -1, 1, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(3, focusDistanceSlider);
        setSliderValues(focusDistanceSlider, 1, 0);
        Label centerYLabel = mmBuilder.buildLabel(RVMM_CENTER_Y_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(2, centerYLabel);
        Slider centerYSlider = mmBuilder.buildSlider(RVMM_CENTER_Y_SLIDER, null, null, null, 0, 1080, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(3, centerYSlider);
        setSliderValues(centerYSlider, 500, 3);
        Label cycleMethodLabel = mmBuilder.buildLabel(RVMM_CYCLE_METHOD_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(2, cycleMethodLabel);
        ObservableList<String> cycleMethods =
               FXCollections.observableArrayList(
               "NO_CYCLE",
               "REFLECT",
               "REPEAT"
        );
        ComboBox cycleMethodComboBox = mmBuilder.buildComboBox(RVMM_CYCLE_METHOD_COMBO_BOX, cycleMethods, cycleMethods, null, null, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);        
        cycleMethodComboBox.setItems(cycleMethods);
        uiController.addColumn(3, cycleMethodComboBox);
        Label stop1Label = mmBuilder.buildLabel(RVMM_STOP_1_COLOR_LABEL, null, null, CLASS_RVMM_PROMPT, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(2, stop1Label);
        ColorPicker stop1Picker = mmBuilder.buildColorPicker(RVMM_STOP_1_COLOR_PICKER, null, null, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        uiController.addColumn(3, stop1Picker);
        setSliderFunctions(angleSlider, ocean, 0);
        setSliderFunctions(focusDistanceSlider, ocean, 1);
        setSliderFunctions(centerXSlider, ocean, 2);
        setSliderFunctions(centerYSlider, ocean, 3);
        setSliderFunctions(radiusSlider, ocean, 4);
        proportionalCheckBox.setOnAction(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            oceanGradient = mmData.newOceanGradient();
            mmData.setProportional(proportionalCheckBox.isSelected());
            RadialGradient rg = mmData.newOceanGradient();
            ChangeRadialGradient_Transaction transaction = new ChangeRadialGradient_Transaction(mmData, oceanGradient, rg);
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        cycleMethodComboBox.setOnMousePressed(m-> {
            cycleSelected = true;
            cycleMethodComboBox.setOnAction(e-> {
                if(cycleSelected) {
                    MapMakerData mmData = (MapMakerData)app.getDataComponent();
                    oceanGradient = mmData.newOceanGradient();
                    mmData.setCycleMethod(cycleMethodComboBox.getValue().toString());
                    RadialGradient rg = mmData.newOceanGradient();
                    ChangeRadialGradient_Transaction transaction = new ChangeRadialGradient_Transaction(mmData, oceanGradient, rg);
                    app.processTransaction(transaction);
                    cycleSelected = false;
                    app.getFileModule().markAsEdited(true);
                }
            });
        });
        stop0Picker.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            oceanGradient = mmData.newOceanGradient();
            mmData.setStop0(stop0Picker.getValue());
            RadialGradient rg = mmData.newOceanGradient();
            ChangeRadialGradient_Transaction transaction = new ChangeRadialGradient_Transaction(mmData, oceanGradient, rg);
            app.processTransaction(transaction);
        });
        stop1Picker.setOnAction(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            oceanGradient = mmData.newOceanGradient();
            mmData.setStop1(stop1Picker.getValue());
            RadialGradient rg = mmData.newOceanGradient();
            ChangeRadialGradient_Transaction transaction = new ChangeRadialGradient_Transaction(mmData, oceanGradient, rg);
            app.processTransaction(transaction);
        });
        outerMapPane.setOnMouseMoved(e-> {
            mX = e.getX();
            mY = e.getY();
        });
        mapPane.setOnScroll(e -> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            app.getFileModule().markAsEdited(true);
            if(e.getDeltaY() < 0) {
                if(mapPane.getScaleX() > 1.0 && mapPane.getScaleY() > 1.0) {
                    mapPane.setScaleX(mapPane.getScaleX()*0.5);
                    mapPane.setScaleY(mapPane.getScaleY()*0.5);
                    double changeX = (mapPane.getTranslateX() - (mapPane.getWidth()/2) + mX)/2;
                    double changeY = (mapPane.getTranslateY() - (mapPane.getHeight()/2) + mY)/2;
                    mapPane.setTranslateX(changeX);
                    mapPane.setTranslateY(changeY);
                }
            }
            else {
                    mapPane.setScaleX(mapPane.getScaleX()*2);
                    mapPane.setScaleY(mapPane.getScaleY()*2);
                    double changeX = mapPane.getTranslateX()*2 + (mapPane.getWidth()/2) - mX;
                    double changeY = mapPane.getTranslateY()*2 + (mapPane.getHeight()/2) - mY;
                    mapPane.setTranslateX(changeX);
                    mapPane.setTranslateY(changeY);
            }
            mmData.adjustLineThickness();
        });
        mapPane.setOnMousePressed(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            selectedImg = null;
            mmData.setImageSelected(false);
            app.getFoolproofModule().updateAll();
            tempX = e.getX();
            tempY = e.getY();
            for(ImageView img : mmData.getImages()) {
                img.setEffect(null);
            }        
        });
        ocean.setOnMouseClicked(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            ObservableList mapNodes = mapPane.getChildren();
            for(int i = 1; i < mapNodes.size(); i++) {
                Polygon p = (Polygon)mapNodes.get(i);
                SubregionPrototype other = mmData.findSubregion(p);
                if(other != null) {
                    p.setFill(other.getColor());
                }
            }
            mmData.clearSelected();
        });
        mapPane.setOnMouseDragged(e-> {
            app.getFileModule().markAsEdited(true);
            double moveX = (e.getX() - tempX)*mapPane.getScaleX();
            double moveY = (e.getY() - tempY)*mapPane.getScaleY();
            mapPane.setTranslateX(mapPane.getTranslateX() + moveX);
            mapPane.setTranslateY(mapPane.getTranslateY() + moveY);
        });
      
        ((BorderPane)workspace).setCenter(body);

    }
        
    private void setSliderValues(Slider s, double unit, int count){
        s.setMajorTickUnit(unit);
        s.setMinorTickCount(count);
        s.setShowTickLabels(true);
        s.setSnapToTicks(false);
    }
    
    public void initFoolproofDesign() {
        AppGUIModule gui = app.getGUIModule();
        AppFoolproofModule foolproofSettings = app.getFoolproofModule();
        foolproofSettings.registerModeSettings(RVMM_FOOLPROOF_SETTINGS, 
                new MapMakerFoolproofDesign((RegioVincoMapMakerApp)app));
    }

    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
       
    }
    
    public void changeName(MapMakerData data, String o, String n) {
        ChangeMapName_Transaction transaction = new ChangeMapName_Transaction(data, o, n);
        app.processTransaction(transaction);
    }
            
    public void setImageViewFunctions(ImageView regionImgView) {
        MapMakerData mmData = (MapMakerData)app.getDataComponent();
        regionImgView.setOnMouseClicked(e-> {
            selectedImg = regionImgView;
            mmData.setImageSelected(true);
            app.getFoolproofModule().updateAll();
            for(ImageView img : mmData.getImages()) {
                if(img.equals(selectedImg)) {
                    DropShadow shadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(108, 122, 137), 100, 0.6, 0, 0);
                    img.setEffect(shadow);
                }
                else {
                    img.setEffect(null);
                }
            }
        });
        regionImgView.setOnMousePressed(e -> {
            oldTransX = regionImgView.getTranslateX();
            oldTransY = regionImgView.getTranslateY();
            tempX = e.getX();
            tempY = e.getY();
        });
        regionImgView.setOnMouseDragged(e-> {
            app.getFileModule().markAsEdited(true);
            regionImgView.setTranslateX(regionImgView.getTranslateX() + e.getX() - tempX);
            regionImgView.setTranslateY(regionImgView.getTranslateY() + e.getY() - tempY);
        });
        regionImgView.setOnMouseReleased(e-> {
            if((oldTransX != regionImgView.getTranslateX()) && (oldTransY != regionImgView.getTranslateY())) {
                MoveImage_Transaction transaction = new MoveImage_Transaction(regionImgView, oldTransX, oldTransY);
                app.processTransaction(transaction);
            }
        });
    }

    public void setSliderFunctions(Slider s, Rectangle ocean, int i) {
        s.setOnMousePressed(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            oceanGradient = mmData.newOceanGradient();
        });
        s.setOnMouseDragged(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            setRadialGradient(mmData, i, s.getValue());
            RadialGradient rg = mmData.newOceanGradient();
            ocean.setFill(rg);
        });
        s.setOnMouseClicked(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            oceanGradient = mmData.newOceanGradient();
            setRadialGradient(mmData, i, s.getValue());
            RadialGradient rg = mmData.newOceanGradient();
            if(!oceanGradient.equals(rg)) {
                ChangeRadialGradient_Transaction transaction = new ChangeRadialGradient_Transaction(mmData, oceanGradient, rg);
                app.processTransaction(transaction);
            }
        });
        s.setOnMouseReleased(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            setRadialGradient(mmData, i, s.getValue());
            RadialGradient rg = mmData.newOceanGradient();
            ChangeRadialGradient_Transaction transaction = new ChangeRadialGradient_Transaction(mmData, oceanGradient, rg);
            app.processTransaction(transaction);
        });        
    } 
    public void setRadialGradient(MapMakerData data, int i, double v) {
        if(i == 0) // angle
            data.setAngle(v);
        else if(i == 1) // distance
            data.setDistance(v);
        else if(i == 2) // centerX
            data.setCenterX(v);
        else if(i == 3) // centerY
            data.setCenterY(v);
        else if(i == 4) // radius
            data.setRadius(v);
    }
    public void setBorderSliderFunction(Slider s) {
        s.setOnMousePressed(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            oldThickness = mmData.getBorderThickness();
        });
        s.setOnMouseDragged(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            mmData.adjustLines(oldThickness, mmData.getBorderColor());
        });
        s.setOnMouseClicked(e-> {
            app.getFileModule().markAsEdited(true);
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            oldThickness = mmData.getBorderThickness();
            if(oldThickness != s.getValue()){
                ChangeBorder_Transaction transaction = new ChangeBorder_Transaction(mmData, oldThickness, mmData.getBorderColor(), s.getValue(), mmData.getBorderColor());
                app.processTransaction(transaction);
            }
        });
        s.setOnMouseReleased(e-> {
            MapMakerData mmData = (MapMakerData)app.getDataComponent();
            if(oldThickness != s.getValue()){
                ChangeBorder_Transaction transaction = new ChangeBorder_Transaction(mmData, oldThickness, mmData.getBorderColor(), s.getValue(), mmData.getBorderColor());
                app.processTransaction(transaction);
            }
        });
    }
    
    public void highlightSubregion(SubregionPrototype sub, Pane map, MapMakerData data) {
        ObservableList<Polygon> area = sub.getArea();
        ObservableList mapNodes = map.getChildren();
        for(int i = 1; i < mapNodes.size(); i++) {
            Polygon p = (Polygon)mapNodes.get(i);
            if(area.contains(p)) {
                p.setFill(Color.GREEN);
            }
            else {
                SubregionPrototype other = data.findSubregion(p);
                if(other != null) {
                    p.setFill(other.getColor());
                }
            }
        }
    }    
}
