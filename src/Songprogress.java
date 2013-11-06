import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class Songprogress extends Thread {
	
	public Slider slide;
	public MediaPlayer check;

	public Songprogress() {
		
		this.slide = new Slider();
		
            
	}
	
	@Override
	public void run() {
		
		slide.setValue(this.check.getCurrentTime().toSeconds());
		try {
			sleep(400);
		}
		catch(InterruptedException ex) {
			
		}
	}
	
	public void test(MediaPlayer MP) {
		check = MP;
		System.out.println(check.getMedia().getDuration().toMillis());
	}
	
	public void setparam(MediaPlayer MP) {
		check = MP;
		this.slide.setMax(check.getTotalDuration().toSeconds());
		this.slide.setMin(0);
	}
	
	public Slider getslider() {
		return slide;
	}
}
