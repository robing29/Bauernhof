package oop_Bauernhof;

public class Huhn extends Tier implements IProdukt {

	public Huhn(String name, int alter, Geschlechter geschlecht) {
		super(name, alter, 3600, "gack-ack-ack ackack-ack", 1, geschlecht, TierInfos.Tragezeiten.get("Huhn"), new HuehnerEi("Huhn", name, 0));
		// TODO Auto-generated constructor stub
	}

	@Override
	public Huhn createNewInstance(String name, int alter, Geschlechter geschlecht) {
		// TODO Auto-generated method stub
		return new Huhn(name, alter, geschlecht);
	}

	@Override
	public void erzeugeProdukt(int tag) {
		// TODO Auto-generated method stub
		Bauernhof.NeuesProdukt(new HuehnerEi(this.getGattung(), this.getName(), tag));
	}
	
}
