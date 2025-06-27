package Auth.command;

import Auth.Auth;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LoginCommand extends Command {

    public LoginCommand() {
        super("login",
                "Введите пароль",
                "/login [пароль]",
                List.of("login", "l")); // Алиасы команды
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Команда только для игроков");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("Используйте: " + getUsage());
            return true;
        }
        if (isRegistered()) {
            if (Auth.getInstance().verifyPassword(player, args[0])) {
                player.sendMessage("Вы успешно авторизировались!");
                Auth.getInstance().addToLoggedPlayers(player.getUniqueId().toString());
                return true;
            }
            return false;
        }
        return false;
    }
}
