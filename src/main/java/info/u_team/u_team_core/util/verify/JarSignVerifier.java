package info.u_team.u_team_core.util.verify;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Stopwatch;
import com.google.common.io.ByteStreams;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.CertificateHelper;
import net.minecraftforge.forgespi.language.IModFileInfo;

// TODO evaluate if this is still useful. Forge has now a system for checking the fingerprint too, but makes this
// information not accessible
public class JarSignVerifier {
	
	private static final Logger LOGGER = LogManager.getLogger("JarSignVerifier");
	
	public static void checkSigned(String modid) {
		final Stopwatch watch = Stopwatch.createStarted();
		final VerifyStatus status = verify(modid);
		watch.stop();
		LOGGER.debug("Took {} to check if mod {} is signed.", watch, modid);
		if (status == VerifyStatus.SIGNED) {
			LOGGER.info("Mod " + modid + " is signed with a valid certificate.");
		} else if (status == VerifyStatus.UNSIGNED) {
			LOGGER.warn("---------------------------------------------------------------------------------");
			LOGGER.warn("Mod " + modid + " is not signed with a valid certificate but should be signed.");
			LOGGER.warn("Please download the mod only from trusted sources such as curseforge.com!");
			LOGGER.warn("---------------------------------------------------------------------------------");
		} else if (status == VerifyStatus.DEV) {
			LOGGER.info("Mod " + modid + " is loaded in dev environment.");
		}
	}
	
	public static VerifyStatus verify(String modid) {
		final IModFileInfo info = ModList.get().getModFileById(modid);
		
		// TODO replace with this as this is forge native
		// if (info instanceof ModFileInfo concreteInfo) {
		// LOGGER.info("The code signing fingerprint for {} reported by forge is {}", modid,
		// concreteInfo.getCodeSigningFingerprint().orElse("NULL"));
		//
		// var signers = info.getFile().getSecureJar().getManifestSigners();
		// if (signers != null) {
		// Arrays.stream(signers).forEach(csa -> {
		// System.out.println(CertificateHelper.getFingerprints(csa.getSignerCertPath().getCertificates().toArray(Certificate[]::new)));
		// });
		// }
		//
		// // if (FMLEnvironment.secureJarsEnabled) {
		// // LOGGER.info("The trust data reported by forge is {}", concreteInfo.getTrustData().orElse("NULL"));
		// // }
		// }
		
		// We don't need to check sign in dev environment
		if (!FMLEnvironment.production) {
			return VerifyStatus.DEV;
		}
		
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
			
			try (final Stream<JarEntry> entryStream = jarFile.stream()) {
				// Check sign on every resource excluding directories and the certificate files
				if (entryStream.filter(JarSignVerifier::checkEntryForSign).allMatch(entry -> {
					// Read everything so the certificate gets loaded but trash the input
					try (final InputStream stream = jarFile.getInputStream(entry)) {
						ByteStreams.copy(stream, ByteStreams.nullOutputStream());
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
