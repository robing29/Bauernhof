package oop_Bauernhof;

import java.util.List;
import java.util.Random;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

public class Bauernhof {

	private Bauernhof(int plaetze, int LandmasseInQuadratmetern) {
		TierischeBewohner = new ArrayList<Tier>();
		ProduktInventar = new ArrayList<Produkt>();
		setPlaetze(plaetze);
		this.LandmasseInQuadratmetern = LandmasseInQuadratmetern;
		Credits = 300;
		futterZurVerfügungErzeugen();
	}

	public static Bauernhof getInstance(int Plaetze, int LandmasseInQuadratmetern) {
		if (b == null) {
			b = new Bauernhof(Plaetze, LandmasseInQuadratmetern);
		}

		return b;
	}

	private static int Credits;

	public int getCredits() {
		return Credits;
	}

	public static void aktuelleCredits() {
		System.out.println("Aktuelle Credits: " + Credits);
	}

	public void addCredits(int credits) throws TransactionException {
		if (credits < 0)
			throw new TransactionException("Kann keinen negativen Betrag gutschreiben");
		Credits += credits;
		System.out.println(credits + " Credits wurden deinem Konto gutgeschrieben.");
		System.out.println("Aktuelle Credits: " + Credits);
	}

	public void subtractCredits(int credits) throws TransactionException {
		if (credits < 0)
			throw new TransactionException("Kann keinen negativen Betrag abziehen!");
		if (credits > Credits)
			throw new TransactionException("Konto kann nicht überzogen werden!");
		Credits -= credits;
		System.out.println(credits + " Credits wurden von deinem Konto abgezogen");
		System.out.println("Aktuelle Credits: " + Credits);
	}

	private static Bauernhof b;

	private static ArrayList<Tier> TierischeBewohner;

	private static ArrayList<Produkt> ProduktInventar;

	private int FutterZurVerfügung;

	public void setFutterZurVerfügung(int futterZurVerfügung) {
		FutterZurVerfügung = futterZurVerfügung;
	}

	public int getFutterZurVerfügung() {
		return FutterZurVerfügung;
	}

	public void futterZurVerfügungErzeugen() {
		FutterZurVerfügung += LandmasseInQuadratmetern;
		System.out.println(LandmasseInQuadratmetern + " Futter wurde aus freier Landmasse erzeugt. Du hast jetzt " + FutterZurVerfügung + " zur Verfügung.");
	}

	private static int Plaetze;

	private int LandmasseInQuadratmetern;

	private ArrayList<Tier> Friedhof;

	public void tagesRoutine(int tag) {
		bewohnerAltern();
		bewohnerFressen(); // Und sterben (evtl. trennen?)
		TierischeBewohner.removeIf(x -> x.IstTot() == true); // Gestorbene Tiere aus Bestand entfernen
		bewohnerPaaren();
		bewohnerGebaeren();
		bewohnerProduzieren(tag);
	}

	public void monatsRoutine(int tag, boolean istErsterAufruf) {
		if(istErsterAufruf) {
			zumMonatsMarktGehen();
		} else {
			System.out.println("Ein Monat ist rum");
			bewohnerStellenSichVor(false);
			Helper.delayForXSeconds(2);
			bewohnerMachenLaute(false);
			Helper.delayForXSeconds(2);
			zumMonatsMarktGehen();
		}
		
	}
	
	private void bewohnerProduzieren(int tag) {
		TierischeBewohner.stream().filter(tier -> tier.getGeschlecht() == Geschlechter.weiblich)
				.forEach(tier -> tier.erzeugeProdukt(tag));
	}

	private void bewohnerAltern() {
		for (Tier tier : TierischeBewohner) {
			tier.setAlter(tier.getAlter() + 1);
//			System.out.println(tier.getAlterFormatiert());
		}
	}

