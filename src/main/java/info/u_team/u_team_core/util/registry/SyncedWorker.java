package info.u_team.u_team_core.util.registry;

public class SyncedWorker {
	
	@SuppressWarnings("deprecation")
	public static void runOnMainThread(Runnable runnable) {
		net.minecraftforge.fml.DeferredWorkQueue.runLater(runnable);
	}
	
}
