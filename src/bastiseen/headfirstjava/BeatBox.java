package headfirstjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.sound.midi.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BeatBox extends Application
{
	private Scene scene;
	
	private BorderPane root;			// This is the background of the application
	
	private VBox vboxInstruments; 	// Container of instrument list
	private String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi_Hat", "Accoustic Snare", "Crash Cymbal", "Hand Clap",
			"High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "CowBell", "Vibraslap", "Low-mid Tom", "High Agogo",
			"Open Hi Conga"};
	private int[] instruments = {33, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
	
	private VBox vboxbuttons;		// Container of buttons
	private Button btnStart;
	private Button btnStop;
	private Button btnUpTempto;
	private Button btnDownTemtpo;
	private Button btnSave;
	private Button btnRestore;
	
	private GridPane gpSelectors;	// Container of selectors
	private ArrayList<CheckBox> selectors;
	
	private Sequencer sequencer;
	private Sequence sequence;
	private Track track;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("My Fisrt Beat Box");
		root = new BorderPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(root, 565, 320);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		buildGUI();
		setUpMidid();
		
		btnStart.setOnAction(event -> click_Start());
		btnStop.setOnAction(event -> click_Stop());
		btnUpTempto.setOnAction(event -> click_TempoUp());
		btnDownTemtpo.setOnAction(event -> click_TempoDown());
		btnSave.setOnAction(event -> click_Save());
		btnRestore.setOnAction(event -> click_Restore());
	}
	
	private void click_Restore()
	{
		boolean[] checkBoxSate = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(new File("CheckBox.ser"));
			ObjectInputStream is = new ObjectInputStream(fileIn);
			checkBoxSate = (boolean[]) is.readObject();
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < 256; i++)
		{
			CheckBox box = selectors.get(i);
			box.setSelected(checkBoxSate[i]);
		}
		sequencer.stop();
		//click_Start();
	}

	private void click_Save()
	{
		boolean[] checkBoxSate = new boolean[256];
		for (int i = 0; i < 256; i++)
		{
			CheckBox box = selectors.get(i);
			if (box.isSelected()) checkBoxSate[i] =  true;
		}
		try
		{
			FileOutputStream fileOut = new FileOutputStream(new File("CheckBox.ser"));
			ObjectOutputStream os = new ObjectOutputStream(fileOut);
			os.writeObject(checkBoxSate);
			os.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void click_TempoDown() {
		float tempo = sequencer.getTempoFactor();
		sequencer.setTempoFactor((float) (tempo * 0.93));
	}

	private void click_TempoUp() {
		float tempo = sequencer.getTempoFactor();
		sequencer.setTempoFactor((float) (tempo * 1.03));
	}

	private void click_Stop() {
		sequencer.stop();
	}

	private void click_Start() {
		int[] trackList = null;
		
		sequence.deleteTrack(track);
		track = sequence.createTrack();
		
		for (int i = 0; i < 16; i++)
		{
			trackList = new int[16];
			int key = instruments[i];
			
			for (int j = 0; j < 16; j++)
			{
				CheckBox checkBox = selectors.get(j + (16 * i));
				if (checkBox.isSelected())
				{
					trackList[j] = key;
				}
				else
				{
					trackList[j] = 0;
				}
			}	
			makeTracks(trackList);
			track.add(makeEvent(176, 1, 127, 0, 16));
		}
		track.add(makeEvent(192, 9, 1, 0, 15));
		try
		{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void makeTracks(int[] list)
	{
		for (int i = 0; i < 16; i++)
		{
			int key = list[i];
			if (key != 0)
			{
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i + 1));
			}
		}
	}

	private void setUpMidid()
	{
		try
		{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}
		catch (Exception e)
		{
			
		}
	}

	private void buildGUI() {
		vboxInstruments = new VBox();
		vboxInstruments.setSpacing(2);
		for (int i = 0; i < 16; i++)
		{
			vboxInstruments.getChildren().add(new Label(instrumentNames[i]));
		}
		root.setLeft(vboxInstruments);
		
		vboxbuttons = new VBox();
		vboxbuttons.setSpacing(5);
		btnStart = new Button("Start");
		btnStop = new Button("Stop");
		btnUpTempto = new Button("Tempo Up");
		btnDownTemtpo = new Button("Tempo Down");
		btnSave = new Button("Save");
		btnRestore = new Button ("Restore");
		vboxbuttons.getChildren().addAll(btnStart, btnStop, btnUpTempto, btnDownTemtpo, btnSave, btnRestore);
		root.setRight(vboxbuttons);
		
		gpSelectors = new GridPane();
		gpSelectors.setPadding(new Insets(0,5,0,5));
		gpSelectors.setHgap(2);
		gpSelectors.setVgap(2);
		selectors = new ArrayList<>();
		for (int y = 0; y < 16; y++)
		{
			for (int x = 0; x < 16; x++)
			{
				CheckBox box = new CheckBox();
				box.setSelected(false);
				selectors.add(box);
				gpSelectors.add(box, x, y);
			}
		}
		root.setCenter(gpSelectors);
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
        Application.launch(args);
    }
	
}
