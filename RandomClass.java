package oop_Bauernhof;

import java.util.*;


public class RandomClass {
	public RandomClass() {
		Random ran = new Random();
		if(Bauernhof.isErsterMarktBesuch) {
			luckyNumber = ran.nextInt(1,21);
			anzahlPlaetze = ran.nextInt(1,3);
			preisfaktor = ran.nextInt(7,13);
		}
	}
	
	public static void randomize(){
		Random ran = new Random();
		luckyNumber = ran.nextInt(1,21);
		anzahlPlaetze = ran.nextInt(1,3);
		preisfaktor = ran.nextInt(2,6);
		quadratmeter = ran.nextInt(1,11);
	}
	
	public static int luckyNumber = 0;
	public static int anzahlPlaetze = 0;
	public static int preisfaktor = 0;
	public static int quadratmeter = 0;
}
