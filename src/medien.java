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
import javafx.stage.Stage;
import javafx.scene.media.MediaView;
import java.util.*;


//Buttons and layout
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;




public class medien extends Application {
	

	public void start(Stage primaryStage)
    
    {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000,1000);
        final List<MediaPlayer> MP = new ArrayList<MediaPlayer>();
        final List<Label> Title = new ArrayList<Label>();
        
        String NewT[] = geturl();
        Title.add(new Label(NewT[1]));
        primaryStage.setTitle("Uniplayer-Beta - " + NewT[1]);
        MP.add(new MediaPlayer(new Media(NewT[0])));
        
        final MediaView mediaview = new MediaView(MP.get(0));
        final HBox bb = new HBox();
        final VBox list = new VBox();
        
        
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
            	
            	String NewT[] = geturl();
            	MP.get(MP.size()-1).stop();
            	MP.add(new MediaPlayer(new Media(NewT[0])));
            	mediaview.setMediaPlayer(MP.get((MP.size()-1)));
            	MP.get((MP.size()-1)).play();
            	
            	list.getChildren().addAll(Title.get((Title.size()-1)));
            
            	
            }
        });
        
        //Layout
        bb.getChildren().addAll(startpause, chosefile);
        list.getChildren().addAll(Title.get(0));
        bb.setPadding(new Insets(15, 12, 15, 12));
        bb.setPadding(new Insets(20, 9, 0, 0));
        root.setBottom(bb);
        root.setRight(list);
        root.setCenter(mediaview);
        MP.get(0).setAutoPlay(true);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
	        	
	}
	
	@SuppressWarnings("deprecation")
	String[] geturl()
    {
    	File read;
    	String URL[] = new String[2];
    	
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
           URL[0] = read.toURL().toExternalForm().replace(" ", "%20");
           URL[1] = read.getName();
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
