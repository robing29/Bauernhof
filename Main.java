package oop_Bauernhof;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Klassen bzw. Objekte initialisieren
		Bauernhof bauernhof = Bauernhof.getInstance(8, 56);
		System.out.println(bauernhof.getInfosUeberDenBauernhof());
		chooseYourStarter(bauernhof);
//		try {
//			Bauernhof.NeuerBewohner(new Ziege("Milka", 700, Geschlechter.weiblich));
//			Bauernhof.NeuerBewohner(new Rind("Ferrero", 1000, Geschlechter.maennlich));
//			Bauernhof.NeuerBewohner(new Rind("Nutella", 1200, Geschlechter.maennlich));
//			Bauernhof.NeuerBewohner(new Rind("Nudossi", 1121, Geschlechter.maennlich));
//			Bauernhof.NeuerBewohner(new Rind("Haribo", 300, Geschlechter.maennlich));
//			Bauernhof.NeuerBewohner(new Ziege("Christian Ziege", 365, Geschlechter.maennlich));
//		} catch (KeinPlatzException E) {
//			System.out.println("DEBUG Mode, Plaetze erstmal egal");
//		}
		
		
		//Seed setzen, evtl. mit User Input
		System.out.println(bauernhof.getInfosUeberDenBauernhof());
		int tageDerBauernhofSimulation = 1000;
		bauernhof.bewohnerStellenSichVor(false);
		for (int tag = 1; tag <= tageDerBauernhofSimulation; tag++) {
			System.out.println("Tag: " + (tag));
			Helper.delayForXSeconds(0.2);
			if (tag == 2) {
				System.out.println("Du gehst direkt zum Monatsmarkt. Dort kannst du Tiere kaufen und verkaufen.");
				System.out.println("Beim Wochenmarkt kannst du nur deine Produkte verkaufen und - mit etwas Glück - Land und Plätze kaufen.");
				bauernhof.monatsRoutine(tag, true);
			}
			if (tag % 30 == 0) {
				bauernhof.monatsRoutine(tag, false);
			} else if (tag % 7 == 0) {
				bauernhof.wochenRoutine();
			}
			else {
				bauernhof.tagesRoutine(tag);
				Helper.delayForXSeconds(0.2);
			}
		}
	}
	
	public static void chooseYourStarter(Bauernhof b) {
		System.out.println("Willkommen beim Bauernhofspiel!");
		Helper.delayForXSeconds(1);
		System.out.println("Du hast einen Bauernhof mit " + Bauernhof.getPlaetze() + " Plätzen und " + b.getFutterZurVerfügung() + " qm² Landmasse.");
		Helper.delayForXSeconds(1);
		System.out.println("Ein Rind braucht 4 Plätze, eine Ziege braucht 3 Plätze und ein Huhn braucht einen Platz.");
		Helper.delayForXSeconds(1);
		System.out.println("Ein Rind isst 4 Einheiten Futter pro Tag, eine Ziege braucht 2 Einheiten Futter, ein Huhn braucht 1 Einheit Futter pro Tag");
		Helper.delayForXSeconds(1);
		System.out.println("Achte gut auf genügend Futter. Tiere können krank werden und sogar sterben! Jedes qm² Land erzeugt eine Einheit Futter pro Woche.");
		Helper.delayForXSeconds(1);
		System.out.println("Das reicht erstmal für den Anfang, den Rest musst du selbst herausfinden.");
		System.out.println("Mit welchem Tier möchtest du starten?");
		HashMap<Integer, String> welchesTier = new HashMap<>();
		welchesTier.put(1, "Rind");
		welchesTier.put(2, "Ziege");
		welchesTier.put(3, "Huhn");
		int auswahl = Helper.optionenAuswaehlenGeneric(welchesTier);
		try {
			switch (auswahl) {
			case 1:
				Bauernhof.NeuerBewohner(new Rind(Helper.getUserInput("Wie soll dein Starterpokemon heißen?"), TierInfos.Geschlechtsreife.get("Rind"), Geschlechter.weiblich));
				break;
			case 2:
				Bauernhof.NeuerBewohner(new Ziege(Helper.getUserInput("Wie soll dein Starterpokemon heißen?"), TierInfos.Geschlechtsreife.get("Ziege"), Geschlechter.weiblich));
				break;
			case 3:
				Bauernhof.NeuerBewohner(new Huhn(Helper.getUserInput("Wie soll dein Starterpokemon heißen?"), TierInfos.Geschlechtsreife.get("Huhn"), Geschlechter.weiblich));
				break;
			default:
				System.out.println("Default sollte nicht möglich sein");
			}
		} catch (Exception E) {
			System.out.println("Irgendwas ist schiefgelaufen.");
		}
		System.out.println("Viel Spaß!");
		Helper.delayForXSeconds(2);
		
	}

}
