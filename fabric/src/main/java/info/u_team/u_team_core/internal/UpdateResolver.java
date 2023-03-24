package info.u_team.u_team_core.internal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import info.u_team.u_team_core.UCoreMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.hycrafthd.update_checker.UpdateChecker;
import net.hycrafthd.update_checker.UpdateChecker.Mod;
import net.hycrafthd.update_checker.Version;
import net.minecraft.SharedConstants;

public class UpdateResolver {
	
	public static final String URL_ENTRY = UCoreMod.MODID + ":update_url";
	
	public static void load() {
		final List<Mod> mods = FabricLoader.getInstance().getAllMods().stream().map(container -> {
			final ModMetadata metaData = container.getMetadata();
			if (metaData.containsCustomValue(URL_ENTRY)) {
				final String modid = metaData.getId();
				final String currentVersion = metaData.getVersion().getFriendlyString();
				final String updateUrl = metaData.getCustomValue(URL_ENTRY).getAsString();
				
				return Optional.<Mod> ofNullable(new Mod(modid, currentVersion, updateUrl));
			}
			return Optional.<Mod> empty();
		}).flatMap(Optional::stream).collect(Collectors.toList());
		
		UpdateChecker.check(SharedConstants.getCurrentVersion().getName(), mods, string -> {
			try {
				return new Version<net.fabricmc.loader.api.Version>() {
					
					private final net.fabricmc.loader.api.Version version = net.fabricmc.loader.api.Version.parse(string);
					
					@Override
					public int compareTo(Version<?> o) {
						return version.compareTo((net.fabricmc.loader.api.Version) o.getImpl());
					}
					
					@Override
					public net.fabricmc.loader.api.Version getImpl() {
						return version;
					}
					
					@Override
					public String toString() {
						return version.getFriendlyString();
					}
				};
			} catch (final VersionParsingException ex) {
				throw new RuntimeException("Cannot parse version for update checker", ex);
			}
		});
	}
	
}
