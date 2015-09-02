import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import java.awt.*;
import javax.swing.*;
import java.awt.Component;


public class Tabbed extends JPanel {

	private ArrayList<Color> ColorSet;
	Map<Character,Color> charColor;

	public Tabbed(Parser ps, Solver slv) {	

		super(new GridLayout(1, 1));

		ColorSet = new ArrayList<Color>();
		Color c1 = new Color(254,67,101);
		ColorSet.add(c1);
		Color c2 = new Color(249,205,173);
		ColorSet.add(c2);
		Color c3 = new Color(131,175,155);
		ColorSet.add(c3);
		Color c4 = new Color(200,200,169);
		ColorSet.add(c4);
		Color c5 = new Color(255,150,128);
		ColorSet.add(c5);
		Color c6 = new Color(175,215,237);
		ColorSet.add(c6);
		Color c7 = new Color(178,190,126);
		ColorSet.add(c7);
		Color c8 = new Color(227,160,93);
		ColorSet.add(c8);
		Color c9 = new Color(114,111,128);
		ColorSet.add(c9);
		Color c10 = new Color(56,13,49);
		ColorSet.add(c10);
		Color c11 = new Color(89,61,67);
		ColorSet.add(c11);
		Color c12 = new Color(224,160,158);
		ColorSet.add(c12);
		Color c13 = new Color(161,47,47);
		ColorSet.add(c13);
		Color c14 = new Color(230,179,61);
		ColorSet.add(c14);
		Color c15 = new Color(217,104,49);
		ColorSet.add(c15);
		Color c16 = new Color(153,80,84);
		ColorSet.add(c16);
		Color c17 = new Color(39,72,98);
		ColorSet.add(c17);
		Color c18 = new Color(23,44,60);
		ColorSet.add(c18);


		charColor = new HashMap<Character, Color>();

		JTabbedPane tabbedPane = new JTabbedPane();


		//panel 1
		JComponent panel1 = new JPanel();
		BoxLayout p1Layout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
		panel1.setLayout(p1Layout);

		JComponent TilesContainer = new JPanel();
		BoxLayout tcLayout = new BoxLayout(TilesContainer, BoxLayout.Y_AXIS);
		JLabel title1 = new JLabel("Tiles");
		title1.setAlignmentX(Component.CENTER_ALIGNMENT);
		TilesContainer.setLayout(tcLayout);

		panel1.add(title1);
		panel1.add(TilesContainer);

		//add Tile pieces to Tiles container
		TilesContainer.add(Box.createRigidArea(new Dimension(0,10)));

		char init = '@';
		for( int k = 0; k < ps.getTiles().size(); k++){
			JLabel tlb = new JLabel("Tile "+(++init)+" :");
			JComponent ttjc = drawComp(ps.getTiles().get(k));
			TilesContainer.add(tlb);
			TilesContainer.add(Box.createRigidArea(new Dimension(0,15)));
			TilesContainer.add(ttjc);
			TilesContainer.add(Box.createRigidArea(new Dimension(0,25)));
		}

		JScrollPane scroller = new JScrollPane(TilesContainer);  
		panel1.add(scroller, BorderLayout.CENTER);	
		tabbedPane.addTab("Tiles", null, panel1,
				"Display Tiles");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		//Panel 2

		JComponent panel2 = new JPanel();
		BoxLayout p2Layout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
		panel2.setLayout(p2Layout);

		JLabel lb = new JLabel("Target Configuration:");
		JLabel sps1 = new JLabel(" ");
		JLabel sps2 = new JLabel(" ");

		JComponent mainboard = drawComp(ps.getMainBoard());

		lb.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.add(sps1);
		panel2.add(lb);
		panel2.add(sps2);
		panel2.add(mainboard);

		tabbedPane.addTab("Target", null, panel2,
				"Dispay the target configuration");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);


		//panel3
		JComponent panel3 = new JPanel();
		BoxLayout p3Layout = new BoxLayout(panel3, BoxLayout.Y_AXIS);
		panel3.setLayout(p3Layout);

		JComponent soluContainer = new JPanel();
		BoxLayout scLayout = new BoxLayout(soluContainer, BoxLayout.Y_AXIS);
		JLabel titleSolu = new JLabel("Solutions");
		TilesContainer.add(Box.createRigidArea(new Dimension(0,15)));
		titleSolu.setAlignmentX(Component.CENTER_ALIGNMENT);
		soluContainer.setLayout(scLayout);

		panel3.add(titleSolu);
		panel3.add(soluContainer);

		for( int k = 0; k < slv.getSolutions().size(); k++){
			JLabel tlb = new JLabel("Solution "+(k+1)+" :");
			JComponent ttjc = drawComp(slv.getSolutions().get(k));
			soluContainer.add(tlb);
			soluContainer.add(Box.createRigidArea(new Dimension(0,15)));
			soluContainer.add(ttjc);
			soluContainer.add(Box.createRigidArea(new Dimension(0,25)));
		}

		if( slv.getSolutions().size()== 0){
			JLabel ns = new JLabel("No solution available!");
			soluContainer.add(Box.createRigidArea(new Dimension(0,30)));
			soluContainer.add(ns);
		}
		JScrollPane scrollerSolu = new JScrollPane(soluContainer);  
		panel3.add(scrollerSolu, BorderLayout.CENTER);

		tabbedPane.addTab("Solutions", null, panel3,
				"Display the solution");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		TilesContainer.add(Box.createRigidArea(new Dimension(0,10)));

		add(tabbedPane);

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	public JComponent drawComp(TComponent cpt){

		int width = cpt.getRight()-cpt.getLeft()+1;
		int height = cpt.getBottom()-cpt.getTop()+1;
		GridLayout GLayoutMain = new GridLayout(width, height);

		JComponent board = new JPanel();
		board.setLayout(GLayoutMain);
		board.setPreferredSize(new Dimension(height*40, width*40));
		board.setMinimumSize(new Dimension(height*40, width*40));
		board.setMaximumSize(new Dimension(height*40, width*40));
		for(int i = cpt.left; i <= cpt.right; i++){
			for(int j = cpt.top; j <= cpt.bottom; j++){
				Color cspace = new Color((float)0.0, (float)0.0, (float)0.0, (float)0.0);
				JPanel square = new JPanel( new BorderLayout() );
				square.setSize(1, 1);
				Color cl = getGridColor(cpt.getGrid(i, j));
				square.setBackground(cl);
				if(!cl.equals(cspace))
					square.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				board.add(square);
			}
		}
		return board;

	}
	public Color getGridColor(Grid g){
		Color cspace = new Color((float)0.0, (float)0.0, (float)0.0, (float)0.0);
		if( g.getValue()==' '){
			return cspace;
		}
		if( charColor.get(g.getValue())==null){
			charColor.put(g.getValue(), ColorSet.get(0));
			ColorSet.remove(ColorSet.get(0));
		}
		return charColor.get(g.getValue());
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}


}