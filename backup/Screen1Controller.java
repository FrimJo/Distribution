package screensframework;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class Screen1Controller implements Initializable, 
                                            ControlledScreen { 

     ScreensController myController; 

     public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	} 

 }