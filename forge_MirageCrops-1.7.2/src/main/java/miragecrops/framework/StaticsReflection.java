package miragecrops.framework;

import java.lang.reflect.Field;

public class StaticsReflection
{

	/**
	 * 例外が発生する場合、それをRuntimeExceptionで包み即座にスローする。
	 */
	public static void setStaticFieldValue(Class<?> clazz, String name, Object value)
	{
		try {
			Field field = clazz.getField(name);
			field.set(null, value);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 例外が発生する場合、それをRuntimeExceptionで包み即座にスローする。
	 */
	public static Object getStaticFieldValue(Class<?> clazz, String name)
	{
		try {
			Field field = clazz.getField(name);
			return field.get(null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
