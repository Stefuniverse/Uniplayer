
//Import from external libaries, providing the graphical Layout
import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import Interfaces.Styles;

//Main class sets up the graphical interface and controls every other class
public class Layout extends Application implements Styles {
	
	
	static double Playlist_Width = 300;
	
	// Streams images
	final Image pause = new Image(getClass().getResourceAsStream("ressources/Pause.png"));
	final Image play = new Image(getClass().getResourceAsStream("ressources/Play.png"));
	final Image Next = new Image(getClass().getResourceAsStream("ressources/Next.png"));
	final Image Prev = new Image(getClass().getResourceAsStream("ressources/Prev.png"));
	final Image beginning = new Image(getClass().getResourceAsStream("ressources/beginning.png"));
	final Image sound = new Image(getClass().getResourceAsStream("ressources/sound.png"));
	
	//Creates all important Layout-elements
	final static BorderPane root = new BorderPane();
	final static BorderPane UIe = new BorderPane();
	final static HBox volumec = new HBox();
	final static HBox progc = new HBox();
    final static HBox bb = new HBox();
    final static HBox pb = new HBox();
    final static VBox menu = new VBox();
    final static VBox list = new VBox();
    
    //Creates all Buttons for UI
    final static Button startpause = new Button();
    final static Button prev = new Button();
    final static Button next = new Button();
    final static Button restart = new Button();
    final static Slider Volume = new Slider();
    final static Label Vimage = new Label();
    
    // Creates Menu
    final static MenuBar menubar = new MenuBar();
    final static Menu menuFile = new Menu("File");
    final static Menu menuhelp = new Menu("help");
    final static MenuItem playatonce = new MenuItem("Play at once");
    final static MenuItem playlater = new MenuItem("Play later");
    final static MenuItem Settings = new MenuItem("Settings");
    final static MenuItem about = new MenuItem("about");
    
    // Creates an additional thread, wich is monitoring the progress of a song or video
    final static Songprogress Mip = new Songprogress();

