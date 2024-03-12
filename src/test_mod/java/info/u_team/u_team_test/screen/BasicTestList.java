package info.u_team.u_team_test.screen;

import info.u_team.u_team_core.gui.elements.ScrollableList;

public class BasicTestList extends ScrollableList<BasicTestListEntry> {
	
	public BasicTestList(int x, int y, int width, int height) {
		super(x, y, width, height, 20, 15);
		func_244606_c(false);
		func_244605_b(false);
		setShouldUseScissor(true);
		setShouldRenderTransparentBorder(true);
		setTransparentBorderSize(10);
		
		for (int i = 0; i < 50; i++) {
			addEntry(new BasicTestListEntry());
		}
	}
	
}
