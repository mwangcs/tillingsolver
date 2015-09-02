import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.SwingUtilities;


public class Solver {

	private ArrayList<TComponent> solutions;

	public Solver(Parser p, boolean rotate, boolean reflect){
		solutions = new ArrayList<TComponent>();
		TComponent soluB = new TComponent();

		Iterator iter = p.getMainBoard().getComp().iterator();
		while(iter.hasNext()){			
			Grid ng = ((Grid)iter.next());
			soluB.add(new Grid(ng.getRow(), ng.getCol(), '.'));
		}

		if(p.isMainSingle()){
			putOnBoard2(p.getMainBoard(), p.getTiles(), soluB, 0, 'A', rotate, reflect);
		}
		else{
			putOnBoard(p.getMainBoard(), p.getTiles(), soluB, 0, 'A', rotate, reflect);
		}

	}

	public ArrayList<TComponent> getSolutions(){
		return solutions;
	}

	public void removeDuplicates(){

		for(int i = 0; i < solutions.size(); i++){
			for(int j = 0; j < i; j++){
				if(solutions.get(i).equals(solutions.get(j))){
					solutions.get(i).clear();
					break;
				}
			}
		}
		for(int k = solutions.size()-1; k >= 0; k--){	
			if(solutions.get(k).isClear()){
				solutions.remove(k);	
			}
		}

	}

	public void putBoard(TComponent mainb, ArrayList<TComponent> tt, TComponent solutionb, int depth, char tag){
		if(depth >= tt.size())
			return;
		TComponent temp = tt.get(depth);

		for ( int i = 0; i <= (mainb.getRight() -temp.getRight()); i++ ){
			for ( int j = 0; j <= (mainb.getBottom() -temp.getBottom()); j++ ){


			}
		}
	}

	public void putOnBoard(TComponent mainb, ArrayList<TComponent> tt, TComponent solutionb, 
			int depthi, char depth, boolean rotateFlag, boolean reflectflag){

		if(depthi >= tt.size())
			return;

		ArrayList<TComponent> varTiles = new ArrayList<TComponent>();
		TComponent org = tt.get(depthi);
		varTiles.add(org);

		if( (!(org.isRLSym() && org.isTBSym()))){

			if (rotateFlag == true){
				TComponent rtt90 = new TComponent(org.rotate90());
				rtt90.update();
				varTiles.add(rtt90);


				TComponent rtt180 = new TComponent(org.rotate180());
				rtt180.update();
				varTiles.add(rtt180);


				TComponent rtt270 = new TComponent(org.rotate270());
				rtt270.update();
				varTiles.add(rtt270);
			}
		}

		if( reflectflag){
			if (!(org.isRLSym())){
				TComponent rlR = new TComponent(org.rlReflect());
				rlR.update();
				varTiles.add(rlR);
			}
			if(!(org.isTBSym())){
				TComponent tbR = new TComponent(org.tbReflect());
				tbR.update();
				varTiles.add(tbR);
			}
		}

		TComponent solutioncopy;	

		for (int k = 0; k < varTiles.size(); k++){
			TComponent temp = varTiles.get(k);

			for ( int i = 0; i <= (mainb.getRight() -temp.getRight()); i++ ){
				for ( int j = 0; j <= (mainb.getBottom() -temp.getBottom()); j++ ){

					solutioncopy = new TComponent(solutionb.getComp());	

					Iterator iter = temp.getComp().iterator();
					boolean flag = true;
					while(iter.hasNext()){			
						Grid nextG = (Grid)iter.next();
						Grid mainbG = mainb.getGrid((nextG.getRow()+i), (nextG.getCol()+j));
						Grid soluG = solutioncopy.getGrid((nextG.getRow()+i), (nextG.getCol()+j));
						if(mainbG.getValue()!=nextG.getValue() || (!soluG.isClear())){
							flag = false;
							break;
						}					
					}

					if(flag == true){
						Iterator iterc = temp.getComp().iterator();
						while(iterc.hasNext()){	
							Grid nextGc = (Grid)iterc.next();
							solutioncopy.getGrid((nextGc.getRow()+i), (nextGc.getCol()+j)).setValue(depth);
						}

						if(depthi == (tt.size()-1))
							solutions.add(solutioncopy);

						char depthc = depth;
						depthc++;
						int depthic = depthi+1;
						putOnBoard(mainb, tt, solutioncopy, depthic, depthc, rotateFlag, reflectflag);
					}			
				}
			}
		}
	}

	public void putOnBoard2(TComponent mainb, ArrayList<TComponent> tt, TComponent solutionb, 
			int depthi, char depth, boolean rotateFlag, boolean reflectflag){

		if(depthi >= tt.size())
			return;

		ArrayList<TComponent> varTiles = new ArrayList<TComponent>();
		TComponent org = tt.get(depthi);
		varTiles.add(org);

		if( (!(org.isRLSym() && org.isTBSym())) && rotateFlag){
			TComponent rtt90 = new TComponent(org.rotate90());
			rtt90.update();
			varTiles.add(rtt90);


			TComponent rtt180 = new TComponent(org.rotate180());
			rtt180.update();
			varTiles.add(rtt180);


			TComponent rtt270 = new TComponent(org.rotate270());
			rtt270.update();
			varTiles.add(rtt270);

		}

		if( reflectflag){
			if (!(org.isRLSym())){
				TComponent rlR = new TComponent(org.rlReflect());
				rlR.update();
				varTiles.add(rlR);
			}
			if(!(org.isTBSym())){
				TComponent tbR = new TComponent(org.tbReflect());
				tbR.update();
				varTiles.add(tbR);
			}
		}

		TComponent solutioncopy;	

		for (int k = 0; k < varTiles.size(); k++){
			TComponent temp = varTiles.get(k);

			for ( int i = 0; i <= (mainb.getRight() -temp.getRight()); i++ ){
				for ( int j = 0; j <= (mainb.getBottom() -temp.getBottom()); j++ ){

					solutioncopy = new TComponent(solutionb.getComp());	

					Iterator iter = temp.getComp().iterator();
					boolean flag = true;
					while(iter.hasNext()){			
						Grid nextG = (Grid)iter.next();
						Grid soluG = solutioncopy.getGrid((nextG.getRow()+i), (nextG.getCol()+j));
						if(!soluG.isClear()){
							flag = false;
							break;
						}					
					}

					if(flag == true){
						Iterator iterc = temp.getComp().iterator();
						while(iterc.hasNext()){	
							Grid nextGc = (Grid)iterc.next();
							solutioncopy.getGrid((nextGc.getRow()+i), (nextGc.getCol()+j)).setValue(depth);
						}

						if(depthi == (tt.size()-1))
							solutions.add(solutioncopy);

						char depthc = depth;
						depthc++;
						int depthic = depthi+1;
						putOnBoard2(mainb, tt, solutioncopy, depthic, depthc , rotateFlag, reflectflag);
					}			
				}
			}
		}
	}

	public void printSolution(){
		System.out.println("printSolution");
		for(int i = 0; i < solutions.size(); i++){
			solutions.get(i).print();
			System.out.println("-------------");
		}


	}
}
