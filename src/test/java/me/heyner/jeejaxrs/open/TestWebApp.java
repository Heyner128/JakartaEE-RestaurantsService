package me.heyner.jeejaxrs.open;


import me.heyner.jeejaxrs.dao.RestaurantDao;
import me.heyner.jeejaxrs.service.RestaurantService;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.testing.Application;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Configuration;
import org.apache.openejb.testing.Module;

import java.util.Properties;

/**
 * Créée un serveur d'application JEE pour les tests.
 */
@Application
public class TestWebApp {

    @Module
    public PersistenceUnit persistence() {
        PersistenceUnit unit = new PersistenceUnit("DefaultPU");
        unit.setJtaDataSource("RestaurantsTestDatabase");
        unit.setNonJtaDataSource("RestaurantsTestDatabaseUnmanaged");
        unit.setProperty("jakarta.persistence.schema-generation.database.action", "drop-and-create");
        unit.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
        unit.setProperty("openjpa.Log", "DefaultLevel=WARN,Runtime=INFO,Tool=INFO,SQL=TRACE");

        return unit;
    }

    @Module
    @Classes(cdi = true, value = {
            RestaurantService.class, RestaurantDao.class
    })
    public WebApp app() {
        return new WebApp();
    }

    @Configuration
    public Properties configInMemory() throws Exception {
        Properties p = new Properties();
        p.put("RestaurantsTestDatabase", "new://Resource?type=DataSource");
        p.put("RestaurantsTestDatabase.JdbcDriver", "org.h2.Driver");
        p.put("RestaurantsTestDatabase.JdbcUrl", "jdbc:h2:file:~/h2-data/restaurants-test");
        return p;
    }
}
