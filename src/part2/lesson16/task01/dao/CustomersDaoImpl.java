package part2.lesson16.task01.dao;

import org.apache.logging.log4j.Level;
import part2.lesson16.task01.dto.Customer;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CustomersDaoImpl.
 * Dao implementation for work with "customers" table.
 *
 * @author Aydar_Safiullin
 */
public class CustomersDaoImpl extends GeneralDaoImpl<Customer> {

    public CustomersDaoImpl() {
        tableName = "customers";
    }

    @Override
    public long addNewItem(Customer item) {
        long id = 0;
        if (item.getId() != 0) {
            return 0;
        }
        String sqlCommand = String.format("INSERT INTO %s VALUES (DEFAULT, ?, ?, ?)", tableName);
        try (Connection connection = traditionalConnectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, item.getName());
                statement.setString(2, item.getPhoneNumber());
                statement.setString(3, item.getAddress());
                statement.executeUpdate();

                // Получаем сгенерированный ключ.
                id = getGeneratedKey(statement);
                // Присваиваем id клиенту.
                item.setId(id);
                logger.log(Level.INFO, "Добавлен новый элемент id {} в таблицу {}", id, tableName);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Ошибка при добавлении элемента в таблицу", e);
        }
        return id;
    }

    @Override
    public Customer getItemById(long id) {
        Customer customer = null;
        String sqlCommand = "SELECT * FROM " + tableName;

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt(1) == id) {
                    String name = rowSet.getString("name");
                    String phone = rowSet.getString("phone");
                    String address = rowSet.getString("address");
                    customer = new Customer(name, phone, address);
                    customer.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Ошибка при получении элемента из таблицы", e);
        }
        return customer;
    }

    @Override
    public boolean updateItemByObject(Customer item) {
        String sqlCommand = "SELECT * FROM " + tableName;
        boolean result = false;

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setConcurrency(RowSet.CONCUR_UPDATABLE);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt("id") == item.getId()) {
                    rowSet.updateString("name", item.getName());
                    rowSet.updateString("phone", item.getPhoneNumber());
                    rowSet.updateString("address", item.getAddress());
                    rowSet.updateRow();
                    result = true;
                }
            }
            logger.log(Level.INFO, "Обновлен элемент id {} из таблицы {}", item.getId(), tableName);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Ошибка при внесении изменений в таблицу",e);
        }
        return result;
    }
}
