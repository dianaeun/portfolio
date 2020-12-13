package rvmm.workspace.dialogs;

import static djf.AppPropertyType.APP_ERROR_CONTENT;
import static djf.AppPropertyType.APP_ERROR_TITLE;
import static djf.AppPropertyType.APP_PATH_EXPORT;
import static djf.AppPropertyType.LOAD_ERROR_CONTENT;
import static djf.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.AppPropertyType.LOAD_WORK_TITLE;
import djf.modules.AppLanguageModule;
import djf.ui.dialogs.AppDialogsFacade;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static rvmm.MapMakerPropertyType.APP_PATH_RAW_MAP_DATA;
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
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_BOX;

/**
 *
 * @author McKillaGorilla, Daye Eun
 */
public class NewMapDialog extends Stage {
    RegioVincoMapMakerApp app;
    VBox dialog;
    GridPane gridPane;
    File selectedFile;
    File choosen;
    
    Label titleLabel = new Label();
    FlowPane titleSpace = new FlowPane();
    Label region = new Label();
    TextField regionName = new TextField();
    Button chooseParentButton = new Button();
    Label parent = new Label();
    Button chooseDataButton = new Button();
    Label data = new Label();
    
    HBox okPane = new HBox();
    Button okButton = new Button();

    boolean editing;

    EventHandler cancelHandler;
    EventHandler newMapOkHandler;
    
    public NewMapDialog(RegioVincoMapMakerApp initApp) {
        // KEEP THIS FOR WHEN THE WORK IS ENTERED
        app = initApp;
        dialog = new VBox();
        titleSpace.getChildren().add(titleLabel);
        titleLabel.getStyleClass().add(CLASS_RVMM_DIALOG_HEADER);
        titleSpace.getStyleClass().add(CLASS_RVMM_BOX);
        titleSpace.setAlignment(Pos.CENTER);
        dialog.getChildren().add(titleSpace);

        // Put them in the XML File
        chooseParentButton.setText("Choose Parent Region Directory");
        chooseDataButton.setText("Choose Data File");
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
//        scene.getStylesheets().add(CLASS_RVMM_DIALOG_GRID);                             
        
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
//            ((Labeled)node).setTooltip(new Tooltip(""));
            //languageSettings.addLabeledControlProperty(nodeId + "_TOOLTIP", ((Labeled)node).tooltipProperty().get().textProperty());
        }
        
        // PUT IT IN THE UI
        if (col >= 0)
            gridPane.add(node, col, row, colSpan, rowSpan);

        // SETUP IT'S STYLE SHEET
        node.getStyleClass().add(styleClass);
    }

    private void initDialog() {
        // THE NODES ABOVE GO DIRECTLY INSIDE THE GRID
        initGridNode(region, RVMM_NEW_MAP_DIALOG_REGION_NAME, CLASS_RVMM_DIALOG_PROMPT, 0, 0, 5, 1, true);
        initGridNode(regionName, RVMM_NEW_MAP_DIALOG_REGION_NAME_TEXT_FIELD, CLASS_RVMM_DIALOG_TEXT_FIELD,   1, 0, 1, 1, false);
        initGridNode(chooseParentButton, RVMM_NEW_MAP_DIALOG_CHOOSE_PARENT_BUTTON, CLASS_RVMM_DIALOG_BUTTON, 0, 1, 1, 1, true);
        initGridNode(parent,  null, CLASS_RVMM_DIALOG_PROMPT, 1, 1, 1, 1, true);
        initGridNode(chooseDataButton, RVMM_NEW_MAP_DIALOG_CHOOSE_DATA_BUTTON, CLASS_RVMM_DIALOG_BUTTON, 0, 2, 1, 1, true);
        initGridNode(data, RVMM_NEW_MAP_DATA_DESCRIPTION, CLASS_RVMM_DIALOG_PROMPT,  1, 2, 1, 1, true);
        initGridNode(okPane, null, CLASS_RVMM_DIALOG_PANE, 1, 3, 10, 1, false);

        okButton = new Button();
        app.getGUIModule().addGUINode(RVMM_NEW_MAP_DIALOG_OK_BUTTON, okButton);
        okButton.setText("Ok");
        okButton.getStyleClass().add(CLASS_RVMM_DIALOG_BUTTON);
        okPane.getChildren().add(okButton);
        okPane.setAlignment(Pos.CENTER_RIGHT);

        AppLanguageModule languageSettings = app.getLanguageModule();
        languageSettings.addLabeledControlProperty(RVMM_NEW_MAP_DIALOG_OK_BUTTON + "_TEXT",    okButton.textProperty());
      
        // AND SETUP THE EVENT HANDLERS
        chooseParentButton.setOnAction(e-> {
            DirectoryChooser dC = new DirectoryChooser();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String path = props.getProperty(APP_PATH_EXPORT);
            File directory = new File(path);
            dC.setInitialDirectory(directory);
            choosen = dC.showDialog(this);
            if(choosen != null) {
                parent.setText(choosen.getName());
            }
        });
        chooseDataButton.setOnAction(e-> {
            FileChooser fC = new FileChooser();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String path = props.getProperty(APP_PATH_RAW_MAP_DATA);
            fC.setInitialDirectory(new File(path));
            selectedFile = fC.showOpenDialog(this);
            if(selectedFile != null) {
                data.setText(selectedFile.getName());
            }
        });
        okButton.setOnAction(e->{
            makeNewMap();
        });  
    }
    
    private void makeNewMap() {
        String Name = regionName.getText();
        if((selectedFile != null)) {
            try {
                app.getFileModule().importWork(selectedFile);
                ((MapMakerData)(app.getDataComponent())).setName(Name);
                if(choosen != null) {
                    String[] path = choosen.getAbsolutePath().split("export");
                    String relPath = "./export" + path[1] + "/";
                    ((MapMakerData)(app.getDataComponent())).setExportPath(relPath);
                }
                app.getTPS().clearAllTransactions();                
                app.getFoolproofModule().updateAll();
            } catch (Exception e) {
                AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(), LOAD_ERROR_TITLE, LOAD_ERROR_CONTENT);
            }
        }
        this.hide();
    }

    public void showNewMapDialog() {        
        // USE THE TEXT IN THE HEADER FOR ADD
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(RVMM_NEW_MAP_DIALOG_HEADER_TEXT);
        titleLabel.setText(headerText);
        setTitle(headerText);

        String regionNameLabel = props.getProperty(RVMM_NEW_MAP_DIALOG_REGION_TEXT);
        region.setText(regionNameLabel);
        regionName.setText("");
        String defaultParentDisplay = props.getProperty(RVMM_NEW_MAP_DIALOG_DEFAULT_PARENT_TEXT);
        parent.setText(defaultParentDisplay);
        String defaultDataDisplay = props.getProperty(RVMM_NEW_MAP_DIALOG_DEFAULT_DATA_TEXT);
        data.setText(defaultDataDisplay);
        
        show();
    }
    
}