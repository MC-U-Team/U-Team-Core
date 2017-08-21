package info.u_team.u_team_core.intern.client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class ClientSocket extends Socket {
	
	private final PrintWriter writer;
	private final Scanner scanner;
	
	public ClientSocket() throws Throwable {
		super("localhost", 2553);
		this.writer = new PrintWriter(this.getOutputStream());
		this.scanner = new Scanner(this.getInputStream());
	}
	
	public boolean auth() {
		Session session = Minecraft.getMinecraft().getSession();
		MinecraftSessionService service = Minecraft.getMinecraft().getSessionService();
		try {
			String token = null;
			this.writer.println("auth_username: " + session.getUsername());
			this.writer.flush();
			if (scanner.hasNextLine()) {
				token = scanner.nextLine();
			}
			System.out.println(token);
			if (token != null) {
				Minecraft.getMinecraft().getSessionService().joinServer(Minecraft.getMinecraft().getSession().getProfile(), Minecraft.getMinecraft().getSession().getToken(), SHA1.hash(token));
				writer.println("fin: " + Minecraft.getMinecraft().getSession().getUsername());
				writer.flush();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public PrintWriter getWriter() {
		return writer;
	}
	
}