	private void bewohnerGebaeren() {
		List<Tier> newTiere = new ArrayList<>();
		for (Tier tier : TierischeBewohner) {
			if (tier.getTrageZeit() == 0) {
				// User input: Name the baby
				Random ran = new Random();
				Geschlechter geschlecht = Geschlechter.values()[ran.nextInt(2)];
				System.out.println(tier.getName() + " gebärt ein neues Tier der Gattung " + tier.getGattung() + ", "
						+ "Geschlecht: " + geschlecht);
				Helper.delayForXSeconds(2);
				newTiere.add(tier.createNewInstance(Helper.getUserInput("Gib den Namen des Neugeborenen ein:"), 0,
						geschlecht));
				tier.setIstSchwanger(false);
				tier.setTrageZeit(TierInfos.Tragezeiten.get(tier.getGattung()));

			}
			if (tier.isIstSchwanger() == true) {

				if (tier.getTrageZeit() % 5 == 0) {
					System.out.println(
							"Noch " + tier.getTrageZeit() + " Tage verbleibend, bis " + tier.getName() + " gebaert.");
				}
				tier.setTrageZeit(tier.getTrageZeit() - 1);
			}
		}
		for (Tier tier : newTiere) {
			try {
				Bauernhof.NeuerBewohner(tier);
			} catch (KeinPlatzException e) {
				System.out.println("Du hattest eigentlich nicht genügend Plätze für ein Neugeborenes. Aber die Dorfeinwohner kamen dir zuhilfe und haben deinen Stall kurzerhand ausgebaut");
				setPlaetze(getPlaetze() + tier.getBrauchtAnzahlPlaetze());
				belegtePlaetze += tier.getBrauchtAnzahlPlaetze();
			}
		}
	}

	public void wochenRoutine() {
		futterZurVerfügungErzeugen(); // Futter wird aus Landmasse-Plaetze generiert
		zumWochenMarktGehen();
	}

	private List<Tier> MarktKauf = new ArrayList<>();
	
	public static boolean isErsterMarktBesuch = true;
	
	private boolean glueckGehabt = false;
	
	private boolean plaetzeGekauft = false;	
	
	private static boolean plaetzeKaufenBesucht = false;
	
	private void zumMonatsMarktGehen() {


		Map<String, Runnable> marktOptionen = new LinkedHashMap<>();
		Random ran = new Random();
		
		if (isErsterMarktBesuch) {
			RandomClass.randomize();
			produkteVerkaufen();
			System.out.println(
					"Nach dem du deine Produkte losgeworden bist, schaust du dich auf Monatsmarkt um und hast folgende Optionen: ");
			
			MarktKauf.add(new Rind("Rind", ran.nextInt(1, (TierInfos.Tragezeiten.get("Rind")/ 2)), Geschlechter.values()[ran.nextInt(2)]));
			MarktKauf.add(new Ziege("Ziege", ran.nextInt(1, (TierInfos.Tragezeiten.get("Ziege")/ 2)), Geschlechter.values()[ran.nextInt(2)]));
			MarktKauf.add(new Huhn("Huhn", ran.nextInt(1, (TierInfos.Tragezeiten.get("Huhn")/2)), Geschlechter.values()[ran.nextInt(2)]));
		} else {
			System.out.println("Du bist noch auf dem Monatsmarkt");
		}
		final int finalplaetzeRNG = RandomClass.luckyNumber;
		final int finalplaetzeAnzahlRNG = RandomClass.anzahlPlaetze;
		final int finalfaktorPreisRNG = RandomClass.preisfaktor;
		
		if (isErsterMarktBesuch == true || glueckGehabt == true || plaetzeKaufenBesucht == false) {
			marktOptionen.put("Bauernhofplätze kaufen", () -> plaetzeKaufen(finalplaetzeRNG, 1, finalfaktorPreisRNG, false));
		}
		
		marktOptionen.put("Tiere kaufen", this::tiereKaufen);
		marktOptionen.put("Tiere verkaufen", this::tiereVerkaufen);
		
		marktOptionen.put("Markt verlassen", null);
		Map<Integer, Map.Entry<String, Runnable>> marktMenu = new LinkedHashMap<>();
		int i = 1;
		for (Map.Entry<String, Runnable> entry : marktOptionen.entrySet()) {
			marktMenu.put(i, entry);
			i++;
		}
		isErsterMarktBesuch = false;
		Bauernhof.aktuelleCredits();
		System.out.println(this.getInfosUeberDenBauernhof());
		Helper.optionenAuswaehlen(marktMenu);
		isErsterMarktBesuch = true;
		plaetzeGekauft = false;
		MarktKauf.clear();
		plaetzeKaufenBesucht = false;
	}

