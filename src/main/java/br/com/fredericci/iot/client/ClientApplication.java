package br.com.fredericci.iot.client;

import br.com.fredericci.iot.common.Message;

public class ClientApplication {

	public static void main(String[] args) throws Exception {
		
		Client client  = new Client();
		
		client.connect("localhost", 1985);
		
		client.send(new Message("Hi"));
		
		client.send(new Message("This is a simple socket implementation!"));
		
		client.send(new Message("Bye")); //shutown the server
		
		client.close();
		
	}
	
}
