//Imports from external libaries provides the function of the Mediaplayer
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
	// Creates the Lists for saving user input and all functional variables to use the mediaplayer
	static List<String> URL = new LinkedList<String>();
    static List<Playlist_Entry> Title = new ArrayList<Playlist_Entry>();
    //represents the Index of the track in URL and Title
    static int current = 0;
    static MediaPlayer MP;
    static MediaView mediaview = new MediaView();

	static void updateview(final Stage s, int Newtoplay, final Songprogress Mip) {
			
			//Testing if there was any Mediaplayer before
			if (MP != null) {
		
				MP.stop();
				//gives the Playlistentry the standardstyle back
				if ((current % 2) != 0) {
					Title.get(current).setLStyle(Layout_Main1);
				} else {
					Title.get(current).setLStyle(Layout_Main2);
				}
			}
			//Creates new MediaPlayer Instance with the new SOng to play
			MP = new MediaPlayer(new Media(URL.get(Newtoplay)));
			//Plays the next song in the end of this, when there is anyone else
			MP.setOnEndOfMedia(new Runnable() {
				@Override public void run() {
					if (current != Title.size()-1) {
						updateview(s, (current+1), Mip);
					}
				}
			});
			//Plays the track, when fully buffered and starts the songprogress of the song
			MP.setOnReady(new Runnable() {
				@Override public void run() {
					Mip.setparam(MP);
					MP.play();
				}
			});
    
			//changes the new track to current track
			current = Newtoplay;
			//Sets the "active" Style
			if ((current % 2) != 0) {
		
				Title.get(current).setLStyle(Layout_active1);
			} else {
		
				Title.get(current).setLStyle(Layout_active2);
			}
			// Makes the Mediaplayer visible
			mediaview.setMediaPlayer(MP);
			//Sets new headline
			s.setTitle("Uniplayer-Alpha " + Title.get(current).getText());
	}
	// function to play and pause a track, including a test, wich ensures a track is played when the button is pressed
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
	//Changes Volume
	static void setV(double x) {
		
		if (MP != null)
			MP.setVolume(x);
	}
	//Returning important values. Mainly deprecated
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
