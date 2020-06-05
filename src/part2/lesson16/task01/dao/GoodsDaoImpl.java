package part2.lesson16.task01.dao;

import org.apache.logging.log4j.Level;
import part2.lesson16.task01.dto.Good;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

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
    public long addNewItem(Good item) {
        // Список сгенерерованных ключей.
        long id = 0;
        String sqlCommand = String.format("INSERT INTO %s VALUES (DEFAULT, ?, ?, ?, ?, ?)", tableName);

        try (Connection connection = traditionalConnectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, item.getType());
                statement.setString(2, item.getManufacturer());
                statement.setString(3, item.getModel());
                statement.setInt(4, item.getPrice());
                statement.setInt(5, item.getQuantity());
                statement.executeUpdate();

                // Получаем сгенерированный ключ.
                id = getGeneratedKey(statement);
                logger.log(Level.INFO, "Добавлен новый элемент id {} в таблицу {}", id, tableName);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Ошибка при добавлении элемента в таблицу", e);
        }
        return id;
    }

    @Override
    public Good getItemById(long id) {
        Good good = null;
        String sqlCommand = "SELECT * FROM " + tableName;

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
                    // На основе полученной инфрмации формируем товар.
                    good = new Good(type, manufacturer, model);
                    good.setPrice(price);
                    good.setQuantity(quantity);
                    good.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Ошибка при получении элемента из таблицы", e);
        }
        return good;
    }

    @Override
    public boolean updateItemByObject(Good item) {
        String sqlCommand = "SELECT * FROM " + tableName;
        boolean result = false;

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setConcurrency(RowSet.CONCUR_UPDATABLE);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                if (rowSet.getInt("id") == item.getId()) {
                    rowSet.updateString("type", item.getType());
                    rowSet.updateString("manufacturer", item.getManufacturer());
                    rowSet.updateString("model", item.getModel());
                    rowSet.updateInt("price", item.getPrice());
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
