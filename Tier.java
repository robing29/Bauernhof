package oop_Bauernhof;

import java.util.Random;

public abstract class Tier implements IProdukt {
	public Tier(String name, int alter, int durchschnittlichesTodesalter, String laut,
			int futterbedarf, Geschlechter geschlecht, int trageZeit, Produkt produkt) {
		this.Gattung = this.getClass().toString();
		this.Name = name;
		this.ID = ++AnzahlBisherErzeugterTiere;
		this.Alter = alter;
		this.DurchschnittlichesTodesalter = durchschnittlichesTodesalter;
//		this.Besonderheiten = besonderheiten;
		this.IstKrank = false;
		this.FutterbedarfProTag = futterbedarf;
		this.Laut = laut;
		this.Geschlecht = geschlecht;
		this.TrageZeit = trageZeit;
		this.GattungsProdukt = produkt;
		berechneWert(true, produkt);
	}

	public void berechneWert(boolean firstTime, Produkt produkt) {
		if(firstTime) {
			this.Wert += TierInfos.GattungsWerte.get(getClass().toString().substring(getClass().toString().lastIndexOf('.') + 1));
			this.Wert += TierInfos.ProduktWerte.get(produkt.getName());
		}
		
		if(Alter < Geschlechtsreife) {
			this.Wert *= 0.7;
		}
		float restLebenszeit = DurchschnittlichesTodesalter - Alter;
		this.Wert = (int)((float)this.Wert * (restLebenszeit / DurchschnittlichesTodesalter));
	}

	private Produkt GattungsProdukt;

	private String Gattung;

	private String Name;

	public void setName(String name) {
		Name = name;
	}

	private int ID;
	
	private int Geschlechtsreife = TierInfos.Geschlechtsreife.get(getClass().toString().substring(getClass().toString().lastIndexOf('.') + 1));
	private int BrauchtAnzahlPlaetze = TierInfos.BrauchtAnzahlPlaetze.get(getClass().toString().substring(getClass().toString().lastIndexOf('.') + 1));
	public int getBrauchtAnzahlPlaetze() {
		return BrauchtAnzahlPlaetze;
	}

	private boolean isGeschlechtsreif;

	public boolean isGeschlechtsreif() {
		return isGeschlechtsreif;
	}

	public void setGeschlechtsreif(boolean isGeschlechtsreif) {
		this.isGeschlechtsreif = isGeschlechtsreif;
	}

	private static int AnzahlBisherErzeugterTiere = 0;

	private int Alter;
	// Alter in Tagen rechnen, aber bei Get formatierte Ausgabe mit Jahr und Tagen

	public int getAlter() {
		return Alter;
	}
	
	public String getAlterFormatiert(){
		int jahr = (Alter / 365);
		int tage = (Alter % 365);
		String alter = this.Name + " ist " + jahr + (jahr == 1 ? " Jahr" : " Jahre") + " und " + tage + (tage == 1 ? " Tag " : " Tage ") + "alt.";
		return alter;
	}
	public String getAlterFormatiert(boolean ohneNamen){
		int jahr = (Alter / 365);
		int tage = (Alter % 365);
		String alter = jahr + (jahr == 1 ? " Jahr" : " Jahre") + " und " + tage + (tage == 1 ? " Tag " : " Tage ");
		return alter;
	}

	public void setAlter(int alter) {
		Alter = alter;
		if(Alter >= this.Geschlechtsreife) {
			isGeschlechtsreif = true;
		}
	}

	private Geschlechter Geschlecht;

	public Geschlechter getGeschlecht() {
		return Geschlecht;
	}

	public void setGeschlecht(Geschlechter geschlecht) {
		Geschlecht = geschlecht;
	}

	private int DurchschnittlichesTodesalter;

	private boolean IstKrank;

	private int FutterbedarfProTag;

	private String Laut;

	public int getFutterbedarfProTag() {
		if (IstKrank) {
			return (FutterbedarfProTag * 2);
		}
		return FutterbedarfProTag;
	}

	private int DeathCounter = 5;

	private boolean IstTot = false;

	private boolean IstSchwanger = false;

	public boolean isIstSchwanger() {
		return IstSchwanger;
	}

	public void setIstSchwanger(boolean istSchwanger) {
		IstSchwanger = istSchwanger;
	}

	private int TrageZeit;

	public int getTrageZeit() {
		return TrageZeit;
	}

	public void setTrageZeit(int trageZeit) {
		TrageZeit = trageZeit;
	}

	public boolean IstTot() {
		return IstTot;
	}

	public void setIstTot(boolean istTot) {
		IstTot = istTot;
	}

	public String getName() {
		return Name;
	}
	
	private int Wert;

	public int getWert() {
		return Wert;
	}

	public void setWert(int wert) {
		Wert = wert;
	}

	public abstract Tier createNewInstance(String name, int alter, Geschlechter geschlecht);

	public void paaren(int anzahlMaennlicherGattungsgenossenImRichtigenAlter) {
		if (IstSchwanger) {
			return;
		}
		if (this.Geschlecht == Geschlechter.weiblich) {
			Random ran = new Random(System.currentTimeMillis());
			for (int i = 0; i < anzahlMaennlicherGattungsgenossenImRichtigenAlter; i++) {
				if (ran.nextInt(1, 101) > 98) { //normalerweise > 98
					setIstSchwanger(true);
					System.out.println();
					System.out.println(this.Name + " ist schwanger.");
					
					//TODO: Cooldown implementieren, Geschlechtsreife bzw. Tage nach Schwangerschaft
					Helper.delayForXSeconds(4);
					
					break;
				}
			}
		}
	};

	public void fressen(Bauernhof bauernhof) {

		if (bauernhof.getFutterZurVerfügung() < 1) {
			System.out.println(this.Name + " kann nicht essen. Kein Futter mehr vorhanden");
			krankWerden();
			sterben();

		} else if (bauernhof.getFutterZurVerfügung() < FutterbedarfProTag) {
			System.out.println(this.Name + " hat noch ein bisschen Hunger");
			bauernhof.setFutterZurVerfügung(0);
		} else {
			System.out.println(this.Name + " frisst genüsslich das Futter");
			this.gesundWerden();
			bauernhof.setFutterZurVerfügung(bauernhof.getFutterZurVerfügung() - this.FutterbedarfProTag);
		}
	};

	public void krankWerden() {
		this.IstKrank = true;
	}

	public void gesundWerden() {
		this.IstKrank = false;
		this.DeathCounter = 5;
	}

	public void sterben() {
		if (IstKrank && DeathCounter == 0) {
			System.out.println();
			System.out.println(this.Name + " ist gestorben. Eine Schweigeminute, bitte.");
			System.out.println();
			this.IstTot = true;

			Helper.delayForXSeconds(6);
		}
		if (IstKrank && DeathCounter > 0) {
			System.out.println(
					this.Name + " braucht schnell Futter, sonst überlebt es nur noch " + DeathCounter + " Tag(e)");
			DeathCounter--;
		}
	}

	public void infosUeberIDNameGattungUndGeschlecht() {
		System.out.println("ID: " + this.ID + ", Name: " + this.Name + ", Gattung: " + getGattung() + ", Geschlecht: " + getGeschlecht());
	}

	public void gibLaut() {
		System.out.println(this.Name + ": " + Laut);
	}

	public String getGattung() {
		return Gattung.substring(Gattung.lastIndexOf('.') + 1);
	}
	
	public abstract void erzeugeProdukt(int tag);
}
