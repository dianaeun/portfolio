package rvmm.workspace.dialogs;

import static djf.AppPropertyType.APP_ERROR_CONTENT;
import static djf.AppPropertyType.APP_ERROR_TITLE;
import static djf.AppPropertyType.LOAD_ERROR_CONTENT;
import static djf.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.AppPropertyType.LOAD_WORK_TITLE;
import djf.modules.AppLanguageModule;
import djf.ui.dialogs.AppDialogsFacade;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_DIMENSION_DIALOG_HEADER_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_DIMENSION_DIALOG_HEIGHT_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_DIMENSION_DIALOG_HEIGHT_TEXT_FIELD;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_DIMENSION_DIALOG_WIDTH_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_CHANGE_DIMENSION_DIALOG_WIDTH_TEXT_FIELD;
import static rvmm.MapMakerPropertyType.RVMM_MAP_PANE;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DATA_DESCRIPTION;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_CHOOSE_DATA_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_CHOOSE_PARENT_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_DEFAULT_DATA_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_DEFAULT_PARENT_TEXT;
import rvmm.RegioVincoMapMakerApp;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_GRID;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_HEADER;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_PROMPT;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_TEXT_FIELD;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_BUTTON;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_PANE;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_HEADER;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_HEADER_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_OK_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_REGION_NAME;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_REGION_NAME_TEXT_FIELD;
import static rvmm.MapMakerPropertyType.RVMM_NEW_MAP_DIALOG_REGION_TEXT;
import rvmm.transactions.ChangeMapDimension_Transaction;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_BOX;

/**
 *
 * @author McKillaGorilla, Daye Eun
 */
public class ChangeDimensionDialog extends Stage {
    RegioVincoMapMakerApp app;
    VBox dialog;
    GridPane gridPane;
        
    double h;
    double w;
    
    Label titleLabel = new Label();
    FlowPane titleSpace = new FlowPane();
    Label height = new Label();
    TextField heightValue = new TextField();
    Label width = new Label();
    TextField widthValue = new TextField();
    
    HBox okPane = new HBox();
    Button okButton = new Button();

    boolean editing;

    EventHandler cancelHandler;
    EventHandler newMapOkHandler;
    
    public ChangeDimensionDialog(RegioVincoMapMakerApp initApp) {
        // KEEP THIS FOR WHEN THE WORK IS ENTERED
        app = initApp;
        dialog = new VBox();
        titleSpace.getChildren().add(titleLabel);
        titleLabel.getStyleClass().add(CLASS_RVMM_DIALOG_HEADER);
        titleSpace.getStyleClass().add(CLASS_RVMM_BOX);
        titleSpace.setAlignment(Pos.CENTER);
        dialog.getChildren().add(titleSpace);
      
        h = ((MapMakerData)app.getDataComponent()).getMapHeight();
        w = ((MapMakerData)app.getDataComponent()).getMapWidth();

        gridPane = new GridPane();
        dialog.getChildren().add(gridPane);
        gridPane.getStyleClass().add(CLASS_RVMM_DIALOG_GRID);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.setAlignment(Pos.CENTER);
        initDialog();

        // NOW PUT THE GRID IN THE SCENE AND THE SCENE IN THE DIALOG
        Scene scene = new Scene(dialog, 900, 500);
        this.setScene(scene);
        
        // SETUP THE STYLESHEET
        app.getGUIModule().initStylesheet(this);                          
        
        // MAKE IT MODAL
        this.initOwner(app.getGUIModule().getWindow());
        this.initModality(Modality.APPLICATION_MODAL);
    }
       
    protected void initGridNode(Node node, Object nodeId, String styleClass, int col, int row, int colSpan, int rowSpan, boolean isLanguageDependent) {
        // GET THE LANGUAGE SETTINGS
        AppLanguageModule languageSettings = app.getLanguageModule();
        
        // TAKE CARE OF THE TEXT
        if (isLanguageDependent) {
            languageSettings.addLabeledControlProperty(nodeId + "_TEXT", ((Labeled)node).textProperty());
        }
        
        // PUT IT IN THE UI
        if (col >= 0)
            gridPane.add(node, col, row, colSpan, rowSpan);

        // SETUP IT'S STYLE SHEET
        node.getStyleClass().add(styleClass);
    }

    private void initDialog() {
        // THE NODES ABOVE GO DIRECTLY INSIDE THE GRID
        initGridNode(height, RVMM_CHANGE_DIMENSION_DIALOG_HEIGHT_TEXT, CLASS_RVMM_DIALOG_PROMPT, 0, 0, 5, 1, true);
        initGridNode(heightValue, RVMM_CHANGE_DIMENSION_DIALOG_HEIGHT_TEXT_FIELD, CLASS_RVMM_DIALOG_TEXT_FIELD,   1, 0, 1, 1, false);
        initGridNode(width, RVMM_CHANGE_DIMENSION_DIALOG_WIDTH_TEXT, CLASS_RVMM_DIALOG_BUTTON, 0, 1, 1, 1, true);
        initGridNode(widthValue,  RVMM_CHANGE_DIMENSION_DIALOG_WIDTH_TEXT_FIELD, CLASS_RVMM_DIALOG_TEXT_FIELD, 1, 1, 1, 1, false);
        initGridNode(okPane, null, CLASS_RVMM_DIALOG_PANE, 1, 2, 10, 1, false);

        okButton = new Button();
        app.getGUIModule().addGUINode(RVMM_NEW_MAP_DIALOG_OK_BUTTON, okButton);
        okButton.setText("Ok");
        okButton.getStyleClass().add(CLASS_RVMM_DIALOG_BUTTON);
        okPane.getChildren().add(okButton);
        okPane.setAlignment(Pos.CENTER_RIGHT);

        AppLanguageModule languageSettings = app.getLanguageModule();
        languageSettings.addLabeledControlProperty(RVMM_NEW_MAP_DIALOG_OK_BUTTON + "_TEXT",    okButton.textProperty());
      
        // AND SETUP THE EVENT HANDLERS
        okButton.setOnAction(e->{
            changeDimension();
        });  
    }
    
    private void changeDimension() {
        String heightString = heightValue.getText();
        String widthString = widthValue.getText();
        MapMakerData data = (MapMakerData)app.getDataComponent();
        if(height != null && width != null) {
            double nW = Double.parseDouble(widthString);
            double nH = Double.parseDouble(heightString);
            ObservableList<ArrayList<ArrayList<Double>>> oldData = data.getRelocatePolygons();
            data.changeDimension(nW, nH);
            data.setRelocatePolygons(oldData);
            data.setTranslateX(0);
            data.setTranslateY(0);
            data.setScale(1);
            app.getFileModule().markAsEdited(true);
//            ChangeMapDimension_Transaction transaction = new ChangeMapDimension_Transaction(data, w, h, nW, nH);
//            app.processTransaction(transaction);
            w = nW;
            h = nH;
        }
        this.hide();
    }

    public void showChangeDimensionDialog() {        
        // USE THE TEXT IN THE HEADER FOR ADD
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(RVMM_CHANGE_DIMENSION_DIALOG_HEADER_TEXT);
        titleLabel.setText(headerText);
        setTitle(headerText);
        
        String hText = props.getProperty(RVMM_CHANGE_DIMENSION_DIALOG_HEIGHT_TEXT);
        String wText = props.getProperty(RVMM_CHANGE_DIMENSION_DIALOG_WIDTH_TEXT);
        height.setText(hText);
        width.setText(wText);
        heightValue.setText(h+"");
        widthValue.setText(w+"");
        showAndWait();
    }
    
}