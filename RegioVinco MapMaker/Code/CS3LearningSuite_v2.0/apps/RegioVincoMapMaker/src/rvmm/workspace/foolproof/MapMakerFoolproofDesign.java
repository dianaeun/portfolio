package rvmm.workspace.foolproof;

import djf.modules.AppGUIModule;
import djf.ui.foolproof.FoolproofDesign;
import rvmm.RegioVincoMapMakerApp;
import static rvmm.MapMakerPropertyType.RVMM_MOVE_DOWN_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_MOVE_UP_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_REMOVE_IMAGE_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_SNAP_IMAGE_BOTTOM_LEFT_BUTTON;
import static rvmm.MapMakerPropertyType.RVMM_SNAP_IMAGE_TOP_LEFT_BUTTON;
import rvmm.data.MapMakerData;

/**
 *
 * @author Daye Eun
 */
public class MapMakerFoolproofDesign implements FoolproofDesign {
    RegioVincoMapMakerApp app;
    
    public MapMakerFoolproofDesign(RegioVincoMapMakerApp initApp) {
        app = initApp;
    }

    @Override
    public void updateControls() {
        AppGUIModule gui = app.getGUIModule();       
        // CHECK AND SEE IF A TABLE ITEM IS SELECTED
        MapMakerData data = (MapMakerData)app.getDataComponent();
        boolean itemIsSelected = data.isSubregionSelected();
        int index = data.getSubregionIndex(data.getSelectedSubregion());
        gui.getGUINode(RVMM_MOVE_UP_BUTTON).setDisable(!itemIsSelected || index == 0);
        gui.getGUINode(RVMM_MOVE_DOWN_BUTTON).setDisable(!itemIsSelected || index == data.getNumSubregions()-1);
        
        boolean imageSelected = data.getImageSelected();
        gui.getGUINode(RVMM_REMOVE_IMAGE_BUTTON).setDisable(!imageSelected);
        gui.getGUINode(RVMM_SNAP_IMAGE_TOP_LEFT_BUTTON).setDisable(!imageSelected);
        gui.getGUINode(RVMM_SNAP_IMAGE_BOTTOM_LEFT_BUTTON).setDisable(!imageSelected);        
    }
}