import Interfaces.Styles;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Playlist_Entry extends HBox implements Styles {
	
	HBox main;
	Label l;
	
	//This is a costumized HBox, wich I need in order to add more elements to a Plylist entry
	public Playlist_Entry(String Input, final Stage s, final Songprogress Mip) {
		
		//Creates a new Label and add all styleinformations from the interface style with all eventHandlers
		l = new Label(Input);
		
		l.setMinWidth(s.getWidth()*0.15);
	
		if (Mediafunctions.getnames().size() % 2 != 0) {
			l.setStyle(Layout_Main1);	
		}
	
		else {	
			l.setStyle(Layout_Main2);	
		}
	
	
		l.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
	    	
				Mediafunctions.updateview(s, Mediafunctions.getnames().indexOf(Playlist_Entry.this), Mip);        
			}
		});
	
		l.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
	    	
				if (Mediafunctions.getnames().indexOf(Playlist_Entry.this) % 2 != 0) {
	        
					l.setStyle(Layout_Action1);
	    	
				}
				else {
					l.setStyle(Layout_Action2);
				}
	            
			}
		});
	
		l.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				if (Mediafunctions.getnames().indexOf(Playlist_Entry.this) != Mediafunctions.getcurrent())
				{
					if (Mediafunctions.getnames().indexOf(Playlist_Entry.this) % 2 != 0) {
	    			
						l.setStyle(Layout_Main1);		    			
					} else {
	    			
	    			l.setStyle(Layout_Main2);
					}
				} else {
					
					if (Mediafunctions.getnames().indexOf(Playlist_Entry.this) % 2 != 0) {
			    	l.setStyle(Layout_active1);	
					}
					else {
						l.setStyle(Layout_active2);
					}
				}        
			}
		});
		//At the moment only this label is added to the HBox, but you can include nearly everything in this Box
		this.getChildren().addAll(l);
	}
	
	//Gets the text of the Label
	public String getText() {
		
		return this.l.getText();
	}
	//Sets the style of the Label
	public void setLStyle(final String Style) {
		
		this.l.setStyle(Style);
		
	}
	
}
