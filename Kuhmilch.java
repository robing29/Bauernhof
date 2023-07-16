package oop_Bauernhof;

public class Kuhmilch extends Produkt{

	public Kuhmilch(String erzeugerGattung, String erzeugerName, int tag) {
		super("Kuhmilch", 10 , erzeugerGattung, erzeugerName, tag);
		//System.out.println(this.getClass() + " wurde von " + erzeugerName + " produziert.");
	}
}