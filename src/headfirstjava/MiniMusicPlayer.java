package headfirstjava;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MiniMusicPlayer extends Application
{	
	private Scene scene;
	private Pane root;
	private Group rect;
	private Button btnAddRectangle;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Setting the title to the stage
		primaryStage.setTitle("My Fisrt Music Video");
		root = new Pane();
		rect = new Group();
		btnAddRectangle = new Button("Add a rectangle");
		
		root.getChildren().addAll(rect, btnAddRectangle);
		
		btnAddRectangle.setOnAction(event -> drawRectangle());
		
		scene = new Scene(root, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		try
		{
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			
			int[] controllers = {127};
			sequencer.addControllerEventListener(event ->
			{
				System.out.println("la");
				drawRectangle();
			}, controllers);
			
			Sequence sequence = new Sequence(Sequence.PPQ,  4);
			Track track = sequence.createTrack();
			
			for (int i = 5; i < 60; i += 4)
			{
				track.add(makeEvent(144, 1, i, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i));
				track.add(makeEvent(128, 1, i, 100, i + 2));
			}
			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(220);
			sequencer.start();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void drawRectangle()
	{
		System.out.println("Drawing a rectangle");
		Rectangle rectangle = new Rectangle(); 
		double red = (Math.random());
		double green = (Math.random());
		double blue = (Math.random());		
		rectangle.setFill(new Color(red, green, blue, 1));
		double height = ((Math.random() * 120) + 10);
		double width = ((Math.random() * 120) + 10);
		double x = ((Math.random() * 40) + 10);
		double y = ((Math.random() * 40) + 10);
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		rectangle.setX(x);
		rectangle.setY(y);
		root.getChildren().add(rectangle);	
		
	}

	MidiEvent makeEvent(int command, int channel, int one, int two, int tick)
	{
		MidiEvent event = null;
		try
		{
			ShortMessage message = new ShortMessage();
			message.setMessage(command, channel, one, two);
			event = new MidiEvent(message, tick);
		}
		catch (Exception e) {}
	
	return event;
	}
	
	public static void main(String args[])
	{
		launch(args);
	}
}


