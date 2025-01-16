package miragecrops.framework;

import java.lang.reflect.Field;

public class StaticsReflection
{

	/**
	 * ��O����������ꍇ�A�����RuntimeException�ŕ�ݑ����ɃX���[����B
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
	 * ��O����������ꍇ�A�����RuntimeException�ŕ�ݑ����ɃX���[����B
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
