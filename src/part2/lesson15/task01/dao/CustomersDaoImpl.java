package part2.lesson15.task01.dao;

import part2.lesson15.task01.Customer;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomersDaoImpl.
 * Dao implementation for work with "customers" table.
 * @author Aydar_Safiullin
 */
public class CustomersDaoImpl extends GeneralDaoImpl<Customer> {

    public CustomersDaoImpl() {
        tableName = "customers";
    }

    @Override
    public List<Long> addNewItem(List<Customer> items) {
        List<Long> idOfAddedObjects = new ArrayList<>();
        String sqlCommand = "INSERT INTO customers VALUES (DEFAULT, ?, ?)";

        try (Connection connection = traditionalConnectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
                for (Customer customer : items) {
                    statement.setString(1, customer.getName());
                    statement.setLong(2, customer.getPhoneNumber());
                    statement.addBatch();
                }
                statement.executeBatch();

                // Добавляем сгенерированный ключи в список.
                getGeneratedKeys(idOfAddedObjects, statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idOfAddedObjects;
    }

    @Override
    public Customer getItemById(long id) {
        Customer customer = null;
        String sqlCommand = "SELECT * FROM customers";

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt(1) == id) {
                    customer = new Customer(rowSet.getString(2), rowSet.getInt(3));
                    customer.setId(id);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean updateItemById(long id, int variableValue) {
        String sqlCommand = "SELECT * FROM customers";
        boolean result = false;

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setConcurrency(RowSet.CONCUR_UPDATABLE);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt(1) == id) {
                    rowSet.updateInt(3, variableValue);
                    rowSet.updateRow();
                    result = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
