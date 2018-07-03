package info.u_team.u_team_core.util;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.util.NonNullList;

public class NonNullListCustom<E> extends NonNullList<E> {
	
	public NonNullListCustom(List<E> delegate) {
		this(delegate, null);
	}
	
	public NonNullListCustom(List<E> delegate, @Nullable E defaulttype) {
		super(delegate, defaulttype);
	}
	
}
