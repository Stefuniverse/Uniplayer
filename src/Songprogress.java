import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

// This Class creates a new Thread in order to observe the Trackprogress in a Trackprogressbar
public class Songprogress extends Thread {
	
	public Slider slide;
	public MediaPlayer check;
	
	//Creates an instance of Songprogress, this is neccessary to start a new thread
	public Songprogress() {
		
		this.slide = new Slider();
		slide.setValueChanging(true);
		slide.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            //Has the user changed the value in the progressbar?
			public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	//Excludes the changes from run()
            	if (((old_val.doubleValue() - new_val.doubleValue()) <= -1) || ((new_val.doubleValue() - old_val.doubleValue()) <= -1)) {
            	//Player becomes set to the new time
                check.seek(new Duration(new_val.doubleValue()*1000));
            	}
            }
		});
	}
	
	//This gives the progressbar the correct value, while playing a Track
	@Override
	public void run() {
		
		while (true) {
			
			pstop();
				//Ensures there is any Mediaplayer
			if (check != null) {
				//works while the Mediaplayer is playing
				while (check.getStatus() == Status.PLAYING) {
					//Changes the value
					slide.setValue(check.getCurrentTime().toSeconds());
					pstop();
				}
			}
		}
	}
	//thread is sleeping for 0.1 sec for better performance
	public void pstop() {
		try {
			sleep(100);
		}
		catch(InterruptedException ex) {
		}
	}
	//Sets the new Mediaplayerobject for this instance and sets new Max and min value for the slider
	public void setparam(MediaPlayer MP) {
		check = MP;
		slide.setMax(check.getTotalDuration().toSeconds());
		slide.setMin(0);
	}
	//Returns the slider to a graphical UI
	public Slider getslider() {
		return slide;
	}
}
