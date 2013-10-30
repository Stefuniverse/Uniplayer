//By Stefan Pawlowski updated; 26.10.2013 Uniplayer

//Imports File

import java.io.*;
import java.net.MalformedURLException;

//Basic Media-Player functionality
import javax.swing.*;

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
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;




public class medien extends Application {
	
	static final String Layout_Main = ("-fx-background-color: #333333;"
				+ "-fx-text-fill: white;"
				+ "-fx-font-size: 15;");
	
	static final String Layout_active = ("-fx-text-fill: #7DA1EB;");
	
	static final String Layout_Action = ("-fx-text-fill : #FF6600");
	
	MediaView mediaview = new MediaView();
	final BorderPane root = new BorderPane();
    final HBox bb = new HBox();
    final VBox list = new VBox();

    final Button startpause = new Button();
    final Button playatonce = new Button();
    final Button prev = new Button();
    final Button next = new Button();
    final Button playlater = new Button();
    
    
	int[] width = null;
	int[] height = null;
	String[] URL = null;
    List<Label> Title = new ArrayList<Label>();
    MediaPlayer MP;
    int current;

	public void start(final Stage pS)
    {
        
        startpause.setText("Pause");
        playatonce.setText("Play at once");
        prev.setText("Prev");
        next.setText("Next");
        playlater.setText("PLay later");
        pS.setTitle("Uniplayer-Alpha");
        
        
        startpause.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent event) {
        		
                playpause();
                
            }
        });
        
        playatonce.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	geturl(pS);
            	
            	updateview(pS, Title.size()-1);
    
            	
            }
        });
        
        prev.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if ((0 != current) && (!Title.isEmpty())) {
            		
            		updateview(pS,current--);
            	}
       	
            }
        });
        
        next.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if (((Title.size()-1) != current) && (!Title.isEmpty())) {
            		
            		updateview(pS, current++);
            		
            	}
            	
            }
        });
        
        playlater.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	geturl(pS);
    	
            }
        });
        
        //Layout
        bb.getChildren().addAll(startpause, playatonce, playlater, prev, next);
        bb.setPadding(new Insets(15, 12, 15, 12));
        list.setPadding(new Insets(20, 9, 0, 0));
        root.setBottom(bb);
        root.setRight(list);
        root.setCenter(mediaview);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        pS.setX(bounds.getMinX());
        pS.setY(bounds.getMinY());
        pS.setWidth(bounds.getWidth());
        pS.setHeight(bounds.getHeight());
        mediaview.setFitWidth(bounds.getWidth()*0.7);
		mediaview.setFitHeight(bounds.getHeight()*0.8);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        pS.setScene(scene);
        pS.show();
	        	
	}
	
	@SuppressWarnings("deprecation")
	void geturl(Stage pS)
    {
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
        	   //getdimensions(read.getPath());
        	   URL[URL.length] = ( read.toURL().toExternalForm().replace(" ", "%20"));
        	   Title.add(Labelbuilder(read.getName(),pS));
               
           		list.getChildren().addAll(Title.get((Title.size()-1)));
           }
           catch (MalformedURLException ex)
           {
        	   System.out.println("URL konnte nicht geholt werden");

           };
        }

    }
	
	Label Labelbuilder(String Input, final Stage s)
	{
		final Label l = new Label(Input);
		l.setStyle(Layout_Main);
		
		l.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	
		        updateview(s, Title.indexOf(l));
		            
		    }
		});
		
		l.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		        l.setStyle(Layout_Action);
		            
		    }
		});
		
		l.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	if (Title.indexOf(l) != current)
		    	{
		    		l.setStyle(Layout_Main);
		    	}
		    	else
		    	{
		    		Title.get(current).setStyle(Layout_active);
		    	}
		            
		    }
		});
		
		return l;
	}
	
	MediaPlayer Playerbuilder(String URL, final Stage s)
	{
		final MediaPlayer m = new MediaPlayer(new Media(URL));
		
		m.setOnEndOfMedia(new Runnable() {
			@Override public void run() {
				
				Title.get(current).setStyle(Layout_Main);
				if (Title.indexOf(m) != Title.size()-1) {
					
					updateview(s, current++);
				}
			}
		});
		
		return m;
	}
	
	void updateview(Stage s, int Newtoplay)
	{
		
		MP = new MediaPlayer(new Media(URL[Newtoplay]));
		MP.play();
		Title.get(current).setStyle(Layout_Main);
		current = Newtoplay;
		Title.get(current).setStyle(Layout_active);
		
		mediaview.setMediaPlayer(MP);
		
		s.setTitle("Uniplayer-Alpha " + Title.get(current).getText());
		
	}
	
	void playpause() {
		
		if ("Pause".equals(startpause.getText()))
        {
        	if (MP != null)
        	{
        		MP.pause();
        		startpause.setText("Play");
        	}
        }
        else
        {
        	if (MP != null)
        	{
        		MP.play();
        		startpause.setText("Pause");
        	}
        }
	}
	


	public static void main(String args[])
    {
    	launch();
    }


	
}
