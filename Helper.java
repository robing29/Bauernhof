package oop_Bauernhof;

import java.util.*;

public class Helper {
	public static String getUserInput(String message) {
		System.out.println();
		System.out.println(message);
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	public static void delayForXSeconds(double sekunden) {
		long milis = (long)(sekunden * 1000);
		try {
			Thread.sleep(milis);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void optionenAuswaehlen(Map<Integer, Map.Entry<String,Runnable>> map) {
		for (int counter = 0; counter < map.size(); counter++) {
			System.out.print("Option " + (counter+1) + ": ");
			System.out.println(map.get(counter+1).getKey());
		}
		boolean isValidUserInput = false;
		while(isValidUserInput == false) { //Endlosschleife
			try {
				int auswahl = Integer.parseInt(getUserInput("Wähle eine der Optionen aus!"));
				isValidUserInput = true;
				if(map.get(auswahl).getValue() != null) {
					map.get(auswahl).getValue().run(); //Funktion aufrufen basierend auf der Auswahl
				} else {
					System.out.println(map.get(auswahl).getKey() + " wurde ausgewählt."); 
				}
				
				
			} catch (Exception e) {
				System.out.println("Bitte nur eine Ziffer eingeben und mit Enter bestätigen");
			}
		}
		
	}
	
	public static int optionenAuswaehlenGeneric(Map<Integer, String> map) {
		for (int counter = 0; counter < map.size(); counter++) {
			System.out.print("Option " + (counter+1) + ": ");
			System.out.println(map.get(counter+1));
		}
		boolean isValidUserInput = false;
		while(isValidUserInput == false) { //Endlosschleife
			try {
				int auswahl = Integer.parseInt(getUserInput("Wähle eine der Optionen aus!"));
				if(auswahl > map.size()) {
					throw new FalscheAuswahlException("Bitte einen validen Wert auswählen!");
				}
				System.out.println(map.get(auswahl) + " wurde ausgewählt.");
				isValidUserInput = true;
				return auswahl;
				
			} catch (FalscheAuswahlException e) {
				System.out.println(e.getMessage());
			}			
			catch (Exception e) {
				System.out.println("Bitte nur eine Ziffer eingeben und mit Enter bestätigen");
			}
		}
		return 0;
		
	}
	
	public static int optionenAuswaehlenTierVerkauf(Map<Integer, Tier> map) {
		for (int counter = 0; counter < map.size(); counter++) {
			System.out.print("Option " + (counter+1) + ": ");
			map.get(counter+1).infosUeberIDNameGattungUndGeschlecht();
			System.out.println("Wert: " + map.get(counter+1).getWert());
		}
		
		System.out.println("Option " + (map.size()+1) + ": Nicht verkaufen");
		Bauernhof.aktuelleCredits();
		boolean isValidUserInput = false;
		while(isValidUserInput == false) {
			try {
				int auswahl = Integer.parseInt(getUserInput("Verkaufe eines der Tiere: "));
				isValidUserInput = true;
				return auswahl;
				
			} catch (Exception e) {
				System.out.println("Bitte nur eine Ziffer eingeben und mit Enter bestätigen");
			}
		}
		return 0;
		
	}
	public static int optionenAuswaehlenTierKauf(Map<Integer, Tier> map) {
		for (int counter = 0; counter < map.size(); counter++) {
			System.out.print("Option " + (counter+1) + ": ");
			System.out.println(map.get(counter+1).getGattung() + ", Alter: " + map.get(counter+1).getAlterFormatiert(true) + ", Geschlecht: " + map.get(counter+1).getGeschlecht() + ", Preis: " + map.get(counter+1).getWert()); 
		}
		System.out.println("Option " + (map.size()+1) + ": Nicht kaufen");
		Bauernhof.aktuelleCredits();
		boolean isValidUserInput = false;
		while(isValidUserInput == false) {
			try {
				int auswahl = Integer.parseInt(getUserInput("Welches Tier möchtest du kaufen? "));
				isValidUserInput = true;
				return auswahl;
				
			} catch (Exception e) {
				System.out.println("Bitte nur eine Ziffer eingeben und mit Enter bestätigen");
			}
		}
		return 0;
		
	}
}
