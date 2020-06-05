package part2.lesson16.task01.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson16.task01.dto.Customer;
import part2.lesson16.task01.dto.Good;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * OnlineShop.
 *
 * @author Aydar_Safiullin
 */
public class OnlineShop {
    private static final Logger logger = LogManager.getLogger(OnlineShop.class);
    private Controller controller;

    public OnlineShop(Customer customer) {
        controller = new Controller(customer);
    }

    /**
     * Client interface.
     */
    public void useByClient() {
        logger.info("Добро пожаловать!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                logger.info("Выберите действие:\n" +
                        "1 - посмотреть каталог\n" +
                        "2 - выбрать товар\n" +
                        "3 - купить\n" +
                        "Любой другой символ - выйти\n");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        logger.info("Просмотр каталога товаров");
                        controller.viewCatalog();
                        break;
                    case "2":
                        logger.info("Выбор товаров");
                        logger.info("Введите артикул товара");
                        int id = Integer.parseInt(reader.readLine());
                        logger.info("Введите нужное количество");
                        int quantity = Integer.parseInt(reader.readLine());
                        controller.choiceGood(id, quantity);
                        break;
                    case "3":
                        logger.info("Оплата покупки");
                        controller.pay();
                        logger.info("Оплата прошла");
                        break;
                    default:
                        logger.info("Спасибо! Ждём Вас снова!");
                        return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Administrator interface.
     */
    protected void useByAdmin() {
        logger.info("Вы вошли в систему");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                logger.info("Выберите действие:\n" +
                        "1 - посмотреть каталог\n" +
                        "2 - посмотреть список доставки\n" +
                        "3 - посмотреть список клиентов\n" +
                        "4 - закупить товар\n" +
                        "5 - изменить информацию о товаре\n" +
                        "6 - изменить информацию о доставке\n" +
                        "7 - изменить информацию о клиенте\n" +
                        "8 - обновить базу данных\n" +
                        "9 - доставить товар\n" +
                        "Любой другой символ - выйти\n");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        logger.info("Просмотр каталога товаров");
                        controller.viewCatalog();
                        break;
                    case "2":
                        logger.info("Просмотр списка доставки");
                        controller.viewDeliveryList();
                        break;
                    case "3":
                        logger.info("Просмотр списка клиентов");
                        controller.viewCustomers();
                        break;
                    case "4":
                        logger.info("Добавление нового товара");
                        Good addGood = createGood(reader);
                        controller.restock(addGood);
                        break;
                    case "5":
                        logger.info("Изменение информации о товаре");
                        logger.info("Выберите изменяемый товар");
                        int goodId = Integer.parseInt(reader.readLine());
                        Good updatedGood = createGood(reader);
                        updatedGood.setId(goodId);
                        controller.updateGood(updatedGood);
                        break;
                    case "6":
                        logger.info("Изменение информации о доставке");
                        logger.info("Введите артикул");
                        int goodIdFromDelivery = Integer.parseInt(reader.readLine());
                        Good deliveryGood = createGood(reader);
                        deliveryGood.setId(goodIdFromDelivery);
                        Customer deliveryCustomer = createCustomer(reader);
                        deliveryGood.setCustomer(deliveryCustomer);
                        controller.updateDelivery(deliveryGood);
                        break;
                    case "7":
                        logger.info("Изменение информации о клиенте");
                        logger.info("Введите id клиента");
                        int customerId = Integer.parseInt(reader.readLine());
                        Customer updateCustomer = createCustomer(reader);
                        updateCustomer.setId(customerId);
                        controller.updateCustomer(updateCustomer);
                        break;
                    case "8":
                        controller.reset();
                        break;
                    case "9":
                        controller.delivery();
                        logger.info("Товары доставлены");
                        break;
                    default:
                        logger.info("Вы покинули в систему");
                        return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Customer createCustomer(BufferedReader reader) throws IOException {
        logger.info("Введите имя клиента");
        String name = reader.readLine();
        logger.info("Введите номер телефона клиента");
        String phoneNumber = reader.readLine();
        logger.info("Введите адресс клиента");
        String address = reader.readLine();
        return new Customer(name, phoneNumber, address);
    }

    private Good createGood(BufferedReader reader) throws IOException {
        logger.info("Введите тип товара");
        String type = reader.readLine();
        logger.info("Введите производителя товара");
        String manufacturer = reader.readLine();
        logger.info("Введите модель товара");
        String model = reader.readLine();
        logger.info("Введите цену товара");
        int price = Integer.parseInt(reader.readLine());
        logger.info("Введите количество товара");
        int quantity = Integer.parseInt(reader.readLine());
        Good good = new Good(type, manufacturer,model);
        good.setPrice(price);
        good.setQuantity(quantity);
        return good;
    }
}
