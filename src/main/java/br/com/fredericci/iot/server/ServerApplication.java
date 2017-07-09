package br.com.fredericci.iot.server;

public class ServerApplication {

	public static void main(String[] args) throws Exception {
		
		Server server = new Server(1985);
		
		server.startAndWait();
		
	}

}
