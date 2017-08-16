package info.u_team.u_team_core.intern.client;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * Generates a broken Minecraft-style twos-complement signed hex digest. Tested and confirmed to match vanilla.<br>
 * <br>
 * Use with permissions under the following conditions:<br>
 * The MIT License (MIT)<br>
 * Copyright (c) 2016-2017 Aesen "unascribed" Vismea <aesen@unascribed.com><br>
 * <a href="https://gist.github.com/unascribed/70e830d471d6a3272e3f">https://gist.github.com/unascribed/70e830d471d6a3272e3f<a>
 * 
 * @author Aesen "unascribed" Vismea
 */
public class SHA1 {
	
	public static String hash(String str) {
		try {
			byte[] digest = digest(str, "SHA-1");
			return new BigInteger(digest).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static byte[] digest(String string, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest messagedigest = MessageDigest.getInstance(algorithm);
		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
		return messagedigest.digest(bytes);
	}
}
