package autoclicker;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AutoClickerModEventHandler {
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		// コンフィグが変更された時に呼ばれる。
		if (event.modID.equals(AutoClickerMod.MOD_ID))
			AutoClickerModConfigCore.syncConfig();
	}
}
