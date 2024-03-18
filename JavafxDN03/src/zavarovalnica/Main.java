package zavarovalnica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    int minH = 500;
    int minW = 800;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Aplikacija za zavarovanje avtomobilov");
        primaryStage.setScene(new Scene(root, minW, minH));
        //primaryStage.setMinHeight(minH);
        //primaryStage.setMinWidth(minW);
        primaryStage.show();
        //primaryStage.setMaximized(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
