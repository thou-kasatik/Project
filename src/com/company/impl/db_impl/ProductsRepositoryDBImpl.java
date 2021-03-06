package com.company.impl.db_impl;

import com.company.SQLSettings.DBConnectionProvider;
import com.company.models.Product;
import com.company.repositories.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepositoryDBImpl implements ProductRepository {
    private static final String TABLE_NAME = "Products";
    private static final String VENDORCODE_COLUMN_NAME = "vendorCode";
    private static final String PRESENCE_COLUMN_NAME = "presence";
    private static final String TECHNICTYPE_COLUMN_NAME = "technicType";
    private static final String BRAND_COLUMN_NAME = "brand";
    private static final String MODEL_COLUMN_NAME = "model";
    private static final String PRICE_COLUMN_NAME = "price";

    private final DBConnectionProvider dbProvider;

    public ProductsRepositoryDBImpl(DBConnectionProvider dbProvider) {
        this.dbProvider = dbProvider;
    }

    @Override
    public List<Product> getAll() {
        final String query = String.format("SELECT * FROM %s;", TABLE_NAME);
        List<Product> products = new ArrayList<>();
        try (Connection c = dbProvider.getConnection()) {
            PreparedStatement q = c.prepareStatement(query);
            ResultSet rslt = q.executeQuery();
            while (rslt.next()) {
                int vC = rslt.getInt(VENDORCODE_COLUMN_NAME);
                int presence = rslt.getInt(PRESENCE_COLUMN_NAME);
                String type = rslt.getString(TECHNICTYPE_COLUMN_NAME);
                String brand = rslt.getString(BRAND_COLUMN_NAME);
                String model = rslt.getString(MODEL_COLUMN_NAME);
                int price = rslt.getInt(PRICE_COLUMN_NAME);

                products.add(new Product(vC, presence != 0, type, brand, model, price));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;

    }

    @Override
    public Product getByVendorCode(int reqVendorCode) {
        final String query = String.format("SELECT * FROM %s WHERE %s == ?;", TABLE_NAME, VENDORCODE_COLUMN_NAME);
        try (Connection c = dbProvider.getConnection()) {
            PreparedStatement q = c.prepareStatement(query);
            q.setInt(1, reqVendorCode);
            ResultSet rslt = q.executeQuery();
            while (rslt.next()) {
                int vendorCode = rslt.getInt(VENDORCODE_COLUMN_NAME);
                int presence = rslt.getInt(PRESENCE_COLUMN_NAME);
                String type = rslt.getString(TECHNICTYPE_COLUMN_NAME);
                String brand = rslt.getString(BRAND_COLUMN_NAME);
                String model = rslt.getString(MODEL_COLUMN_NAME);
                int price = rslt.getInt(PRICE_COLUMN_NAME);
                return new Product(vendorCode, presence != 0, type, brand, model, price);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getByName(String brand, String model) {
        final String query = String.format("SELECT * FROM %s WHERE %s == ? AND %s == ?;", TABLE_NAME, BRAND_COLUMN_NAME, MODEL_COLUMN_NAME);
        try (Connection c = dbProvider.getConnection()) {
            PreparedStatement q = c.prepareStatement(query);
            q.setString(1, brand);
            q.setString(2, model);
            ResultSet rslt = q.executeQuery();
            while (rslt.next()) {
                int vC = rslt.getInt(VENDORCODE_COLUMN_NAME);
                int presence = rslt.getInt(PRESENCE_COLUMN_NAME);
                String type = rslt.getString(TECHNICTYPE_COLUMN_NAME);
                String b = rslt.getString(BRAND_COLUMN_NAME);
                String m = rslt.getString(MODEL_COLUMN_NAME);
                int price = rslt.getInt(PRICE_COLUMN_NAME);
                return new Product(vC, presence != 0, type, b, m, price);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(Product p) {
        final String query = String.format("INSERT INTO %s VALUES(?,?,?,?,?,?);", TABLE_NAME);
        try (Connection c = dbProvider.getConnection()) {
            PreparedStatement q = c.prepareStatement(query);
            q.setInt(1, p.getVendorCode());
            q.setInt(2, p.isPresence() ? 1 : 0);
            q.setString(3, p.getTechnicType());
            q.setString(4, p.getBrand());
            q.setString(5, p.getModel());
            q.setInt(6, p.getPrice());
            q.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Product p) {
        final String query = String.format("UPDATE %s SET %s =?, %s=?, %s=?, %s=?, %s=? WHERE %s=?;", TABLE_NAME, PRESENCE_COLUMN_NAME, TECHNICTYPE_COLUMN_NAME, BRAND_COLUMN_NAME, MODEL_COLUMN_NAME, PRICE_COLUMN_NAME, VENDORCODE_COLUMN_NAME);
        try (Connection c = dbProvider.getConnection()) {
            PreparedStatement q = c.prepareStatement(query);
            q.setInt(1, p.isPresence() ? 1 : 0);
            q.setString(2, p.getTechnicType());
            q.setString(3, p.getBrand());
            q.setString(4, p.getModel());
            q.setInt(5, p.getPrice());
            q.setInt(6, p.getVendorCode());
            q.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void delete(Product p) {
        final String query = String.format("DELETE FROM %s WHERE %s=?;", TABLE_NAME, VENDORCODE_COLUMN_NAME);
        try (Connection c = dbProvider.getConnection()) {
            PreparedStatement q = c.prepareStatement(query);
            q.setInt(1, p.getVendorCode());
            q.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void copy(Product p, Product k) {

    }
}