    // Overriding start-methode of application
	@Override
	public void start(final Stage pS)
    {
		//Enables an intance of the settings-class in order to change settings
		
		final settings set = new settings(pS);
		
		//Including images to buttons
        startpause.setGraphic(new ImageView(pause));
        prev.setGraphic(new ImageView(Prev));
        next.setGraphic(new ImageView(Next));
        restart.setGraphic(new ImageView(beginning));
        
        // Styles Buttons from the Interface Styles with CSS-Styles
        startpause.setStyle(Layout_Button);
        next.setStyle(Layout_Button);
        prev.setStyle(Layout_Button);
        restart.setStyle(Layout_Button);
        
        //starting the songprogressthread
        Mip.start();
        
        //Prepares the soundslider and sets the title of the stage(window)
        Vimage.setGraphic(new ImageView(sound));
        pS.setTitle("Uniplayer-Alpha");
        Volume.setMax(1);
        Volume.setMin(0);
        Volume.setValue(1);
        
        // Eventhandler for all buttons and slider
        Settings.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		set.start();
        	}
        });
        
		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if (!Mediafunctions.getnames().isEmpty()) {
					Mediafunctions.updateview(pS, Mediafunctions.getcurrent(), Mip);
				}
			}
		});
		
        startpause.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent event) {
        		
        		Mediafunctions.playpause(startpause, play, pause);
                
            }
        });
        
        playatonce.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	choosefile(pS);
            	Mediafunctions.updateview(pS, Mediafunctions.getnames().size()-1, Mip);
            	
            }
        });
        
        prev.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if ((0 != Mediafunctions.getcurrent()) && (!Mediafunctions.getnames().isEmpty())) {
            		
            		Mediafunctions.updateview (pS,(Mediafunctions.getcurrent())-1, Mip);
            	}
            }
        });
        
        next.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if (((Mediafunctions.getnames().size()-1) != Mediafunctions.getcurrent()) && (!Mediafunctions.getnames().isEmpty())) {
            		
            		Mediafunctions.updateview(pS, (Mediafunctions.getcurrent()+1), Mip);
            		
            	}
            	
            }
        });
        
        playlater.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	choosefile(pS);
            }
        });
        
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Stage about = new Stage();
            	Label whoami = new Label("Developed by Stefan Pawlowski.");
            	Scene About = new Scene(whoami, 200,20);
            	about.setScene(About);
            	about.show();
            }
        });
        
        Volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
			public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	Mediafunctions.setV(new_val.doubleValue());
            }
		});
        
        //setup affinities between single UI-elements
        menuFile.getItems().addAll(playatonce,playlater,Settings);
        menuhelp.getItems().addAll(about);
        menubar.getMenus().addAll(menuFile, menuhelp);
        volumec.getChildren().addAll(Vimage,Volume);
        pb.getChildren().addAll(Mip.getslider());
        menu.getChildren().addAll(pb, UIe);
        bb.getChildren().addAll(prev, restart, startpause, next);
        UIe.setCenter(bb);
        UIe.setRight(volumec);
        root.setBottom(menu);
        root.setCenter(Mediafunctions.getmediaview());
        root.setTop(menubar);
        
        refresh(pS, new LinkedList<Boolean>(), new LinkedList<Double>());
        
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        pS.setScene(scene);
        pS.show();
    }
	
	public static void refresh(Stage pS, List<Boolean> checks, List<Double> doubles) {
		
        
		//Button size downscaling
        restart.setScaleX(0.5);
        restart.setScaleY(0.5);
        prev.setScaleX(0.5);
        prev.setScaleY(0.5);
        next.setScaleX(0.5);
        next.setScaleY(0.5);
        startpause.setScaleX(0.5);
        startpause.setScaleY(0.5);
        
        
        bb.setPadding(new Insets(15, 12, 15, 12));
        pb.setPadding(new Insets(15, 30, 15, 30));
        volumec.setPadding(new Insets(0, 30, 0, 0));
        volumec.setAlignment(Pos.CENTER);
        
        bb.setAlignment(Pos.CENTER);
        pb.setAlignment(Pos.CENTER);
        
       

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        pS.setX(bounds.getMinX());
        pS.setY(bounds.getMinY());
        pS.setWidth(bounds.getWidth());
        pS.setHeight(bounds.getHeight());
       
        Mip.getslider().setMinWidth(pS.getWidth()-80);
        menu.setMinWidth(pS.getWidth());
        menu.setMaxHeight(pS.getHeight()*0.2);
        menu.setStyle(Layout_Panel);
        menu.setAlignment(Pos.BOTTOM_CENTER);
        Mediafunctions.getmediaview().setFitWidth(pS.getWidth()-Playlist_Width);
        Mediafunctions.getmediaview().setFitHeight(pS.getHeight()*0.8);
	
		// Playlist
		list.setMaxWidth(Playlist_Width);
        list.setMinWidth(Playlist_Width);
        list.setMinHeight(pS.getHeight()*0.7);
        list.setStyle(Layout_Playlist);
        
        // Compares options from the List, given to this methode
        if (checks.isEmpty() || (checks.get(0)  && !checks.get(1))) {
        	root.setRight(list);
        }
        else if (checks.get(0) && checks.get(1)){
        	Stage playlist = new Stage();
        	Scene playlists = new Scene(list, Playlist_Width,pS.getHeight()*0.7);
        	playlist.setScene(playlists);
        	playlist.setTitle("Uniplayer - Playlist");
        	playlist.show();
        }
        
	}
	        	
	
	// Methode to grab a file, contains a deprecated methode
	@SuppressWarnings("deprecation")
	public void choosefile(Stage pS) {
		File read;
		JFileChooser fileChooser = new JFileChooser();
		
				fileChooser.setFileSelectionMode(
		        JFileChooser.FILES_ONLY );
		        int result = fileChooser.showOpenDialog( fileChooser);

		        // user clicked Cancel button on dialog
		        if ( result == JFileChooser.CANCEL_OPTION ){
		        	
		        } else {
		           read = fileChooser.getSelectedFile();
		           try {   
		        		   
		        	   Mediafunctions.getURL().add(read.toURL().toExternalForm().replace(" ", "%20"));
		        	   Mediafunctions.getnames().add(new Playlist_Entry(read.getName(),pS, Mip));
		        	   list.getChildren().addAll(Mediafunctions.getnames().get((Mediafunctions.getnames().size()-1)));
		           }
		           //URL-Error
		           catch (MalformedURLException ex){
		        	   ex.printStackTrace();

		           };
		       }	
			}
	//Main Methode launchs the application
	public static void main(String args[]) {
    	launch();
    }
}
