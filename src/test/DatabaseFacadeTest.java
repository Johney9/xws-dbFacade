package test;

import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import facades.IdGeneratorFacade;
import util.XWSDate;

public class DatabaseFacadeTest {

	@Test
	public void testStoreInDatabaseT() throws DatatypeConfigurationException {
		XMLGregorianCalendar date = XWSDate.getCurrentDate();
		String racun1 = "123456";
		String racun2 = UUID.randomUUID().toString();
		String racun3 = "1234567";
		racun3=racun3.substring(0, racun3.length()-1);
		String hash1 = IdGeneratorFacade.generateIdXWS(racun1, date.toString());
		String hash2 = IdGeneratorFacade.generateIdXWS(racun2, date.toString());
		String hash3 = IdGeneratorFacade.generateIdXWS(racun3, date.toString());
		
		System.out.printf("Racuni: %s, %s, %s, daju hasheve: %s, %s, %s", racun1,racun2,racun3,hash1,hash2,hash3);
	}

	@Test
	public void testStoreInDatabaseTString() {
		//pass("Not yet implemented");
	}

}
