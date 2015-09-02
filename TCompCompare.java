import java.util.Comparator;


public class TCompCompare implements Comparator<TComponent> {
	    @Override
	    public int compare(TComponent o1, TComponent o2) {
	        return ((Integer)o2.size()).compareTo(((Integer)o1.size()));
	    }
	}