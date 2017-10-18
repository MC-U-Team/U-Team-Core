package info.u_team.u_team_core.render;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Logger;

import info.u_team.u_team_core.intern.UCoreConstants;

/**
 * Draw class for a higher level drawing 
 * 
 * @author MrTroble
 * @date 17.9.2017
 *
 */

public class GLTG {

	private BufferEntry entry;
	private Logger log;
	
	public GLTG() {
		this.entry = BufferEntry.getBufferEntry();
		this.log = UCoreConstants.LOGGER;
	}
	
	/**
	 * 
	 * @
	 * 
	 */
	public void drawManual(@Nonnull float[][] vertex, float[][] color, float[][] tex, float[][] normal) {
		if(entry == null) {
			log.error("BufferEntry in GLTG drawManual is null");
			return;
		}
		if(vertex == null) {
			log.error("Vertex array in GLTG drawManual is null");
			return;
		}
		DrawFormat form;
		if(color == null) {
			if(tex == null) {
				log.error("Invalide DrawFormat tex must not be null if color is");
				return;
			}
			if(normal == null) {
				form = DrawFormat.POS_TEX;
			} else {
				form = DrawFormat.POS_TEX_NORMAL;
			}
		} else if(tex == null) {
			form = DrawFormat.POS_COLOR;
		} else if(normal == null) {
			form = DrawFormat.POS_TEX_COLOR;
		} else {
			form = DrawFormat.POS_TEX_COLOR_NORMAL;
		}
		entry.start(form);
		int i = -1;
		for(float[] vert : vertex) {
			i++;
			try {
				entry.vertex(vert[0], vert[1], vert[2]);
				if(color != null && (form == DrawFormat.POS_COLOR || form == DrawFormat.POS_TEX_COLOR || form == DrawFormat.POS_TEX_COLOR_NORMAL)) {
					entry.color(color[i][0], color[i][1], color[i][2], color[i][3]);
				}
				if(tex != null && (form == DrawFormat.POS_TEX || form == DrawFormat.POS_TEX_COLOR || form == DrawFormat.POS_TEX_NORMAL || form == DrawFormat.POS_TEX_COLOR_NORMAL)) {
					entry.tex(tex[i][0], tex[i][1]);
				}
				if(normal != null && (form == DrawFormat.POS_TEX_NORMAL || form == DrawFormat.POS_TEX_COLOR_NORMAL)) {
					entry.tex(tex[i][0], tex[i][1]);
				}
				entry.end();
			} catch (ArrayIndexOutOfBoundsException e) {
				log.error("Error in draw of Pylogen \n Maybe wrong verticies :/", e);
			}
		}
		entry.endDraw();
	}

	
}
