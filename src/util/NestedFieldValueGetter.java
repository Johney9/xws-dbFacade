package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EmptyStackException;

import exceptions.MethodFoundException;

/**
 * Utility class used for finding a nested field, and returning its value using the fields getter method.
 * Using depth first
 * @author Nikola Mihalek
 */
public class NestedFieldValueGetter {
	
	protected Object retVal;
	
	protected final String[] methodNames={"getId", "getIdSwiftBanke","getIdPibFirme","getIdNalogaZaPlacanje", "getBrojRacuna", "getIdPoruke"};
	
	/**
	 * Constructor
	 */
	public NestedFieldValueGetter() {
		retVal=null;
	}
	
	/**
	 * 
	 * @param target the object that needs its nested field returned
	 * @param methodNames getter method(s) for the field
	 * @return value of the field, or null if nothing is found
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */	
	public Object findField(Object target, String...methodNames) throws MethodFoundException {
		
		if(methodNames==null) {
			methodNames=this.methodNames;
		}
		
		for(String methodName : methodNames) {
			
			try {
				try {
					Method m = target.getClass().getDeclaredMethod(methodName);
					retVal = m.invoke(target);
					//throw new MethodFoundException(methodName);
				} catch (IllegalAccessException iae) {
					//move on
				}
					catch (InvocationTargetException ite) {
					//move on
				}
					catch (IllegalArgumentException iare) {
					//move on
				}
				
			} catch (NoSuchMethodException e) {
				
				for(Method method : target.getClass().getDeclaredMethods()) {
					
					if(method.getName().startsWith("get")) {
						
							try {
								Object newObject;
								newObject = method.invoke(target);
								if(newObject!=null) {
									newObject=newObject.getClass().cast(newObject);
									findField(newObject, methodNames);
								}
							} catch (IllegalArgumentException e1) {
								// move on
							} catch (IllegalAccessException e1) {
								// move on
							} catch (InvocationTargetException e1) {
								// move on
							}
						
					}
				}
			}
		}
		
		return retVal;
		
	}
}
