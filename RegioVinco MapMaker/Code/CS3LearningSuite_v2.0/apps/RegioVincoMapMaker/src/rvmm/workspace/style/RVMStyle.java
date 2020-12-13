package rvmm.workspace.style;

/**
 * This class lists all CSS style types for this application. These
 * are used by JavaFX to apply style properties to controls like
 * buttons, labels, and panes.

 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class RVMStyle {
    public static final String EMPTY_TEXT = "";
    public static final int BUTTON_TAG_WIDTH = 75;

    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS M3Workspace'S COMPONENTS TO A STYLE SHEET THAT IT USES
    // NOTE THAT FOUR CLASS STYLES ALREADY EXIST:
    // top_toolbar, toolbar, toolbar_text_button, toolbar_icon_button
    
    public static final String CLASS_RVMM_PANE          = "rvmm_pane";
    public static final String CLASS_RVMM_BOX           = "rvmm_box";            
    public static final String CLASS_RVMM_BIG_HEADER    = "rvmm_big_header";
    public static final String CLASS_RVMM_SMALL_HEADER  = "rvmm_small_header";
    public static final String CLASS_RVMM_PROMPT        = "rvmm_prompt";
    public static final String CLASS_RVMM_TITLE        = "rvmm_title";
    public static final String CLASS_RVMM_TEXT_FIELD    = "rvmm_text_field";
    public static final String CLASS_RVMM_ICON_BUTTON   = "rvmm_icon_button";
    
    public static final String CLASS_RVMM_TABLE         = "rvmm_table";
    public static final String CLASS_RVMM_COLUMN        = "rvmm_column";
    public static final String CLASS_RVMM_MAP_OCEAN       = "rvmm_map_ocean";
    
    public static final String CLASS_RVMM_TOGGLE       = "rvmm_toggle";
    
    // STYLE CLASSES FOR THE ADD/EDIT ITEM DIALOG
    public static final String CLASS_RVMM_DIALOG_GRID           = "rvmm_dialog_grid";
    public static final String CLASS_RVMM_DIALOG_HEADER         = "rvmm_dialog_header";
    public static final String CLASS_RVMM_DIALOG_PROMPT         = "rvmm_dialog_prompt";
    public static final String CLASS_RVMM_DIALOG_TEXT_FIELD     = "rvmm_dialog_text_field";
    public static final String CLASS_RVMM_DIALOG_CHECK_BOX      = "rvmm_dialog_check_box";
    public static final String CLASS_RVMM_DIALOG_BUTTON         = "rvmm_dialog_button";
    public static final String CLASS_RVMM_DIALOG_PANE           = "rvmm_dialog_pane";
}