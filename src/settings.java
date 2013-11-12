import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class settings {
	
	final Stage pS;
	final VBox optionlister;
	final HBox Buttons;
	final BorderPane root;
	final Scene settings;
	final Button save;
	final Button cancel;
	
	public settings() {
		pS = new Stage();
		optionlister = new VBox();
		Buttons = new HBox();
		root = new BorderPane();
		settings = new Scene(root,400,600);
		save = new Button("Save");
		cancel = new Button("Cancel");
		
		settingsitem Playlist = new settingsitem(true, true, "length of the Playlist", 200.0, 600.0);
		
		optionlister.getChildren().addAll(Playlist);
		Buttons.getChildren().addAll(save, cancel);
		root.setCenter(optionlister);
		root.setBottom(Buttons);
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pS.close();
			}
		});
		
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Still working on it");
			}
		});
		
		pS.setScene(settings);
		pS.setTitle("Uniplayer-Options");
	}

	public void start() {
		pS.show();
		}

}
