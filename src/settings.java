
import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class settings{
	
	final Stage oS;
	final Stage Main;
	final VBox optionlister;
	final HBox Buttons;
	final BorderPane root;
	final Scene settings;
	final Button save;
	final Button cancel;
	final List<Boolean> checkboxvalues;
	final List<Double> doubles;
	
	//This creates an instance of settings, wich creates the optionitems, in this way we can create options for every MediaPlayer
	//The program could easily become inhanced in this way, but i hadnt the time yet to do this
	public settings(Stage main) {
		//Saves all datas the user sets in the options
		checkboxvalues = new LinkedList<Boolean>();
		doubles = new LinkedList<Double>();
		//Options layout
		oS = new Stage();
		Main = main;
		optionlister = new VBox();
		Buttons = new HBox();
		root = new BorderPane();
		settings = new Scene(root,400,600);
		//Buttons
		save = new Button("Save");
		cancel = new Button("Cancel");
		
		// Settingsitems
		final settingsitem Playlist = new settingsitem(true, true, "length of the Playlist", 200.0, 600.0);
		
		//Affinities for all elements
		optionlister.getChildren().addAll(Playlist);
		Buttons.getChildren().addAll(save, cancel);
		root.setCenter(optionlister);
		root.setBottom(Buttons);
		
		//listeners for the Buttons
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				oS.close();
			}
		});
		//stores the options and refreshs the window
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				checkboxvalues.clear();
				doubles.clear();
				
				checkboxvalues.add(Playlist.getcheckbox1());
				checkboxvalues.add(Playlist.getcheckbox2());
				doubles.add(Playlist.getValue());
				Layout.refresh(Main, checkboxvalues, doubles);
				
			}
		});
		// Setting title
		oS.setScene(settings);
		oS.setTitle("Uniplayer-Options");
	}
	//starts the options-window
	public void start() {
		oS.show();
		}

}
