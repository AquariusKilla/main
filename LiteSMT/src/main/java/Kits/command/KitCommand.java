package Kits.command;

import Kits.Kits;
import Kits.KitsDataBase;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class KitCommand extends Command {
    public KitCommand() {
        super("kit",
                "Киты на выбор",
                "/kit [Название]",
                List.of("k", "kit")); // Алиасы команды
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Только для игроков!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("Используйте: " + getUsage());
            return true;
        }

        String kitName = args[0].toLowerCase();
        switch (kitName) {

            case "start":
                handleStartKit(player);
                break;
            default:
                player.sendMessage("Неизвестный кит: " + kitName);
                break;
        }

        return true;
    }

    private void handleStartKit(Player player){
        Timestamp curredTime = Timestamp.valueOf(LocalDateTime.now());
        if (!Kits.getInstance().canActivateKit(player, curredTime)) {
            player.sendMessage("Вы не можете активировать свой кит еще какоето время");
            return;
        }

        PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() == -1) {
                player.sendMessage("Ваш инвентарь полон!");
                return;
            }
            ItemStack bow = new ItemStack(Material.BOW);
            bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
            pickaxe.addEnchantment(Enchantment.LUCK, 2);
            inventory.addItem(bow);
            inventory.addItem(pickaxe);
            Kits.saveCommandTime(player, "start");
            player.sendMessage("Вы получили стартовый набор!");

    }
}