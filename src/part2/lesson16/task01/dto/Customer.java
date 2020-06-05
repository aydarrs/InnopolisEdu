package part2.lesson16.task01.dto;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson16.task01.application.OnlineShop;

import java.util.Objects;


/**
 * Customer.
 * @author Aydar_Safiullin
 */
public class Customer {
    private static final Logger logger = LogManager.getLogger(Customer.class);
    private String name;
    private String phoneNumber;
    private String address;
    private long id;

    public Customer() {};

    public Customer(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void connect() {
        OnlineShop onlineShop = new OnlineShop(this);
        logger.log(Level.DEBUG, "Подключен новый клиент {}", name);
        onlineShop.useByClient();
        logger.log(Level.DEBUG, "Клиент {} покинул приложение", name);
    }

    public Customer(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s\n" +
                "id: %d\n" +
                "Телефон: %s\n" +
                "Аддрес: %s\n",
                name, id, phoneNumber, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
