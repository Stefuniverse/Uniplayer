//By Stefan Pawlowski updated; 26.10.2013 Uniplayer

//Imports File


import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.media.MediaView;



import java.util.*;

//Buttons and layout
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public class medien extends Application {
	
	static final String Layout_Main1 = ("-fx-background-color: #333333;"
				+ "-fx-text-fill: white;"
				+ "-fx-font-size: 12;");
	
	static final String Layout_Main2 = ("-fx-background-color: #666666;"
			+ "-fx-text-fill: white;"
			+ "-fx-font-size: 12;");
	
	
	static final String Layout_active1 = ("-fx-text-fill: #7DA1EB;"
			+ "-fx-background-color: #333333;"
			+ "-fx-font-size: 12;");
	
	static final String Layout_active2 = ("-fx-text-fill: #7DA1EB;"
			+ "-fx-background-color: #666666;"
			+ "-fx-font-size: 12;");
	
	static final String Layout_Action1 = ("-fx-text-fill: #FF6600;"
			+ "-fx-background-color: #333333;"
			+ "-fx-font-size: 12;");
	
	static final String Layout_Action2 = ("-fx-text-fill: #FF6600;"
			+ "-fx-background-color: #666666;"
			+ "-fx-font-size: 12;");
	
	static final String Layout_Playlist = ("-fx-background-color: #333333;"
										+ "-fx-border-width: 2;"
										+ "-fx-border-color: white;");
	
	static final String Layout_Panel = ("-fx-background-color: #333333;"
									+ "-fx-border-width: 2;"
									+ "-fx-border-color: white;");
	
	static final double Playlist_Width = 300.0;
	
	MediaView mediaview = new MediaView();
	final BorderPane root = new BorderPane();
    final HBox bb = new HBox();
    final HBox pb = new HBox();
    final VBox list = new VBox();
    final VBox Menu = new VBox();

    final Button startpause = new Button();
    final Button playatonce = new Button();
    final Button prev = new Button();
    final Button next = new Button();
    final Button playlater = new Button();
    Slider Songp = new Slider();
    
	int[] width = null;
	int[] height = null;
    MediaPlayer MP;

	@Override
	public void start(final Stage pS)
    {
		final Mediabinder bind = new Mediabinder(pS);
        startpause.setText("Pause");
        playatonce.setText("Play at once");
        prev.setText("Prev");
        next.setText("Next");
        playlater.setText("PLay later");
        pS.setTitle("Uniplayer-Alpha");
        Songp = Mip.getslider();
        Mip.start();
        
					
        startpause.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent event) {
        		
                playpause();
                
            }
        });
        
        playatonce.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	bind.start();
            	updateview(pS, Title.size()-1);
            	
            }
        });
        
        prev.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if ((0 != bind.getcurrent()) && (!Title.isEmpty())) {
            		
            		bind.updateview (pS,(bind.getcurrent())-1);
            	}
            }
        });
        
        next.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if (((Title.size()-1) != bind.getcurrent()) && (!Title.isEmpty())) {
            		
            		bind.updateview(pS, (bind.getcurrent()+1));
            		
            	}
            	
            }
        });
        
        playlater.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	bind.start();
    	
            }
        });
        
        //Layout
        bb.getChildren().addAll(startpause, playatonce, playlater, prev, next);
        pb.getChildren().addAll(Songp);
        Menu.getChildren().addAll(pb, bb);
        bb.setPadding(new Insets(15, 12, 15, 12));
        pb.setPadding(new Insets(15, 30, 15, 30));
        bb.setAlignment(Pos.CENTER);
        pb.setAlignment(Pos.CENTER);
       
        list.setPadding(new Insets(20, 0, 0, 5));
        root.setBottom(Menu);
        root.setRight(list);
        root.setCenter(mediaview);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        pS.setX(bounds.getMinX());
        pS.setY(bounds.getMinY());
        pS.setWidth(bounds.getWidth());
        pS.setHeight(bounds.getHeight());
       
        Songp.setMinWidth(pS.getWidth()-80);
        list.setMinWidth(Playlist_Width);
        list.setMinHeight(pS.getHeight()*0.8);
        list.setStyle(Layout_Playlist);
        
        Menu.setMinWidth(pS.getWidth());
        Menu.setMaxHeight(pS.getHeight()*0.2);
        Menu.setStyle(Layout_Panel);
        Menu.setAlignment(Pos.BOTTOM_CENTER);
        mediaview.setFitWidth(pS.getWidth() - Playlist_Width);
		mediaview.setFitHeight(pS.getHeight()*0.8);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        pS.setScene(scene);
        pS.show();
	        	
	}
	
	void playpause() {
		
		if ("Pause".equals(startpause.getText())) {
        	if (MP != null) {
        		MP.pause();
        		startpause.setText("Play");
        	}
        }
        else {
        	if (MP != null)
        	{
        		MP.play();
        		startpause.setText("Pause");
        	}
        }
	}
	
	MediaPlayer getplayer() {
		return MP;
	}
	
	
	public static void main(String args[]) {
    	launch();
    }
}
