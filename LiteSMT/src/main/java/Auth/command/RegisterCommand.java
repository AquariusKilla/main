package Auth.command;

import Auth.Auth;
import Kits.Kits;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class RegisterCommand extends Command {
    public RegisterCommand() {
        super("regiser",
                "Введите пароль",
                "/r [пароль] [пароль]",
                List.of("reg", "register", "r")); // Алиасы команды
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Команда только для игроков");
            return true;
        }
        if(!Auth.getInstance().isRigistred(player)) {
            if (args.length < 2) {
                player.sendMessage("Используйте: " + getUsage());
                return true;
            }
        }
        if (Auth.getInstance().isRigistred(player)) {  // исправлено условие
            player.sendMessage("Ты уже зарегистрирован!");
            return true;
        }
        if (args[0].equals(args[1])) {

            player.sendMessage("Вы успешно зарегистрировались!");
            Auth.getInstance().PlayerSaveInformation(player, args[0]);
            Auth.getInstance().addToLoggedPlayers(player.getUniqueId().toString());
            return true;  // возвращаем true при успехе
        } else {
            player.sendMessage("Пароли не совпадают!");
            return true;
        }
    }
}
