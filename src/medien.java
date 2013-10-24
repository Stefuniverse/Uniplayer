//Imports File
import java.io.File;
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

//Buttons
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;




public class medien extends Application {
	
    public void start(Stage primaryStage)
    
    {
        primaryStage.setTitle("Uniplayer-Beta");
        Group root = new Group();
        Scene scene = new Scene(root,1000,1000);
        Media audio = new Media(geturl());
         final MediaPlayer MP = new MediaPlayer(audio);
        MP.setAutoPlay(true);
        MediaView mediaview = new MediaView(MP);
        
        final Button startpause = new Button();
        startpause.setText("Pause");
        startpause.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                if ("Pause".equals(startpause.getText()))
                {
                	MP.pause();
                	startpause.setText("Play");
                }
                else
                {
                	MP.play();
                	startpause.setText("Pause");
                }
            }
        });
        
        
        ((Group)scene.getRoot()).getChildren().add(mediaview);
        ((Group)scene.getRoot()).getChildren().add(startpause);
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
