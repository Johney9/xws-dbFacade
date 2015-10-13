package test;

import java.math.BigInteger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rs.ac.uns.ftn.xws.cbs.zahtev_za_izvod.ZahtevZaIzvod;
import facades.DatabaseWriter;

public class DBWriterFacadeTest {

	public String schemaName = "bezvezna";
	
	@Before
	public void setUp() throws Exception {
		//RESTUtil.startBase();
		//RESTUtil.createSchema(schemaName);
	}

	@After
	public void tearDown() throws Exception {
		//RESTUtil.dropSchema(schemaName);
		//RESTUtil.stopBase();
	}

	@Test
	public void testSave() {
		ZahtevZaIzvod ziz = new ZahtevZaIzvod();
		ziz.setBrojRacuna("007007007007007007");
		try {
			ziz.setDatum(DatatypeFactory.newInstance().newXMLGregorianCalendar());
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ziz.setRedniBrojPreseka(new BigInteger("1"));
		
		DatabaseWriter<ZahtevZaIzvod> dbwf = new DatabaseWriter<ZahtevZaIzvod>(ziz, schemaName);
		//DBReaderFacade<ZahtevZaIzvod> dbr = new DBReaderFacade<ZahtevZaIzvod>(new ZahtevZaIzvod(), "(//zahtev_za_izvod/text())", schemaName);
		try {
			dbwf.store();
			ziz.setRedniBrojPreseka(new BigInteger("1"));
			dbwf.store();
			ziz.setRedniBrojPreseka(new BigInteger("2"));
			dbwf.store();
			ziz.setRedniBrojPreseka(new BigInteger("3"));
			dbwf.store();
			ziz.setRedniBrojPreseka(new BigInteger("4"));
			dbwf.store();
			//ZahtevZaIzvod zzz= dbr.read();
			//System.out.println(zzz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
