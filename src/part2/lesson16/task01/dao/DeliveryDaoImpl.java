package part2.lesson16.task01.dao;

import org.apache.logging.log4j.Level;
import part2.lesson16.task01.dto.Customer;
import part2.lesson16.task01.dto.Good;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

/**
 * DeliveryDaoImpl.
 * Dao implementation for work with "delivery" table.
 *
 * @author Aydar_Safiullin
 */
public class DeliveryDaoImpl extends GeneralDaoImpl<Good> {
    private Customer customer;
    GeneralDao<Good> goodsDao = new GoodsDaoImpl();

    public DeliveryDaoImpl(Customer customer) {
        tableName = "delivery";
        this.customer = customer;
    }

    @Override
    public long addNewItem(Good item) {
        long id = 0;
        String sqlCommand = String.format("INSERT INTO %s VALUES (DEFAULT, ?, ?, ?)", tableName);
        int quantity = item.getQuantity();
        item.setCustomer(customer);

        try (Connection connection = traditionalConnectToDB()) {
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint();
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
                // Если в списке доставки еще не было такого товара,
                // то просто добавляем его.
                if (!item.equals(getItemById(item.getId()))) {
                    statement.setString(1, customer.getName());
                    statement.setLong(2, item.getId());
                    statement.setInt(3, item.getQuantity());
                    statement.executeUpdate();
                    connection.commit();

                    // Получаем сгенерированный ключ.
                    id = getGeneratedKey(statement);

                    // После добавления в таблицу доставки, изменяем кол-во
                    // данного товара в общей таблице товаров.
                    // Для этого сперва получаем объект из таблицы товаров.
                    Good editedGood = goodsDao.getItemById(item.getId());
                    // И меняем в нем количество.
                    int newQuantity = editedGood.getQuantity() - quantity;
                    editedGood.setQuantity(newQuantity);
                    // Обновляем информацию в таблице товаров.
                    if (!goodsDao.updateItemByObject(editedGood)) {
                        // Если не получается, то откатываемся в начало.
                        connection.rollback(savepoint);
                    }
                    logger.log(Level.INFO, "Добавлен новый элемент id {} в таблицу {}", id, tableName);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Ошибка при добавлении элемента в таблицу", e);
        }
        return id;
    }

    @Override
    public Good getItemById(long id) {
        Good toDeliveryGood = null;
        String sqlCommand = "SELECT * FROM " + tableName;

        try (JdbcRowSet rowSet = getRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt("id") == id) {
                    String customerName = rowSet.getString("customer");
                    int vendorCode = rowSet.getInt("id_from_goods");
                    int quantity = rowSet.getInt("quantity");
                    // При доставке товара из базы доставки учитываем только количество доставляемого.
                    // Остальную информацию о товаре берем из каталога
                    toDeliveryGood = new Good(customerName, vendorCode, quantity);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Ошибка при получении элемента из таблицы", e);
        }
        return toDeliveryGood;
    }


    @Override
    public boolean updateItemByObject(Good item) {
        String sqlCommand = "SELECT * FROM " + tableName;
        boolean result = false;

        try (JdbcRowSet rowSet = getRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setConcurrency(RowSet.CONCUR_UPDATABLE);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getLong("id_from_goods") == item.getId()) {
                    rowSet.updateString("customer", item.getCustomer().getName());
                    rowSet.updateLong("id_from_goods", item.getId());
                    rowSet.updateInt("quantity", item.getQuantity());
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
