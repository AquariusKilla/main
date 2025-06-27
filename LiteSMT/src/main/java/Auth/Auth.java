package Auth;

import Auth.command.LoginCommand;
import Auth.command.LogoutCommand;
import Auth.command.RegisterCommand;
import MainPlugin.MainPlugin;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Auth {
    @Getter
    private static Auth instance;
    @Getter
    private final List<String> loggedPlayers = new ArrayList<>();
    public void addToLoggedPlayers(String value) {
        loggedPlayers.add(value);
    }
    public void removeLoggedPlayer(String value) {
        loggedPlayers.remove(value);
    }
    public Auth() {
        instance = this;
        AuthDatabase.initialize();
        MainPlugin.getInstance().getServer().getPluginManager().registerEvents(new AuthHandler(), MainPlugin.getInstance());
        MainPlugin.getInstance().getServer().sendMessage(Component.text("Модуль регистрации работает"));
        MainPlugin.getInstance().getServer().getCommandMap().registerAll("auth.command", List.of(new LoginCommand(), new RegisterCommand(), new LogoutCommand()));
    }

    public void PlayerSaveInformation(Player player, String password) {
        AuthDatabase.addUser(String.valueOf(player.getUniqueId()), player.getName(), password);
    }

    public String getPassword(Player player) {
        return AuthDatabase.getPassword(player.getUniqueId().toString());
    }

    public boolean verifyPassword(Player player, String password) {
        if (this.getPassword(player).equals(password)) {
            return true;
        }
        player.sendMessage("Неверный пароль");
        return false;
    }

    public boolean isRigistred(Player player) {
        if (getPassword(player) != null) ;
        {
            return true;
        }
    }

    public boolean isAuthorized(Player player) {
        for (String loggedPlayer : loggedPlayers) {
            if (Objects.equals(loggedPlayer, player.getUniqueId().toString())) {
                return false;
            }
        }
        return true;
    }
}
