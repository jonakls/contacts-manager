package io.github.jonakls.contactsmanager.database;

import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

public class MySQLManager extends DatabaseManager {

    private static class MySQLManagerHolder {
        private static final MySQLManager INSTANCE = new MySQLManager();
    }

    public static MySQLManager get() {
        return MySQLManagerHolder.INSTANCE;
    }

    private MySQLManager() {
        // Private constructor to prevent instantiation
    }

    @Override
    public void connect() {
        this.dataSource = new HikariDataSource();
        final Dotenv dotenv = this.configManager.getDotenv();

        this.dataSource.setJdbcUrl(String.format(
                "jdbc:mysql://%s:%s/%s",
                dotenv.get("MYSQL_HOST"),
                dotenv.get("MYSQL_PORT"),
                dotenv.get("MYSQL_DATABASE")
        ));
        this.dataSource.setUsername(dotenv.get("MYSQL_USER"));
        this.dataSource.setPassword(dotenv.get("MYSQL_PASSWORD"));
        this.dataSource.setDriverClassName(dotenv.get("MYSQL_DRIVER"));

        super.logger.info("Connected to MySQL database successfully.");
    }

    @Override
    public void disconnect() {
        if (this.dataSource != null && !this.dataSource.isClosed()) {
            this.dataSource.close();
        }
    }
}
