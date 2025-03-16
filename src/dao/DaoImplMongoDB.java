package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.inc;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.FindIterable;


import org.bson.Document;

import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao {

	MongoCollection<Document> collection;
	MongoDatabase mongoDatabase;
	
	ObjectId id;
	
	@Override
	public void connect(){
		// TODO Auto-generated method stub
		String uri = "mongodb://localhost:27017/P6UF3M06DAM2";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		mongoDatabase = mongoClient.getDatabase("shop");

	}


	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		collection = mongoDatabase.getCollection("employee");
		Document doc = collection.find(Filters.and(
				Filters.eq("employeeId", employeeId),
				Filters.eq("password", password)
				)).first();
		if (doc != null) {
			return new Employee(
					doc.getInteger("employeeId"),
					doc.getString("name"),
					doc.getString("password")
					);
		}
		return null; 
	}
	

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		collection = mongoDatabase.getCollection("inventory");
		ArrayList<Product> localInventory = new ArrayList<Product>();
		Iterable<Document> document = collection.find();
		for (Document doc : document) {
			
			String name = doc.get("name", String.class);
	        Number priceNumber = doc.get("wholesalerPrice", Document.class).get("value", Number.class);
	        double wholesalerPrice = priceNumber.doubleValue();  // Convertir a double correctamente			boolean available = doc.get("available", Boolean.class);
	        boolean available = doc.getBoolean("available");
	        int stock = doc.get("stock", Integer.class);

			localInventory.add(new Product(name, wholesalerPrice, available, stock));
		}
		return localInventory;
	}


	@Override
	public boolean writeInventory(List<Product> localInventory) {
		try {
			collection = mongoDatabase.getCollection("historical_inventory");
			Document document = null;
			for (Product product : localInventory) {
				document = new Document("id_product", product.getId())
						.append("name", product.getName())
						.append("price", product.getPrice())
						.append("available", product.isAvailable())
						.append("stock", product.getStock())
						.append("created_at", new java.sql.Timestamp(System.currentTimeMillis()));
				collection.insertOne(document);
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public boolean exportInventoryToHistory(List<Product> inventory) {
		// TODO Auto-generated method stub
		//return false;
		return writeInventory(inventory);
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		collection = mongoDatabase.getCollection("inventory");
		Document document = new Document("id", product.getId())
				.append("name", product.getName())
				.append("wholesalerPrice", new Document("value", product.getWholesalerPrice().getValue())
						.append("currency", product.getWholesalerPrice().getCurrency()))
				.append("available", product.isAvailable()).append("stock", product.getStock());
		collection.insertOne(document);
	}

	@Override
	public void deleteProduct(String name) {
		// TODO Auto-generated method stub
		collection = mongoDatabase.getCollection("inventory");
		DeleteResult result = collection.deleteMany(eq("name", name));
		System.out.println("product deleted by name" + result.toString());
	}

	@Override
	public void addStock(String name, int stock) {
		// TODO Auto-generated method stub
		collection = mongoDatabase.getCollection("inventory");
		UpdateResult result = collection.updateOne(eq("name", name), inc("stock", stock));
		System.out.println("product stock updated by name" + result.toString());
	}

}
