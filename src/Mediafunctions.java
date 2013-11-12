import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Interfaces.Styles;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Mediafunctions implements Styles {
	
	
	
	static List<String> URL = new LinkedList<String>();
    static List<Playlist_Entry> Title = new ArrayList<Playlist_Entry>();
    static int current = 0;
    static MediaPlayer MP;
    
    static MediaView mediaview = new MediaView();

	static void updateview(final Stage s, int Newtoplay, final Songprogress Mip) {
	
			if (MP != null) {
		
				MP.stop();
	
				if ((current % 2) != 0) {
					Title.get(current).setLStyle(Layout_Main1);
				} else {
					Title.get(current).setLStyle(Layout_Main2);
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
		
				Title.get(current).setLStyle(Layout_active1);
			} else {
		
				Title.get(current).setLStyle(Layout_active2);
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
	
	static public List<Playlist_Entry> getnames() {
				return Title; 
			}
	
	static public MediaView getmediaview() {
				return mediaview;
			}
	
	
	static public List<String> getURL() {
				return URL;
			}
}
