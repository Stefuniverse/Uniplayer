package Interfaces;
//This interface contains the CSS-Style for all objects
public interface Styles {
	//Standard Layout Style for playlist-entries
	static final String Layout_Main1 = ("-fx-background-color: #333333;"
			+ "-fx-text-fill: white;"
			+ "-fx-font-size: 12;");

	static final String Layout_Main2 = ("-fx-background-color: #666666;"
		+ "-fx-text-fill: white;"
		+ "-fx-font-size: 12;");

	//Active style for playlist-entries(Song is playing)
	static final String Layout_active1 = ("-fx-text-fill: #7DA1EB;"
		+ "-fx-background-color: #333333;"
		+ "-fx-font-size: 12;");

	static final String Layout_active2 = ("-fx-text-fill: #7DA1EB;"
		+ "-fx-background-color: #666666;"
		+ "-fx-font-size: 12;");
	//Action style for playlist-entries(MouseOver)
	static final String Layout_Action1 = ("-fx-text-fill: #FF6600;"
		+ "-fx-background-color: #333333;"
		+ "-fx-font-size: 12;");

	static final String Layout_Action2 = ("-fx-text-fill: #FF6600;"
		+ "-fx-background-color: #666666;"
		+ "-fx-font-size: 12;");
	
	//Layout of the playlist itself 
	static final String Layout_Playlist = ("-fx-background-color: #333333;"
									+ "-fx-border-width: 2;"
									+ "-fx-border-color: white;");
	
	//Layout for the optionpanel
	static final String Layout_Panel = ("-fx-background-color: #333333;"
								+ "-fx-border-width: 2;"
								+ "-fx-border-color: white;");
	//Layout for Buttons
	static final String Layout_Button = ("-fx-background-color: null;");
	
}
