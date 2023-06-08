package info.u_team.u_team_core.api.registry.client;

import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface EntityRendererRegister {
	
	static EntityRendererRegister create() {
		return Factory.INSTANCE.create();
	}
	
	<T extends Entity> void register(Supplier<? extends EntityType<? extends T>> supplier, EntityRendererProvider<T> provider);
	
	<T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> supplier, BlockEntityRendererProvider<T> provider);
	
	void register();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		EntityRendererRegister create();
	}
	
}
