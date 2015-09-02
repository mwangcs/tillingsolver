import java.util.HashSet;
import java.util.Iterator;


public class TComponent {

	HashSet<Grid> comp;
	int left;
	int right;
	int top;
	int bottom;

	public TComponent(){
		left = 99999;
		right = -1;
		top = 99999;
		bottom = -1;
		comp = new HashSet<Grid>();
	}

	public TComponent(HashSet<Grid> c){
		comp = new HashSet<Grid>();
		Iterator iter = c.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			Grid addtoG = new Grid(ng.getRow(), ng.getCol(), ng.getValue());
			comp.add(addtoG);
			if( ng.getRow()< left){
				left = ng.getRow();
			}
			if( ng.getRow() > right){
				right = ng.getRow();
			}
			if( ng.getCol() < top){
				top = ng.getCol();
			}
			if ( ng.getCol() > bottom){
				bottom = ng.getCol();
			}
		}
	}

	public HashSet<Grid> rotate90(){
		HashSet<Grid> r90 = new HashSet<Grid>();
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			Grid newg = new Grid(ng.getRow(), ng.getCol(), ng.getValue());
			int r = newg.getCol();
			int c = -newg.getRow();
			newg.setRow(r);
			newg.setCol(c);
			r90.add(newg);
		}
		return r90;
	}

	public void update(){
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid nextG = (Grid)iter.next();
			nextG.setDim(nextG.getRow()-left, nextG.getCol()-top);
		}
		right = right-left;
		bottom = bottom-top;
		left=0;
		top=0;

	}

	public HashSet<Grid> rotate180(){
		HashSet<Grid> r180 = new HashSet<Grid>();
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			Grid newg = new Grid(ng.getRow(), ng.getCol(), ng.getValue());
			int r = -newg.getRow();
			int c = -newg.getCol();
			newg.setRow(r);
			newg.setCol(c);
			r180.add(newg);
		}
		return r180;
	}

	public HashSet<Grid> rotate270(){
		HashSet<Grid> r270 = new HashSet<Grid>();
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			Grid newg = new Grid(ng.getRow(), ng.getCol(), ng.getValue());
			int r = -newg.getCol();
			int c = newg.getRow();
			newg.setRow(r);
			newg.setCol(c);
			r270.add(newg);
		}
		return r270;
	}

	public HashSet<Grid> rlReflect(){
		HashSet<Grid> rlR = new HashSet<Grid>();
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			Grid newg = new Grid(ng.getRow(), ng.getCol(), ng.getValue());
			int r = newg.getRow();
			int c = -newg.getCol();
			newg.setRow(r);
			newg.setCol(c);
			rlR.add(newg);
		}
		return rlR;
	}

	public HashSet<Grid> tbReflect(){
		HashSet<Grid> tbR = new HashSet<Grid>();
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			Grid newg = new Grid(ng.getRow(), ng.getCol(), ng.getValue());
			int r = -newg.getRow();
			int c = newg.getCol();
			newg.setRow(r);
			newg.setCol(c);
			tbR.add(newg);
		}
		return tbR;
	}

	public boolean isRLSym(){
		boolean flag = true;
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			if(ng.getValue()!= this.getGrid(ng.getRow(), (bottom-ng.getCol())).getValue()){
				flag = false;
			}
		}
		return flag;
	}

	public boolean isTBSym(){
		boolean flag = true;
		Iterator iter = comp.iterator();
		while(iter.hasNext()){
			Grid ng = ((Grid)iter.next());
			if(ng.getValue()!= this.getGrid((right-ng.getRow()), ng.getCol()).getValue()){
				flag = false;
			}
		}
		return flag;
	}

	public int size(){
		return comp.size();
	}

	public int boxSize(){
		return (right-left+1)*(bottom-top+1);
	}

	public void add(Grid g){
		comp.add(g);
		if( g.getRow()< left){
			left = g.getRow();
		}
		if( g.getRow() > right){
			right = g.getRow();
		}
		if( g.getCol() < top){
			top = g.getCol();
		}
		if ( g.getCol() > bottom){
			bottom = g.getCol();
		}
	} //add a grid to the component

	public int[] getDim(){
		int[] dim = new int[4];
		dim[0] = left;
		dim[1] = right;
		dim[2] = top;
		dim[3] = bottom;
		return dim;
	}

	public int getLeft(){
		return left;
	}

	public int getRight(){
		return right;
	}

	public int getTop(){
		return top;
	}

	public int getBottom(){
		return bottom;
	}

	public void clear(){
		Iterator iter = comp.iterator();
		while(iter.hasNext()){			
			((Grid)iter.next()).setValue('.');
		}
	}

	public boolean isClear(){
		boolean flag = true;
		Iterator iter = comp.iterator();
		while(iter.hasNext()){	
			char value = ((Grid)iter.next()).getValue();
			if(value!='.'){
				flag = false;
			}
		}
		return flag;
	}

	public void delete(){
		comp.clear();
	}

	public Grid getGrid(int row, int col){			
		Pair p = new Pair(row, col); 
		Iterator iter = comp.iterator();
		while(iter.hasNext()){			
			Grid nextG = (Grid)iter.next();
			if (nextG.getDim().equals(row, col)){
				return nextG;
			}
		}
		Grid g = new Grid(row, col, ' ');
		return g;
	}

	public Grid getGrid(int row, int col, char c){			
		Pair p = new Pair(row, col); 
		Iterator iter = comp.iterator();
		while(iter.hasNext()){			
			Grid nextG = (Grid)iter.next();
			if (nextG.getDim().equals(row, col)){
				return nextG;
			}
		}
		Grid g = new Grid(row, col, '.');
		return g;
	}

	public HashSet<Grid> getComp(){
		return comp;
	}

	public boolean fullyMarked(){
		boolean flag = true;
		Iterator iter = comp.iterator();
		while(iter.hasNext()){	
			char value = ((Grid)iter.next()).getValue();
			if(value=='.'){
				flag = false;
			}
		}
		return flag;
	}

	public void print(){
		Grid g = new Grid();
		for(int i = left; i <= right; i++){
			for(int j = top; j <= bottom; j++){
				g = getGrid(i, j, '.');
				System.out.print(g.getValue());
			}
			System.out.println(" ");
		}
	}
	public boolean isSingle(){
		boolean flag = true;
		Iterator iter = comp.iterator();
		char value = ((Grid)iter.next()).getValue();
		while(iter.hasNext()){	
			Grid nextG = (Grid)iter.next();
			if(nextG.getValue()!=value){
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof TComponent)) return false;
		TComponent Tobj = (TComponent) o;
		boolean flag = true;

		Iterator iter = comp.iterator();
		while(iter.hasNext()){	
			Grid ng  = (Grid)iter.next();
			if( !ng.equals(Tobj.getGrid(ng.getRow(), ng.getCol())))
				flag = false;
		}
		return flag;
	}



}
