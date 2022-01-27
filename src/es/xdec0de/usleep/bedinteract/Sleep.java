package es.xdec0de.usleep.bedinteract;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

import es.xdec0de.usleep.api.SleepAPI;
import es.xdec0de.usleep.utils.USPMessage;
import es.xdec0de.usleep.utils.USPSetting;
import es.xdec0de.usleep.utils.files.USPConfig;
import es.xdec0de.usleep.utils.files.USPMessages;

public class Sleep implements Listener {

	SleepAPI uSleepAPI = new SleepAPI();

	public static List<String> onDelay = new ArrayList<>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBedEnter(PlayerBedEnterEvent e) {
		Player p = e.getPlayer();
		if(e.getBed().getLocation().getWorld().getEnvironment().equals(Environment.NORMAL)) {
			if(e.getBedEnterResult().equals(BedEnterResult.OK)) {
				if(!uSleepAPI.handleSleep(p))
					USPMessages.sendMessage(p, USPMessage.NO_PERMS);
			} else {
				e.setCancelled(true);
				USPMessage msg = USPMessage.valueOf(e.getBedEnterResult().name());
				if(USPConfig.getBoolean(USPSetting.ACTIONBAR_ENABLED))
					USPMessages.sendDelayedActionbar(p, msg);
				if(USPConfig.getBoolean(USPSetting.PERCENT_SLEEP_SOUNDS_ENABLED))
					p.playSound(p.getLocation(), Sound.valueOf(USPConfig.getString(USPSetting.PERCENT_SLEEP_ERROR_SOUND)), 1.0F, 1.0F); 
			}
		}
	}
}
