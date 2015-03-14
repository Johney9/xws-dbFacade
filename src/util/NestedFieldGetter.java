package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class used for finding a nested field, and returning it's value using the fields getter method.
 * @author Nikola Mihalek
 *
 * @param <T> class type
 */
public class NestedFieldGetter {
	
	protected Object target;
	protected Object retVal;
	
	/**
	 * Constructor
	 * @param target object that will have its key extracted
	 */
	public NestedFieldGetter(Object target) {
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
	public Object findField(Object target, String...methodNames) {
		
		for(String name : methodNames) {
			try {
				try {
					Method m = target.getClass().getDeclaredMethod(name);
					retVal = m.invoke(target);
					break;
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
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
						} catch (IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e1) {
							//move on
						}
					}
				}
			}
		}
		
		return retVal;
		
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
}
