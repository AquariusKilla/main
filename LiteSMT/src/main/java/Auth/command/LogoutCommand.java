package Auth.command;

import Auth.Auth;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LogoutCommand extends Command {

    public LogoutCommand() {
        super("logout",
                "Введите шоб разлогиница",
                "/logout",
                List.of("logout")); // Алиасы команды
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Команда только для игроков");
            return true;
        }
        Auth.getInstance().removeLoggedPlayer(player.getUniqueId().toString());
        player.sendMessage("Вы разлогинились!");
        return false;
}
}
