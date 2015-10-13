package facades;

import util.NestedFieldValueGetter;

public class IdGeneratorFacade {
	
	//TODO: fix so it actually generates an id
	
	protected Object target;
	protected final String defaultIdKey = "getId";
	protected final String defaultBrojRacunaKey = "getBrojRacuna";

	public IdGeneratorFacade(Object target) {
		this.target=target;
	}
	
	/*
	 * Gets the default id used in the XWS project models
	 */
	public String findIdXWS() {
		NestedFieldValueGetter nfg = new NestedFieldValueGetter();
		String retVal="";
		try {
			retVal = (String) nfg.findField(target, defaultIdKey, defaultBrojRacunaKey);
		}
		catch (ClassCastException cce) {
			System.err.println("Error casting to string! At: " +this.getClass().getSimpleName() + " @ " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		}
		return retVal;
	}
	
	public static String generateIdXWS(String...keys) {
		Integer hashCode = 1;
		for(String key : keys) {
			if(key!=null) hashCode += key.hashCode();
		}
		return hashCode.toString();
	} 

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public String getDefaultIdKey() {
		return defaultIdKey;
	}

	public String getDefaultBrojRacunaKey() {
		return defaultBrojRacunaKey;
	}
}
