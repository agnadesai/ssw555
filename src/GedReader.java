

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.GregorianCalendar;
import java.lang.Integer;

public class GedReader {
	private boolean birthD = false;
	private boolean deathD = false;
	private boolean marrD = false;
	private boolean divD = false;
	private Hashtable<String, Family> familyIndex;
	private Hashtable<String, Individual> personIndex;
	
	public GedReader(){
		familyIndex = new Hashtable<String, Family>(50);
		personIndex = new Hashtable<String, Individual>(200);
	}
	
	public void readGED(File input){
		String line;
		Scanner rf;
		String id;
		Family curF = null;
		Individual curI = null;
		boolean parseInd = false;
		boolean parseFam = false;
		
		try{
			if(input != null)
			{
				rf = new Scanner(input);
				int lineNumber = 1;
				while(rf.hasNextLine()){
					line = rf.nextLine();
					if(line.length() > 0){
						if(line.charAt(0) == '0')
						{
							if(line.indexOf("FAM") != -1 || line.indexOf("INDI") != -1 || line.indexOf("TRLR") != -1){
								if(parseInd == true){
									personIndex.put(curI.getId(), curI);
									parseInd = false;
								}
								else if(parseFam == true){
									familyIndex.put(curF.getID(), curF);
									parseFam = false;
								}
								if(line.indexOf("FAM") != -1){
									parseFam = true;
									parseInd = false;
									id = parseID(line);
									curF = new Family(id, lineNumber);
								}
								else if(line.indexOf("INDI") != -1){
									parseInd = true;
									parseFam = false;
									id = parseID(line);
									curI = new Individual(id, lineNumber);
								}
							}
						}else{
							if(parseInd == true)
								addIndData(line, curI);
							else if(parseFam == true)
								addFamData(line, curF);
						}
					}
					lineNumber++;
				}
			}
		} catch(FileNotFoundException e){
			System.out.println("The file provided was invalid.");
		}
	}
	
	public ProblemList findProblems() {
		ProblemList pl = new ProblemList();
		ProblemFinder pf = new ProblemFinder(familyIndex, personIndex);
		
		// loop through each person and check for each kind of problem that can occur
		// in an individual
		for ( String s : personIndex.keySet() ) {
			if ( ProblemFinder.isBirthDateAfterDeathDate( personIndex.get(s) )) {
				pl.add( new Error( personIndex.get(s).getLineNumber(), "Person " + personIndex.get(s).getId() + " has a birthdate that occurs later than their death date."));
			}
			if(pf.marriageToDeadPerson(personIndex.get(s)))
			{
				pl.add(new Error(personIndex.get(s).getLineNumber(), "Person " + personIndex.get(s).getId() + " was married to a person who was already dead"));
			}
			

			
			
		}		

		
		return pl;
	}
	
	public String parseID(String entry){
		String delim = "[ ]+";
		String[] tokens = entry.split(delim);
		String found = "";
		int i;
		
		for(i = 0; i < tokens.length; i++){
			if(tokens[i].indexOf("@") != -1){
				found = tokens[i];
				i = tokens.length;
			}
		}
			
		return found;
	}
	
	public void addIndData(String entry, Individual cur){
		String delim = "[ ]+";
		String[] tokens = entry.split(delim);
		String tag = tokens[1];
		String name = "";
		int i;
		int month;
		
		if(tag.equalsIgnoreCase("NAME")){
			for(i = 2; i < tokens.length; i++)
				name = name + tokens[i];
			cur.setName(name);
		}else if(tag.equalsIgnoreCase("SEX")){
			cur.setSex(tokens[2]);
		}else if(tag.equalsIgnoreCase("BIRT")){
			birthD = true;
		}else if(tag.equalsIgnoreCase("DEAT")){
			deathD = true;
		}else if(tag.equalsIgnoreCase("FAMC")){
			cur.addFamC(tokens[2]);
		}else if(tag.equalsIgnoreCase("FAMS")){
			cur.addFamS(tokens[2]);
		}else if(tag.equalsIgnoreCase("DATE")){
			if(tokens.length > 3){
				month = getMonth(tokens[3]);
				GregorianCalendar m = new GregorianCalendar(Integer.parseInt(tokens[4]), month, Integer.parseInt(tokens[2]));
				if(birthD == true){
					birthD = false;
					cur.setBirthDate(m);
				}
				else if(deathD == true){
					deathD = false;
					cur.addDeathDate(m);
				}	
			}
		}
	}
	
	public void addFamData(String entry, Family cur){
		String delim = "[ ]+";
		String[] tokens = entry.split(delim);
		String tag = tokens[1];
		int month = 1;
		
		if(tag.equalsIgnoreCase("HUSB")){
			cur.setHusb(tokens[2]);
		}else if(tag.equalsIgnoreCase("WIFE")){
			cur.setWife(tokens[2]);
		}else if(tag.equalsIgnoreCase("CHIL")){
			cur.addChild(tokens[2]);
		}else if(tag.equalsIgnoreCase("MARR")){
			marrD = true;
		}else if(tag.equalsIgnoreCase("DIV")){
			divD = true;
		}else if(tag.equalsIgnoreCase("DATE")){
			if(tokens.length > 3){
				month = getMonth(tokens[3]);
				GregorianCalendar m = new GregorianCalendar(Integer.parseInt(tokens[4]), month, Integer.parseInt(tokens[2]));
				if(marrD == true){
					marrD = false;
					cur.setMarriage(m);
				}
				else if(divD == true){
					divD = false;
					cur.setDivorce(m);
				}	
			}
		}
	}
	
	public int getMonth(String month)
	{
		if(month.equalsIgnoreCase("JAN"))
			return 1;
		else if(month.equalsIgnoreCase("FEB"))
			return 2;
		else if(month.equalsIgnoreCase("MAR"))
			return 3;
		else if(month.equalsIgnoreCase("APR"))
			return 4;
		else if(month.equalsIgnoreCase("MAY"))
			return 5;
		else if(month.equalsIgnoreCase("JUN"))
			return 6;
		else if(month.equalsIgnoreCase("JUL"))
			return 7;
		else if(month.equalsIgnoreCase("AUG"))
			return 8;
		else if(month.equalsIgnoreCase("SEP"))
			return 9;
		else if(month.equalsIgnoreCase("OCT"))
			return 10;
		else if(month.equalsIgnoreCase("NOV"))
			return 11;
		else if(month.equalsIgnoreCase("DEC"))
			return 12;
		return 1; //Default to January if invalid month provided.
	}
	
}



