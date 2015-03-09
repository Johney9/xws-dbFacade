package facades;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import util.RestUrl;
import util.converter.GenericXWSUnmarshaller;

import com.xmldb.rest.RESTUtil;
import com.xmldb.rest.misc.RequestMethod;

/**
 * Class modeled after the {@code RESTPost} class. Unmarshalles the object from xml and returns it
 * @author Nikola
 *
 * @param <T> class that is being unmarshalled
 */
public class DBReaderFacade<T> {
	
	protected String query;
	protected T type;
	protected String schemaName;
	
	/**
	 * Constructor
	 * @param type class type
	 * @param query typed in XPath format { "(//XmlRootElement/XmlLeafElement)"+"attribute() = value" }
	 * @param schemaName the database schema's name
	 */
	public DBReaderFacade(T type, String query, String schemaName) {
		this.query = query;
		this.type = type;
		this.schemaName = schemaName;
	}

	/**
	 * Read from the database
	 * @return read object, of type {@code T}
	 * @throws Exception
	 */
	public T read() throws Exception {
		System.out.println("=== POST: execute a query ===");

		/* URL konekcije ka konkretnom resursu - semi baze */
		URL url = new URL(RestUrl.REST_URL + schemaName);
		System.out.println("\n* URL: " + url);

		/* XML formatiran query koji se salje serveru */
		
		/* 
		 * Sa wrap = yes se eksplicitno oznacava da ce vraceni fragmenti XML-a 
		 * biti obavijeni 'results' elementom iz 'rest' namespace-a 
		 */
		String request = 
				"<query xmlns='http://basex.org/rest'>\n"
				+ "  <text>"+query+"</text>\n"
				+ "  <parameter name='wrap' value='yes'/>\n" 
				+ "</query>";
		
		System.out.println("\n* Query:\n" + request);

		/* Uspostavljanje konekcije za zadati URL */
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		/* Tip konekcije je OUTPUT */
		conn.setDoOutput(true);
		/* Postavljenje POST HTTP request metode */
		conn.setRequestMethod(RequestMethod.POST);
		/* Postavljanje Content-Type u header-u HTTP request-a */
		conn.setRequestProperty("Content-Type", "application/query+xml");

		// Get and cache output stream
		OutputStream out = conn.getOutputStream();

		/* Sam query koji se salje serveru je UTF-8 encode-iran */
		out.write(request.getBytes("UTF-8"));
		out.close();

		/* Response kod vracen od strane servera */
		int responseCode = RESTUtil.printResponse(conn);

		T retVal=null;
		/* Ako je sve proslo kako treba... */
		if (responseCode == HttpURLConnection.HTTP_OK) {
			/* Prikazi vraceni XML fragment */
			System.out.println("\n* Result:");
			try {
				BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
				GenericXWSUnmarshaller<T> gxum = new GenericXWSUnmarshaller<T>(type, in);
				retVal=gxum.unmarshall();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/* Obavezno zatvaranje tekuce konekcije */
		conn.disconnect();
		return retVal;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public T getType() {
		return type;
	}

	public void setType(T type) {
		this.type = type;
	}
}
