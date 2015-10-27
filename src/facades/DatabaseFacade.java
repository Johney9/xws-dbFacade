package facades;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

@SuppressWarnings("rawtypes")
public class DatabaseFacade {
	
	protected DatabaseReader dbReader;
	protected DatabaseWriter dbWriter;
	protected DatabaseDeleter dbDeleter;
	
	public <T> void storeInDatabase(T target) throws JAXBException, IOException, SAXException, Exception {
		dbWriter = new DatabaseWriter<T>(target);
		dbWriter.store();
		dbWriter=null;
	}
	
	public <T> void storeInDatabase(T target, String schemaName) throws JAXBException, IOException, SAXException, Exception {
		dbWriter = new DatabaseWriter<T>(target, schemaName);
		dbWriter.store();
		dbWriter=null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readFromDatabase(T type, String schemaName) throws JAXBException, SAXException, IOException, Exception {
		dbReader = new DatabaseReader<T>(type, schemaName);
		T retVal =  (T) dbReader.read();
		dbReader=null;
		return retVal;
	}
	
	public int removeFromDatabase(String fileName, String schemaName) throws SAXException, JAXBException, IOException, Exception {
		dbDeleter = new DatabaseDeleter(fileName, schemaName);
		int retVal = dbDeleter.delete();
		dbDeleter=null;
		return retVal;
	}
	
	public <T> int removeFromDatabase(T type, String schemaName) throws SAXException, JAXBException, IOException, Exception {
		dbDeleter = new DatabaseDeleter<T>(type, schemaName);
		int retVal = dbDeleter.delete();
		dbDeleter=null;
		return retVal;
	}
}