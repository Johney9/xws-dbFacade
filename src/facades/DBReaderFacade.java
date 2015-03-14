package facades;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import util.converter.GenericXWSUnmarshaller;

import com.xmldb.rest.RESTUtil;

/**
 * Class used for unmarshalling objects from the database
 * @author Nikola
 *
 * @param <T> class that is being unmarshalled
 */
public class DBReaderFacade<T> {
	
	protected String fileName;
	protected T type;
	protected String schemaName;
	
	/**
	 * Constructor
	 * @param type class type
	 * @param fileName typed in XPath format { "(//XmlRootElement/XmlLeafElement)"+"attribute() = value" }
	 * @param schemaName the database schema's name
	 */
	public DBReaderFacade(T type, String fileName, String schemaName) {
		this.fileName = fileName;
		this.type = type;
		this.schemaName = schemaName;
	}

	/**
	 * Read from the database
	 * @return read object, of type {@code T}
	 * @throws JAXBException, IOException
	 * @throws Exception exception probably thrown by {@code RESTUtil.class}
	 */
	public T read() throws JAXBException, IOException, Exception {
		T retVal = null;
		
		BufferedInputStream in = new BufferedInputStream(RESTUtil.retrieveSpecificResource(fileName, schemaName));
		
		GenericXWSUnmarshaller<T> gxum = new GenericXWSUnmarshaller<T>(type, in);
		retVal=gxum.unmarshall();
		in.close();

		return retVal;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public T getType() {
		return type;
	}

	public void setType(T type) {
		this.type = type;
	}
}
