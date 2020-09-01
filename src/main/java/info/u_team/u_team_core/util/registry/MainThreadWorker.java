package info.u_team.u_team_core.util.registry;

@Deprecated
public class MainThreadWorker {
	
	@Deprecated
	public static void run(Runnable runnable) {
		net.minecraftforge.fml.DeferredWorkQueue.runLater(runnable);
	}
	
}
