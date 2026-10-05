package app.facade;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import app.service.APPService;

public class APPServiceHandler extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket tcpSocket;
	private APPService appService;
    
    // Request protocol
    private static final String REQUEST_PRODUCTS = "GET_PRODUCTS"; 

	public APPServiceHandler(Socket socket, APPService appService) {
		this.appService = appService;
		try {
			this.tcpSocket = socket;
		    this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			this.start(); // Start the thread to handle the client
		} catch (IOException e) {
			System.err.println("   # APPServiceHandler - Error de IO al crear streams: " + e.getMessage());
		}
	}

	public void run() {
		try {
			// Recieve the request ("GET_PRODUCTS") from the client
			String data = this.in.readUTF();
			System.out.println("   - APPServiceHandler - Solicitud recibida desde '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");
			
            String response = "";
			
            if (data.equals(REQUEST_PRODUCTS)) {
                // Call the service to get products as JSON
            	response = "OK#";
                response += appService.getProductsAsJson();
                System.out.println("   - APPServiceHandler - Send " + response.length() + " bytes of JSON.");
            } else {
                response = "ERROR#Unknown command";
                System.err.println("   - APPServiceHandler - Unknown comand: " + data);
            }
			
            // Give the response back to the client
			this.out.writeUTF(response); 
			
		} catch (EOFException e) {
			System.err.println("   # APPServiceHandler - EOF error: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("   # APPServiceHandler - IO error: " + e.getMessage());
		} finally {
			try {
				if (tcpSocket != null) {
                    tcpSocket.close();
                }
			} catch (IOException e) {
				System.err.println("   # APPServiceHandler - Error al cerrar socket: " + e.getMessage());
			}
		}
	}
}