package ListenersChat;

import Game.MathGame;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import MainPlugin.MainPlugin;

public class MainChatListener implements Listener {
    private MathGame mathGame;
public MainChatListener(MathGame mathGame){
     this.mathGame = mathGame;
}

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer((source, sourceDisplayName, message, viewer) ->
                Component.text()
                        .append(sourceDisplayName.color(TextColor.fromHexString("#a8a432")))
                        .append(Component.text(" : "))
                        .append(message)
                        .build());
    }
    @EventHandler
    public void handleChatGame (AsyncChatEvent event) {
        SignedMessage message = event.signedMessage();
        if(String.valueOf(mathGame.answer).equals(message.message())){
            if(mathGame.isSolved){
                event.getPlayer().sendMessage("Пример уже решен броу!");
                event.setCancelled(true);
                return;
            }
            event.getPlayer().sendMessage("Поздравляю, вы дали верный ответ!");
            MainPlugin.getInstance().getServer().sendMessage(Component.text(event.getPlayer().getName() +" дал правильный ответ  = " + mathGame.answer));
            event.setCancelled(true);
            mathGame.isSolved = true;
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.quitMessage(Component.text()
                .append(Component.text("["))
                .append(Component.text("-", TextColor.fromHexString("#28ff03")))
                .append(Component.text("] "))
                .append(event.getPlayer().displayName())
                .build());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(Component.text()
                .append(Component.text("["))
                .append(Component.text("+", TextColor.fromHexString("#28ff03")))
                .append(Component.text("] "))
                .append(event.getPlayer().displayName())
                .build());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String deathMessage = "";

        if (player.getKiller() != null) {
            // Убил другой игрок
            deathMessage = "§cИгрок " + player.getKiller().getName() + " убил " + player.getName();
        } else if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            // Убила сущность (моб, стрела и т.д.)
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) player.getLastDamageCause();
            Entity killer = damageEvent.getDamager();

            // Обработка стрел и других проективов
            if (killer instanceof Projectile) {
                Projectile projectile = (Projectile) killer;
                if (projectile.getShooter() instanceof Entity) {
                    killer = (Entity) projectile.getShooter();
                }
            }

            String killerName = killer instanceof Player ? ((Player) killer).getName() :
                    formatEntityName(killer.getType().name());
            deathMessage = "§e" + player.getName() + " был убит " + killerName;
        } else if (player.getLastDamageCause() != null) {
            // Умер от окружающей среды
            String cause = formatDeathCause(player.getLastDamageCause().getCause().name());
            deathMessage = "§7" + player.getName() + " умер от " + cause;
        } else {
            // Неизвестная причина смерти
            deathMessage = "§7" + player.getName() + " умер при загадочных обстоятельствах";
        }

        event.setDeathMessage(deathMessage);

    }

    private String formatEntityName(String entityType) {
        return entityType.toLowerCase().replace("_", " ");
    }

    private String formatDeathCause(String cause) {
        return cause.toLowerCase().replace("_", " ");
    }
}