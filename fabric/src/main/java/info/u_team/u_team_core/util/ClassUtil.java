package info.u_team.u_team_core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.util.ExceptionUtil;

public class ClassUtil {
	
	public static Stream<Path> walkModResources(String modid) {
		final ModContainer container = FabricLoader.getInstance().getModContainer(modid).orElseThrow(() -> new IllegalStateException("Mod " + modid + " is not loaded but expected by annotation scanner"));
		
		return container.getRootPaths().stream() //
				.flatMap(rootPaths -> {
					try {
						return Files.walk(rootPaths);
					} catch (final IOException ex) {
						throw ExceptionUtil.wrap(ex);
					}
				});
	}
	
	public static Set<Path> findModClasses(String modid) {
		return walkModResources(modid) //
				.filter(path -> path.toString().endsWith(".class")) //
				.collect(Collectors.toSet());
	}
	
}
