package pro.gameside.HelpPlugin;

import com.sun.webkit.plugin.PluginManager;
import java.io.File;
import java.util.HashMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pro.gameside.Events.PlayerEventListener;

/**
 *
 * @author redap
 */
public class HelpPlugin extends JavaPlugin implements Listener {
    public static HashMap<String, Long> fg_cooldown = new HashMap();
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
        getLogger().info("[GSHelper] Плагин включен.");
        
    }
    
    @Override
    public void onDisable() {
        getLogger().info("[GSHelper] Плагин выключен.");
    }
}
