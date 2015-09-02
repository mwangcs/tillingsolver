import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;


public class Parser {

	private TComponent mainBoard;
	private ArrayList<TComponent> Tiles;
	private TComponent file;
	private TComponent fileMark;
	private int frowN;
	private int fmaxRowLen;
	private boolean valid;

	public Parser(){
		frowN = 0;
		fmaxRowLen = 0;
		Tiles = new ArrayList<TComponent>();
		file =  new TComponent();
		fileMark = new TComponent();
		mainBoard = new TComponent();
		valid = true;
	}

	public void orderTiles(){
		Collections.sort(Tiles, new TCompCompare());
	}


	public boolean isMainSingle(){
		return mainBoard.isSingle();
	}

	public boolean isValid(){
		return valid;
	}


	public void parseFile(String filename) throws FileNotFoundException{
		Scanner myScanner = new Scanner(new File(filename));
		ArrayList senten= new ArrayList(); 
		frowN = 0;
		while(myScanner.hasNext()){
			String bline = myScanner.nextLine();
			senten.add(bline);
			if(bline.length() > fmaxRowLen){
				fmaxRowLen = bline.length();
			}
			frowN++;
		}
		for(int i = 0; i < frowN; i++){
			for(int j = 0; j < fmaxRowLen; j++){
				char value = senten.get(i).toString().charAt(j);
				if( value !=' '){
					Grid g = new Grid(i, j, value);
					file.add(g);
				}				
			}
		}
		Iterator iter = file.getComp().iterator();
		while(iter.hasNext()){
			Grid fileG = (Grid)iter.next();
			Grid newG = new Grid(fileG.getRow(), fileG.getCol(), fileG.getValue());
			fileMark.add(newG);
		}
	}
	public TComponent getFile(){
		return file;
	}


	public void setTiles(){ 
		Iterator iter = file.getComp().iterator();
		while(iter.hasNext()){
			Grid fileG = (Grid)iter.next();
			Grid fileMarkG = fileMark.getGrid(fileG.getRow(), fileG.getCol());
			if(!fileMarkG.isMarked()){
				TComponent cpt = new TComponent();
				cpt.add(fileG);	
				probe(cpt, fileG);
				Tiles.add(cpt);
				fileMarkG.mark();
			}
		}
		orderTiles();
	}


	public ArrayList<TComponent> getTiles(){
		return Tiles;
	}

	//checked
	public void probe(TComponent cp, Grid g){
		int r = g.getRow();
		int c = g.getCol();
		if(file.getGrid(r-1, c).getValue()!=' ' && !(fileMark.getGrid(r-1, c).isMarked())){
			cp.add(file.getGrid(r-1, c));
			fileMark.getGrid(r-1, c).mark();
			probe(cp, file.getGrid(r-1, c));
		}
		if(file.getGrid(r, c-1).getValue()!=' ' && !(fileMark.getGrid(r, c-1).isMarked())){
			cp.add(file.getGrid(r, c-1));
			fileMark.getGrid(r, c-1).mark();
			probe(cp, file.getGrid(r, c-1));
		}
		if(file.getGrid(r+1, c).getValue()!=' ' && !(fileMark.getGrid(r+1, c).isMarked())){
			cp.add(file.getGrid(r+1, c));
			fileMark.getGrid(r+1, c).mark();
			probe(cp, file.getGrid(r+1, c));
		}
		if(file.getGrid(r, c+1).getValue()!=' ' && !(fileMark.getGrid(r, c+1).isMarked())){
			cp.add(file.getGrid(r, c+1));
			fileMark.getGrid(r, c+1).mark();
			probe(cp, file.getGrid(r, c+1));
		}
	}

	//Get the main board out of all Tiles and remove it from the set of Tiles
	public void setMainBoard(){
		int ind = 0;
		int totalGrid = 0;
		for(int i=0; i< Tiles.size(); i++){
			totalGrid += Tiles.get(i).size();
			if(Tiles.get(i).size()>=mainBoard.size()){
				mainBoard = Tiles.get(i);
				ind = i;
			}
		}
		if( (2*mainBoard.size())!= totalGrid){
			valid = false; 
		}
		Tiles.remove(ind);
	}
	public TComponent getMainBoard(){
		return mainBoard;
	}

	public void updateTiles() {

		for(int i = 0; i < Tiles.size(); i++){
			Tiles.get(i).update();
		}

	}
}
