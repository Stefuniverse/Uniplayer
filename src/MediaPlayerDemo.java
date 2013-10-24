//BY STEFAN

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;

    {
    	File file;
    	String read;
    	JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(
           JFileChooser.FILES_ONLY );
        int result = fileChooser.showOpenDialog( fileChooser);

        // user clicked Cancel button on dialog
        if ( result == JFileChooser.CANCEL_OPTION )
           file = null;
        else
           file = fileChooser.getSelectedFile();
        	try {
				read = FileUtils.readFileToString(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				read = null;
				
			}
        	System.out.println(file);
                
       // javafx.scene.media.Media audio = new javafx.scene.media.Media(read);
                
       // MediaPlayer MP = new MediaPlayer(audio);
                
                
             
    }
       
}
