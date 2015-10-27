package facades;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public class DatabaseWriter<T> {
	
	protected String schemaName;
	protected T marshalee;
	private GenericXWSMarshaller<T> gxm;
	
	/**
	 * Default constructor, use this one
	 * @param marshalee object being saved to the database
	 */
	public DatabaseWriter(T marshalee) {
		this.marshalee=marshalee;
		this.schemaName=generateId();
	}
	
	/**
	 * Constructor used for testing
	 * @param marshalee object being saved to the database
	 * @param schemaName the database schema's name
	 */
	public DatabaseWriter(T marshalee, String schemaName) {
		this.schemaName=schemaName;
		this.marshalee=marshalee;
	}
	
	//not working correctly
	private String generateId() {
		IdGeneratorFacade igf = new IdGeneratorFacade(marshalee);
		return igf.findIdXWS();
	}
	
	/**
	 * Save to the database
	 * @throws JAXBException, IOException, SAXException
	 * @throws Exception exception probably thrown by {@code RESTUtil.class}
	 */
	@Deprecated
	public void storeOld() throws JAXBException, IOException, SAXException, Exception {
		System.out.println("=== PUT: create a new database ===");

		try {
			RESTUtil.createSchema(schemaName);
		} catch (Exception e) {
			System.err.println("Creating of schema unsucsessful. Trying without creating a schema.");
			System.out.println("DBWriterFacade.save()");
		}
		
		/* URL konekcije ka konkretnom resursu - semi baze */
		String fullPackageName = marshalee.getClass().getPackage().getName();
		@SuppressWarnings("unused")
		String resourceId = fullPackageName.substring(fullPackageName.lastIndexOf(".")+1);
		URL url = new URL(RESTUtil.REST_URL + schemaName);// + "/" + resourceId +".xml");
		System.out.println("\n* URL: " + url);
		
		/* Uspostavljanje konekcije */
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		/* Tip konekcije je OUTPUT */
		conn.setDoOutput(true);
		/* Postavljanje HTTP request metode */
		conn.setRequestMethod(RequestMethod.PUT);

		/* Preuzimanje output stream-a iz otvorene konekcije */
		BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
		
		System.out.println("connection to database: "+out.toString());
		gxm = new GenericXWSMarshaller<T>(marshalee, out);
		
		/* Slanje podataka kroz stream */
		System.out.println("\n* Send document...");
		gxm.marshall();
		
		/* Response kod vracen od strane servera */
		RESTUtil.printResponse(conn);

		/* Obavezno zatvaranje tekuce konekcije */
		conn.disconnect();
	}
	
	public void store() throws JAXBException, IOException, SAXException, Exception {
		
		String fullPackageName = marshalee.getClass().getPackage().getName();
		String resourceId = fullPackageName.substring(fullPackageName.lastIndexOf(".")+1);
		resourceId+=".xml";
				
		File tempFile = new File(resourceId);
		FileOutputStream out = new FileOutputStream(tempFile);
		gxm = new GenericXWSMarshaller<T>(marshalee, out);
		gxm.marshall();

		FileInputStream resource = new FileInputStream(tempFile);
		System.out.printf("+++DB WRITER; schemaName=%s resourceId=%s",schemaName,resourceId);
		System.out.println();
		int code = RESTUtil.getSchema(schemaName);
		if(code==404) {
			RESTUtil.createSchema(schemaName);
		}
		RESTUtil.updateResource(schemaName, resourceId, resource);
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
