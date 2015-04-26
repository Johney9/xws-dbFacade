package test;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sun.xml.bind.marshaller.XMLWriter;

import rs.ac.uns.ftn.xws.cbs.mt102.Mt102;
import rs.ac.uns.ftn.xws.cbs.mt102.Mt102PojedinacniNalog;
import util.converter.GenericXWSMarshaller;
import facades.DatabaseFacade;

public class BlaBla {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws JAXBException, IOException, SAXException, Exception {
		DatabaseFacade dbf = new DatabaseFacade();
		
		Mt102 target = new Mt102();
		
		XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
		
		target.setDatum(cal);
		target.setDatumValute(cal);
		target.setIdPoruke("KECA");
		target.setObracunskiRacunBankeDuznika("123456789123456789");
		target.setObracunskiRacunBankePoverioca("123456789123456788");
		target.setSifraValute("DIN");
		target.setSwiftKodBankeDuznika("SBBER001");
		target.setSwiftKodBankePoverioca("SBBER002");
		target.setUkupanIznos(BigDecimal.valueOf(9001));
		
		for(int i=0; i<53; i++) {
			
			Mt102PojedinacniNalog e = new Mt102PojedinacniNalog();
			
			e.setDatumNaloga(cal);
			e.setDuznikNalogodavac("MALOLETNICE INC");
			e.setIdNalogaZaPlacanje(i+":KECA");
			e.setIznos(BigDecimal.valueOf(169.83));
			e.setModelOdobrenja("93");
			e.setModelZaduzenja("70");
			e.setPozivNaBrojOdobrenja("99999999999999999999");
			e.setPozivNaBrojZaduzenja("77777777777777777777");
			e.setPrimalacPoverilac("KECA INDUSTRIES");
			e.setRacunDuznika("123456789123456789");
			e.setRacunPoverioca("123456789123456788");
			e.setSifraValute("123");
			e.setSvrhaPlacanja("ODSTETA");
			
			target.getPojedinacniNalozi().add(e);
		}
		
		//dbf.storeInDatabase(target);
		
		FileOutputStream out = new FileOutputStream("mt102.xml");
		GenericXWSMarshaller<Mt102> gxm = new GenericXWSMarshaller<Mt102>(target, out);
		gxm.marshall();
	}

}