	private void zumWochenMarktGehen() {

		Map<String, Runnable> marktOptionen = new LinkedHashMap<>();
		Random ran = new Random();
		
		if (isErsterMarktBesuch) {
			RandomClass.randomize();
			if(RandomClass.luckyNumber > 13) {
				glueckGehabt = true;
			}
			produkteVerkaufen();
			System.out.println("Du hörst dich ein bisschen um, vielleicht möchte ja jemand Land verkaufen?");
		} else if (isErsterMarktBesuch == false && glueckGehabt == false){
			System.out.println("Diese Woche hast du kein Glück beim Kauf von Plätzen. Du kannst dir aber deine Tiere genau angucken.");
		}
		final int finalplaetzeRNG = RandomClass.luckyNumber;
		final int finalplaetzeAnzahlRNG = RandomClass.anzahlPlaetze;
		final int finalfaktorPreisRNG = RandomClass.preisfaktor;
		
		if (isErsterMarktBesuch == true || glueckGehabt == true || plaetzeKaufenBesucht == false) {
			marktOptionen.put("Bauernhofplätze kaufen", () -> plaetzeKaufen(finalplaetzeRNG, 1, finalfaktorPreisRNG, true));
		}
		marktOptionen.put("Bauernhofbewohner vorstellen", () -> bewohnerStellenSichVor(true));
		marktOptionen.put("Bauernhofbewohner Laute machen lassen", () -> bewohnerMachenLaute(true));
		marktOptionen.put("Markt verlassen", null);
		
		Map<Integer, Map.Entry<String, Runnable>> marktMenu = new LinkedHashMap<>();
		int i = 1;
		for (Map.Entry<String, Runnable> entry : marktOptionen.entrySet()) {
			marktMenu.put(i, entry);
			i++;
		}
		isErsterMarktBesuch = false;
		Bauernhof.aktuelleCredits();
		Helper.optionenAuswaehlen(marktMenu);
		isErsterMarktBesuch = true;
		plaetzeGekauft = false;
		MarktKauf.clear();
		plaetzeKaufenBesucht = false;
		glueckGehabt = false;
	}
	
