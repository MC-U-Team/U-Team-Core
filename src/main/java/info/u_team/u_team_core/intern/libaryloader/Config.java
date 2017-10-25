package info.u_team.u_team_core.intern.libaryloader;

import java.util.HashMap;

public class Config {
	
	static String jnaplatform = "http://central.maven.org/maven2/net/java/dev/jna/jna-platform/4.2.2/jna-platform-4.2.2.jar";
	static String jna = "http://central.maven.org/maven2/net/java/dev/jna/jna/4.2.2/jna-4.2.2.jar";
	static String oshicore = "http://central.maven.org/maven2/com/github/oshi/oshi-core/3.4.0/oshi-core-3.4.0.jar";
	static String slf4japi = "http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar";
	
	private static HashMap<String, String> map;
	
	static {
		map = new HashMap<>();
		map.put(jnaplatform, getName(jnaplatform));
		map.put(jna, getName(jna));
		map.put(oshicore, getName(oshicore));
		map.put(slf4japi, getName(slf4japi));
	}
	
	public static HashMap<String, String> getDownloadMap() {
		return map;
	}
	
	private static String getName(String link) {
		String[] array = link.split("/");
		return array[array.length - 1];
	}
	
}
