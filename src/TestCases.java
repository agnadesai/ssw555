

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;



import org.junit.Test;

public class TestCases {

	@Test
	public void testWhenBirthdateLaterThanDeathDate() {
		Individual i1 = new Individual("1");
		Individual i2 = new Individual("2");
		
		i1.setBirthDate(new GregorianCalendar(1950, 10, 31));
		i2.setBirthDate(new GregorianCalendar(1950, 10, 31));
		
		i1.addDeathDate(new GregorianCalendar(1920, 10, 31));
		i2.addDeathDate(new GregorianCalendar(1990, 10, 31));
		
		assertTrue( ProblemFinder.isBirthDateAfterDeathDate(i1) );
		assertTrue( !ProblemFinder.isBirthDateAfterDeathDate(i2) );
	}
	
	public void testMarriageToDeadPerson()
	{
		Hashtable<String, Family> familyIndex = new Hashtable<String, Family>();
		Hashtable<String, Individual> personIndex = new Hashtable<String, Individual>(200);

		Individual i1 = new Individual("1");
		i1.setSex("M");
		i1.setBirthDate(new GregorianCalendar(1980, 2, 14));
		i1.addDeathDate(new GregorianCalendar(2010, 6, 30));
		i1.addFamS("fam1");
		i1.addFamS("fam2");
		
		Individual i2 = new Individual("2");
		i2.setSex("F");
		i2.setBirthDate(new GregorianCalendar(1982, 8, 14));
		i2.addFamS("fam1");
		
		Individual i3 = new Individual("2");
		i2.setSex("F");
		i2.setBirthDate(new GregorianCalendar(1979, 4, 1));
		i2.addFamS("fam2");
		
		Family f1 = new Family("fam1");
		f1.setHusb("1");
		f1.setWife("2");
		f1.setMarriage(new GregorianCalendar(2011, 7, 17));
		
		Family f2 = new Family("fam2");
		f1.setHusb("1");
		f1.setWife("3");
		f1.setMarriage(new GregorianCalendar(2008, 8, 6));
		f1.setDivorce(new GregorianCalendar(2010, 6, 30));
		
		familyIndex.put(f1.getID(), f1);
		familyIndex.put(f2.getID(), f2);
		
		personIndex.put(i1.getId(), i1);
		personIndex.put(i2.getId(), i2);
		personIndex.put(i3.getId(), i3);
		
		ProblemFinder pf = new ProblemFinder(familyIndex, personIndex);
		
		assertTrue(!pf.marriageToDeadPerson(i1));
		assertTrue(pf.marriageToDeadPerson(i2));
		assertTrue(!pf.marriageToDeadPerson(i3));
	}
	
	@Test
	public void testWrongSpouseClassification()
	{
		Hashtable<String, Family> familyIndex = new Hashtable<String, Family>();
		Hashtable<String, Individual> personIndex = new Hashtable<String, Individual>(200);
		
		Family fam = new Family("F1");
		
		Individual ind1 = new Individual("1");
		Individual ind2 = new Individual("2"); 
		
		ind1.setSex("M");
		ind2.setSex("F");
		
		ind1.addFamS(fam.getID());
		ind2.addFamS(fam.getID());
		
		fam.setWife(ind1.getId());
		fam.setHusb(ind2.getId());
		
		familyIndex.put(fam.getID(), fam);
		
		ProblemFinder pf = new ProblemFinder(familyIndex, personIndex);
		
		assertTrue(pf.wrongSpouseClassification(ind1));
		assertTrue(pf.wrongSpouseClassification(ind2));
		
	}
}
