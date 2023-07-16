package oop_Bauernhof;

public class Ziege extends Tier implements IProdukt {
	public Ziege(String name, int alter, Geschlechter geschlecht) {
		super(name, alter, 5475, "Blöööök", 2, geschlecht, TierInfos.Tragezeiten.get("Ziege"), new Ziegenmilch("Ziege", name, 0));
	}
	
	@Override
	public Ziege createNewInstance(String name, int alter, Geschlechter geschlecht) {
		return new Ziege(name, alter, geschlecht);
	}
	
	@Override
	public void erzeugeProdukt(int tag) {
		Bauernhof.NeuesProdukt(new Ziegenmilch(this.getGattung(),this.getName(), tag));
	}
}

