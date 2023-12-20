package info.u_team.u_team_test.test_multiloader.screen;

import info.u_team.u_team_core.gui.elements.ScrollableList;

public class BasicTestList extends ScrollableList<BasicTestListEntry> {
	
	public BasicTestList(int x, int y, int width, int height) {
		// super(x, y, width, height, 20, 15); TODO
		super(y, width, height, 20, 15);
		setRenderHeader(false, 0);
		setRenderBackground(false);
		setRenderTransparentBorder(true);
		setTransparentBorderSize(10);
		
		for (int index = 0; index < 50; index++) {
			addEntry(new BasicTestListEntry());
		}
	}
	
}
