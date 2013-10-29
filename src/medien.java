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

import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.ICodec;

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
	
	final int[] width = null;
	final int[] height = null;
	 List<MediaPlayer> MP = new ArrayList<MediaPlayer>();
     List<Label> Title = new ArrayList<Label>();
    int current;
    final MediaView mediaview = new MediaView();

	public void start(final Stage pS)
    {
		pS.setTitle("Uniplayer-Alpha");
		
        BorderPane root = new BorderPane();
        
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
 
            	//Didn't accept the rest of the style (kinda magic?)
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
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        pS.setX(bounds.getMinX());
        pS.setY(bounds.getMinY());
        pS.setWidth(bounds.getWidth());
        pS.setHeight(bounds.getHeight());

        Scene scene = new Scene(root);
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
        	   getdimensions(read.getPath());
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
		
		Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
		mediaview.setScaleX((bounds.getWidth()/width[current])*0.7);
		//bounds.getWidth()/(MP.get(current).getMedia().getWidth()*0.7)
		
	}
	

	void getdimensions(String Path)
	{
		// Create a Xuggler container object
		
		IContainer container = IContainer.make();

		// Open up the container
		if (container.open(Path, IContainer.Type.READ, null) < 0)
		{
			System.out.println("Pathfehler");
			width[width.length] = 0;
			height[height.length] = 0;
			
		}

		// query how many streams the call to open found
		int numStreams = container.getNumStreams();

		// and iterate through the streams to find the first video stream
		int videoStreamId = -1;
		IStreamCoder videoCoder = null;
		for (int i = 0; i < numStreams; i++) {
		  // Find the stream object
		  IStream stream = container.getStream(i);
		  // Get the pre-configured decoder that can decode this stream;
		  IStreamCoder coder = stream.getStreamCoder();

		  if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
		    videoStreamId = i;
		    videoCoder = coder;
		    break;
		  }
		}
		if (videoStreamId == -1)
			if (container.open(Path, IContainer.Type.READ, null) < 0)
			{
				System.out.println("NoVideo");
				width[width.length] = 0;
				height[height.length] = 0;
				
			}

		/*
		 * Now we have found the video stream in this file. 
		 * Let's open up our decoder so it can do work.
		 */
		if (videoCoder.open() < 0)
			if (container.open(Path, IContainer.Type.READ, null) < 0)
			{
				System.out.println("DecoderError");
				width[width.length] = 0;
				height[height.length] = 0;
				
			}

		// here you have what you need
		height[height.length] = videoCoder.getHeight();
		width[width.length] = videoCoder.getWidth();
	}

	public static void main(String args[])
    {
    	launch();
    }
}
