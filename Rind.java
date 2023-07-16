package oop_Bauernhof;

public class Rind extends Tier implements IProdukt {
	 
	
	public Rind(String name, int alter, Geschlechter geschlecht) {
		//TODO: Tragezeiten.get variabel mit Gattungsnamen machen
		super(name, alter, 7300, "Muuuuuuuuuuuuuuuuh" , 4, geschlecht, 280, new Kuhmilch("Rind", name, 0));
	}
	
	@Override
	public void erzeugeProdukt(int tag) {
		 Bauernhof.NeuesProdukt(new Kuhmilch(this.getGattung(),this.getName(), tag));
	}

	@Override
	public Rind createNewInstance(String name, int alter, Geschlechter geschlecht) {
		return new Rind(name, alter, geschlecht);
	}
}
