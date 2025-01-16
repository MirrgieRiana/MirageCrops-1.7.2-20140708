package miragecrops.framework.material;

public enum EnumMachineTier
{
	unusable(null),
	veryVeryCheap("Tier-02"),
	veryCheap("Tier-01"),
	cheap("Tier00"),
	normal("Tier01"),
	advanced("Tier02"),
	veryAdvanced("Tier03"),
	veryVeryAdvanced("Tier04"), ;

	private static final EnumMachineTier[] values = values();
	public final String tierSuffix;

	private EnumMachineTier(String tierSuffix)
	{
		this.tierSuffix = tierSuffix;
	}

	public EnumMachineTier[] getCompatibleTiers()
	{
		EnumMachineTier[] tiers = new EnumMachineTier[ordinal()];

		for (int i = 0; i < tiers.length; i++) {
			tiers[i] = values[i + 1];
		}

		return tiers;
	}

}
