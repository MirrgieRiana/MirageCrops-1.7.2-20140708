do {
	$java_file_name = '';
	{
		version => [
			['build.gradle', $VersionFileEntry::regexps{"build.gradle"}],
			['src/main/resources/mcmod.info', $VersionFileEntry::regexps{"mcmod.info"}],
			($java_file_name ? [$java_file_name, $VersionFileEntry::regexps{"java VERSION"}] : ()),
			['../projects/mirrg.minecraft.bronze/version.txt', qr/()(.*)()/],
		],
		modid => [
			['build.gradle', $VersionFileEntry::regexps{"build.gradle archivesBaseName"}],
			['src/main/resources/mcmod.info', $VersionFileEntry::regexps{"mcmod.info modid"}],
			($java_file_name ? [$java_file_name, $VersionFileEntry::regexps{"java MODID"}] : ()),
		],
		name => [
			['src/main/resources/mcmod.info', $VersionFileEntry::regexps{"mcmod.info name"}],
			($java_file_name ? [$java_file_name, $VersionFileEntry::regexps{"java NAME"}] : ()),
		],
		description => [
			['src/main/resources/mcmod.info', $VersionFileEntry::regexps{"mcmod.info description"}],
		],
	}
}
