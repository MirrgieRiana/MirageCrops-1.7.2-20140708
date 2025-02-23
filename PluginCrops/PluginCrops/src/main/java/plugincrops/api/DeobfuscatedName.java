package plugincrops.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
	ElementType.FIELD,
	ElementType.METHOD,
	ElementType.TYPE
})
@Retention(RetentionPolicy.CLASS)
public @interface DeobfuscatedName
{

	public String value();

}
