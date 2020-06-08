package part2.lesson16.task01.dao;

import org.junit.Test;
import part2.lesson16.task01.dto.Customer;
import part2.lesson16.task01.dto.Good;

import javax.sql.rowset.JdbcRowSet;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * GeneralDaoTest.
 *
 * @author Aydar_Safiullin
 */

public class GeneralDaoTest {
    Connection connection = mock(Connection.class);;
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);
    JdbcRowSet rowSet = mock(JdbcRowSet.class);
    Savepoint savepoint = mock(Savepoint.class);
    // Здесь использовал не интерфейс, а оригинальный класс объектов,
    // т.к. очень много внутренней логики сделано внутри классов, и она недоступна, если объявить элемент через интерфейс
    GoodsDaoImpl goodsDao = spy(new GoodsDaoImpl());
    DeliveryDaoImpl deliveryDao = spy(new DeliveryDaoImpl(new Customer("Stub", "Stub", "Stub")));
    CustomersDaoImpl customersDao = spy(new CustomersDaoImpl());

    @Test
    public void isAddingNewItemIntoGoodsTableWork() throws SQLException {
        when(goodsDao.traditionalConnectToDB()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(1L);
        Good good = new Good("type", "manufacturer", "model");
        good.setPrice(1);
        good.setQuantity(1);

        long result = goodsDao.addNewItem(good);

        verify(goodsDao, times(1)).traditionalConnectToDB();
        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(statement, times(1)).setString(1, "type");
        verify(statement, times(1)).setString(2, "manufacturer");
        verify(statement, times(1)).setString(3, "model");
        verify(statement, times(1)).setInt(4, 1);
        verify(statement, times(1)).setInt(5, 1);
        verify(statement, times(1)).executeUpdate();
        assertEquals(result, 1L);
    }

    @Test
    public void isAddingNewItemIntoDeliveryTableWork() throws SQLException {
        deliveryDao.goodsDao = mock(GoodsDaoImpl.class);
        when(deliveryDao.traditionalConnectToDB()).thenReturn(connection);
        when(connection.setSavepoint()).thenReturn(savepoint);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(2L);
        Good good = new Good("type", "manufacturer", "model");
        good.setPrice(2);
        good.setQuantity(2);
        when(deliveryDao.goodsDao.getItemById(anyLong())).thenReturn(good);
        when(deliveryDao.getItemById(anyLong())).thenReturn(null);

        long result = deliveryDao.addNewItem(good);

        verify(statement, times(1)).setString(1, "Stub");
        verify(statement, times(1)).setLong(2, good.getId());
        verify(statement, times(1)).setInt(3, 2);
        verify(statement, times(1)).executeUpdate();
        assertEquals(result, 2L);
    }

    @Test
    public void isAddingNewItemIntoCustomersTableWork() throws SQLException {
        when(customersDao.traditionalConnectToDB()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(3L);
        Customer customer = new Customer("Stub", "Stub", "Stub");

        long result = customersDao.addNewItem(customer);

        verify(statement, times(1)).setString(1, "Stub");
        verify(statement, times(1)).setString(2, "Stub");
        verify(statement, times(1)).setString(3, "Stub");
        verify(statement, times(1)).executeUpdate();
        assertEquals(result, 3L);
    }

    @Test
    public void isGettingItemFromGoodsTableWork() {
        when()
    }
}
