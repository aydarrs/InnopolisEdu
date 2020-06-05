package part2.lesson16.task01.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson16.task01.dto.Customer;
import part2.lesson16.task01.dto.Good;
import part2.lesson16.task01.dao.CustomersDaoImpl;
import part2.lesson16.task01.dao.DeliveryDaoImpl;
import part2.lesson16.task01.dao.GeneralDao;
import part2.lesson16.task01.dao.GoodsDaoImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller.
 * Realization of application logic.
 *
 * @author Aydar_Safiullin
 */
public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private Customer customer;
    private GeneralDao<Good> goodsBase;
    private GeneralDao<Good> deliveryBase;
    private GeneralDao<Customer> customersBase;
    // Имитация корзины покупок.
    private List<Good> customerBasket = new ArrayList<>();

    protected Controller() {
    }

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
        String msg = viewTable(goodsBase);
        logger.info(msg);
    }

    /**
     * Show all items of "delivery" table.
     */
    protected void viewDeliveryList() {
        String msg = viewTable(deliveryBase);
        logger.info(msg);
    }

    /**
     * Show all items of "customers" table.
     */
    protected void viewCustomers() {
        String msg = viewTable(customersBase);
        logger.info(msg);
    }

    /**
     * Add good's in consumer basket.
     * @param id of good.
     * @param quantity of good.
     */
    protected void choiceGood(int id, int quantity) {
        Good good = goodsBase.getItemById(id);
        good.setQuantity(quantity);
        customerBasket.add(good);
    }

    /**
     * Add bought goods at "delivery" table.
     * Add new customer at "customers" table.
     */
    protected void pay() {
        // Добавляем нового покупателя в базу.
        if (!isCustomerAdd(customer)) {
            customersBase.addNewItem(customer);
        }
        // Добавляем товары из корзины в список доставки.
        for (Good good : customerBasket) {
            deliveryBase.addNewItem(good);
        }
        // Очищаем корзину.
        customerBasket.clear();
    }

    /**
     * Add new good into "goods" table.
     * @param good - added good.
     */
    protected void restock(Good good) {
        goodsBase.addNewItem(good);
    }

    /**
     * Update "goods" table.
     * @param good - edited good with new properties.
     */
    protected void updateGood(Good good) {
        goodsBase.updateItemByObject(good);
    }

    /**
     * Update "delivery" table.
     * @param good - edited good with new properties.
     */
    protected void updateDelivery(Good good) {
        deliveryBase.updateItemByObject(good);
    }

    /**
     * Update "customers" table.
     * @param customer - edited customer with new properties.
     */
    protected void updateCustomer(Customer customer) {
        customersBase.updateItemByObject(customer);
    }

    /**
     * Clear "delivery" table.
     */
    protected void delivery() {
        // Вытаскиваем все id из списка доставки.
        List<Integer> vendorCodes = deliveryBase.getDatabaseItemsIdList();
        // Доставляем довар и убираем его из списка доставки.
        for (Integer id : vendorCodes) {
            deliveryBase.deleteItemById(id);
        }
    }

    /**
     * Renew database.
     */
    protected void reset() {
        goodsBase.reset();
        logger.warn("База перезагружена");
    }

    /**
     * @param dao - implementation of dao-interface.
     * @return String format of table.
     */
    private String viewTable(GeneralDao dao) {
        // Просто выводим всю инфу о таблице.
        List<Integer> vendorCodes = dao.getDatabaseItemsIdList();
        StringBuilder builder = new StringBuilder();

        // Для доставки отдельное строковое представление.
        // Для остальных таблиц строковое представление соответствует их объектам
        if (!dao.getTableName().equals("delivery")) {
            for (int id : vendorCodes) {
                builder.append(dao.getItemById(id))
                .append("================================================================\n");
            }
            return builder.toString();
        }

        for (int id : vendorCodes) {
            builder.append("Доставка № ")
                    .append(id)
                    .append("\n")
                    .append(deliveryBase.getItemById(id).toStringFromDelivery())
                    .append("\n================================================================\n");
        }
        return builder.toString();

    }

    /**
     * Cheek is customer added yet.
     * @param customer - cheeked customer.
     * @return true if customer added, else return false.
     */
    private boolean isCustomerAdd(Customer customer) {
        List<Customer> customers = new ArrayList<>();
        // Получаем все id из базы клиентов.
        List<Integer> customersId = customersBase.getDatabaseItemsIdList();
        // Получаем всех клиентов из базы
        for (int id : customersId) {
            customers.add(customersBase.getItemById(id));
        }
        // Проверяем, есть ли там наш клиент
        return customers.contains(customer);
    }
}
