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
        try {
            if (session == null || !session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar con la base de datos");
        }
    }

    @Override
    public void disconnect() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        try {
            tx = session.beginTransaction();
            Query<Employee> query = session.createQuery("FROM Employee WHERE employeeId = :id AND password = :password", Employee.class);
            query.setParameter("id", employeeId);
            query.setParameter("password", password);
            Employee employee = query.uniqueResult();
            tx.commit();
            return employee;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Product> getInventory() {
        ArrayList<Product> inventory = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            List<Product> productList = query.list();
            inventory.addAll(productList);
            for (Product product : inventory) {
                product.setWholesalerPrice(new Amount(product.getPrice()));
                product.setPublicPrice(new Amount(product.getPrice() * 2));
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return inventory;
    }

    @Override
    public boolean writeInventory(List<Product> inventory) {
        try {
            tx = session.beginTransaction();
            for (Product product : inventory) {
                session.saveOrUpdate(product);
            }
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addProduct(Product product) {
        try {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addStock(String productName, int additionalStock) {
        try {
            tx = session.beginTransaction();
            Query<Product> query = session.createQuery("FROM Product WHERE name = :name", Product.class);
            query.setParameter("name", productName);
            Product product = query.uniqueResult();
            if (product != null) {
                product.setStock(product.getStock() + additionalStock);
                session.update(product);
                tx.commit();
                return true;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String productName) {
        try {
            tx = session.beginTransaction();
            Query<Product> query = session.createQuery("FROM Product WHERE name = :name", Product.class);
            query.setParameter("name", productName);
            Product product = query.uniqueResult();
            if (product != null) {
                session.remove(product);
                tx.commit();
                return true;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean exportInventoryToHistory(List<Product> inventory) {
        try {
            tx = session.beginTransaction();
            for (Product product : inventory) {
                session.save(new ProductHistory(product));
            }
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
