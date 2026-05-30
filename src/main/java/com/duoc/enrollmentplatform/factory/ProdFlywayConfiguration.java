package com.duoc.enrollmentplatform.factory;

import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@Profile("prod")
public class ProdFlywayConfiguration {

    private final DataSource dataSource;

    public ProdFlywayConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public FlywayMigrationStrategy prodFlywayMigrationStrategy() {
        return flyway -> {
            disableParallelDmlForOracle();
            reconcileSchemaHistoryWhenTablesMissing();
            flyway.repair();
            flyway.migrate();
        };
    }

    private void disableParallelDmlForOracle() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("ALTER SESSION DISABLE PARALLEL DML");
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not configure Oracle session for Flyway", exception);
        }
    }

    private void reconcileSchemaHistoryWhenTablesMissing() {
        if (coursesTableExists()) {
            return;
        }
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM \"flyway_schema_history\"");
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not reset Flyway history for remigration", exception);
        }
    }

    private boolean coursesTableExists() {
        String sql = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'COURSES'";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not verify courses table on Oracle", exception);
        }
    }
}
