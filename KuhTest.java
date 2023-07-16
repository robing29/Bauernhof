package oop_Bauernhof;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KuhTest {

	@Test
	void testGetNewInstance() {
		Random ran = new Random();
		Geschlechter geschlecht = Geschlechter.values()[ran.nextInt(2)];
		Rind kuh2 = new Rind("Ferrero", 0, Geschlechter.maennlich);
		Tier neuesTier = kuh2.createNewInstance("Neugeborenes", 0 , geschlecht);
		Assertions.assertNotNull(neuesTier);
		Ziege ziege = new Ziege("testZiege", 0, Geschlechter.maennlich);
		Tier neuesTier2 = ziege.createNewInstance("neu", 32, geschlecht);
		Assertions.assertNotNull(neuesTier2);
		Assertions.assertEquals("neu",neuesTier2.getName());
		Assertions.assertEquals(32, neuesTier2.getAlter());
	}
	@Test
	void testgetAlterFormatiert() {
		Rind rind = new Rind("testRind", 542, Geschlechter.weiblich);
		Assertions.assertEquals("testRind ist 1 Jahr und 177 Tage alt.",rind.getAlterFormatiert());
		
		Rind rind2 = new Rind("testRind", 177, Geschlechter.weiblich);
		Assertions.assertEquals("testRind ist 0 Jahre und 177 Tage alt.",rind2.getAlterFormatiert());
		
		Rind rind3 = new Rind("testRind", 12387, Geschlechter.weiblich);
		Assertions.assertEquals("testRind ist 33 Jahre und 342 Tage alt.",rind3.getAlterFormatiert());
	}
	@Test
	void testBerechneWert() {
		Rind rind2 = new Rind("testRind", 177, Geschlechter.weiblich);
		Assertions.assertEquals(rind2.getWert(), 53 ); //55 ist Grundwert. 55 * 0,97 = 53 
		
		Rind rind3 = new Rind("testRind", 3500, Geschlechter.weiblich); //3800/7300 = 0,52
		Assertions.assertEquals(rind3.getWert(), 57 ); //55 * 2 * 0,52 = 57 
	}
}
