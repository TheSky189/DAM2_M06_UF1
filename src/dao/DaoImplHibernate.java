package dao;

import model.Amount;
import model.Employee;
import model.Product;
import model.ProductHistory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class DaoImplHibernate implements Dao {
	
	private Session session;
	private Transaction tx;
	
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		org.hibernate.SessionFactory sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
		
	}
	
	
    @Override
    public ArrayList<Product> getInventory() {
    	ArrayList<Product> inventory = new ArrayList<>();
    	try {
    		tx = session.beginTransaction();
    		Query qry = session.createQuery("select product from Product product");
    		List<Product> productList = qry.list();
    		
    		inventory.addAll(productList);
    		
    		for (Product product : inventory) {
    			System.out.println(product);
    			
    			product.setWholesalerPrice(new Amount (product.getPrice()));
    			product.setPublicPrice(new Amount (product.getPrice()*2));
    		}
    		
    		tx.commit();
    		System.out.println("Get Inventory successfully");
    	
    	} catch (HibernateException e) {
    		if (tx != null)
    			tx.rollback();
    		e.printStackTrace();
    	}
    	
    	return inventory;
        
    }

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		try {

			tx = session.beginTransaction();

			session.save(product);

			tx.commit();
			System.out.println("Product inserted Successfully.");
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	
	}

    @Override
    public boolean exportInventoryToHistory(List<Product> inventory) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            for (Product product : inventory) {
//                session.save(new ProductHistory(product.getId(), product.getName(), product.getWholesalerPrice().getValue(), product.isAvailable(), product.getStock()));
//                
//            }
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//            return false;
//        }
    	try {
			tx = session.beginTransaction();

			//we generate and save all new Mark elements
			for (Product product : inventory) {
				ProductHistory inventoryHistory = new ProductHistory(product.getId(), product.getName(), product.getPrice(), product.isAvailable(), product.getStock());
				session.save(inventoryHistory);			
			}
			// we update this information in the Database
			tx.commit();
			System.out.println("Write Inventory Successfully.");
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
			return false;
		}
    }

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		session.close();
	}

	@Override
	public boolean writeInventory(List<Product> inventory) {
		// TODO Auto-generated method stub
		try {
			tx = session.beginTransaction();

			//we generate and save all new Mark elements
			for (Product product : inventory) {
				ProductHistory inventoryHistory = new ProductHistory(product.getId(), product.getName(), product.getPrice(), product.isAvailable(), product.getStock());
				session.save(inventoryHistory);			
			}
			// we update this information in the Database
			tx.commit();
			System.out.println("Write Inventory Successfully.");
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void deleteProduct(String name) {
		// TODO Auto-generated method stub		
		try {
			for (Product product : this.getInventory()) {
				if (product.getName().equals(name)) {
					tx = session.beginTransaction();
					session.remove(product);
					tx.commit();
					System.out.println("Deleted Successfully.");					
				}				
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}

	@Override
	public void addStock(String name, int stock) {
		// TODO Auto-generated method stub
		try {
			for (Product product : this.getInventory()) {
				if (product.getName().equals(name)) {
					product.setStock(product.getStock() + stock);
					tx = session.beginTransaction();
					session.save(product);
					tx.commit();
					System.out.println("Stock added Successfully.");					
				}				
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}
}