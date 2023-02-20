package autoclicker;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class AutoClickerModConfigGuiFactory implements IModGuiFactory {
  public void initialize(Minecraft minecraftInstance) {}

  public Class<? extends GuiScreen> mainConfigGuiClass() {
    return (Class)AutoClickerModConfigGui.class;
  }

  public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
    return null;
  }

  public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement element) {
    return null;
  }

  public static class AutoClickerModConfigGui extends GuiConfig {
    public AutoClickerModConfigGui(GuiScreen parent) {
      super(parent, (new ConfigElement(AutoClickerModConfigCore.cfg

            .getCategory("General")))
          .getChildElements(), "AutoLeftClicker", false, false, "Auto Left Clicker");
    }
  }
}
