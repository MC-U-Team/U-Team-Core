package info.u_team.u_team_core.util.verify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.Locale;
import java.util.Optional;
import java.util.jar.Manifest;
import java.util.stream.Stream;

import org.slf4j.Logger;

import com.google.common.base.Stopwatch;
import com.mojang.logging.LogUtils;

import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.SecureJar.ModuleDataProvider;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.CertificateHelper;
import net.minecraftforge.forgespi.language.IModFileInfo;

public class JarSignVerifier {
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
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
		
		// We don't need to check sign in dev environment
		if (!FMLEnvironment.production) {
			return VerifyStatus.DEV;
		}
		
		final SecureJar jar = info.getFile().getSecureJar();
		final ModuleDataProvider moduleData = jar.moduleDataProvider();
		
		final Optional<String> fingerPrintOptional = getFingerPrint(Optional.ofNullable(moduleData.getManifest()));
		if (fingerPrintOptional.isEmpty()) {
			return VerifyStatus.UNSIGNED;
		}
		final String fingerprint = fingerPrintOptional.get();
		
		try (final Stream<Path> paths = Files.find(jar.getRootPath(), Integer.MAX_VALUE, JarSignVerifier::validPath)) {
			if ((jar.getManifestSigners() == null) || paths.parallel().map(path -> pathSigned(moduleData, path, fingerprint)).anyMatch(signed -> !signed)) {
				return VerifyStatus.UNSIGNED;
			}
			
			return VerifyStatus.SIGNED;
		} catch (final Exception ex) {
			return VerifyStatus.UNSIGNED;
		}
	}
	
	private static boolean validPath(Path path, BasicFileAttributes attributes) {
		if (path.getNameCount() < 0 || !attributes.isRegularFile()) {
			return false;
		}
		final String name = path.getFileName().toString().toUpperCase(Locale.ROOT);
		return !name.endsWith(".SF") && !name.endsWith(".DSA") && !name.endsWith(".EC") && !name.endsWith(".RSA");
	}
	
	private static Optional<String> getFingerPrint(Optional<Manifest> manifest) {
		return manifest.map(Manifest::getMainAttributes).map(attributes -> attributes.getValue("Fingerprint")).map(fingerPrint -> fingerPrint.replace(":", "").toLowerCase(Locale.ROOT));
	}
	
	private static boolean pathSigned(ModuleDataProvider moduleData, Path path, String fingerprint) {
		LOGGER.trace("Check {} for valid signature.", path);
		
		final byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (final IOException ex) {
			return false;
		}
		
		final CodeSigner[] codeSigners = moduleData.verifyAndGetSigners(path.toString(), bytes);
		
		return CertificateHelper.getFingerprints(Stream.of(codeSigners) //
				.flatMap(signers -> signers.getSignerCertPath().getCertificates().stream()) //
				.toArray(Certificate[]::new)) //
				.stream() //
				.filter(fingerprint::equals) //
				.findAny() //
				.isPresent();
	}
	
	public static enum VerifyStatus {
		SIGNED,
		UNSIGNED,
		DEV;
	}
	
}
