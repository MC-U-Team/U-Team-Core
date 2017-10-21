package info.u_team.u_team_core.schematic;

import java.io.*;
import java.util.function.BiConsumer;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.util.io.NBTStreamUtil;

/**
 * Schematic API<br>
 * -> Writer
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class USchematicWriter {
	
	private USchematicSaveRegion region;
	private OutputStream stream;
	private BiConsumer<Boolean, Long> consumer;
	
	public USchematicWriter(USchematicSaveRegion region, File file) throws IOException {
		this(region, new FileOutputStream(file));
	}
	
	public USchematicWriter(USchematicSaveRegion region, OutputStream stream) throws IOException {
		this.region = region;
		this.stream = stream;
	}
	
	public USchematicWriter start() {
		startSaver();
		return this;
	}
	
	public USchematicWriter finished(BiConsumer<Boolean, Long> consumer) {
		this.consumer = consumer;
		return this;
	}
	
	private void startSaver() {
		Thread thread = new Thread(() -> {
			boolean success = true;
			long time = System.currentTimeMillis(); // Time measurement
			
			try {
				NBTStreamUtil.writeNBTToStream(region.saveNBT(), stream);
			} catch (IOException ex) {
				UCoreConstants.LOGGER.error("Error while trying to save schematic region.", ex);
				success = false;
			}
			
			consumer.accept(success, System.currentTimeMillis() - time);
		});
		thread.setName("Schematic Saver");
		thread.start();
	}
	
}
