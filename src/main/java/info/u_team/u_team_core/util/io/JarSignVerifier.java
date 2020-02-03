package info.u_team.u_team_core.util.io;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Optional;
import java.util.jar.*;

import com.google.common.io.ByteStreams;

import net.minecraftforge.fml.common.CertificateHelper;
import net.minecraftforge.fml.loading.*;

public class JarSignVerifier {
	
	public static VerifyStatus verify(String modid) {
		if (FMLLoader.getNaming().equals("mcp")) {
			return VerifyStatus.DEV;
		}
		
		final Path path = LoadingModList.get().getModFileById(modid).getFile().getFilePath();
		
		if (Files.isDirectory(path)) {
			return VerifyStatus.DEV;
		}
		
		try (final JarFile jarFile = new JarFile(path.toFile())) {
			final JarEntry entry = jarFile.getJarEntry(JarFile.MANIFEST_NAME);
			
			// Read everything so the certificate gets loaded but trash the input
			try (final InputStream stream = jarFile.getInputStream(entry)) {
				ByteStreams.toByteArray(stream);
			}
			
			final Optional<String> fingerPrintOptional = Optional.ofNullable(jarFile.getManifest().getMainAttributes().getValue("Fingerprint"));
			
			if (!fingerPrintOptional.isPresent()) {
				return VerifyStatus.UNSIGNED;
			}
			
			final String fingerprint = fingerPrintOptional.get().replace(":", "").toLowerCase();
			
			// Check if finger print is valid
			if (CertificateHelper.getFingerprints(entry.getCertificates()).stream().filter(fingerprint::equals).findAny().isPresent()) {
				return VerifyStatus.SIGNED;
			}
			
			return VerifyStatus.UNSIGNED;
		} catch (Exception ex) {
			return VerifyStatus.UNSIGNED;
		}
	}
	
	public static enum VerifyStatus {
		SIGNED,
		UNSIGNED,
		DEV;
	}
	
}
