package pro.gameside.Events;

import javax.swing.text.html.parser.Entity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pro.gameside.HelpPlugin.HelpPlugin;
import static pro.gameside.HelpPlugin.HelpPlugin.fg_cooldown;

/**
 *
 * @author redap
 */
public class PlayerEventListener implements Listener {

        private HelpPlugin config;
        public PlayerEventListener(HelpPlugin configuration){
            this.config = configuration;
        }
    
        @EventHandler
        public void onMobSpawn(CreatureSpawnEvent event){
            EntityType type = event.getEntityType();
            if(type.equals(EntityType.ZOMBIE) || type.equals(EntityType.ZOMBIE_HORSE)
                    || type.equals(EntityType.ZOMBIE_VILLAGER) || type.equals(EntityType.BLAZE)
                    || type.equals(EntityType.CAVE_SPIDER) || type.equals(EntityType.CREEPER)
                    || type.equals(EntityType.ENDERMAN) || type.equals(EntityType.ENDER_DRAGON)
                    || type.equals(EntityType.GHAST) || type.equals(EntityType.GIANT)
                    || type.equals(EntityType.MAGMA_CUBE) || type.equals(EntityType.PIG_ZOMBIE)
                    || type.equals(EntityType.SKELETON) || type.equals(EntityType.SKELETON_HORSE)
                    || type.equals(EntityType.SLIME) || type.equals(EntityType.SPIDER)
                    || type.equals(EntityType.WITCH) || type.equals(EntityType.WITHER)
                    || type.equals(EntityType.WITHER_SKELETON) || type.equals(EntityType.WITHER_SKULL)){
                if(config.getConfig().getBoolean("gameplay.nomobs.monster")==true){
                    String noworldsspawn[] = config.getConfig().getString("gameplay.nomobs.worlds").replaceAll(" ", "").split(",");
                    for(String world : noworldsspawn){
                        if(event.getEntity().getWorld().getName().equals(world))
                            event.setCancelled(true);
                    }
                }
            }else if(type.equals(EntityType.COW) || type.equals(EntityType.SHEEP)
                    || type.equals(EntityType.PIG) || type.equals(EntityType.CHICKEN)
                    || type.equals(EntityType.BAT) || type.equals(EntityType.OCELOT)
                    || type.equals(EntityType.PARROT)){
                if(config.getConfig().getBoolean("gameplay.nomobs.animal")==true){
                    String noworldsspawn[] = config.getConfig().getString("gameplay.nomobs.worlds").replaceAll(" ", "").split(",");
                    for(String world : noworldsspawn){
                        if(event.getEntity().getWorld().getName().equals(world))
                            event.setCancelled(true);
                    }
                }
            }
        }
        
        @EventHandler 
        public void onRain(WeatherChangeEvent event) {
            if(config.getConfig().getBoolean("gameplay.norain.toggle")==true){
                String norainworlds[] = config.getConfig().getString("gameplay.norain.worlds").replaceAll(" ","").split(",");
                for(String world : norainworlds){
                    if(event.getWorld().getName().equals(world)){
                        boolean rain = event.toWeatherState();
                        if(rain)
                            event.setCancelled(true);   
                    }
                }
            }
        }
        
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            if(config.getConfig().getBoolean("gameplay.alwaysday.toggle")==true){
            String alwaysdayworlds[] = config.getConfig().getString("gameplay.alwaysday.worlds").replaceAll(" ", "").split(",");
                for(String world : alwaysdayworlds){
                    if(player.getWorld().getName().equals(world)){
                        if(player.getWorld().getTime()>11000L){
                            Bukkit.getServer().getWorld(world).setTime(0L);
                        }
                    }
                }   
            }
            
            if(config.getConfig().getBoolean("fungun.toggle")==true){
                String fungunworlds[] = config.getConfig().getString("fungun.worlds").replaceAll(" ", "").split(",");
                for(String world : fungunworlds){
                    if(player.getWorld().getName().equals(world)){
                        ItemStack gun = new ItemStack(Material.BLAZE_ROD);
                        ItemMeta gunmeta = gun.getItemMeta();
                        gunmeta.setDisplayName(config.getConfig().getString("fungun.displayname"));
                        gun.setItemMeta(gunmeta);
                        player.getInventory().setItem(config.getConfig().getInt("fungun.slot"), gun);
                    }
                }
            }
        }
        
        @EventHandler
        public void onClickMeowballs(PlayerInteractEvent e) {
            Player p = e.getPlayer();
            if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                if ((p.getItemInHand().getType() == Material.BLAZE_ROD)){
                    long current = System.currentTimeMillis();
                    if (!fg_cooldown.containsKey(p.getName())) {
                        fg_cooldown.put(p.getName(), current - 10L);
                    }
                    long end = (fg_cooldown.get(p.getName()));
                    if (end < current) {
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);
                        p.launchProjectile(Snowball.class);

                        fg_cooldown.put(p.getName(), current + config.getConfig().getInt("fungun.cooldown")*1000);
                    } else {
                        p.sendMessage(ChatColor.RED+"[Игровая Сторона] "+ChatColor.WHITE+"Подождите, перед тем как снова использовать это.");
                    }
                }
            }
        }
        
        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
            if ((e.getDamager() instanceof Snowball)) {
                e.setCancelled(true);
            }
            if ((e.getDamager() instanceof EnderPearl)) {
                e.setCancelled(true);
            }
            if ((e.getDamager() instanceof Egg)) {
                e.setCancelled(true);
            }
            if ((e.getDamager() instanceof Arrow)) {
                e.setCancelled(true);
            }
        }
        
        @EventHandler
        public void onHit(ProjectileHitEvent e){
            if ((e.getEntity() instanceof Snowball)){
                Location SnowBall = e.getEntity().getLocation();
                World world = e.getEntity().getWorld();
                world.playEffect(SnowBall, Effect.FIREWORKS_SPARK, 100);
                world.playEffect(SnowBall, Effect.FIREWORKS_SPARK, 10);
                world.playEffect(SnowBall, Effect.FIREWORKS_SPARK, 10);
                world.playEffect(SnowBall, Effect.FIREWORKS_SPARK, 10);
                world.playEffect(SnowBall, Effect.HEART, 10);
                world.playEffect(SnowBall, Effect.HEART, 10);
                world.playEffect(SnowBall, Effect.LAVA_POP, 10);
                world.playEffect(SnowBall, Effect.LAVA_POP, 10);
                world.playEffect(SnowBall, Effect.LAVA_POP, 10);
                world.playEffect(SnowBall, Effect.LAVA_POP, 50);
                world.playEffect(SnowBall, Effect.LAVA_POP, 50);
                world.playEffect(SnowBall, Effect.LAVA_POP, 50);
                world.playEffect(SnowBall, Effect.ENDER_SIGNAL, 10);
                world.playSound(SnowBall, Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
            }
        }
        

}
