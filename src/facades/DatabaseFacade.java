package facades;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

@SuppressWarnings("rawtypes")
public class DatabaseFacade {
	
	protected DatabaseReader dbReader;
	protected DatabaseWriter dbWriter;
	
	public <T> void storeInDatabase(T target) throws JAXBException, IOException, SAXException, Exception {
		dbWriter = new DatabaseWriter<T>(target);
		dbWriter.store();
		dbWriter=null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readFromDatabase(T type, String schemaName) throws JAXBException, IOException, Exception {
		dbReader = new DatabaseReader<T>(type, schemaName);
		T retVal =  (T) dbReader.read();
		dbReader=null;
		return retVal;
	}
}