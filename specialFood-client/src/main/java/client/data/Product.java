
package client.data;

public record Product (
	long id,
	String name,
	String description,
	String producerName,
	String producerOrigin,
	double price,
	int availableQuantity,
	String company
) {}
