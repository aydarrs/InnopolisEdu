package part1.lesson10.task01.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Admin.
 * Class for remote server off.
 * @author Aydar_Safiullin
 */
public class Admin extends Client{
    public static final String ADMIN_NAME = "admin";

    public Admin(String name) {
        super(name);
    }

    public static void main(String[] args) {
        Client client = new Client(ADMIN_NAME);
    }
}
