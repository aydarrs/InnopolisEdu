package part2.lesson15.task01.dao;

import part2.lesson15.task01.Good;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GoodsDaoImpl.
 * Dao implementation for work with "goods" table.
 * @author Aydar_Safiullin
 */
public class GoodsDaoImpl extends GeneralDaoImpl<Good> {

    public GoodsDaoImpl() {
        tableName = "goods";
    }

    @Override
    public List<Long> addNewItem(List<Good> items) {
        // Список сгенерерованных ключей.
        List<Long> idOfAddedObjects = new ArrayList<>();
        String sqlCommand = "INSERT INTO goods VALUES (DEFAULT, ?, ?, ?, ?, ?)";

        try (Connection connection = traditionalConnectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
                for (Good good : items) {
                    statement.setString(1, good.getType());
                    statement.setString(2, good.getManufacturer());
                    statement.setString(3, good.getModel());
                    statement.setInt(4, good.getPrice());
                    statement.setInt(5, good.getQuantity());
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
    public Good getItemById(long id) {
        Good good = null;
        String sqlCommand = "SELECT * FROM goods";

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt("id") == id) {
                    // Вытягивыем информацию о товаре.
                    String type = rowSet.getString("type");
                    String manufacturer = rowSet.getString("manufacturer");
                    String model = rowSet.getString("model");
                    int price = rowSet.getInt("price");
                    int quantity = rowSet.getInt("quantity");
                    // На основе полученной инфы формируем товар.
                    good = new Good(type, manufacturer, model);
                    good.setPrice(price);
                    good.setQuantity(quantity);
                    good.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return good;
    }

    @Override
    public boolean updateItemById(long id, int variableValue) {
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
                    // Если количество товаров меньше запрошенного, кидаем исключение.
                    if (newQuantity < 0) {
                        throw new SQLException("Отсутствует требуемое количество товаров");
                    }
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
