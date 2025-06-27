package Broadcaster;

import net.kyori.adventure.text.Component;
import MainPlugin.MainPlugin;

public class Broadcaster {
    private static int broadcastkey = 0;
    private static final String[] link = {
            "Vk.Com/analfree",
            "Discrod.com/pussyfree",
            "BreadixHuyadix"
    };
    public Broadcaster() {
        MainPlugin.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(MainPlugin.getInstance(),()-> {
            this.broadcast();
        },0,120 * 20);
    }

    public void broadcast() {
        MainPlugin.getInstance().getServer().sendMessage(Component.text("Подпишись на наши соцсети" + link[broadcastkey]));
        broadcastkey++;
        if (broadcastkey > link.length - 1) {
            broadcastkey = 0;
        }
    }
}

