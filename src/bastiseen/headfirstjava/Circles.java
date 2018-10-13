package bastiseen.headfirstjava;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Circles extends Application {

     Group root;
     Scene scene;
     Button button;


    @Override
    public void start(Stage s) {     

        root = new Group();
        scene = new Scene(root, 250, 350);
        button = new Button();
        button.setText("create circle");

    root.getChildren().add(button);
    s.setScene(scene);
    s.show();

   button.setOnAction(new EventHandler<ActionEvent>() {

       @Override
       public void handle(ActionEvent e) {
        Circle r = new Circle();
            r.setCenterX(90f);
            r.setCenterY(90f);
            r.setRadius(32.0f);

            r.setFill(Color.ORANGE);

       TranslateTransition translate = new TranslateTransition(
               Duration.millis(5000));
       translate.setToX(1);
       translate.setToY(432);


    ParallelTransition transition = new ParallelTransition(r,translate);
       transition.setCycleCount(1); 
       transition.play();
       root.getChildren().add(r);

       }
   });
    }

    public static void main(String args[]) {
        Application.launch(args);

    }

}