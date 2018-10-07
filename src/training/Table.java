package training;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Table extends Application
{
     private Scene scene;
     private VBox display;
     private GridPane table;
     private ProgressBar progress;
     private Label label;
     private Button buttonUpdate;
     private Button buttonReset;
     private HBox buttonHBox;
     
     double[] progressValues = {0.3, 0.4, 0.5, 0.6, 0.7};
     private String[] labelValues = {"30%", "40%", "50%", "60%", "70%"};
     
     int categoryCount = progressValues.length;
     
     private final int PREFERED_WIDTH = 100;


    @Override
    public void start(Stage s)
    {
    	display = new VBox(10);
    	buttonUpdate = new Button("Update");
    	buttonReset = new Button("Reset");
    	buttonHBox = new HBox(10);
    	
    	buttonHBox.getChildren().addAll(buttonUpdate, buttonReset);
    	
    	table = new GridPane();
    	
    	display.getChildren().addAll(table, buttonHBox);
    	
    	buttonHBox.setAlignment(Pos.TOP_CENTER);
    	
        scene = new Scene(display, 300, 100);
        
        for (int col = 0; col < categoryCount; col++)
        {
        	progress = new ProgressBar();
            progress.setPrefWidth(PREFERED_WIDTH);
            progress.setProgress(0.0);
            label = new Label("0%");
            label.setPrefWidth(PREFERED_WIDTH);
            label.setAlignment(Pos.CENTER);
            
            table.add(progress, col, 0);
            table.add(label, col, 0);
        }
        
        s.setScene(scene);
	    s.show();
 
        buttonUpdate.setOnAction(event -> update());
        buttonReset.setOnAction(event -> reset());
    }

    private void reset()
    {
    	{
	        int counter = 0;
	        for(Node node : table.getChildren())
	        {
	        	if (counter % 2 == 0)
	        	{
	        		progress = (ProgressBar) node;
	        		progress.setProgress(0.0);
	        	}
	        	else
	        	{
	        		label = (Label) node;
	        		label.setText("0%");	
	        	}
	        	counter++;
        	}		
    
        }
	}

	private void update()
	{
    	{
	        int counter = 0;
	        for(Node node : table.getChildren())
	        {
	        	int column = GridPane.getColumnIndex(node);
	        	if (counter % 2 == 0)
	        	{
	        		progress = (ProgressBar) node;
	        		progress.setProgress(progressValues[column]);
	        	}
	        	else
	        	{
	        		label = (Label) node;
	        		label.setText(labelValues[column]);	
	        	}
	        	counter++;
        	}		
    
        }
	}

	public static void main(String args[])
    {
    Application.launch(args);
    }
}
	

