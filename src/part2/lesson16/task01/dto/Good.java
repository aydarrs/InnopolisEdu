package part2.lesson16.task01.dto;

import java.util.Objects;

/**
 * Good.
 * Product description class.
 * @author Aydar_Safiullin
 */
public class Good {
    private String type;
    private String manufacturer;
    private String model;
    private int price;
    private int quantity;
    private long id;
    private Customer customer;

    public Good(String type, String manufacturer, String model) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public Good(String customerName, long id, int quantity) {
        this.customer = new Customer(customerName);
        this.id = id;
        this.quantity = quantity;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return String.format("Наименование товара: %s фирмы %s модели %s\n" +
                        "Артикул: %d\n" +
                        "Цена: %d\n" +
                        "Количество %d\n",
                type, manufacturer, model, id, price, quantity);
    }

    public String toStringFromDelivery() {
        return String.format("Клиент: %s\n" +
                        "Артикул: %d\n" +
                        "Количество: %d\n",
                customer.getName(), id, quantity);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return type.equals(good.type) &&
                manufacturer.equals(good.manufacturer) &&
                model.equals(good.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, manufacturer, model);
    }
}
