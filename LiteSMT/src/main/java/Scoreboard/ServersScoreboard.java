package Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ServersScoreboard {

    public void createScoreboard(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        // Создаём Objective (цель)
        Objective obj = board.registerNewObjective("stats", "dummy", "§6§lСтатистика");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR); // Показываем справа

        // Добавляем строки
        Score kills = obj.getScore("§cУбийства: ");
        kills.setScore(10); // Установить значение

        Score money = obj.getScore("§aДеньги: ");
        money.setScore(1000);
        // Присваиваем Scoreboard игроку
        player.setScoreboard(board);
    }
}
