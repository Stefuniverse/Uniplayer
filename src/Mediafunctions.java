import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Mediafunctions {
	
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
	
	static List<String> URL = new LinkedList<String>();
    static List<Label> Title = new ArrayList<Label>();
    static int current = 0;
    static MediaPlayer MP;
    
    static MediaView mediaview = new MediaView();

	static Label Labelbuilder(String Input, final Stage s, final Songprogress Mip)
	{
		final Label l = new Label(Input);
	
		l.setMinWidth(s.getWidth()*0.15);
	
		if (Title.size() % 2 != 0) {
			l.setStyle(Layout_Main1);	
		}
	
		else {	
			l.setStyle(Layout_Main2);	
		}
	
	
		l.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
	    	
				updateview(s, Title.indexOf(l), Mip);        
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
					} else {
	    			
	    			l.setStyle(Layout_Main2);
					}
				} else {
					
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

	static void updateview(final Stage s, int Newtoplay, final Songprogress Mip) {
	
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
						updateview(s, (current+1), Mip);
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
			mediaview.setMediaPlayer(MP);
			s.setTitle("Uniplayer-Alpha " + Title.get(current).getText());
	}
	static void playpause(Button startpause, Image play, Image pause) {
		
			if ((MP != null) && (MP.getStatus() == Status.PLAYING)) {
				if (MP != null) {
					MP.pause();
					startpause.setGraphic(new ImageView(play));
				}
			}
			else {
				if (MP != null)
				{
					MP.play();
					startpause.setGraphic(new ImageView(pause));
				}
			}
	}

	static void setV(double x) {
		if (MP != null)
			MP.setVolume(x);
	}
	static public int getcurrent() {
				return current;
			}
	
	static public List<Label> getnames() {
				return Title; 
			}
	
	static public MediaView getmediaview() {
				return mediaview;
			}
	
	
	static public List<String> getURL() {
				return URL;
			}
}



