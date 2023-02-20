package autoclicker;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AutoClickerModEventHandler {
  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.modID.equals("AutoLeftClicker"))
      AutoClickerModConfigCore.syncConfig();
  }
}
