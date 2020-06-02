package part2.lesson15.task01.dao;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GeneralDaoImpl.
 * Super class for Dao model implementations.
 * @author Aydar_Safiullin
 */
public abstract class GeneralDaoImpl<T> implements GeneralDao<T> {
    private static final String URL = "jdbc:postgresql://localhost:5432/OnlineShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qwerty";
    protected String tableName;

    /**
     * Set parameters for RowSet.
     * @param rowSet - customized RowSet.
     * @throws SQLException
     */
    protected final void rowSetCustomize(RowSet rowSet) throws SQLException {
        rowSet.setUrl(URL);
        rowSet.setUsername(USER);
        rowSet.setPassword(PASSWORD);
    }

    /**
     * Connect to database.
     * @return Connection to database.
     * @throws SQLException
     */
    protected final Connection traditionalConnectToDB() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public boolean deleteItemById(long id) {
        String sqlCommand = String.format("DELETE FROM %s WHERE id = %d", tableName, id);
        boolean result = false;

        try (Connection connection = traditionalConnectToDB()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlCommand);
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Add a new objects id's in list.
     * @param idOfAddedObjects - list for generated id's.
     * @param statement - statement of connection.
     * @throws SQLException
     */
    protected void getGeneratedKeys(List<Long> idOfAddedObjects, PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            while (generatedKeys.next()) {
                idOfAddedObjects.add(generatedKeys.getLong(1));
            }
        }
    }

    /**
     * @return all id's from table.
     */
    public List<Integer> getDatabaseItemsIdList() {
        String sqlCommand = "SELECT * FROM " + tableName;
        List<Integer> itemsId = new ArrayList<>();

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSetCustomize(rowSet);
            rowSet.setCommand(sqlCommand);
            rowSet.execute();

            while (rowSet.next()) {
                itemsId.add(rowSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsId;
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS goods\n;" +
                        "DROP TABLE IF EXISTS delivery\n;" +
                        "DROP TABLE IF EXISTS customers\n;" +
                        "CREATE TABLE goods (id BIGSERIAL PRIMARY KEY, type VARCHAR(100) NOT NULL, manufacturer VARCHAR(100) NOT NULL,\n" +
                        "model VARCHAR(100) NOT NULL, price INTEGER NOT NULL, quantity INTEGER);\n" +
                        "INSERT INTO goods (type, manufacturer, model, price, quantity) VALUES\n" +
                        "('Notebook', 'Lenovo', 'ThinkPad', 90000, 5), ('Mobile', 'Iphone', '11', 90000, 10),\n" +
                        "('TV', 'Samsung', 'EV1234', 40000, 9), ('Mobile', 'Samsung', 'Galaxy', 80000, 15),\n" +
                        "('Notebook', 'HP', 'Pavilion', 40000, 12), ('Mobile', 'Samsung', 'A50', 13000, 30);\n" +
                        "CREATE TABLE delivery (id BIGSERIAL PRIMARY KEY, customer VARCHAR(100) NOT NULL, id_from_goods INTEGER NOT NULL,\n" +
                        "quantity INTEGER NOT NULL);\n" +
                        "CREATE TABLE customers (id BIGSERIAL PRIMARY KEY, name varchar(50) NOT NULL, phone varchar(20) NOT NULL);\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

