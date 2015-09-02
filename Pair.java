
public class Pair {
	public int t;
	public int u;
	public TComponent original;
	public TComponent mark;

	public Pair(int i, int j) {         
		this.t= i;
		this.u= j;
	}
	
	public Pair(TComponent t, TComponent m) {         
		this.original= t;
		this.mark= m;
	}
	

	public boolean equals(int i, int j) {
		if(t==i && u==j){
			return true;
		}
		return false;
	}
}