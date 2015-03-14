package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import rs.ac.uns.ftn.xws.cbs.zahtev_za_izvod.ZahtevZaIzvod;
import util.converter.GenericXWSUnmarshaller;

public class GenericXWSUnmarshallerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUnmarshall() {
		try {
			GenericXWSUnmarshaller<ZahtevZaIzvod> gxu = new GenericXWSUnmarshaller<ZahtevZaIzvod>(new ZahtevZaIzvod(), new FileInputStream("test.xml"));
			try {
				gxu.unmarshall();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
