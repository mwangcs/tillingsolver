
public class Grid {
	private int row;
	private int col;
	private char val;

	public Grid(){
		row = -1;
		col = -1;
		val = ' ';
	}

	public Grid(int r, int c, char v){
		row = r;
		col = c;
		val = v;
	}

	public int getRow(){
		return row;
	}

	public void setRow(int r){
		row = r;
	}

	public int getCol(){
		return col;
	}

	public void setCol(int c){
		col = c;
	}

	public void setDim(int r, int c){
		row = r;
		col = c;
	}

	public char getValue(){
		return val;
	}

	public void setValue( char v){
		val = v;
	}

	public void mark(){
		val ='1';
	}
	public boolean isMarked( ){
		if( val == '1'){
			return true;
		}
		return false;
	}

	public boolean isClear( ){
		if( val == '.'){
			return true;
		}
		return false;
	} 

	public Pair getDim(){
		Pair p = new Pair(row, col);
		return p;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Grid))
			return false;
		Grid g = (Grid)o;
		if ( row == g.row && col == g.col && val == g.val){
			return true;
		}
		return false;
	}
}
