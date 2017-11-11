package info.u_team.u_team_core.schematic;

import java.io.*;
import java.util.function.BiConsumer;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.util.io.NBTStreamUtil;

/**
 * Schematic API<br>
 * -> Reader
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class USchematicReader {
	
	private USchematicLoadRegion region;
	private InputStream stream;
	private BiConsumer<Boolean, Long> consumer;
	
	public USchematicReader(USchematicLoadRegion region, File file) throws IOException {
		this(region, new FileInputStream(file));
	}
	
	public USchematicReader(USchematicLoadRegion region, InputStream stream) throws IOException {
		this.region = region;
		this.stream = stream;
	}
	
	public USchematicReader start() {
		startLoader();
		return this;
	}
	
	public USchematicReader finished(BiConsumer<Boolean, Long> consumer) {
		this.consumer = consumer;
		return this;
	}
	
	private void startLoader() {
		Thread thread = new Thread(() -> {
			boolean success = true;
			long time = System.currentTimeMillis(); // Time measurement
			
			try {
				region.readNBT(NBTStreamUtil.readNBTFromStream(stream));
			} catch (IOException ex) {
				UCoreConstants.LOGGER.error("Error while trying to load schematic region.", ex);
				success = false;
			}
			
			if (consumer != null) {
				consumer.accept(success, System.currentTimeMillis() - time);
			}
		});
		thread.setName("Schematic Loader");
		thread.start();
	}
	
}
