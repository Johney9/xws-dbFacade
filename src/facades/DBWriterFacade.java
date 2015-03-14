package facades;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import util.converter.GenericXWSMarshaller;

import com.xmldb.rest.RESTUtil;
import com.xmldb.rest.misc.RequestMethod;

/**
 * Class modeled after the {@code RESTPut} class. Marshalles the object to xml and saves it in the database.
 * @author Nikola
 * @param <T> class that is being marshalled
 */
public class DBWriterFacade<T> {
	
	protected String schemaName;
	protected T marshalee;
	private GenericXWSMarshaller<T> gxm;
	
	/**
	 * Default constructor, use this one
	 * @param marshalee object being saved to the database
	 */
	public DBWriterFacade(T marshalee) {
		this.marshalee=marshalee;
		this.schemaName=generateId();
	}
	
	/**
	 * Constructor used for testing
	 * @param marshalee object being saved to the database
	 * @param schemaName the database schema's name
	 */
	public DBWriterFacade(T marshalee, String schemaName) {
		this.schemaName=schemaName;
		this.marshalee=marshalee;
	}
	
	private String generateId() {
		IdGeneratorFacade igf = new IdGeneratorFacade(marshalee);
		return igf.generateIdXws();
	}
	
	/**
	 * Save to the database
	 * @throws JAXBException, IOException, SAXException
	 * @throws Exception exception probably thrown by {@code RESTUtil.class}
	 */
	public void save() throws JAXBException, IOException, SAXException, Exception {
		System.out.println("=== PUT: create a new database ===");

		try {
			RESTUtil.createSchema(schemaName);
		} catch (Exception e) {
			System.err.println("Creating of schema unsucsessful. Trying without creating a schema.");
			System.out.println("DBWriterFacade.save()");
		}
		
		/* URL konekcije ka konkretnom resursu - semi baze */
		String fullPackageName = marshalee.getClass().getPackage().getName();
		String resourceId = fullPackageName.substring(fullPackageName.lastIndexOf(".")+1);
		URL url = new URL(RESTUtil.REST_URL + schemaName + "/" + resourceId +".xml");
		System.out.println("\n* URL: " + url);

		/* Uspostavljanje konekcije */
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		/* Tip konekcije je OUTPUT */
		conn.setDoOutput(true);
		/* Postavljanje HTTP request metode */
		conn.setRequestMethod(RequestMethod.PUT);

		/* Preuzimanje output stream-a iz otvorene konekcije */
		BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
		gxm = new GenericXWSMarshaller<T>(marshalee, out);
		
		/* Slanje podataka kroz stream */
		System.out.println("\n* Send document...");
		gxm.marshall();
		
		/* Response kod vracen od strane servera */
		RESTUtil.printResponse(conn);

		/* Obavezno zatvaranje tekuce konekcije */
		conn.disconnect();
	}
	
	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public T getMarshalee() {
		return marshalee;
	}

	public void setMarshalee(T marshalee) {
		this.marshalee = marshalee;
	}
	
}
