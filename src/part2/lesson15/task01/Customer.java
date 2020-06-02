package part2.lesson15.task01;

import part2.lesson15.task01.apllication.OnlineShop;

import java.util.Objects;


/**
 * Customer.
 * @author Aydar_Safiullin
 */
public class Customer {
    private String name;
    private long phoneNumber;
    private long id;

    public Customer() {};

    public Customer(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Клиент %s\n" +
                "id: %d\n" +
                "Телефон: %d\n" +
                "_________________________________________", name, id, phoneNumber);
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

    public static void main(String[] args) {
        Customer customer = new Customer("Vasily", 3451123);
        OnlineShop onlineShop = new OnlineShop(customer);
        onlineShop.useByClient();
    }
}
