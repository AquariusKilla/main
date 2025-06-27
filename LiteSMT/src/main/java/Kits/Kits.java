package Kits;

import Auth.AuthDatabase;
import Kits.command.KitCommand;
import MainPlugin.MainPlugin;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Kits {
    @Getter
    private static Kits instance;

    public Kits() {
        instance = this;
        KitsDataBase.initialize();
        MainPlugin.getInstance().getServer().sendMessage(Component.text("Модуль китов активирован!"));
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register("kit.command", new KitCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveCommandTime(Player player, String kitName) {
        try {
            Timestamp nextActivation = Timestamp.valueOf(LocalDateTime.now().plusHours(1));
            KitsDataBase.saveTimeActivation(String.valueOf(player.getUniqueId()), kitName, nextActivation);
        } catch (Exception e) {
            System.err.println("Ошибка сохранения времени активации команды: " + e.getMessage());
        }
    }

    public boolean canActivateKit(Player player, Timestamp curredTime) {
        Timestamp next_activation_time = KitsDataBase.getTime(player);
        if (next_activation_time == null) {
            return true;
        }
        if (curredTime.getTime() < next_activation_time.getTime()) {
            player.sendMessage("Вы уже получали этот кит недавно!");
            return false;
        }
        return true;
    }
}


