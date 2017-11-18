package info.u_team.u_team_core.render;

import javax.annotation.Nonnull;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;

/**
 * Entry for the MC standard VertexBuffer
 * 
 * @author MrTroble
 * @date 17.9.2017
 *
 */

public class BufferEntry {
	
	private static BufferEntry INSTANCE;
	
	public static BufferEntry getBufferEntry() {
		if (INSTANCE == null)
			INSTANCE = new BufferEntry();
		return INSTANCE;
	}
	
	private Tessellator tesllator;
	private BufferBuilder buffer;
	
	public BufferEntry() {
		this.tesllator = Tessellator.getInstance();
		this.buffer = this.tesllator.getBuffer();
	}
	
	public BufferEntry vertex(float x, float y, float z) {
		this.buffer.pos(x, y, z);
		return this;
	}
	
	public BufferEntry tex(float u, float v) {
		this.buffer.tex(u, v);
		return this;
	}
	
	public BufferEntry color(float r, float b, float g, float a) {
		this.buffer.color(r, g, b, a);
		return this;
	}
	
	public BufferEntry normal(float x, float y, float z) {
		this.buffer.normal(x, y, z);
		return this;
	}
	
	public BufferEntry start(@Nonnull DrawFormat format) {
		if (format == null) {
			UCoreConstants.LOGGER.error("DrawFormar is null in BufferEntry.start()");
		}
		VertexFormat v = null;
		switch (format) {
		case POS_COLOR:
			v = DefaultVertexFormats.POSITION_COLOR;
			break;
		case POS_TEX:
			v = DefaultVertexFormats.POSITION_TEX;
			break;
		case POS_TEX_COLOR:
			v = DefaultVertexFormats.POSITION_TEX_COLOR;
			break;
		case POS_TEX_COLOR_NORMAL:
			v = DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL;
			break;
		case POS_TEX_NORMAL:
			v = DefaultVertexFormats.POSITION_TEX_NORMAL;
			break;
		}
		if (v == null) {
			UCoreConstants.LOGGER.error("Somthing went wrong with the draw start (Wrong vertex Format)");
		}
		this.buffer.begin(7, v);
		return this;
	}
	
	public void end() {
		this.buffer.endVertex();
	}
	
	public void endDraw() {
		this.tesllator.draw();
	}
}
