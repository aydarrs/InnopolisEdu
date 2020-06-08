package part2.lesson16.task01.dao;

import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import part2.lesson16.task01.dto.Customer;
import part2.lesson16.task01.dto.Good;

import javax.sql.rowset.JdbcRowSet;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
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
    public void isGettingItemFromGoodsTableWork() throws SQLException {
        when(goodsDao.getRowSet()).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);
        when(rowSet.getInt("id")).thenReturn(1);

        Good good = goodsDao.getItemById(1);

        verify(rowSet).getString("type");
        verify(rowSet).getString("manufacturer");
        verify(rowSet).getString("model");
        verify(rowSet).getInt("price");
        verify(rowSet).getInt("quantity");
        assertNotNull(good);
    }

    @Test
    public void isGettingItemFromDeliveryTableWork() throws SQLException {
        when(deliveryDao.getRowSet()).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);
        when(rowSet.getInt("id")).thenReturn(1);

        Good good = deliveryDao.getItemById(1);

        verify(rowSet).getString("customer");
        verify(rowSet).getInt("id_from_goods");
        verify(rowSet).getInt("quantity");
        assertNotNull(good);
    }

    @Test
    public void isGettingItemFromCustomersTableWork() throws SQLException {
        when(customersDao.getRowSet()).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);
        when(rowSet.getInt(1)).thenReturn(1);

        Customer customer = customersDao.getItemById(1);

        verify(rowSet).getString("name");
        verify(rowSet).getString("phone");
        verify(rowSet).getString("address");
        assertNotNull(customer);
    }

    @Test
    public void isUpdatingInGoodsTableWork() throws SQLException {
        Good good = new Good("Stub", "Stub", "Stub");
        when(goodsDao.getRowSet()).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);
        when(rowSet.getLong("id")).thenReturn(good.getId());

        boolean result = goodsDao.updateItemByObject(good);

        verify(rowSet).updateString("type", "Stub");
        verify(rowSet).updateString("manufacturer", "Stub");
        verify(rowSet).updateString("model", "Stub");
        verify(rowSet).updateInt("price", 0);
        verify(rowSet).updateInt("quantity", 0);
        verify(rowSet).updateRow();
        assertTrue(result);
    }

    @Test
    public void isUpdatingInDeliveryTableWork() throws SQLException {
        Good good = new Good("Stub", "Stub", "Stub");
        good.setCustomer(new Customer("Stub", "Stub", "Stub"));
        when(deliveryDao.getRowSet()).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);
        when(rowSet.getLong("id_from_goods")).thenReturn(good.getId());

        boolean result = deliveryDao.updateItemByObject(good);

        verify(rowSet).updateString("customer", "Stub");
        verify(rowSet).updateLong("id_from_goods", 0);
        verify(rowSet).updateInt("quantity", 0);
        verify(rowSet).updateRow();
        assertTrue(result);
    }

    @Test
    public void isUpdatingInCustomersTableWork() throws SQLException {
        Customer customer = new Customer("Stub", "Stub", "Stub");
        when(customersDao.getRowSet()).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);
        when(rowSet.getLong("id")).thenReturn(customer.getId());

        boolean result = customersDao.updateItemByObject(customer);

        verify(rowSet).updateString("name", "Stub");
        verify(rowSet).updateString("phone", "Stub");
        verify(rowSet).updateString("address", "Stub");
        verify(rowSet).updateRow();
        assertTrue(result);
    }

}
