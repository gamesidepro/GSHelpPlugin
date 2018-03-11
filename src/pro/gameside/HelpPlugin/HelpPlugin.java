package pro.gameside.HelpPlugin;

import com.sun.webkit.plugin.PluginManager;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pro.gameside.Events.PlayerEventListener;

/**
 *
 * @author redap
 */
public class HelpPlugin extends JavaPlugin implements Listener {
    public static HashMap<String, Long> fg_cooldown = new HashMap();
    private String host,database,table,user,pass;
    private int port;
    public static Connection connection;
    
    @Override
    public void onEnable() {
        // Загрузка конфигурации / создание / инициализация бд
        
        /* Слушаем ивенты */
        getServer().getPluginManager().registerEvents(new PlayerEventListener(this),
                                        this);
        
        /* Настраиваем конфиг файл */
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists()){
            getConfig().addDefault("gameplay.alwaysday.toggle", false);
            getConfig().addDefault("gameplay.alwaysday.worlds", "world,hotv");
            getConfig().addDefault("gameplay.norain.toggle", false);
            getConfig().addDefault("gameplay.norain.worlds", "world,hotv");
            getConfig().addDefault("gameplay.nohunger.toggle", false);
            getConfig().addDefault("gameplay.nohunger.worlds", "world,hotv");
            getConfig().addDefault("gameplay.nofalldamage.toggle", false);
            getConfig().addDefault("gameplay.nofalldamage.worlds", "world,hotv");
            getConfig().addDefault("gameplay.creative.toggle", false);
            getConfig().addDefault("gameplay.creative.worlds", "world,hotv");
            getConfig().addDefault("gameplay.fly.toggle", false);
            getConfig().addDefault("gameplay.fly.worlds", "world,hotv");
            getConfig().addDefault("gameplay.nomobs.monster", false);
            getConfig().addDefault("gameplay.nomobs.animal", false);
            getConfig().addDefault("gameplay.nomobs.worlds", "world,hotv");
            
            getConfig().addDefault("fungun.toggle", false);
            getConfig().addDefault("fungun.worlds", "world,hotv");
            getConfig().addDefault("fungun.cooldown", 3);
            getConfig().addDefault("fungun.slot", 4);
            getConfig().addDefault("fungun.displayname", "§6§lПУШКА-ПЕРДУШКА");
            
            getConfig().addDefault("database.host", "localhost");
            getConfig().addDefault("database.port", "3306");
            getConfig().addDefault("database.user", "root");
            getConfig().addDefault("database.password", "");
            getConfig().addDefault("database.database", "minigames");
            getConfig().addDefault("database.table", "pex_requires");
            getConfig().addDefault("database.worlds", "world,hotv");
            
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        
        /* Подключаемся к базе */
        
        this.host = getConfig().getString("database.host");
        this.port = getConfig().getInt("database.port");
        this.user = getConfig().getString("database.user");
        this.pass = getConfig().getString("database.password");
        this.database = getConfig().getString("database.database");
        this.table = getConfig().getString("database.table");
        
        try {     
            openConnection();   
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HelpPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getLogger().info("[GSHelper] Плагин включен.");
        
    }
    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.pass);
        }
    }
    
    @Override
    public void onDisable() {
        getLogger().info("[GSHelper] Плагин выключен.");
    }
}
