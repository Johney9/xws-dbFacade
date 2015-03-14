package test;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rs.ac.uns.ftn.xws.cbs.faktura.Faktura;
import rs.ac.uns.ftn.xws.cbs.faktura.FakturaZaglavlje;
import rs.ac.uns.ftn.xws.cbs.mt103.Mt103;
import facades.DBWriterFacade;

public class TestDBWriter {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		Faktura fakt = new Faktura();
		fakt.setZaglavlje(new FakturaZaglavlje());
		fakt.getZaglavlje().setIdPoruke("123456789123456789");
		DBWriterFacade<Faktura> dbw = new DBWriterFacade<Faktura>(fakt);
		try {
			dbw.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSave2() {
		Mt103 fakt = new Mt103();
		fakt.setIdPoruke("101");
		DBWriterFacade<Mt103> dbw = new DBWriterFacade<>(fakt);
		try {
			dbw.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
