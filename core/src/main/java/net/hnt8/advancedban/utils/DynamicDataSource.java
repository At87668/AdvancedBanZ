package net.hnt8.advancedban.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.hnt8.advancedban.MethodInterface;
import net.hnt8.advancedban.Universal;
import java.io.File;
import java.io.IOException;

public class DynamicDataSource {
    private HikariConfig config = new HikariConfig();

    public DynamicDataSource(boolean preferMySQL) throws ClassNotFoundException {
        MethodInterface mi = Universal.get().getMethods();
        if (preferMySQL) {
            String ip = mi.getString(mi.getMySQLFile(), "MySQL.IP", "Unknown");
            String dbName = mi.getString(mi.getMySQLFile(), "MySQL.DB-Name", "Unknown");
            String usrName = mi.getString(mi.getMySQLFile(), "MySQL.Username", "Unknown");
            String password = mi.getString(mi.getMySQLFile(), "MySQL.Password", "Unknown");
            String properties = mi.getString(mi.getMySQLFile(), "MySQL.Properties", "verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=utf8");
            int port = mi.getInteger(mi.getMySQLFile(), "MySQL.Port", 3306);

            Class.forName("com.mysql.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?"+properties);
            config.setUsername(usrName);
            config.setPassword(password);
        } else {
            // Use SQLite for local storage by default
            String driverClassName = "org.sqlite.JDBC";

            // Ensure plugin data folder /data exists and DB file is created
            File dataFolder = mi.getDataFolder();
            File dataDir = new File(dataFolder, "data");
            if (!dataDir.exists()) {
                boolean ok = dataDir.mkdirs();
                if (!ok) {
                    throw new ClassNotFoundException("Could not create data directory: " + dataDir.getAbsolutePath());
                }
            }

            File dbFile = new File(dataDir, "storage.db");
            try {
                if (!dbFile.exists()) {
                    dbFile.createNewFile();
                }
            } catch (IOException e) {
                Universal.get().getLogger().severe("Failed to create SQLite database file: " + dbFile.getAbsolutePath());
                throw new ClassNotFoundException("Failed to create sqlite db file", e);
            }

            Class.forName(driverClassName);
            config.setDriverClassName(driverClassName);
            // store database as file 'storage.db' inside plugin data folder
            String jdbcPath = dbFile.getAbsolutePath().replace('\\', '/');
            config.setJdbcUrl("jdbc:sqlite:" + jdbcPath);
            // SQLite does not require username/password
        }
    }

    public HikariDataSource generateDataSource(){
        return new HikariDataSource(config);
    }
}
