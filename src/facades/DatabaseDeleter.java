package facades;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import com.xmldb.rest.RESTUtil;

public class DatabaseDeleter<T> {
	
	protected String fileName;
	protected String schemaName;
	protected T type;
	
	public DatabaseDeleter(String fileName, String schemaName) {
		this.fileName = fileName.trim().toLowerCase();
		this.schemaName = schemaName.trim();
	}
	
	public DatabaseDeleter(T type, String schemaName) {
		this.type=type;
		this.schemaName=schemaName;
		generateFileName();
	}
	
	private void generateFileName() {
		String fullPackageName = type.getClass().getPackage().getName();
		this.fileName = fullPackageName.substring(fullPackageName.lastIndexOf(".")+1);
		this.fileName+=".xml";
	}
	
	public int delete() throws SAXException, JAXBException, IOException, Exception {
		
		int retVal=-1;
		
		if(schemaName==null) throw new IllegalArgumentException("Schema name must be given");
		
		if(fileName==null) {
			retVal = RESTUtil.dropSchema(schemaName);
		} else {
			retVal = RESTUtil.deleteResource(schemaName, fileName);
		}
		
		return retVal;
	}
}