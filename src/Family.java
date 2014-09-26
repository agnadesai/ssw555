/*Family Data Structure Class
 * Each family contains an individual family ID number
 * information on marriage/divorce
 * Individuals designated as husband and wife
 * Individuals designated as children
 * The ID number will be the key to the hash table that contains family objects
 */

import java.util.Vector;
import java.util.GregorianCalendar;

public class Family {
	private Vector<String> children;
	private String famID;
	private String husband;
	private String wife;
	private GregorianCalendar married;
	private GregorianCalendar divorced;
	private int lineNumber;
	
	public Family(String id)
	{
		famID = id;
		children = new Vector<String>();
		married = null;
		divorced = null;
		setLineNumber(0);
	}
	
	public Family(String id, int LineNum)
	{
		famID = id;
		children = new Vector<String>();
		married = null;
		divorced = null;
		setLineNumber(LineNum);
	}
	
	public void addChild(String cID)
	{
		children.add(cID);
	}
	
	public Vector<String> getChildren()
	{
		return children;
	}
	
	public void setHusb(String hID)
	{
		husband = hID;
	}
	
	public void setWife(String wID)
	{
		wife = wID;
	}
	
	public void setMarriage(GregorianCalendar m)
	{
		married = m;
	}
	
	public void setDivorce(GregorianCalendar d)
	{
		divorced = d;
	}
	
	public int getChildSize()
	{
		return children.size();
	}
	
	public String getHusb()
	{
		return husband;
	}
	
	public String getWife()
	{
		return wife;
	}
	
	public GregorianCalendar getMD()
	{
		return married;
	}
	
	public GregorianCalendar getDD()
	{
		return divorced;
	}
	
	public String getID()
	{
		return famID;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int lineNum) {
		this.lineNumber = lineNum;
	}
}
