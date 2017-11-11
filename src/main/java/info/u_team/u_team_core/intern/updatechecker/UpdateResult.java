package info.u_team.u_team_core.intern.updatechecker;

/**
 * Update API<br>
 * -> Update result
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class UpdateResult {
	
	private UpdateState state;
	private String newversion = null;
	private String download = null;
	
	public UpdateResult() {
		state = UpdateState.UPTODATE;
	}
	
	public UpdateResult(String newversion, String download) {
		state = UpdateState.NEEDUPDATE;
		this.newversion = newversion;
		this.download = download;
	}
	
	public UpdateState getState() {
		return state;
	}
	
	public String getDownload() {
		return download;
	}
	
	public String getNewVersion() {
		return newversion;
	}
	
}
