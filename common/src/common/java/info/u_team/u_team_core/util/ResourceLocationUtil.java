package info.u_team.u_team_core.util;

import java.util.Comparator;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtil {
	
	public static Comparator<ResourceLocation> nameSpacedComparator() {
		return (first, second) -> {
			final int value = first.getNamespace().compareTo(second.getNamespace());
			return value != 0 ? value : first.getPath().compareTo(second.getPath());
		};
	}
	
}
