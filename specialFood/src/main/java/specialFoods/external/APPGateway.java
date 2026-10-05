package specialFoods.external;

import specialFoods.dto.ProductDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Component
public class APPGateway implements IProductSellerGateway {
	
	// Constants for socket server connection
	@Value("${app.socket.server.port}")
	private int PORT;
	@Value("${app.socket.server.ip}")
	private String IP;
    private static final String DELIMITER = "#"; 
    private static final String GET_PRODUCTS_CMD = "GET_PRODUCTS"; 

    @Override
    public List<ProductDTO> getProducts() {
    	
    	// Obtaining configuration from aplication.properties
    	int serverPort = PORT;
    	String serverIP = IP;
    	
    	// Connect to the socket server and request products
        List<ProductDTO> products = new ArrayList<>();
        String response = null;

        try (Socket socket = new Socket(serverIP, serverPort);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            // Send request to server
            String message = GET_PRODUCTS_CMD;
            out.writeUTF(message);
            System.out.println(" - APPGateway: Sending command to " + serverIP + ":" + serverPort + " -> '" + message + "'");

            // Read the response from server
            response = in.readUTF();
            System.out.println(" - APPGateway: Received response -> '" + response + "'");
            
            // Process the response to extract products
            products = parseSocketResponse(response);

        } catch (UnknownHostException e) {
            System.err.println("# APPGateway: Unknown Host: " + serverIP);
        } catch (IOException e) {
            System.err.println("# APPGateway: IO Error when connecting to socket: " + e.getMessage());
        }

        return products;
    }

    // Parse the socket response and convert it to a list of ProductDTO
    // Response format expect: OK#jsonArrayOfProducts
    private List<ProductDTO> parseSocketResponse(String response) {
        if (response == null || !response.startsWith("OK" + DELIMITER)) {
            System.err.println("# APPGateway: Invalid or Error response from socket server. Response: " + response);
            return new ArrayList<>();
        }

        // Get the data (the JSON)
        String jsonProducts = response.substring(("OK" + DELIMITER).length());

        try {
            ObjectMapper mapper = new ObjectMapper();
            
            // Deserialize the JSON directly to a List<ProductDTO>
            List<ProductDTO> products = mapper.readValue( jsonProducts, new TypeReference<List<ProductDTO>>() {});
            
            //Add the company name to each product
            for (ProductDTO product : products) {
				product.setCompany("APP");
			}
            
            return products;
            
        } catch (Exception e) {
            System.err.println("# APPGateway: Error parsing JSON response: " + e.getMessage());
            System.err.println("# APPGateway: JSON received: " + jsonProducts);
            return new ArrayList<>();
        }
    }
    
} 