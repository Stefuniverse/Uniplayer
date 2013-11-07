import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class Songprogress extends Thread {
	
	public Slider slide;
	public MediaPlayer check;

	public Songprogress() {
		
		this.slide = new Slider();
		slide.setValueChanging(true);
		slide.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	if ((old_val.doubleValue() - new_val.doubleValue()) <= -1) {
                check.seek(new Duration(new_val.doubleValue()*1000));
            	}
            }
		});
	}
	
	@Override
	public void run() {
		
		while (true) {
			
			try {
				sleep(100);
			}
			catch(InterruptedException ex) {
		
			}
			
			if (check != null) {
		
				while (check.getStatus() == Status.PLAYING) {
			
					slide.setValue(this.check.getCurrentTime().toSeconds());
			
					try {
						sleep(100);
					}
					catch(InterruptedException ex) {
					}
				}
			}
		}
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
