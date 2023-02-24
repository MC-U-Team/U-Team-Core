package info.u_team.u_team_core.util;

/**
 * Basic rgb representation of a color with some conversion methods
 *
 * @author HyCraftHD
 */
public class RGB {
	
	public static final RGB BLACK = new RGB(0x000000);
	public static final RGB WHITE = new RGB(ColorUtil.WHITE_RGB);
	
	private final int red, green, blue;
	
	private final int colorRGB;
	
	/**
	 * Creates a new RGB object from a color integer
	 *
	 * @param color hex code of color e.g. 0xFFFFFF for white
	 */
	public RGB(int color) {
		red = (color >> 16 & 255);
		green = (color >> 8 & 255);
		blue = (color & 255);
		colorRGB = color;
	}
	
	/**
	 * Creates a new RGB object from the color components in range from 0 to 255 integers.
	 *
	 * @param red Red component
	 * @param green Green component
	 * @param blue Blue component
	 */
	public RGB(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		colorRGB = ((this.red & 0x0ff) << 16) | ((this.green & 0x0ff) << 8) | (this.blue & 0x0ff);
	}
	
	/**
	 * Creates a new RGB object from the color components in range from 0 to 1 floats.
	 *
	 * @param red Red component
	 * @param green Green component
	 * @param blue Blue component
	 */
	public RGB(float red, float green, float blue) {
		this.red = (int) (red * 255);
		this.green = (int) (green * 255);
		this.blue = (int) (blue * 255);
		colorRGB = ((this.red & 0x0ff) << 16) | ((this.green & 0x0ff) << 8) | (this.blue & 0x0ff);
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
	 * Get the integer (hex) representation of this color in rgb format
	 *
	 * @return Color as an integer
	 */
	public int getColor() {
		return colorRGB;
	}
	
	/**
	 * Set the red component in range from 0 to 255
	 *
	 * @param red Red component
	 * @return A new RGB instance with the red value set
	 */
	public RGB setRed(int red) {
		return new RGB(red, getGreen(), getBlue());
	}
	
	/**
	 * Set the green component in range from 0 to 255
	 *
	 * @param green Green component
	 * @return A new RGB instance with the green value set
	 */
	public RGB setGreen(int green) {
		return new RGB(getRed(), green, getBlue());
	}
	
	/**
	 * Set the blue component in range from 0 to 255
	 *
	 * @param blue Blue component
	 * @return A new RGB instance with the blue value set
	 */
	public RGB setBlue(int blue) {
		return new RGB(getRed(), getGreen(), blue);
	}
	
	/**
	 * Set the red component in range from 0 to 1
	 *
	 * @param red Red component
	 * @return A new RGB instance with the red value set
	 */
	public RGB setRedComponent(float red) {
		return new RGB((int) (red * 255), getGreen(), getBlue());
	}
	
	/**
	 * Set the green component in range from 0 to 1
	 *
	 * @param green Green component
	 * @return A new RGB instance with the green value set
	 */
	public RGB setGreenComponent(float green) {
		return new RGB(getRed(), (int) (green * 255), getBlue());
	}
	
	/**
	 * Set the blue component in range from 0 to 1
	 *
	 * @param blue Blue component
	 * @return A new RGB instance with the blue value set
	 */
	public RGB setBlueComponent(float blue) {
		return new RGB(getRed(), getGreen(), (int) (blue * 255));
	}
	
	@Override
	public String toString() {
		return "RGB [red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}
	
	/**
	 * Returns an {@link RGB} object from an {@link RGBA} object. Alpha value is lost in that conversion.
	 * 
	 * @param rgba RGBA object
	 * @return RGB object
	 */
	public static RGB fromRGBA(RGBA rgba) {
		return new RGB(rgba.getRed(), rgba.getGreen(), rgba.getBlue());
	}
	
}
