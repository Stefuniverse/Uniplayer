import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class settings {

	public static void start() {
		
		Stage pS = new Stage();
		BorderPane root = new BorderPane();
		Scene settings = new Scene(root,400,600);
		pS.setScene(settings);
		pS.show();
	}

}
