package util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import exceptions.MethodFoundException;
import rs.ac.uns.ftn.xws.cbs.faktura.Faktura;
import rs.ac.uns.ftn.xws.cbs.faktura.FakturaStavka;
import rs.ac.uns.ftn.xws.cbs.faktura.FakturaZaglavlje;

public class NestedFieldGetterTest {

	private Faktura testFaktura;

	@Before
	public void init() {
		testFaktura = new Faktura();
		
		for (int i = 0; i < 5; i++) {
			FakturaStavka fs = new FakturaStavka();
			fs.setNazivRobeIliUsluge(i+" sargarepa");
			fs.setKolicina(new BigDecimal(100));
			fs.setJedinicaMere("gram");
			fs.setRedniBroj(BigInteger.valueOf(i));
			testFaktura.getStavke().add(fs);
		}
		
		FakturaZaglavlje fz = new FakturaZaglavlje();
		fz.setAdresaKupca("bez adrese");
		fz.setIdPoruke("poruka");
		fz.setNazivKupca("kupac");
		fz.setPibKupca("2525500");
		
		testFaktura.setZaglavlje(fz);
	}
	
	@Test
	public void testFindField() {
		NestedFieldValueGetter nfg = new NestedFieldValueGetter();
		try {
			Object obj=nfg.findField(testFaktura, "getIdPoruke");
			System.out.println((String)obj);
		} catch (MethodFoundException e) {
			System.err.println("field: "+e.getMessage());
		}
	
	}

}
