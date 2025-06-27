package Auth;

import Auth.command.LoginCommand;
import Auth.command.RegisterCommand;
import MainPlugin.MainPlugin;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

public class AuthHandler implements Listener {
    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        if (!Auth.getInstance().isRigistred(event.getPlayer())) {
            MainPlugin.getInstance().getServer().sendMessage(Component.text("ZOOOOOOOOV"));
            event.getPlayer().sendMessage("Вы не зарегистрированы");
            return;
        }
        if (Auth.getInstance().isAuthorized(event.getPlayer())) {
            event.getPlayer().sendMessage("Вы не авторизованы");
            return;
        }
        event.getPlayer().sendMessage("Вы успешно авторизировались!");
    }

    @EventHandler
    public void handlePlayerMove(PlayerMoveEvent event) {
        if (Auth.getInstance().isAuthorized(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerChat(AsyncChatEvent event) {
        if (Auth.getInstance().isAuthorized(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerBlockBreak(BlockBreakEvent event) {
        if (Auth.getInstance().isAuthorized(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        if (Auth.getInstance().isAuthorized(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerDrop(PlayerDropItemEvent event) {
        if (Auth.getInstance().isAuthorized(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (Auth.getInstance().isAuthorized(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (Auth.getInstance().isAuthorized(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleCommand(PlayerCommandPreprocessEvent event) {
        String[] words = event.getMessage().split(" ");
        String command = words[0].substring(1);
        Command command1 = MainPlugin.getInstance().getServer().getCommandMap().getCommand(command);
        if (Auth.getInstance().isAuthorized(event.getPlayer()) && !(command1 instanceof LoginCommand) && !(command1 instanceof RegisterCommand)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Component.text("Команды могут вводить только авторизованные игроки."));
        }
    }
}