package io.github.jonakls.contactsmanager;

import io.github.jonakls.contactsmanager.database.DatabaseManager;
import io.github.jonakls.contactsmanager.database.MySQLManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactsManagerApp {

    private static final Logger logger = LogManager.getLogger(ContactsManagerApp.class);

    public static void main(String[] args) {
        logger.info("Starting ContactsManagerApp");

        final DatabaseManager databaseManager = MySQLManager.get();
        databaseManager.connect();

        databaseManager.disconnect();

    }
}
