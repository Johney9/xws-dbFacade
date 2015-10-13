package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import rs.ac.uns.ftn.xws.cbs.faktura.Faktura;
import rs.ac.uns.ftn.xws.cbs.mt103.Mt103;
import rs.ac.uns.ftn.xws.cbs.zahtev_za_izvod.ZahtevZaIzvod;
import util.converter.GenericXWSMarshaller;
import util.converter.GenericXWSUnmarshaller;

import com.xmldb.rest.RESTUtil;

import facades.DatabaseReader;
import facades.IdGeneratorFacade;

public class DBReaderFacadeTest {

	private ZahtevZaIzvod ziz;

	@Before
	public void setUp() throws Exception {
		ziz = new ZahtevZaIzvod();
		ziz.setBrojRacuna("123456789123456789");
		try {
			ziz.setDatum(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ziz.setRedniBrojPreseka(new BigInteger("1"));
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void testRead() {
		DatabaseReader<Mt103> mt103Reader = new DatabaseReader<Mt103>(new Mt103(), "mt103.xml", "101");
		try {
			Mt103 mt103 = mt103Reader.read();
			System.out.println(mt103.getIdPoruke());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRead4() {
		
		try {
			GenericXWSMarshaller<ZahtevZaIzvod> gxm = new GenericXWSMarshaller<ZahtevZaIzvod>(ziz, new FileOutputStream(new File("ziz.xml")));
			GenericXWSUnmarshaller<ZahtevZaIzvod> gum = new GenericXWSUnmarshaller<ZahtevZaIzvod>(ziz, new FileInputStream("ziz.xml"));
			
			try {
				gxm.marshall();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ZahtevZaIzvod novi = null;
			try {
				novi = gum.unmarshall();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.err.println("marshalled then unmarshalled: " + "br: "+novi.getBrojRacuna()+" dat: "+novi.getDatum().toString()+" rbp: "+novi.getRedniBrojPreseka());
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	//@Test
	public void pass() {
		IdGeneratorFacade igf = new IdGeneratorFacade(ziz);
		System.out.println(igf.findIdXWS());
		//DBWriterFacade<ZahtevZaIzvod> zizW = new DBWriterFacade<ZahtevZaIzvod>(ziz);
	}
	
	//@Test
	public void sunceTi() {
		try {
			BufferedInputStream in = new BufferedInputStream(RESTUtil.retrieveSpecificResource("zahtev_za_izvod.xml", ziz.getBrojRacuna()));
			//IOUtils.copy(in, new FileOutputStream(new File("fromBase.xml")));
			System.err.println("READING STREAM");
			//IOUtils.copy(in, System.out);
			System.out.println();
			
			GenericXWSUnmarshaller<ZahtevZaIzvod> gxum = new GenericXWSUnmarshaller<ZahtevZaIzvod>(ziz, in);
			ZahtevZaIzvod uu=gxum.unmarshall();
			System.out.println(uu);
			in.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//@Test
	public void testRead3() {
		
		//DBWriterFacade<ZahtevZaIzvod> zizW = new DBWriterFacade<ZahtevZaIzvod>(ziz);
		
		DatabaseReader<ZahtevZaIzvod> zizR = new DatabaseReader<ZahtevZaIzvod>(new ZahtevZaIzvod(), "zahtev_za_izvod", "111");
	
		try {
			//System.err.println("Writing: ");
			//System.err.println();
			//zizW.save();
			System.err.println("Reading: ");
			ZahtevZaIzvod zizara = zizR.read();
			System.out.println(zizara);
			System.err.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testRead2() {
		DatabaseReader<Faktura> fktReader = new DatabaseReader<Faktura>(new Faktura(), "faktura.xml", "j");
		try {
			fktReader.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Unsucsessful: "+e.getLocalizedMessage());
		}
	}

}
