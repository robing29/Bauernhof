package oop_Bauernhof;

public abstract class Produkt {
	
	public Produkt(String name, int wert, String erzeugerGattung, String erzeugerName, int tag) {
		this.Name = name;
		this.Wert = wert;
		this.ErzeugerGattung = erzeugerGattung;
		this.ErzeugerName = erzeugerName;
		this.TagAnDemErzeugtWurde = tag;
	}
	
	private String Name;

	public String getName() {
		return Name;
	}
	private String ErzeugerGattung;
	
	private int Wert;
	
	public int getWert() {
		return Wert;
	}

	public int getTagAnDemErzeugtWurde() {
		return TagAnDemErzeugtWurde;
	}

	private int TagAnDemErzeugtWurde;
	
	private String ErzeugerName;

	public String getErzeugerName() {
		return ErzeugerName;
	}
}
