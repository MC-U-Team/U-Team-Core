package info.u_team.u_team_core.util;

import java.util.*;

import info.u_team.u_team_core.api.IUMetaType;

public class NonNullListUtil {
	
	public static <E> NonNullListCustom<E> create() {
		return new NonNullListCustom<>(new ArrayList<>());
	}
	
	@SafeVarargs
	public static <E> NonNullListCustom<E> from(E defaultelement, E... elements) {
		return new NonNullListCustom<>(Arrays.asList(elements), defaultelement);
	}
	
	public static NonNullListCustom<IUMetaType> createMeta(IUMetaType... elements) {
		return new NonNullListCustom<>(Arrays.asList(elements), null);
	}
	
	public static NonNullListCustom<IUMetaType> createMeta(String... elements) {
		ArrayList<IUMetaType> list = new ArrayList<>();
		for (String element : elements) {
			list.add(new IUMetaType() {
				
				@Override
				public String getName() {
					return element;
				}
			});
		}
		return new NonNullListCustom<>(list, null);
	}
	
}
