package info.u_team.u_team_core.gui.elements;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

public class USlider extends Slider {
	
	protected static ISlider EMTPY_SLIDER = slider -> {
	};
	
	protected static ITooltip EMPTY_TOOLTIP = UButton.EMPTY_TOOLTIP;
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, EMTPY_SLIDER);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, ISlider slider) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, slider, EMPTY_TOOLTIP);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, ITooltip tooltip) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, EMTPY_SLIDER, tooltip);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, ISlider slider, ITooltip tooltip) {
		super(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, UButton.EMTPY_PRESSABLE, slider);
		onTooltip = tooltip;
	}
	
	public void setSlider(ISlider slider) {
		parent = slider;
	}
	
	public void setSlider(Runnable runnable) {
		parent = slider -> runnable.run();
	}
	
	public void setTooltip(ITooltip tooltip) {
		onTooltip = tooltip;
	}
	
}
