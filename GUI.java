import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;


public class GUI {

	public GUI(){

	}

	public String chooseFile(){

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		String mediaUrl=null;

		mediaUrl = fileChooser.getSelectedFile().toURI().toString().substring(5);			
		System.out.println(mediaUrl);			
		return(mediaUrl);
	}


	public void mainWindow(Parser ps, Solver slv){

		JFrame mainW = new JFrame( "Tiling Solver" );
		mainW.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		mainW.setPreferredSize(new Dimension(600, 600));  
		mainW.setMinimumSize(mainW.getPreferredSize()); 

		mainW.add(new Tabbed(ps, slv), BorderLayout.CENTER); 

		mainW.pack();
		mainW.setVisible(true);

	}


	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("Enable rotated tiles?");
		Scanner myScanner = new Scanner(System.in);
		boolean rotateFlag = (myScanner.nextLine().equals("yes"));

		System.out.println("Enable reflected tiles?");
		boolean reflectFlag = (myScanner.nextLine().equals("yes"));

		GUI g = new GUI();
		String fileN = g.chooseFile();
		Parser p = new Parser();

		p.parseFile(fileN);
		p.setTiles();
		p.updateTiles();
		p.setMainBoard();

		if(p.isValid()==false){
			System.out.println("Input file is not a valid puzzle.");
		}
		
		final long startTime = System.currentTimeMillis();
		Solver s = new Solver(p, rotateFlag, reflectFlag);
		s.removeDuplicates();
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) );
		g.mainWindow(p, s);
	}


}
