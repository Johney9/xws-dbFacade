package facades;

import util.NestedFieldGetter;

public class IdGeneratorFacade {
	
	protected Object target;
	protected final String defaultIdKey = "getIdPoruke";
	protected final String defaultBrojRacunaKey = "getBrojRacuna";

	public IdGeneratorFacade(Object target) {
		this.target=target;
	}
	
	/*
	 * Gets the default id used in the XWS project models
	 */
	public String generateIdXws() {
		NestedFieldGetter nfg = new NestedFieldGetter();
		String retVal="";
		try {
			retVal = (String) nfg.findField(target, defaultIdKey, defaultBrojRacunaKey);
		}
		catch (ClassCastException cce) {
			System.err.println("Error casting to string!");
		}
		return retVal;
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
