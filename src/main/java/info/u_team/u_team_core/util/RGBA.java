package info.u_team.u_team_core.util;

/**
 * Basic rgba representation of a color with some conversion methods
 *
 * @author HyCraftHD
 */
public class RGBA {
	
	public static final RGBA BLACK = new RGBA(0x000000FF);
	public static final RGBA WHITE = new RGBA(0xFFFFFFFF);
	
	private final int red, green, blue, alpha;
	
	private final int colorRGBA;
	private final int colorARGB;
	
	/**
	 * Creates a new RGBA object from a color integer
	 *
	 * @param color hex code of color e.g. 0xFFFFFFFF for white
	 */
	public RGBA(int color) {
		red = (color >> 24 & 255);
		green = (color >> 16 & 255);
		blue = (color >> 8 & 255);
		alpha = (color & 255);
		colorRGBA = color;
		colorARGB = ((alpha & 0x0ff) << 24) | ((red & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (blue & 0x0ff);
	}
	
	/**
	 * Creates a new RGBA object from the color components in range from 0 to 255 integers.
	 *
	 * @param red Red component
	 * @param green Green component
	 * @param blue Blue component
	 * @param alpha Alpha component
	 */
	public RGBA(int red, int green, int blue, int alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		colorRGBA = ((this.red & 0x0ff) << 24) | ((this.green & 0x0ff) << 16) | ((this.blue & 0x0ff) << 8) | (this.alpha & 0x0ff);
		colorARGB = ((this.alpha & 0x0ff) << 24) | ((this.red & 0x0ff) << 16) | ((this.green & 0x0ff) << 8) | (this.blue & 0x0ff);
	}
	
	/**
	 * Creates a new RGBA object from the color components in range from 0 to 1 floats.
	 *
	 * @param red Red component
	 * @param green Green component
	 * @param blue Blue component
	 * @param alpha Alpha component
	 */
	public RGBA(float red, float green, float blue, float alpha) {
		this.red = (int) (red * 255);
		this.green = (int) (green * 255);
		this.blue = (int) (blue * 255);
		this.alpha = (int) (alpha * 255);
		colorRGBA = ((this.red & 0x0ff) << 24) | ((this.green & 0x0ff) << 16) | ((this.blue & 0x0ff) << 8) | (this.alpha & 0x0ff);
		colorARGB = ((this.alpha & 0x0ff) << 24) | ((this.red & 0x0ff) << 16) | ((this.green & 0x0ff) << 8) | (this.blue & 0x0ff);
	}
	
	/**
	 * Get red component in range from 0 to 255
	 *
	 * @return Red component
	 */
	public int getRed() {
		return red;
	}
	
	/**
	 * Get green component in range from 0 to 255
	 *
	 * @return Green component
	 */
	public int getGreen() {
		return green;
	}
	
	/**
	 * Get blue component in range from 0 to 255
	 *
	 * @return Blue component
	 */
	public int getBlue() {
		return blue;
	}
	
	/**
	 * Get alpha component in range from 0 to 255
	 *
	 * @return Alpha component
	 */
	public int getAlpha() {
		return alpha;
	}
	
	/**
	 * Get red component in range from 0 to 1
	 *
	 * @return Red component
	 */
	public float getRedComponent() {
		return red / 255F;
	}
	
	/**
	 * Get green component in range from 0 to 1
	 *
	 * @return Green component
	 */
	public float getGreenComponent() {
		return green / 255F;
	}
	
	/**
	 * Get blue component in range from 0 to 1
	 *
	 * @return Blue component
	 */
	public float getBlueComponent() {
		return blue / 255F;
	}
	
	/**
	 * Get alpha component in range from 0 to 1
	 *
	 * @return Alpha component
	 */
	public float getAlphaComponent() {
		return alpha / 255F;
	}
	
	/**
	 * Get the integer (hex) representation of this color in rgba format
	 *
	 * @return Color as an integer
	 */
	public int getColor() {
		return colorRGBA;
	}
	
	/**
	 * Get the integer (hex) representation of this color in argb format
	 *
	 * @return Color as an integer
	 */
	public int getColorARGB() {
		return colorARGB;
	}
	
	/**
	 * Set the red component in range from 0 to 255
	 *
	 * @param red Red component
	 * @return A new RGBA instance with the red value set
	 */
	public RGBA setRed(int red) {
		return new RGBA(red, getGreen(), getBlue(), getAlpha());
	}
	
	/**
	 * Set the green component in range from 0 to 255
	 *
	 * @param green Green component
	 * @return A new RGBA instance with the green value set
	 */
	public RGBA setGreen(int green) {
		return new RGBA(getRed(), green, getBlue(), getAlpha());
	}
	
	/**
	 * Set the blue component in range from 0 to 255
	 *
	 * @param blue Blue component
	 * @return A new RGBA instance with the blue value set
	 */
	public RGBA setBlue(int blue) {
		return new RGBA(getRed(), getGreen(), blue, getAlpha());
	}
	
	/**
	 * Set the alpha component in range from 0 to 255
	 *
	 * @param alpha Alpha component
	 * @return A new RGBA instance with the alpha value set
	 */
	public RGBA setAlpha(int alpha) {
		return new RGBA(getRed(), getGreen(), getBlue(), alpha);
	}
	
	/**
	 * Set the red component in range from 0 to 1
	 *
	 * @param red Red component
	 * @return A new RGBA instance with the red value set
	 */
	public RGBA setRedComponent(float red) {
		return new RGBA((int) (red * 255), getGreen(), getBlue(), getAlpha());
	}
	
	/**
	 * Set the green component in range from 0 to 1
	 *
	 * @param green Green component
	 * @return A new RGBA instance with the green value set
	 */
	public RGBA setGreenComponent(float green) {
		return new RGBA(getRed(), (int) (green * 255), getBlue(), getAlpha());
	}
	
	/**
	 * Set the blue component in range from 0 to 1
	 *
	 * @param blue Blue component
	 * @return A new RGBA instance with the blue value set
	 */
	public RGBA setBlueComponent(float blue) {
		return new RGBA(getRed(), getGreen(), (int) (blue * 255), getAlpha());
	}
	
	/**
	 * Set the alpha component in range from 0 to 1
	 *
	 * @param alpha Alpha component
	 * @return A new RGBA instance with the alpha value set
	 */
	public RGBA setAlphaComponent(float alpha) {
		return new RGBA(getRed(), getGreen(), getBlue(), (int) (alpha * 255));
	}
	
	@Override
	public String toString() {
		return "RGBA [red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + "]";
	}
	
	/**
	 * Returns an {@link RGBA} object from an argb integer. This encoding is used in many minecraft stuff
	 *
	 * @param color ARBA color
	 * @return RGBA object
	 */
	public static RGBA fromARGB(int color) {
		final int red = (color >> 16 & 255);
		final int green = (color >> 8 & 255);
		final int blue = (color & 255);
		final int alpha = (color >> 24 & 255);
		return new RGBA(red, green, blue, alpha);
	}
}
