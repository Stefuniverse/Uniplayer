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
import javafx.scene.input.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;




public class medien extends Application {
	
	final List<MediaPlayer> MP = new ArrayList<MediaPlayer>();
    final List<Label> Title = new ArrayList<Label>();
    int current;
    final MediaView mediaview = new MediaView();

	public void start(final Stage pS)
    {
		pS.setTitle("Uniplayer-Alpha");
		
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000,1000);
        
        final HBox bb = new HBox();
        final VBox list = new VBox();
        
        
        final Button startpause = new Button();
        startpause.setText("Pause");
        startpause.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                if ("Pause".equals(startpause.getText()))
                {
                	if (!MP.isEmpty())
                	{
                		MP.get(current).pause();
                		startpause.setText("Play");
                	}
                }
                else
                {
                	if (!MP.isEmpty())
                	{
                		MP.get(current).play();
                		startpause.setText("Pause");
                	}
                }
            }
        });
        
        final Button playatonce = new Button();
        playatonce.setText("Play at once");
        playatonce.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	String NewT[] = geturl();
            	
            	if (!MP.isEmpty())
            	{
            		MP.get(current).stop();
            		Title.get(current).setStyle("-fx-text-fill: white;");
            	}
            	
            	MP.add(Playerbuilder(NewT[0],pS));
            	MP.get((MP.size()-1)).play();
            	
            	current = (MP.size()-1);
            	
            	Title.add(Labelbuilder(NewT[1],pS));
            	list.getChildren().addAll(Title.get((Title.size()-1)));
 
            	//Didn't accepted the rest of the style (kinda magic?)
            	Title.get(current).setStyle("-fx-text-fill: #7DA1EB;"
            								+ "-fx-background-color: #333333;"
            								+ "-fx-font-size: 15;");
            	
            	
            	updateview(pS);
    
            	
            }
        });
        
        final Button prev = new Button();
        prev.setText("Prev");
        prev.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if ((0 != current) && (!MP.isEmpty()))
            	{
            		MP.get(current).stop();
            		Title.get(current).setStyle("-fx-text-fill: white;");
                	current--;
                	Title.get(current).setStyle("-fx-text-fill: #7DA1EB;");
                	MP.get(current).play();
            	}
            	
            	
            	updateview(pS);
    
            	
            }
        });
        
        final Button next = new Button();
        next.setText("Next");
        next.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	if (((MP.size()-1) != current) && (!MP.isEmpty()))
            	{
            		MP.get(current).stop();
            		Title.get(current).setStyle("-fx-text-fill: white;");
                	current++;
                	Title.get(current).setStyle("-fx-text-fill: #7DA1EB;");
                	MP.get(current).play();
            	}
            	
            	
            	updateview(pS);
    
            	
            }
        });
        
        final Button playlater = new Button();
        playlater.setText("PLay later");
        playlater.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            	String NewT[] = geturl();
            	
            	MP.add(Playerbuilder(NewT[0],pS));
            	
            	Title.add(Labelbuilder(NewT[1],pS));
            	list.getChildren().addAll(Title.get((Title.size()-1)));
            	
            	updateview(pS);
    
            	
            }
        });
        
        //Layout
        bb.getChildren().addAll(startpause, playatonce, playlater, prev, next);
        bb.setPadding(new Insets(15, 12, 15, 12));
        list.setPadding(new Insets(20, 9, 0, 0));
        root.setBottom(bb);
        root.setRight(list);
        root.setCenter(mediaview);
        scene.setFill(Color.BLACK);
        pS.setScene(scene);
        pS.show();
	        	
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
	
	Label Labelbuilder(String Input, final Stage s)
	{
		final Label l = new Label(Input);
		l.setStyle("-fx-background-color: #333333;"
				+ "-fx-text-fill: white;"
				+ "-fx-font-size: 15");
		
		l.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		        MP.get(current).stop();
		        Title.get(current).setStyle("-fx-text-fill: white;");
		        current = Title.indexOf(l);
		        MP.get(current).play();
		        Title.get(current).setStyle("-fx-text-fill: #7DA1EB;");
		        updateview(s);
		            
		    }
		});
		
		l.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		        l.setStyle("-fx-text-fill : #FF6600");
		            
		    }
		});
		
		l.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	if (Title.indexOf(l) != current)
		    	{
		    		l.setStyle("-fx-text-fill : white");
		    	}
		    	else
		    	{
		    		Title.get(current).setStyle("-fx-text-fill: #7DA1EB;");
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
				
				Title.get(current).setStyle("-fx-text-fill: white;");
				if (MP.indexOf(m) != MP.size()-1);
				{
					MP.get(MP.indexOf(m)+1).play();
					current++;
					Title.get(current).setStyle("-fx-text-fill: #7DA1EB;");
					updateview(s);
				}
			}
		});
		
		return m;
	}
	
	void updateview(Stage s)
	{
		mediaview.setMediaPlayer(MP.get(current));
		s.setTitle("Uniplayer-Alpha " + Title.get(current).getText());
		
		
	}

	public static void main(String args[])
    {
    	launch();
    }
}
