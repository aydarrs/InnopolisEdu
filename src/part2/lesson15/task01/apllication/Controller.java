package part2.lesson15.task01.apllication;

import part2.lesson15.task01.Customer;
import part2.lesson15.task01.Good;
import part2.lesson15.task01.dao.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller.
 * Realization of application logic.
 * @author Aydar_Safiullin
 */
public class Controller {
    private Customer customer;
    GeneralDao<Good> goodsBase;
    GeneralDao<Good> deliveryBase;
    GeneralDao<Customer> customersBase;
    // Имитация корзины покупок.
    List<Good> customerBasket = new ArrayList<>();
    List<Customer> customersList = new ArrayList<>();

    protected Controller() {};

    protected Controller(Customer customer) {
        this.customer = customer;
        goodsBase = new GoodsDaoImpl();
        deliveryBase = new DeliveryDaoImpl(customer);
        customersBase = new CustomersDaoImpl();
    }

    /**
     * Show all items of "goods" table.
     */
    protected void viewCatalog() {
        viewTable(goodsBase);
    }

    /**
     * Show all items of "delivery" table.
     */
    protected void viewDeliveryList() {
        viewTable(deliveryBase);
    }

    /**
     * Show all items of "customers" table.
     */
    protected void viewCustomers() {
        viewTable(customersBase);
    }

    /**
     * Add good's in consumer basket.
     * @param vendorCode
     */
    protected void choiceGood(int vendorCode) {
        customerBasket.add(goodsBase.getItemById(vendorCode));
    }

    /**
     * Add bought goods at "delivery" table.
     * Add new customer at "customers" table.
     */
    protected void pay () {
        // Добавляем товары из корзины в список доставки.
        deliveryBase.addNewItem(customerBasket);
        // Очищаем корзину.
        customerBasket.clear();
        if (!customersList.contains(customer)) {
            customersList.add(customer);
        }
        // Добавляем нового покупателя в базу.
        customersBase.addNewItem(customersList);
        customersList.clear();
    }

    /**
     * Used to increase the quantity of available goods.
     * @param vendorCode increased good.
     */
    protected void purchaseGood(int vendorCode) {
        goodsBase.updateItemById(vendorCode, -10);
    }

    /**
     * Used to update customer info.
     * @param id - id in "customers" table.
     * @param phoneNumber - editable parameter.
     */
    protected void updateCustomer(int id, int phoneNumber) {
        customersBase.updateItemById(id, phoneNumber);
    }

    /**
     * Clear "delivery" table.
     */
    protected void delivery() {
        // Вытаскиваем все id из списка доставки.
        List<Integer> vendorCodes = deliveryBase.getDatabaseItemsIdList();
        // Доставляем довар и убираем его из списка доставки.
        for (Integer id : vendorCodes) {
            System.out.println("Доставлен товар:");
            deliveryBase.getItemById(id);
            deliveryBase.deleteItemById(id);
        }
    }

    /**
     * Renew database.
     */
    protected void reset() {
        goodsBase.reset();
    }

    /**
     * Show a table items.
     * @param generalDao - implementation of dao-interface.
     */
    private void viewTable(GeneralDao generalDao) {
        // Просто выводим всю инфу о таблице.
        List<Integer> vendorCodes = generalDao.getDatabaseItemsIdList();
        vendorCodes.stream()
                .map((x) -> generalDao.getItemById(x))
                .forEach(System.out::println);
    }
}
