package plugincrops.coremod;

import java.util.regex.Pattern;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationPluginCrops
{

	public static boolean initialized = false;

	@SideOnly(Side.CLIENT)
	public static class RenderBlockCropsImpl
	{

		public static class CrossingDefault
		{

			private static boolean initialized = false;
			public static Patterns patternsDisplayName;
			public static Patterns patternsAttribute;
			public static Patterns patternsNgDisplayName;
			public static Patterns patternsNgAttribute;

			public static boolean isCrossingDefault(String displayName, String[] attributes)
			{
				if (!initialized) {
					patternsDisplayName.initialize();
					patternsAttribute.initialize();
					patternsNgDisplayName.initialize();
					patternsNgAttribute.initialize();
				}

				return isOk(displayName, attributes) && !isNg(displayName, attributes);
			}

			private static boolean isOk(String displayName, String[] attributes)
			{
				if (patternsDisplayName.anyMatch(displayName)) return true;

				for (String attribute : attributes) {
					if (patternsAttribute.anyMatch(attribute)) return true;
				}

				return false;
			}

			private static boolean isNg(String displayName, String[] attributes)
			{
				if (patternsNgDisplayName.anyMatch(displayName)) return true;

				for (String attribute : attributes) {
					if (patternsNgAttribute.anyMatch(attribute)) return true;
				}

				return false;
			}

			public static class Patterns
			{

				public String[] regexps;
				public Pattern[] patterns;

				public Patterns(String[] regexps)
				{
					this.regexps = regexps;
				}

				public void initialize()
				{
					patterns = new Pattern[regexps.length];
					for (int i = 0; i < regexps.length; i++) {
						patterns[i] = Pattern.compile(regexps[i]);
					}
				}

				public boolean anyMatch(String string)
				{
					for (Pattern pattern : patterns) {
						if (pattern.matcher(string).matches()) return true;
					}
					return false;
				}

			}

		}

	}

	public static enum EnumFix
	{
		growthBoostOfWartsIsDisabled,
		bugsOfAttemptCrossing,
		growthDurationOfStickreed,
		directivityOfWeed,
		under24GrowthOfWeed,
		calculationOfPicking,
		weedExIsNotSaved,
		directivityOfAirQuality,
		renderingApis, ;

		public boolean enabled;

	}

}
