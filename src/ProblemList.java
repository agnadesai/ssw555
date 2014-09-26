

import java.util.Vector;


/**
 * This is the list of all problems in a gedcom file.
 *
 */
public class ProblemList {

	ProblemList() {
		problems = new Vector<Problem>();
	}
	
	public void add(Problem p) {
		problems.add(p);
	}
	
	public Problem get(int lineNumber) {
		for (Problem p : problems) {
			if ( p.getLineNumber() == lineNumber ) {
				return p;
			}
		}
		return null;
	}
	
	private Vector<Problem> problems;

	public int size() {
		return problems.size();
	}

	public String buildOutputString() {
		String s = "";
		
		for (Problem p : problems) {
			s += p.toString();
		}
		return s;
	}
}
