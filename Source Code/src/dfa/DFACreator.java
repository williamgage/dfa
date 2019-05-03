package dfa;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class to execute the program.
 * @author williamgage
 *
 */
public class DFACreator extends Application {

	public static void main(String[] args) {
		
		// Check if text file is provided.
		if (args.length != 1) {
			launch(args);
		}
		else {
			run(args[0]);
		}
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		primaryStage.setTitle("DFA Gen");
		primaryStage.initStyle(StageStyle.UNDECORATED);
	    primaryStage.setScene(new Scene(root));
	    primaryStage.setResizable(false);
	    primaryStage.getIcons().add(new Image("https://i.ibb.co/b2DcYwH/theico.png"));
	    primaryStage.show();
		
	}
	
	private static void run(String in) {
		// Create DFA file.
		File dfa = new File(in);
				
		// List to store lines written in file.
		ArrayList<String> rules = new ArrayList<String>();
				
		// Read the file.
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(dfa));
			String line = reader.readLine();
			while (line != null) 
			{
				rules.add(line);
				line = reader.readLine();
			}
			reader.close();
		}
		catch (Exception ex) 
		{
			System.out.println("Error reading file.");
			ex.printStackTrace();
		}
				
		// Generate DFA based on given rules. 
		DFA generatedDFA = new DFA(rules);
				
		boolean run = true;
		String test;
				
		System.out.println("DFA Successfully generated. Enter q to quit.");
				
		// Ask for input until user quits.
		Scanner input = new Scanner(System.in);
		while (run) {
			
			System.out.print("Enter a string to test: ");
			test = input.next();
			
			try {
				switch (test) {
					case "quit" :	 System.exit(0); break;
					case "QUIT" :	 System.exit(0); break;
					case "q"	: 	 System.exit(0); break;
					case "Q"	:	 System.exit(0); break;
					default		:	 generatedDFA.testString(test);
				}
			}
			catch (Exception ex) {
				System.out.println("String contains characters not in language.");
			}
		}
	}
}
