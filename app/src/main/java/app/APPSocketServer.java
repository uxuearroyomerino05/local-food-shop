package app;

import java.io.IOException;
import java.net.ServerSocket;

import app.facade.APPServiceHandler;
import app.service.APPService;

public class APPSocketServer {
	
	private static int SERVER_PORT = Integer.parseInt(System.getProperty("SERVER_PORT"));
	
	public static void main(String args[]) {
		
		// Obtaining the port from a configuration file (build.gradle)
		int serverPort = SERVER_PORT;
		
        // Start the application service and initialize data
        APPService appService = new APPService();
        DataInitializer.initializeData(appService); 
        
        System.out.println(" - APP Socket Server: Initialized products.");

		try (ServerSocket tcpServerSocket = new ServerSocket(serverPort);) {
			System.out.println(" - APP Socket Server: Listening in the port '" + tcpServerSocket.getLocalPort() + "' ...");
			
			while (true) {
				// For each conexion, create a thread (APPServiceHandler)
				new APPServiceHandler(tcpServerSocket.accept(), appService);
				System.out.println(" - New conexion acepted.");
			}
		} catch (IOException e) {
			System.err.println("# APP Socket Server: Error of IO: " + e.getMessage());
		}
	}
}