package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rs.ac.uns.ftn.xws.cbs.faktura.Faktura;
import rs.ac.uns.ftn.xws.cbs.faktura.FakturaZaglavlje;
import rs.ac.uns.ftn.xws.cbs.izvod.Izvod;
import rs.ac.uns.ftn.xws.cbs.izvod.IzvodZaglavlje;
import rs.ac.uns.ftn.xws.cbs.mt102.Mt102;
import rs.ac.uns.ftn.xws.cbs.zahtev_za_izvod.ZahtevZaIzvod;
import facades.IdGeneratorFacade;

public class TestKeyAcessor {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindKeyPass() {
		ZahtevZaIzvod ziz = new ZahtevZaIzvod();
		ziz.setBrojRacuna("007");
		IdGeneratorFacade igf = new IdGeneratorFacade(ziz);
		System.out.println(igf.generateIdXws());
		
		Mt102 mt102 = new Mt102();
		mt102.setIdPoruke("johney");
		
		IdGeneratorFacade igf2 = new IdGeneratorFacade(mt102);
		System.err.println(igf2.generateIdXws());
		
	}
	
	@Test
	public void testFindKeyRandom() {
		Faktura fkt = new Faktura();
		FakturaZaglavlje fktzag = new FakturaZaglavlje();
		fktzag.setIdPoruke("0212");
		fkt.setZaglavlje(fktzag);
		IdGeneratorFacade igf = new IdGeneratorFacade(fkt);
		System.out.println(igf.generateIdXws());
		
		Izvod izvod = new Izvod();
		IzvodZaglavlje izvodZaglavlje = new IzvodZaglavlje();
		
		izvodZaglavlje.setBrojRacuna("dzonimir");
		izvod.setZaglavlje(izvodZaglavlje);
		IdGeneratorFacade igf2 = new IdGeneratorFacade(izvod);
		System.err.println(igf2.generateIdXws());
	}

}
