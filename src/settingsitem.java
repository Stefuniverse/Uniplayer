import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;


public class settingsitem extends HBox {
	
	Label Option;
	CheckBox checkbox1;
	CheckBox checkbox2;
	Slider slider;
	Label value;

	public settingsitem(Boolean hascheckbox2, Boolean hasslider, String Title, double value1, double value2) {
		
		Option = new Label(Title);
		checkbox1 = new CheckBox("visible?");
		
		this.getChildren().addAll(Option, checkbox1);
		
		if (hascheckbox2) {
			checkbox2 = new CheckBox("Drag out");
			this.getChildren().addAll(checkbox2);
		} 
		if (hasslider) {
			slider = new Slider();
			slider.setMax(value2);
			slider.setMin(value1);
			value = new Label();
			slider.valueProperty().addListener(new ChangeListener<Number>() {
	            @Override
				public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	            	if (((old_val.doubleValue() - new_val.doubleValue()) <= -1) || ((new_val.doubleValue() - old_val.doubleValue()) <= -1)) {
	                value.setText(String.valueOf(new_val.doubleValue()));
	            	}
	            }
			});
			
	                
	        this.getChildren().addAll(value, slider);
		}
	}
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
