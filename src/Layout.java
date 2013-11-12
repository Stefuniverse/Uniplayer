
import java.io.File;
import java.net.MalformedURLException;

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
//Buttons and layout
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.JFileChooser;

import Interfaces.Options_playlist;
import Interfaces.Styles;

public class Layout extends Application implements Styles, Options_playlist {
	
	
	static final String Layout_Button = ("-fx-background-color: null;");
	
	
	final Image pause = new Image(getClass().getResourceAsStream("ressources/Pause.png"));
	final Image play = new Image(getClass().getResourceAsStream("ressources/Play.png"));
	final Image Next = new Image(getClass().getResourceAsStream("ressources/Next.png"));
	final Image Prev = new Image(getClass().getResourceAsStream("ressources/Prev.png"));
	final Image beginning = new Image(getClass().getResourceAsStream("ressources/beginning.png"));
	final Image sound = new Image(getClass().getResourceAsStream("ressources/sound.png"));
	
	final BorderPane root = new BorderPane();
	final BorderPane UIe = new BorderPane();
	final HBox volumec = new HBox();
	final HBox progc = new HBox();
    final HBox bb = new HBox();
    final HBox pb = new HBox();
    final VBox menu = new VBox();
    final VBox list = new VBox();

    final Button startpause = new Button();
    final Button prev = new Button();
    final Button next = new Button();
    final Button restart = new Button();
    final Slider Volume = new Slider();
    final Label Vimage = new Label();
    
    final MenuBar menubar = new MenuBar();
    final Menu menuFile = new Menu("Datei");
    final MenuItem playatonce = new MenuItem("Play at once");
    final MenuItem playlater = new MenuItem("Play later");
    final MenuItem Settings = new MenuItem("Settings");
    
	int[] width = null;
	int[] height = null;
    MediaPlayer MP;
    int current = 0;
    final static Songprogress Mip = new Songprogress();

	@Override
	public void start(final Stage pS)
    {
		final settings set = new settings();
        startpause.setGraphic(new ImageView(pause));
        prev.setGraphic(new ImageView(Prev));
        next.setGraphic(new ImageView(Next));
        restart.setGraphic(new ImageView(beginning));
        
        startpause.setStyle(Layout_Button);
        next.setStyle(Layout_Button);
        prev.setStyle(Layout_Button);
        restart.setStyle(Layout_Button);
        
        Mip.start();
        
        Vimage.setGraphic(new ImageView(sound));
        pS.setTitle("Uniplayer-Alpha");
        Volume.setMax(1);
        Volume.setMin(0);
        Volume.setValue(1);
        
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
        
        Volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
			public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	Mediafunctions.setV(new_val.doubleValue());
            }
		});
        
        refresh(pS);
    }
	
	public void refresh(Stage pS) {
        
        menuFile.getItems().addAll(playatonce,playlater,Settings);
        menubar.getMenus().addAll(menuFile);
        restart.setScaleX(0.5);
        restart.setScaleY(0.5);
        prev.setScaleX(0.5);
        prev.setScaleY(0.5);
        next.setScaleX(0.5);
        next.setScaleY(0.5);
        startpause.setScaleX(0.5);
        startpause.setScaleY(0.5);
        
        bb.getChildren().addAll(prev, restart, startpause, next);
        volumec.getChildren().addAll(Vimage,Volume);
        pb.getChildren().addAll(Mip.getslider());
        menu.getChildren().addAll(pb, UIe);
        UIe.setCenter(bb);
        UIe.setRight(volumec);
        bb.setPadding(new Insets(15, 12, 15, 12));
        pb.setPadding(new Insets(15, 30, 15, 30));
        volumec.setPadding(new Insets(0, 30, 0, 0));
        volumec.setAlignment(Pos.CENTER);
        
        bb.setAlignment(Pos.CENTER);
        pb.setAlignment(Pos.CENTER);
       

        root.setBottom(menu);
        root.setRight(list);
        root.setCenter(Mediafunctions.getmediaview());
        root.setTop(menubar);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        pS.setX(bounds.getMinX());
        pS.setY(bounds.getMinY());
        pS.setWidth(bounds.getWidth());
        pS.setHeight(bounds.getHeight());
       
        Mip.getslider().setMinWidth(pS.getWidth()-80);
        list.setMaxWidth(Playlist_Width);
        list.setMinWidth(Playlist_Width);
        list.setMinHeight(pS.getHeight()*0.7);
        list.setStyle(Layout_Playlist);
        
        menu.setMinWidth(pS.getWidth());
        menu.setMaxHeight(pS.getHeight()*0.2);
        menu.setStyle(Layout_Panel);
        menu.setAlignment(Pos.BOTTOM_CENTER);
        Mediafunctions.getmediaview().setFitWidth(pS.getWidth()-Playlist_Width);
        Mediafunctions.getmediaview().setFitHeight(pS.getHeight()*0.8);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        pS.setScene(scene);
        pS.show();
	}
	        	
	
	
	@SuppressWarnings("deprecation")
	public void choosefile(Stage pS) {
		File read;
		JFileChooser fileChooser = new JFileChooser();
		
				fileChooser.setFileSelectionMode(
		        JFileChooser.FILES_ONLY );
		        int result = fileChooser.showOpenDialog( fileChooser);

		        // user clicked Cancel button on dialog
		        if ( result == JFileChooser.CANCEL_OPTION )
		        {
		        	
		        }
		        else
		        {
		           read = fileChooser.getSelectedFile();
		           try
		           {   
		        		   
		        	   Mediafunctions.getURL().add(read.toURL().toExternalForm().replace(" ", "%20"));
		        	   

		        	   Mediafunctions.getnames().add(new Playlist_Entry(read.getName(),pS, Mip));
		               
		           		list.getChildren().addAll(Mediafunctions.getnames().get((Mediafunctions.getnames().size()-1)));
		           }
		           catch (MalformedURLException ex)
		           {
		        	   ex.printStackTrace();

		           };
		       }	
			}
	
	public static void main(String args[]) {
    	launch();
    }
}
