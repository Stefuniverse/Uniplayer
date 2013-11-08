import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.JFileChooser;


public class Mediabinder extends Thread {
	
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
	
	File read;
	JFileChooser fileChooser;
	List<String> URL;
    List<Label> Title;
    Stage pS;
    int current;
    MediaPlayer MP;
    final Songprogress Mip;
	
	public Mediabinder(Stage main) {
	    
			fileChooser = new JFileChooser();
			URL = new LinkedList<String>();
			Title = new ArrayList<Label>();
			main = pS;
			current = 0;
			Mip = new Songprogress();
			Mip.start();
			Mip.setDaemon(true);
	    }
		
	@SuppressWarnings("deprecation")
		@Override
		public void run() {
			
			fileChooser.setFileSelectionMode(
			           JFileChooser.FILES_ONLY );
			        int result = fileChooser.showOpenDialog( fileChooser);
			        

			        // user clicked Cancel button on dialog
			        if ( result == JFileChooser.CANCEL_OPTION )
			        {
			        	break;
			        }
			        else
			        {
			           read = fileChooser.getSelectedFile();
			           try
			           {   
			        		   
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

	void updateview(final Stage s, int Newtoplay) {
	
	if (MP != null) {
		
		MP.stop();
	
		if ((current % 2) != 0) {
			Title.get(current).setStyle(Layout_Main1);
		} else {
			Title.get(current).setStyle(Layout_Main2);
		}
	}
	
	MP = new MediaPlayer(new Media(URL.get(Newtoplay)));
	
    MP.setOnEndOfMedia(new Runnable() {
		@Override public void run() {
			
			if (current != Title.size()-1) {
				
				updateview(s, (current+1));
				}
			}
		});
    
    MP.setOnReady(new Runnable() {
    	@Override public void run() {
    		Mip.setparam(MP);
    		MP.play();
    	}
    });
    
	
	current = Newtoplay;
	
	if ((current % 2) != 0) {
		
		Title.get(current).setStyle(Layout_active1);
	} else {
		
		Title.get(current).setStyle(Layout_active2);
	}
	mediaview.setMediaPlayer();
	s.setTitle("Uniplayer-Alpha " + Title.get(current).getText());
}
	public int getcurrent() {
		return current;
	}
	public List<Label> getnames() {
		return Title; 
	}
}



