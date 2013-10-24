
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.*;

import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.scene.media.MediaView;




public class medien extends Application {


    public static void main(String args[])
    {
    	launch();
    }
    public void start(Stage primaryStage)
    
    {
        primaryStage.setTitle("Uniplayer-Beta");
        Group root = new Group();
        Scene scene = new Scene(root,1000,1000);
        Media audio = new Media(geturl());
        
        MediaPlayer MP = new MediaPlayer(audio);
        MP.setAutoPlay(true);
        MediaView mediaview = new MediaView(MP);
        ((Group)scene.getRoot()).getChildren().add(mediaview);
        primaryStage.setScene(scene);
        primaryStage.show();
	        	
	}
    
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
}

