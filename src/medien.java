//Imports File
import java.io.*;
import java.net.MalformedURLException;



//Basic Media-Player functionality
import javax.swing.*;

import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.scene.media.MediaView;
import java.util.*;


import javafx.scene.paint.Color;
//Buttons
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.Insets;




public class medien extends Application {
	
    public void start(Stage primaryStage)
    
    {
        primaryStage.setTitle("Uniplayer-Beta");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000,1000);
        final List<MediaPlayer> MP = new ArrayList<MediaPlayer>();
        MP.add(new MediaPlayer(new Media(geturl())));
        final MediaView mediaview = new MediaView(MP.get(0));
        HBox bb = new HBox();
        
        
        final Button startpause = new Button();
        startpause.setText("Pause");
        startpause.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                if ("Pause".equals(startpause.getText()))
                {
                	MP.get((MP.size()-1)).pause();
                	startpause.setText("Play");
                }
                else
                {
                	MP.get((MP.size()-1)).play();
                	startpause.setText("Pause");
                }
            }
        });
        
        final Button chosefile = new Button();
        chosefile.setText("Pick a file");
        chosefile.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	MP.get(MP.size()-1).stop();
            	MP.add(new MediaPlayer(new Media(geturl())));
            	mediaview.setMediaPlayer(MP.get((MP.size()-1)));
            	MP.get((MP.size()-1)).play();
            
            	
            }
        });
        
        //Layout
        bb.getChildren().addAll(startpause, chosefile);
        bb.setPadding(new Insets(15, 12, 15, 12));
        root.setBottom(bb);
        root.setCenter(mediaview);
        MP.get(0).setAutoPlay(true);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
	        	
	}
	
	@SuppressWarnings("deprecation")
	String geturl()
    {
    	File read;
    	String URL = null;
    	
    	JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(
           JFileChooser.FILES_ONLY );
        int result = fileChooser.showOpenDialog( fileChooser);
        

        // user clicked Cancel button on dialog
        if ( result == JFileChooser.CANCEL_OPTION )
        {
        	return null;
        }
        else
        {
           read = fileChooser.getSelectedFile();
           try
           {
           URL = read.toURL().toExternalForm();
           }
           catch (MalformedURLException ex)
           {
        	   System.out.println("URL konnte nicht geholt werden");
        	   return null;
           };
        }
        return URL;
    	
    }

	public static void main(String args[])
    {
    	launch();
    }
}
