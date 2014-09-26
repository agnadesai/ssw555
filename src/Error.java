
/**
 * Errors are a type of problem that represent 
 * conditions that cannot occur in a family
 * tree.
 *
 */
public class Error extends Problem {

	public Error(int lineNum, String msg) {
		super(lineNum, msg);
		
	}
	
	public String toString() {
		String s = "Error\t";
		s += Integer.toString( getLineNumber() );
		s += "\t";
		s += getMessage();
		s += "\n";
				
		return s;
	}

}
