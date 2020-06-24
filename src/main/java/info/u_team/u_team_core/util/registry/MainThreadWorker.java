package info.u_team.u_team_core.util.registry;

public class MainThreadWorker {
	
	@SuppressWarnings("deprecation")
	public static void run(Runnable runnable) {
		net.minecraftforge.fml.DeferredWorkQueue.runLater(runnable);
	}
	
}
