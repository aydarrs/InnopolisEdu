package part2.lesson15.task01;

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

    public Good(String type, String manufacturer, String model) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
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

    @Override
    public String toString() {
        return String.format("Наименование товара: %s фирмы %s модели %s\n" +
                        "Артикул: %d\n" +
                        "Цена: %d\n" +
                        "Количество %d\n" +
                        "________________________________________________________________",
                type, manufacturer, model, id, price, quantity);
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
