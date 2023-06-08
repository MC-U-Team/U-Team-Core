package info.u_team.u_team_core.impl.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.client.EntityRendererRegister;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public abstract class CommonEntityRendererRegister implements EntityRendererRegister {
	
	protected final Map<Supplier<? extends EntityType<?>>, EntityRendererProvider<?>> entityProviders;
	protected final Map<Supplier<? extends BlockEntityType<?>>, BlockEntityRendererProvider<?>> blockEntityProviders;
	
	protected CommonEntityRendererRegister() {
		entityProviders = new HashMap<>();
		blockEntityProviders = new HashMap<>();
	}
	
	@Override
	public <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> supplier, EntityRendererProvider<T> provider) {
		entityProviders.put(supplier, provider);
	}
	
	@Override
	public <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> supplier, BlockEntityRendererProvider<T> provider) {
		blockEntityProviders.put(supplier, provider);
	}
	
}
