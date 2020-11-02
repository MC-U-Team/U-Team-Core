package info.u_team.u_team_core.util.verify;

import java.io.InputStream;
import java.nio.file.*;
import java.security.cert.Certificate;
import java.util.Optional;
import java.util.jar.*;
import java.util.stream.Stream;

import org.apache.logging.log4j.*;

import com.google.common.base.Stopwatch;
import com.google.common.io.ByteStreams;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.CertificateHelper;
import net.minecraftforge.fml.loading.*;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

public class JarSignVerifier {
	
	private static final Logger LOGGER = LogManager.getLogger("JarSignVerifier");
	
	public static void checkSigned(String modid) {
		final Stopwatch watch = Stopwatch.createStarted();
		final VerifyStatus status = verify(modid);
		watch.stop();
		LOGGER.debug("Took " + watch.toString() + " to check if mod " + modid + " is signed.");
		if (status == VerifyStatus.SIGNED) {
			LOGGER.info("Mod " + modid + " is signed with a valid certificate.");
		} else if (status == VerifyStatus.UNSIGNED) {
			LOGGER.warn("---------------------------------------------------------------------------------");
			LOGGER.warn("Mod " + modid + " is not signed with a valid certificate but should be signed.");
			LOGGER.warn("Please download the mod only from trusted sources such as curseforge.com!");
			LOGGER.warn("---------------------------------------------------------------------------------");
		} else {
			LOGGER.info("Mod " + modid + " is loaded in dev environment.");
		}
	}
	
	public static VerifyStatus verify(String modid) {
		
		// We don't need to check sign in dev environment
		if (!FMLEnvironment.production) {
			return VerifyStatus.DEV;
		}
		
		final ModFileInfo info = ModList.get().getModFileById(modid);
		
		if (FMLEnvironment.secureJarsEnabled) {
			return verifyWithForge(info);
		} else {
			return verifyWithJarEntry(info);
		}
	}
	
	private static VerifyStatus verifyWithForge(ModFileInfo info) {
		final Optional<String> fingerPrintOptional = getFingerPrint(info.getManifest());
		
		if (!fingerPrintOptional.isPresent()) {
			return VerifyStatus.UNSIGNED;
		}
		
		final String fingerprint = fingerPrintOptional.get();
		
		final Certificate[] certificates = Java9BackportUtils.toStream(info.getCodeSigners()) //
				.flatMap(csa -> csa[0].getSignerCertPath().getCertificates().stream()) //
				.toArray(Certificate[]::new);
		
		if (CertificateHelper.getFingerprints(certificates).stream().filter(fingerprint::equals).findAny().isPresent()) {
			return VerifyStatus.SIGNED;
		} else {
			return VerifyStatus.UNSIGNED;
		}
	}
	
	private static VerifyStatus verifyWithJarEntry(ModFileInfo info) {
		final Path path = info.getFile().getFilePath();
		
		if (Files.isDirectory(path)) {
			return VerifyStatus.DEV;
		}
		
		try (final JarFile jarFile = new JarFile(path.toFile())) {
			
			// Get fingerprint from manifest
			final Optional<String> fingerPrintOptional = getFingerPrint(Optional.ofNullable(jarFile.getManifest()));
			
			if (!fingerPrintOptional.isPresent()) {
				return VerifyStatus.UNSIGNED;
			}
			
			final String fingerprint = fingerPrintOptional.get();
			
			try (Stream<JarEntry> entryStream = jarFile.stream()) {
				// Check sign on every resource excluding directories and the certificate files
				if (entryStream.filter(JarSignVerifier::checkEntryForSign).allMatch(entry -> {
					// Read everything so the certificate gets loaded but trash the input
					try (final InputStream stream = jarFile.getInputStream(entry)) {
						ByteStreams.toByteArray(stream);
					} catch (final Exception ex) {
						return false;
					}
					// Check if finger print is valid
					return CertificateHelper.getFingerprints(entry.getCertificates()).stream().filter(fingerprint::equals).findAny().isPresent();
				})) {
					return VerifyStatus.SIGNED;
				}
			}
			return VerifyStatus.UNSIGNED;
		} catch (final Exception ex) {
			return VerifyStatus.UNSIGNED;
		}
	}
	
	private static boolean checkEntryForSign(JarEntry entry) {
		if (entry.isDirectory()) {
			return false;
		}
		final String name = entry.getName().toUpperCase();
		return !name.endsWith(".SF") && !name.endsWith(".DSA") && !name.endsWith(".EC") && !name.endsWith(".RSA");
	}
	
	private static Optional<String> getFingerPrint(Optional<Manifest> manifest) {
		return manifest.map(Manifest::getMainAttributes).map(attributes -> attributes.getValue("Fingerprint")).map(fingerPrint -> fingerPrint.replace(":", "").toLowerCase());
	}
	
	public static enum VerifyStatus {
		SIGNED,
		UNSIGNED,
		DEV;
	}
	
}
