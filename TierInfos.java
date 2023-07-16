package oop_Bauernhof;

import java.util.HashMap;

public class TierInfos {
	public static HashMap<String, Integer> Tragezeiten = new HashMap<>() {
		{
			put("Rind", 25); // 280
			put("Ziege", 14); // 150
			put("Huhn", 8);
		}
	};

	public static HashMap<String, Integer> GattungsWerte = new HashMap<>() {
		{
			put("Rind", 400);
			put("Ziege", 200);
			put("Huhn", 100);
		}
	};

	public static HashMap<String, Integer> ProduktWerte = new HashMap<>() {
		{
			put("Kuhmilch", 10);
			put("Ziegenmilch", 8);
			put("Hühnerei", 5);
		}
	};

	public static HashMap<String, Integer> Geschlechtsreife = new HashMap<>() {
		{
			put("Rind", 300);
			put("Ziege", 150);
			put("Huhn", 90);
		}
	};

	public static HashMap<String, Integer> BrauchtAnzahlPlaetze = new HashMap<>() {
		{
			put("Rind", 4);
			put("Ziege", 3);
			put("Huhn", 2);
		}
	};
	public static HashMap<String, Integer> Futterbedarf = new HashMap<>() {
		{
			put("Rind", 4);
			put("Ziege", 2);
			put("Huhn", 1);
		}
	};
}
