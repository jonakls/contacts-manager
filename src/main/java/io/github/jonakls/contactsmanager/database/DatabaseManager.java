package io.github.jonakls.contactsmanager.database;

import com.zaxxer.hikari.HikariDataSource;
import io.github.jonakls.contactsmanager.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public abstract class DatabaseManager {

    protected final Logger logger = LogManager.getLogger(DatabaseManager.class);
    protected final ConfigManager configManager = ConfigManager.get();
    protected HikariDataSource dataSource;

    public abstract void connect();

    public abstract void disconnect();

    public void executeQuery(
            final String query,
            final ResultSetConsumer resultConsumer
    ) {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(query)) {

            resultConsumer.accept(resultSet);

        } catch (SQLException e) {
            this.logger.error("Error executing query: {}", query, e);
        }
    }

    public void executeQuery(
            final String query,
            final ResultSetConsumer resultConsumer,
            final Object... params
    ) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            this.setParameters(preparedStatement, params);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                resultConsumer.accept(resultSet);
            }

        } catch (SQLException e) {
            this.logger.error("Error executing query: {}", query, e);
        }
    }

    public int executeUpdate(
            final String query,
            final Object... params
    ) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            this.setParameters(preparedStatement, params);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            this.logger.error("Error executing update: {}", query, e);
        }
        return 0;
    }

    public int executeUpdate(
            final String query
    ) {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {

            return statement.executeUpdate(query);

        } catch (SQLException e) {
            this.logger.error("Error executing update: {}", query, e);
        }
        return 0;
    }

    public void executeBatchUpdate(
            final String query,
            final Consumer<PreparedStatement> batchFiller,
            final int batchSize
    ) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            for (int i = 0; i < batchSize; i++) {
                batchFiller.accept(preparedStatement);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            this.logger.error("Error executing batch update: {}", query, e);
        }
    }

    private void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }

    @FunctionalInterface
    public interface ResultSetConsumer {
        void accept(ResultSet rs) throws SQLException;
    }
}