	private void plaetzeKaufen(int randomNumber, int anzahlPlaetzeRNG, int faktorPreisRNG, boolean istWochenMarkt) {
		
		if(plaetzeGekauft) {
			System.out.println("Du hast bereits Plätze oder Land gekauft. Probiere es beim nächsten Mal erneut.");
		} else {

		if(plaetzeKaufenBesucht == false) {
			System.out.println(
					"Mit etwas Glück bieten deine Nachbarn hier Teile ihrer Grundstücke an, sodass du mehr Platz und damit Futter für deine Tiere dazukaufen kannst.");
			System.out.println("Schauen wir mal, ob du diese Woche Glück hast.");
			Helper.delayForXSeconds(2);
		}
		plaetzeKaufenBesucht = true;
		
		if (randomNumber > 13) { // Ein Viertel Chance
			System.out.println("Du hattest Glück!");
			glueckGehabt = true; 
			System.out.println(
					"Du kannst entweder Plätze für weitere Tiere kaufen oder das neue Land benutzen, um Futter zu erzeugen.");
			int anzahlPlaetze = anzahlPlaetzeRNG;
			int landmasseZumVerkauf = RandomClass.quadratmeter;
			int faktorPreis = faktorPreisRNG;
			int preisAnzahlPlaetze = 1000;
			int preisLandmasseZumVerkauf = landmasseZumVerkauf * faktorPreis;
			HashMap<Integer, String> plaetzeKaufen = new HashMap<>();
			ArrayList<String> optionen = new ArrayList<>();
			optionen.add(anzahlPlaetze + (anzahlPlaetze > 1 ? " Plätze kaufen für " : " Platz kaufen für ") + preisAnzahlPlaetze + " Credits");
			optionen.add(landmasseZumVerkauf + " Land kaufen (als Futtererzeuger) für " + preisLandmasseZumVerkauf
					+ " Credits");
			optionen.add("Doch nichts kaufen");

			int i = 1;
			for (String option : optionen) {
				plaetzeKaufen.put(i, option);
				i++;
			}
			System.out.println(this.getInfosUeberDenBauernhof());
			int auswahl = Helper.optionenAuswaehlenGeneric(plaetzeKaufen);
			isErsterMarktBesuch = false;
			switch (auswahl) {
			case 1: {
				try {
					subtractCredits(preisAnzahlPlaetze);
					this.setPlaetze(this.getPlaetze() + anzahlPlaetze);
					this.plaetzeGekauft = true;
					System.out.println("Du hast jetzt " + getPlaetze() + "Plätze und " + belegtePlaetze + "belegte Plätze.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					// TODO: handle exception
				}

				break;
			}
			case 2: {
				try {
					subtractCredits(preisLandmasseZumVerkauf);
					this.LandmasseInQuadratmetern += landmasseZumVerkauf;
					this.plaetzeGekauft = true;
					System.out.println("Du gehst zurück zum Markt.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			default: {
				System.out.println("Doch nichts gekauft.");
			}

			}
		} else {
			System.out.println("Diese Woche hattest du leider kein Glück. Schau nächste Woche erneut vorbei.");
		}
		}
		if(istWochenMarkt) {
			zumWochenMarktGehen();
		} else {
			zumMonatsMarktGehen();
		}
		
		
	}

	private void produkteVerkaufen() {
		System.out.println();
		System.out.println(
				"Du bist beim Markt und kannst die Produkte verkaufen, die deine Tiere über die Woche erzeugt haben.");
		int sumOfProducts = 0;
		for (Produkt produkt : ProduktInventar) {
			System.out.println(produkt.getName() + ", erzeugt durch " + produkt.getErzeugerName() + " am "
					+ produkt.getTagAnDemErzeugtWurde() + ". Tag. Wert: " + produkt.getWert() + ".");
			sumOfProducts += produkt.getWert();
		}
		HashMap<Integer, String> AuswahlProdukteMenu = new HashMap<>();
		ArrayList<String> optionen = new ArrayList<>();
		optionen.add("Alle Produkte verkaufen für " + sumOfProducts + " Credits");
		optionen.add("Alle Produkte spenden für 0 Credits");
		optionen.add("Alle Produkte vergammeln lassen");
		int i = 1;
		for (String string : optionen) {
			AuswahlProdukteMenu.put(i, string);
			i++;
		}
		System.out.println();
		int auswahl = Helper.optionenAuswaehlenGeneric(AuswahlProdukteMenu);
		switch (auswahl) {
		case 1:
			try {
				System.out.println("Du hast deine Produkte für " + sumOfProducts + " Credits verkauft.");
				addCredits(sumOfProducts);

			} catch (TransactionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 2:
			System.out.println(
					"Du hast deine Produkte an bedürftige Menschen verteilt. The Lannisters send their regards.");
			break;

		case 3:
			System.out.println("Du lässt deine Produkte lieber verrotten anstatt sie zu verschenken.");
			System.out.println("Shame!");
			Helper.delayForXSeconds(2);
			System.out.println("Shame!");
			Helper.delayForXSeconds(2);
			System.out.println("Shame!");
			Helper.delayForXSeconds(2);
			break;

		default:
			// Darf eig nicht sein, da Werte vorher validiert werden.
			System.out.println("Irgendwas ist schief gelaufen");
			break;
		}
		ProduktInventar.clear();
	}

	private void tiereKaufen() {

		System.out.println("Möchtest du eines der folgenden Tiere kaufen?");

		Map<Integer, Tier> tiereKaufen = new HashMap<>();
		int i = 1;
		for (Tier tier : MarktKauf) {
			tiereKaufen.put(i, tier);
			i++;
		}
		int auswahl = Helper.optionenAuswaehlenTierKauf(tiereKaufen);
		if (auswahl <= tiereKaufen.size()) {
			Tier tier = tiereKaufen.get(auswahl);
			try {
				System.out.println("Du möchtest ein " + tier.getGattung() + " für " + tier.getWert() + " Credits kaufen.");
				this.subtractCredits(tier.getWert());
				tier.setName(Helper.getUserInput("Wie soll dein neues Tier heißen?"));
				NeuerBewohner(tier);
				
				
				
				MarktKauf.remove(tier);
			} catch (KeinPlatzException e) {
				System.out.println("Du hattest keinen Platz für das Tier.");
				System.out.println("Geld wurde wieder gutgeschrieben");
				try {
					this.addCredits(tier.getWert());
				} catch (TransactionException e1) {
					System.out.println(e.getMessage());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		Helper.delayForXSeconds(3);
		zumMonatsMarktGehen();
	}

	private void tiereVerkaufen() {
		System.out.println("Tiere verkaufen wurde ausgewählt. Möchtest du eines der folgenden Tiere verkaufen?");
		int i = 0;
		Map<Integer, Tier> map = new HashMap<>();
		for (Tier tier : TierischeBewohner) {
			tier.infosUeberIDNameGattungUndGeschlecht();
			System.out.println("Verkaufen für " + tier.getWert());
			map.put((i + 1), tier);
			i++;
		}
		int auswahl = Helper.optionenAuswaehlenTierVerkauf(map);
		if (auswahl <= map.size()) {
			Tier ausgewaehltesTier = TierischeBewohner.get((auswahl - 1));
			System.out.println(ausgewaehltesTier.getName() + " wurde verkauft.");
			try {
				this.addCredits(ausgewaehltesTier.getWert());
			} catch (TransactionException e) {
				System.out.println(e.getMessage());
			}
			belegtePlaetze -= ausgewaehltesTier.getBrauchtAnzahlPlaetze();
			TierischeBewohner.remove((auswahl - 1));

		}
		Helper.delayForXSeconds(1.5);
		zumMonatsMarktGehen();
	}

	public void bewohnerStellenSichVor(boolean istWochenmarkt) {
		for (Tier tier : TierischeBewohner) {
			tier.infosUeberIDNameGattungUndGeschlecht();
		}
		Helper.delayForXSeconds(1);
		if(istWochenmarkt) {
			zumWochenMarktGehen();
		}

	}

	public void bewohnerMachenLaute(boolean istWochenmarkt) {
		for (Tier tier : TierischeBewohner) {
			tier.gibLaut();
		}
		Helper.delayForXSeconds(1);
		if(istWochenmarkt) {
			zumWochenMarktGehen();
		}
	}

	public void bewohnerFressen() {
//		System.out.println("Bauernhof hat noch " + FutterZurVerfügung + " Einheiten Futter zur Verfügung");
//		System.out.println("Tiere fangen an zu fressen");
		for (Tier tier : TierischeBewohner) {
			tier.fressen(this);
		}
		System.out.println("Es sind noch " + FutterZurVerfügung + " Einheiten Futter zur Verfügung");
	}

	public void bewohnerPaaren() {
		Map<String, List<Tier>> groupedByGattungen = TierischeBewohner.stream()
				.collect((Collectors.groupingBy(Tier::getGattung)));

		groupedByGattungen.forEach((gattungen, tierList) -> {
			long maennlicheBewohner2 = tierList.stream().filter(tier -> tier.getGeschlecht() == Geschlechter.maennlich)
					.count();

			tierList.stream().filter(tier -> tier.getGeschlecht() == Geschlechter.weiblich)
					.forEach(tier -> tier.paaren((int) maennlicheBewohner2));
		});

	}

	public String getInfosUeberDenBauernhof() {
		return "FutterZurVerfügung: " + FutterZurVerfügung + ", Plaetze: " + getPlaetze() + " - davon belegt: "
				+ belegtePlaetze + ", Landmasse in Quadratmetern: " + LandmasseInQuadratmetern;
	}
	private static int belegtePlaetze;
	public List<Tier> TierischeBewohnerZeigen() {
		return TierischeBewohner;
	}

	public static void NeuerBewohner(Tier tier) throws KeinPlatzException {
		TierischeBewohner.add(tier);
		if(getPlaetze() < (tier.getBrauchtAnzahlPlaetze()+belegtePlaetze)) {
			throw new KeinPlatzException("Du hast nicht genügend Platz um den Bewohner aufzunehmen.");
		} else {
			belegtePlaetze += tier.getBrauchtAnzahlPlaetze();
		}		
		System.out.println("Der Bauernhof hat einen neuen Bewohner: ");
		System.out.println(tier.getGattung() + " " + tier.getName() + ", Geschlecht: " + tier.getGeschlecht());
		System.out.println("Plätze insgesamt: " + (getPlaetze()) + ", belegte Plätze: " + belegtePlaetze);
	}

	public static void NeuesProdukt(Produkt produkt) {
		ProduktInventar.add(produkt);
		System.out.println(produkt.getErzeugerName() + " hat " + produkt.getName() + " erzeugt.");
	}

	public static int getPlaetze() {
		return Plaetze;
	}

	public static void setPlaetze(int plaetze) {
		Plaetze = plaetze;
	}

}
