package solver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class SolverServer {

	private int port;
	private ForkJoinPool tPool;
	private ClientHandler handler;

	public SolverServer(ClientHandler handler,int port){
		this.port = port;
		tPool = new ForkJoinPool();
		this.handler = handler;
	}

	public void start() throws IOException{
		ServerSocket server = new ServerSocket(port);
		Socket aClient = server.accept();

		tPool.invoke(new RecursiveAction() {

			@Override
			protected void compute() {
				try {
					handler.handle(aClient.getInputStream(), aClient.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


}
