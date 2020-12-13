package rvmm.workspace.dialogs;

import djf.modules.AppLanguageModule;
import djf.ui.dialogs.AppDialogsFacade;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import rvmm.RegioVincoMapMakerApp;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_GRID;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_HEADER;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_PROMPT;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_TEXT_FIELD;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_BUTTON;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_DIALOG_PANE;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_HEADER_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_SUBREGION;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_CAPITAL;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_LEADER;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_FLAG;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_SUBREGION_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_CAPITAL_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_LEADER_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_FLAG_TEXT;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_SUBREGION_TEXT_FIELD;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_CAPITAL_TEXT_FIELD;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_FLAG_IMAGE;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_LEADER_TEXT_FIELD;
import static rvmm.MapMakerPropertyType.RVMM_SUBREGION_DIALOG_OK_BUTTON;
import rvmm.transactions.Editsubregions_Transaction;
import static rvmm.workspace.style.RVMStyle.CLASS_RVMM_BOX;

/**
 *
 * @author McKillaGorilla, Daye Eun
 */
public class SubregionDialog extends Stage {
    RegioVincoMapMakerApp app;
    VBox dialog;
    GridPane gridPane;
    
    Label titleLabel = new Label();
    FlowPane titleSpace = new FlowPane();
    HBox prevNextPane = new HBox();
    Button prevButton = new Button();
    Button nextButton = new Button();
    Label subregion = new Label();
    TextField subregionName = new TextField();
    Label capital = new Label();
    TextField capitalName = new TextField();
    Label leader = new Label();
    TextField leaderName = new TextField();
    Label flag = new Label();
    ImageView flagImage = new ImageView();
    
    HBox okPane = new HBox();
    Button okButton = new Button();
    
    boolean editing;
    ArrayList<SubregionPrototype> editedSubregion = new ArrayList<>();
    ArrayList<SubregionPrototype> oldSubregion = new ArrayList<>();

    EventHandler cancelHandler;
    EventHandler newMapOkHandler;
    
    public SubregionDialog(RegioVincoMapMakerApp initApp) {
        // KEEP THIS FOR WHEN THE WORK IS ENTERED
        app = initApp;
        dialog = new VBox();
        titleSpace.getChildren().add(titleLabel);
        titleLabel.getStyleClass().add(CLASS_RVMM_DIALOG_HEADER);
        titleSpace.getStyleClass().add(CLASS_RVMM_BOX);
        titleSpace.setAlignment(Pos.CENTER);
        dialog.getChildren().add(titleSpace);
        dialog.getChildren().add(prevNextPane);

        // Put them in the XML File
        prevButton.setText("Previous");
        nextButton.setText("Next");
        prevNextPane.getChildren().add(prevButton);
        prevNextPane.getChildren().add(nextButton);
        prevNextPane.setPadding(Insets.EMPTY);
        prevNextPane.setSpacing(10);
        prevNextPane.setAlignment(Pos.CENTER_LEFT);
        prevButton.getStyleClass().add(CLASS_RVMM_DIALOG_BUTTON);
        nextButton.getStyleClass().add(CLASS_RVMM_DIALOG_BUTTON);
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
        Scene scene = new Scene(dialog, 1500, 900);
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
        
        initGridNode(subregion, RVMM_SUBREGION_DIALOG_SUBREGION, CLASS_RVMM_DIALOG_PROMPT, 0, 0, 1, 1, true);
        initGridNode(subregionName, RVMM_SUBREGION_DIALOG_SUBREGION_TEXT_FIELD, CLASS_RVMM_DIALOG_TEXT_FIELD,   1, 0, 1, 1, false);
        initGridNode(capital,  RVMM_SUBREGION_DIALOG_CAPITAL, CLASS_RVMM_DIALOG_PROMPT, 0, 1, 1, 1, true);
        initGridNode(capitalName, RVMM_SUBREGION_DIALOG_CAPITAL_TEXT_FIELD, CLASS_RVMM_DIALOG_TEXT_FIELD,  1, 1, 1, 1, false);
        initGridNode(leader,  RVMM_SUBREGION_DIALOG_LEADER, CLASS_RVMM_DIALOG_PROMPT, 0, 2, 1, 1, true);
        initGridNode(leaderName, RVMM_SUBREGION_DIALOG_LEADER_TEXT_FIELD, CLASS_RVMM_DIALOG_TEXT_FIELD,  1, 2, 1, 1, false);
        initGridNode(flag,  RVMM_SUBREGION_DIALOG_FLAG, CLASS_RVMM_DIALOG_PROMPT, 0, 3, 1, 1, true);
        initGridNode(flagImage, RVMM_SUBREGION_DIALOG_FLAG_IMAGE, null,  1, 3, 1, 1, false);
        
        initGridNode(okPane, null, CLASS_RVMM_DIALOG_PANE, 1, 4, 1, 1, false);

        okButton = new Button();
        app.getGUIModule().addGUINode(RVMM_SUBREGION_DIALOG_OK_BUTTON, okButton);
        okButton.setText("Ok");
        okButton.getStyleClass().add(CLASS_RVMM_DIALOG_BUTTON);
        okPane.getChildren().add(okButton);
        okPane.setAlignment(Pos.CENTER_RIGHT);

        AppLanguageModule languageSettings = app.getLanguageModule();
        languageSettings.addLabeledControlProperty(RVMM_SUBREGION_DIALOG_OK_BUTTON + "_TEXT",    okButton.textProperty());
      
        // AND SETUP THE EVENT HANDLERS
    }
    
