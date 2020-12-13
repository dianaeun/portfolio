package rvmm.clipboard;

import djf.components.AppClipboardComponent;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import rvmm.RegioVincoMapMakerApp;
import rvmm.data.MapMakerData;
import rvmm.data.SubregionPrototype;

/**
 *
 * @author McKillaGorilla
 */
public class MapMakerClipboard implements AppClipboardComponent {
    RegioVincoMapMakerApp app;
    ArrayList<SubregionPrototype> clipboardCutItems;
    ArrayList<SubregionPrototype> clipboardCopiedItems;
    
    public MapMakerClipboard(RegioVincoMapMakerApp initApp) {
        app = initApp;
        clipboardCutItems = null;
        clipboardCopiedItems = null;
    }
    
    @Override
    public void cut() {
        MapMakerData data = (MapMakerData)app.getDataComponent();
        if (data.isSubregionSelected() || data.areSubretgionsSelected()) {
            clipboardCutItems = new ArrayList(data.getSelectedSubregions());
            clipboardCopiedItems = null;
//            CutItems_Transaction transaction = new CutItems_Transaction((RegioVincoMapMakerApp)app, clipboardCutItems);
//            app.processTransaction(transaction);
        }
    }

    @Override
    public void copy() {
        MapMakerData data = (MapMakerData)app.getDataComponent();
        if (data.isSubregionSelected() || data.areSubretgionsSelected()) {
            ArrayList<SubregionPrototype> tempItems = new ArrayList(data.getSelectedSubregions());
            copyToCopiedClipboard(tempItems);
        }
    }
    
    private void copyToCutClipboard(ArrayList<SubregionPrototype> itemsToCopy) {
        clipboardCutItems = copyItems(itemsToCopy);
        clipboardCopiedItems = null;        
        app.getFoolproofModule().updateAll();        
    }
    
    private void copyToCopiedClipboard(ArrayList<SubregionPrototype> itemsToCopy) {
        clipboardCutItems = null;
        clipboardCopiedItems = copyItems(itemsToCopy);
        app.getFoolproofModule().updateAll();        
    }
    
    private ArrayList<SubregionPrototype> copyItems(ArrayList<SubregionPrototype> itemsToCopy) {
        ArrayList<SubregionPrototype> tempCopy = new ArrayList();         
        for (SubregionPrototype itemToCopy : itemsToCopy) {
            SubregionPrototype copiedItem = (SubregionPrototype)itemToCopy.clone();
            tempCopy.add(copiedItem);
        }        
        return tempCopy;
    }

    @Override
    public void paste() {
//        MapMakerData data = (MapMakerData)app.getDataComponent();
//        if (data.isSubregionSelected()) {
//            int selectedIndex = data.getSubregionIndex(data.getSelectedSubregion());  
//            ArrayList<SubregionPrototype> pasteItems = clipboardCutItems;
//            if ((clipboardCutItems != null)
//                    && (!clipboardCutItems.isEmpty())) {
//                PasteItems_Transaction transaction = new PasteItems_Transaction((RegioVincoMapMakerApp)app, clipboardCutItems, selectedIndex);
//                app.processTransaction(transaction);
//                
//                // NOW WE HAVE TO RE-COPY THE CUT ITEMS TO MAKE
//                // SURE IF WE PASTE THEM AGAIN THEY ARE BRAND NEW OBJECTS
//                copyToCutClipboard(clipboardCopiedItems);
//            }
//            else if ((clipboardCopiedItems != null)
//                    && (!clipboardCopiedItems.isEmpty())) {
//                PasteItems_Transaction transaction = new PasteItems_Transaction((RegioVincoMapMakerApp)app, clipboardCopiedItems, selectedIndex);
//                app.processTransaction(transaction);
//            
//                // NOW WE HAVE TO RE-COPY THE COPIED ITEMS TO MAKE
//                // SURE IF WE PASTE THEM AGAIN THEY ARE BRAND NEW OBJECTS
//                copyToCopiedClipboard(clipboardCopiedItems);
//            }
//        }
    }    


    @Override
    public boolean hasSomethingToCut() {
        return ((MapMakerData)app.getDataComponent()).isSubregionSelected()
                || ((MapMakerData)app.getDataComponent()).areSubretgionsSelected();
    }

    @Override
    public boolean hasSomethingToCopy() {
        return ((MapMakerData)app.getDataComponent()).isSubregionSelected()
                || ((MapMakerData)app.getDataComponent()).areSubretgionsSelected();
    }

    @Override
    public boolean hasSomethingToPaste() {
        if ((clipboardCutItems != null) && (!clipboardCutItems.isEmpty()))
            return true;
        else if ((clipboardCopiedItems != null) && (!clipboardCopiedItems.isEmpty()))
            return true;
        else
            return false;
    }
}