package part2.lesson16.task01.application;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson16.task01.dto.Customer;

/**
 * Admin.
 * Administrator implementation.
 * @author Aydar_Safiullin
 */
public class Admin extends Customer {
    private static final Logger logger = LogManager.getLogger(Admin.class);
    @Override
    public void connect() {
        logger.log(Level.DEBUG, "Админ подключен");
        OnlineShop onlineShop = new OnlineShop(this);
        onlineShop.useByAdmin();
        logger.log(Level.DEBUG, "Админ покинул приложение");
    }
}
