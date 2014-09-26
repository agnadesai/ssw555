

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class ProblemFinder {

	
	private ProblemList pl;
	private Hashtable<String, Family> familyIndex;
	private Hashtable<String, Individual> personIndex;
	
	public ProblemFinder(){
		pl = new ProblemList();
	}
	
	public ProblemFinder(Hashtable<String, Family> fam, Hashtable<String, Individual> ind)
	{
		pl = new ProblemList();
		familyIndex = fam;
		personIndex = ind;
	}
	
	
	public static boolean isBirthDateAfterDeathDate(Individual i){
		
	for ( GregorianCalendar g : i.getDeathDates())
		if ( i.getBirthDate().after(g) ) {
			return true;
		}
	return false;
}
	
	public boolean marriageToDeadPerson(Individual person)
	{
		for(String s: person.getFamS())
		{
			Family fam = familyIndex.get(s);
			if ( fam != null ) {
				if(person.getSex().equals("M"))
				{
					Individual wife = personIndex.get(fam.getWife());
					if(wife.isDead())
					{
						return wife.getDeathDates().get(0).before(fam.getMD());
					}
				}
				else
				{
					Individual husb = personIndex.get(fam.getHusb());
					if(husb.isDead())
					{
						return husb.getDeathDates().get(0).before(fam.getMD());
					}
				}
			}
		}
		return false;
	}
	
	public boolean wrongSpouseClassification(Individual i)
	{		
		String sex = i.getSex();
		HashSet<String> famS = i.getFamS();
		Iterator<String> famIDs = famS.iterator();
		while(famIDs.hasNext())
		{
			Family fam = familyIndex.get(famIDs.next());
			if ( fam != null ) {
				if(sex.equals("M"))
				{
					if(fam.getWife().equals(i.getId()))
						return true;
				}
				else if(sex.equals("F"))
				{
					if(fam.getHusb().equals(i.getId()))
						return true;
				}
			}
		}
		return false;
		
	}
}
