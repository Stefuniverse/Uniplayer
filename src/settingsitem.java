import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;


public class settingsitem extends HBox {
	//This costum class of HBox is enhanced with a few checkboxes, a slider and some Labels
	//in order to provide an interface to change settings
	Label Option;
	CheckBox checkbox1;
	CheckBox checkbox2;
	Slider slider;
	Label value;
	//Creates the instance with all choosen Items in the HBox
	public settingsitem(Boolean hascheckbox2, Boolean hasslider, String Title, double value1, double value2) {
		
		Option = new Label(Title);
		checkbox1 = new CheckBox("visible?");
		
		this.getChildren().addAll(Option, checkbox1);
		
		//Contains a second checkbox?
		if (hascheckbox2) {
			checkbox2 = new CheckBox("Drag out");
			this.getChildren().addAll(checkbox2);
		} 
		//Contains a slider?
		if (hasslider) {
			slider = new Slider();
			//Borders of the sliders represents the highest possible values for this option 
			slider.setMax(value2);
			slider.setMin(value1);
			value = new Label();
			//This Listener changes the value of the Label to show the change to the user
			slider.valueProperty().addListener(new ChangeListener<Number>() {
	            @Override
				public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) { 
	                value.setText(String.valueOf(new_val.doubleValue()));
	            }
			});
			//Creates affinities       
	        this.getChildren().addAll(value, slider);
		}
	}
	//Returns the different values
	public boolean getcheckbox1() {
		return checkbox1.isSelected();
	}
	public boolean getcheckbox2() {
		try {
			return checkbox2.isSelected();
		} catch (NullPointerException e) {
			return true;
		}
	}
	public double getValue() {
		return slider.getValue();
	}
}