    private void editSubregion(SubregionPrototype sub) {
        MapMakerData mmData = (MapMakerData)app.getDataComponent();
        String name = mmData.getName();
        SubregionPrototype edited = (SubregionPrototype)sub.clone();
        edited.setName(subregionName.getText());
        edited.setCapital(capitalName.getText());
        edited.setLeader(leaderName.getText());
        String path = mmData.getExportPath() + name + "/";
        name = edited.getName();
        path = path + name + " Flag.png";
        File img = new File(path);
        URI imgURL = img.toURI();
        edited.setImage(null);
        if(img.exists()) {
            edited.setImage(new Image(imgURL.toString()));
        }
        if(!sub.equals(edited)) {
            if(oldSubregion.contains(sub)) {
                int index = oldSubregion.indexOf(sub);
                editedSubregion.remove(index);
                editedSubregion.add(index, edited);
            }
            else {
                oldSubregion.add(sub);
                editedSubregion.add(edited);
            }
        }
    }

    public void showSubregionDialog(SubregionPrototype sub) {        
        // USE THE TEXT IN THE HEADER FOR ADD
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(RVMM_SUBREGION_DIALOG_HEADER_TEXT);
        titleLabel.setText(headerText);
        setTitle(headerText);

        String subregionNameLabel = props.getProperty(RVMM_SUBREGION_DIALOG_SUBREGION_TEXT);
        subregion.setText(subregionNameLabel);
        subregionName.setText(sub.getName());
        String capitalNameLabel = props.getProperty(RVMM_SUBREGION_DIALOG_CAPITAL_TEXT);
        capital.setText(capitalNameLabel);
        capitalName.setText(sub.getCapital());
        String leaderNameLabel = props.getProperty(RVMM_SUBREGION_DIALOG_LEADER_TEXT);
        leader.setText(leaderNameLabel);
        leaderName.setText(sub.getLeader());
        String flagNameLabel = props.getProperty(RVMM_SUBREGION_DIALOG_FLAG_TEXT);
        flag.setText(flagNameLabel);
        okButton.setText("Ok");
        Image flagImg = sub.getImage();
        Image defaultImg = new Image("file:./images/NoFlagImage.png");
        flagImage.setImage(defaultImg);
        double value = defaultImg.getWidth()/defaultImg.getHeight();
        flagImage.setFitWidth(value*300);
        flagImage.setFitHeight(300);
        if(flagImg != null) {
            flagImage.setImage(flagImg);
            value = flagImg.getWidth()/flagImg.getHeight();
            flagImage.setFitWidth(value*300);
            flagImage.setFitHeight(300);
        }
        
        MapMakerData data = (MapMakerData)app.getDataComponent();
        int index = data.getSubregionIndex(sub);
        prevButton.setOnAction(e->{
            SubregionPrototype present = sub;
            SubregionPrototype s = data.getSubregionAtIndex(index-1);
            if(index == -1) {
                if(editedSubregion.contains(sub)) {
                    int oldIndex = editedSubregion.indexOf(sub);
                    present = oldSubregion.get(oldIndex);
                    int i = data.getSubregionIndex(present);
                    s = data.getSubregionAtIndex(i-1);
                }
            }
            if(s != null) {
                if(oldSubregion.contains(s)) {
                    int oldIndex = oldSubregion.indexOf(s);
                    s = editedSubregion.get(oldIndex);
                }
                editSubregion(present);
                showSubregionDialog(s);
            }
        });
        nextButton.setOnAction(e-> {
            SubregionPrototype present = sub;
            SubregionPrototype s = data.getSubregionAtIndex(index+1);
            if(index == -1) {
                if(editedSubregion.contains(sub)) {
                    int oldIndex = editedSubregion.indexOf(sub);
                    present = oldSubregion.get(oldIndex);
                    int i = data.getSubregionIndex(present);
                    s = data.getSubregionAtIndex(i+1);
                }
            }
            if(s != null) {
                if(oldSubregion.contains(s)) {
                    int oldIndex = oldSubregion.indexOf(s);
                    s = editedSubregion.get(oldIndex);
                }
                editSubregion(present);
                showSubregionDialog(s);
            }
        });
        okButton.setOnAction(e->{
             SubregionPrototype present = sub;
            if(editedSubregion.contains(sub)) {
                int oldIndex = editedSubregion.indexOf(sub);
                present = oldSubregion.get(oldIndex);
            }
            editSubregion(present);
            if(!oldSubregion.isEmpty()) {
                Editsubregions_Transaction transaction = new Editsubregions_Transaction(data, 
                    (ArrayList<SubregionPrototype>)oldSubregion.clone(), (ArrayList<SubregionPrototype>)editedSubregion.clone());
                app.processTransaction(transaction);
            }
            clearEditedSubregion();
            this.hide();
        });
        show();
    }
    
    public void clearEditedSubregion() {
        oldSubregion.clear();
        editedSubregion.clear();
    }
    
}