package part2.lesson15.task01.dao;

import part2.lesson15.task01.Customer;
import part2.lesson15.task01.Good;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DeliveryDaoImpl.
 * Dao implementation for work with "delivery" table.
 * @author Aydar_Safiullin
 */
public class DeliveryDaoImpl extends GeneralDaoImpl<Good> {
    private Customer customer;
    GeneralDao<Good> generalDao = new GoodsDaoImpl();

    public DeliveryDaoImpl(Customer customer) {
        tableName = "delivery";
        this.customer = customer;
    }

    @Override
    public List<Long> addNewItem(List<Good> items) {
        List<Long> idOfAddedObjects = new ArrayList<>();
        String sqlCommand = "INSERT INTO delivery VALUES (DEFAULT, ?, ?, ?)";

        try (Connection connection = traditionalConnectToDB()) {
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint();
            try(PreparedStatement statement = connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
                for (Good good : items) {
                    statement.setString(1, customer.getName());
                    statement.setLong(2, good.getId());
                    statement.setInt(3, 1);
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();

                // Добавляем сгенерированный ключи в список.
                getGeneratedKeys(idOfAddedObjects, statement);

                // После добавления в таблицу доставки, декрементируем кол-во
                // данного товара в общей таблице товаров.
                // Если не получается, то откатываемся в начало.
                for (Good good : items) {
                    if (!generalDao.updateItemById(good.getId(), 1)) {
                        connection.rollback(savepoint);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idOfAddedObjects;
    }

    @Override
    public Good getItemById(long id) {
        Good toDeliveryGood = null;
        String sqlCommand = "SELECT * FROM delivery";

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt("id") == id) {
                    String customerName = rowSet.getString("customer");
                    int vendorCode = rowSet.getInt("id_from_goods");
                    int quantity = rowSet.getInt("quantity");
                    System.out.println(String.format("Доставка №%d клиенту %s, артикул товара %d, количество %d",
                            id, customerName, vendorCode, quantity));
                    // При доставке товара из базы доставки учитываем только количество доставляемого.
                    // Остальную информацию о товаре берем из каталога
                    Good catalogGood = generalDao.getItemById(vendorCode);
                    toDeliveryGood = new Good(catalogGood.getType(), catalogGood.getManufacturer(), catalogGood.getModel());
                    toDeliveryGood.setPrice(catalogGood.getPrice());
                    toDeliveryGood.setQuantity(quantity);
                    toDeliveryGood.setId(vendorCode);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDeliveryGood;
    }


    @Override
    public boolean updateItemById(long id, int variableValue) {
        // В этом методе изменяем количество доставленного товара.
        String sqlCommand = "SELECT * FROM goods";
        boolean result = false;

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setConcurrency(RowSet.CONCUR_UPDATABLE);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt("id") == id) {
                    int newQuantity = rowSet.getInt("quantity") - variableValue;
                    rowSet.updateInt("quantity", newQuantity);
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
