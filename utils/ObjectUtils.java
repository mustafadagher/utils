package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Mustafa Dagher
 */
public class ObjectUtils {
	/**
	 * takes some properties of an object as a list of Strings each in the format of
	 * ("property name = value") and invokes the target object setters returning the
	 * object with it's values populated
	 * 
	 * @param targetObject
	 * @param objectType
	 * @param properties
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> void populateObjectProperties(T targetObject, Class<T> objectType,
			List<String> properties) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		for (String property : properties) {

			String key = generateMethodName(property);
			String value = property.substring(property.indexOf("=") + 1).trim();

			Method method = objectType.getMethod(key, String.class);
			method.invoke(targetObject, value);
		}
	}
	
	/**
	 * takes a property line and returns the equivalent setter method name of
	 * it's matching object property
	 * 
	 * @param property
	 * @return
	 */
	private static String generateMethodName(String property) {
		String key = property.substring(0, property.indexOf("="))
				.replace(".", "").trim();
		String[] words = key.split(" ");

		StringBuffer sb = new StringBuffer();
		sb.append("set");
		for (String word : words) {
			sb.append(Character.toUpperCase(word.charAt(0))).append(
					word.substring(1));
		}

		return sb.toString().trim();
	}

}
