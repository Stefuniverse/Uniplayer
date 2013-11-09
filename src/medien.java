
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
//Buttons and layout
import javafx.scene.control.Button;
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

public class medien extends Application {
	
	static final String Layout_Playlist = ("-fx-background-color: #333333;"
										+ "-fx-border-width: 2;"
										+ "-fx-border-color: white;");
	
	static final String Layout_Panel = ("-fx-background-color: #333333;"
									+ "-fx-border-width: 2;"
									+ "-fx-border-color: white;");
	
	static final double Playlist_Width = 300.0;
	
	static final String Layout_Button = ("-fx-background-color: null;");
	
	
	final Image pause = new Image(getClass().getResourceAsStream("ressources/Pause.png"));
	final Image play = new Image(getClass().getResourceAsStream("ressources/Play.png"));
	final Image Next = new Image(getClass().getResourceAsStream("ressources/Next.png"));
	final Image Prev = new Image(getClass().getResourceAsStream("ressources/Prev.png"));
	final Image beginning = new Image(getClass().getResourceAsStream("ressources/beginning.png"));
	final Image sound = new Image(getClass().getResourceAsStream("ressources/sound.png"));
	
	final BorderPane root = new BorderPane();
    final HBox bb = new HBox();
    final HBox pb = new HBox();
    final VBox Menu = new VBox();
    final VBox list = new VBox();

    final Button startpause = new Button();
    final Button playatonce = new Button();
    final Button prev = new Button();
    final Button next = new Button();
    final Button playlater = new Button();
    
	int[] width = null;
	int[] height = null;
    MediaPlayer MP;
    final Mediafunctions bind = new Mediafunctions();
    int current = 0;

	@Override
	public void start(final Stage pS)
    {
        startpause.setGraphic(new ImageView(pause));
        prev.setGraphic(new ImageView(Prev));
        next.setGraphic(new ImageView(Next));
        
        startpause.setStyle(Layout_Button);
        next.setStyle(Layout_Button);
        prev.setStyle(Layout_Button);
        
        
        playatonce.setText("Play at once");
        playlater.setText("PLay later");
        pS.setTitle("Uniplayer-Alpha");
        
					
        startpause.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent event) {
        		
                bind.playpause(startpause, play, pause);
                
            }
        });
        
        playatonce.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	choosefile(pS);
            	bind.updateview(pS, bind.getnames().size()-1);
            	
            }
        });
        
        prev.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if ((0 != bind.getcurrent()) && (!bind.getnames().isEmpty())) {
            		
            		bind.updateview (pS,(bind.getcurrent())-1);
            	}
            }
        });
        
        next.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if (((bind.getnames().size()-1) != bind.getcurrent()) && (!bind.getnames().isEmpty())) {
            		
            		bind.updateview(pS, (bind.getcurrent()+1));
            		
            	}
            	
            }
        });
        
        playlater.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	choosefile(pS);
    	
            }
        });
        
        //Layout
        bb.getChildren().addAll( playatonce, playlater, prev, startpause, next);
        pb.getChildren().addAll(bind.getprog());
        Menu.getChildren().addAll(pb, bb);
        bb.setPadding(new Insets(15, 12, 15, 12));
        pb.setPadding(new Insets(15, 30, 15, 30));
        bb.setAlignment(Pos.CENTER);
        pb.setAlignment(Pos.CENTER);
       
        list.setPadding(new Insets(20, 0, 0, 5));
        root.setBottom(Menu);
        root.setRight(list);
        root.setCenter(bind.getmediaview());
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        pS.setX(bounds.getMinX());
        pS.setY(bounds.getMinY());
        pS.setWidth(bounds.getWidth());
        pS.setHeight(bounds.getHeight());
       
        bind.getprog().setMinWidth(pS.getWidth()-80);
        list.setMinWidth(Playlist_Width);
        list.setMinHeight(pS.getHeight()*0.8);
        list.setStyle(Layout_Playlist);
        
        Menu.setMinWidth(pS.getWidth());
        Menu.setMaxHeight(pS.getHeight()*0.2);
        Menu.setStyle(Layout_Panel);
        Menu.setAlignment(Pos.BOTTOM_CENTER);
        bind.getmediaview().setFitWidth(pS.getWidth() - Playlist_Width);
        bind.getmediaview().setFitHeight(pS.getHeight()*0.8);

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
		        		   
		        	bind.getURL().add(read.toURL().toExternalForm().replace(" ", "%20"));
		        	   

		        	   bind.getnames().add(bind.Labelbuilder(read.getName(),pS));
		               
		           		list.getChildren().addAll(bind.getnames().get((bind.getnames().size()-1)));
		           }
		           catch (MalformedURLException ex)
		           {
		        	   System.out.println("URL konnte nicht geholt werden");

		           };
		       }	
			}
	
	
	
	public static void main(String args[]) {
    	launch();
    }
}
