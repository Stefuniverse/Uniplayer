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
	
	static final String Layout_Main1 = ("-fx-background-color: #333333;"
				+ "-fx-text-fill: white;"
				+ "-fx-font-size: 15;");
	
	static final String Layout_Main2 = ("-fx-background-color: #666666;"
			+ "-fx-text-fill: white;"
			+ "-fx-font-size: 15;");
	
	
	static final String Layout_active1 = ("-fx-text-fill: #7DA1EB;"
			+ "-fx-background-color: #333333;"
			+ "-fx-font-size: 15;");
	
	static final String Layout_active2 = ("-fx-text-fill: #7DA1EB;"
			+ "-fx-background-color: #666666;"
			+ "-fx-font-size: 15;");
	
	static final String Layout_Action1 = ("-fx-text-fill: #FF6600;"
			+ "-fx-background-color: #333333;"
			+ "-fx-font-size: 15;");
	
	static final String Layout_Action2 = ("-fx-text-fill: #FF6600;"
			+ "-fx-background-color: #666666;"
			+ "-fx-font-size: 15;");
	
	static final String Layout_Playlist = ("-fx-background-color: #333333;"
										+ "-fx-border-width: 2;"
										+ "-fx-border-color: white;");
	
	static final String Layout_Panel = ("-fx-background-color: #333333;"
									+ "-fx-border-width: 2;"
									+ "-fx-border-color: white;");
	
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
	List<String> URL = new LinkedList<String>();
    List<Label> Title = new ArrayList<Label>();
    MediaPlayer MP;
    int current = 0;

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
        bb.setMinWidth(bounds.getWidth());
        bb.setMinHeight(bounds.getHeight()*0.1);
        bb.setStyle(Layout_Panel);
        list.setMinWidth(bounds.getWidth()*0.15);
        list.setMinHeight(bounds.getHeight());
        list.setStyle(Layout_Playlist);
        mediaview.setFitWidth(bounds.getWidth()*0.8);
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
        	   
        		   
        	URL.add(read.toURL().toExternalForm().replace(" ", "%20"));
        	   

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
		
		Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
		
		l.setMinWidth(bounds.getWidth()*0.15);
		
		if (Title.size() % 2 != 0) {
			
			l.setStyle(Layout_Main1);
			
		}
		
		else {
			
			l.setStyle(Layout_Main2);
			
		}
		
		
		l.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	
		        updateview(s, Title.indexOf(l));
		            
		    }
		});
		
		l.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	
		    	if (Title.indexOf(l) % 2 != 0) {
		        
		    		l.setStyle(Layout_Action1);
		    	
		    	}
		    	else {
		    		l.setStyle(Layout_Action2);
		    	}
		            
		    }
		});
		
		l.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	if (Title.indexOf(l) != current)
		    	{
		    		if (Title.indexOf(l) % 2 != 0) {
		    			
		    			l.setStyle(Layout_Main1);
		    			
		    		}
		    		
		    		else {
		    			
		    			l.setStyle(Layout_Main2);
		    		}
		    	}
		    	
		    	else
		    		
		    	{
		    		if (Title.indexOf(l) % 2 != 0) {
				        
				    	l.setStyle(Layout_active1);
				    	
				    	}
				    else {
				    	l.setStyle(Layout_active2);
				    	}
		    	}
		            
		    }
		});
		
		return l;
	}
	
	
	void updateview(final Stage s, int Newtoplay)
	{
		if (MP != null) {
			
			MP.stop();
		
			if ((current % 2) != 0) {
			
				Title.get(current).setStyle(Layout_Main1);
			
			}
		
			else {
			
				Title.get(current).setStyle(Layout_Main2);
			}
		}
		
		
		
		MP = new MediaPlayer(new Media(URL.get(Newtoplay)));
		
        MP.setOnEndOfMedia(new Runnable() {
			@Override public void run() {
				
				if (current != Title.size()-1) {
					
					updateview(s, current++);
					
					}
				}
			});
        
		MP.play();
		
		current = Newtoplay;
		
		if ((current % 2) != 0) {
			
			Title.get(current).setStyle(Layout_active1);
		
		}
	
		else {
		
			Title.get(current).setStyle(Layout_active2);
		}
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
