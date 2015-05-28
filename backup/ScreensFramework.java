package screensframework;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreensFramework extends Application { 

     public static final String screen1ID = "main"; 
     public static final String screen1File = "Screen1.fxml"; 
     public static final String screen2ID = "poker"; 
     public static final String screen2File = "Screen2.fxml"; 
     public static final String screen3ID = "roulette"; 
     public static final String screen3File = "Screen3.fxml"; 

     @Override 
     public void start(Stage primaryStage) throws 	Exception { 

       ScreensController mainContainer = new ScreensController(); 
       mainContainer.loadScreen(ScreensFramework.screen1ID, 
                            ScreensFramework.screen1File); 
       mainContainer.loadScreen(ScreensFramework.screen2ID, 
                            ScreensFramework.screen2File); 
       mainContainer.loadScreen(ScreensFramework.screen3ID, 
                            ScreensFramework.screen3File); 

       mainContainer.setScreen(ScreensFramework.screen1ID); 

       Group root = new Group(); 
       root.getChildren().addAll(mainContainer); 
       Scene scene = new Scene(root); 
       primaryStage.setScene(scene); 
       primaryStage.show(); 
     }
     
     public static void main(String[] args) {
         launch(args);
     }
    
}