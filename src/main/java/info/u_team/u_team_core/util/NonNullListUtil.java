package info.u_team.u_team_core.util;

import java.util.*;

public class NonNullListUtil {
	
	public static <E> NonNullListCustom<E> create() {
		return new NonNullListCustom<>(new ArrayList<>());
	}
	
	@SafeVarargs
	public static <E> NonNullListCustom<E> from(E defaultelement, E... elements) {
		return new NonNullListCustom<>(Arrays.asList(elements), defaultelement);
	}
	
	// public static NonNullListCustom<IMetaType> createMeta(IMetaType... elements)
	// {
	// return new NonNullListCustom<>(Arrays.asList(elements), null);
	// }
	//
	// public static NonNullListCustom<IMetaType> createMeta(String... elements) {
	// ArrayList<IMetaType> list = new ArrayList<>();
	// for (String element : elements) {
	// list.add(new IMetaType() {
	//
	// @Override
	// public String getName() {
	// return element;
	// }
	// });
	// }
	// return new NonNullListCustom<>(list, null);
	// }
	
}
