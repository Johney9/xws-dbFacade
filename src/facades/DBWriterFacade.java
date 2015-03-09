package facades;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import util.RestUrl;
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
	 * Constructor
	 * @param marshalee object being marshalled
	 * @param schemaName the database schema's nema
	 */
	public DBWriterFacade(T marshalee, String schemaName) {
		this.schemaName=schemaName;
		this.marshalee=marshalee;
	}
	
	/**
	 * Save to the database
	 * @throws IOException
	 */
	public void save() throws IOException {
		System.out.println("=== PUT: create a new database ===");

		/* URL konekcije ka konkretnom resursu - semi baze */
		URL url = new URL(RestUrl.REST_URL + schemaName);
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
		out.close();
		//IOUtils.closeQuietly(out);
		
		/* Response kod vracen od strane servera */
		try {
			RESTUtil.printResponse(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
