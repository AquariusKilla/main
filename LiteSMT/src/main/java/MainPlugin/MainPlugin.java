package MainPlugin;

import Auth.Auth;
import Broadcaster.Broadcaster;
import Game.MathGame;
import Kits.Kits;
import Kits.command.KitCommand;
import ListenersChat.MainChatListener;
import Scoreboard.ServersScoreboard;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.sisu.launch.Main;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainPlugin extends JavaPlugin {
    @Getter
    private static MainPlugin instance; // Класс плагина.
    @Getter
    private static Logger jlogger;

    @Override
    public void onEnable() {

        instance = this;
        jlogger = super.getLogger();
        jlogger.info("траралело тралала");
        MathGame mathGame = new MathGame();
        new Broadcaster();
        new Kits();
        new Auth();
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            mathGame.generationNumber();
            this.getServer().sendMessage(Component.text("Кто введет верный ответ получит деньгу: " + mathGame.example));
            this.getLogger().info("Ответ: " + mathGame.answer);
        }, 0, 120 * 20);
        this.getServer().getPluginManager().registerEvents(new MainChatListener(mathGame), this);
        AtomicInteger curredTime = new AtomicInteger(0);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            int restartTime = 1200;
            if (curredTime.get() % 60 == 0) {
                this.getServer().sendMessage(Component.text("Рестарт через " + (restartTime - curredTime.get()) + " секундочек"));
            }
            if (restartTime - curredTime.get() <= 5) {
                this.getServer().sendMessage(Component.text("Рестарт через " + (restartTime - curredTime.get()) + " секундочек"));
            }
            curredTime.getAndIncrement();
            if (curredTime.get() == restartTime) {
                this.getServer().shutdown();
            }
        }, 0, 20);
    }
}
