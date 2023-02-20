package autoclicker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

public class AutoClickerModGui {
  private static final Minecraft mc = Minecraft.getMinecraft();

  public static boolean isNoticeAutoClicker = true;

  public static int stringColor = -51712;

  public static int edgeColor = -51712;

  public static int edgeWidth = 3;

  private static String text = "AUTCLICKER IS ENABLED";

  public static void setGui() {
    if (isNoticeAutoClicker) {
      ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      int width = resolution.getScaledWidth();
      int height = resolution.getScaledHeight();
      int stringWidth = mc.fontRenderer.getStringWidth(text);
      mc.fontRenderer.drawString(text, width / 2 - stringWidth / 2, edgeWidth + 1, stringColor);
      GuiIngame.drawRect(0, 0, width - edgeWidth, edgeWidth, edgeColor);
      GuiIngame.drawRect(width - edgeWidth, 0, width, height - edgeWidth, edgeColor);
      GuiIngame.drawRect(edgeWidth, height - edgeWidth, width, height, edgeColor);
      GuiIngame.drawRect(0, edgeWidth, edgeWidth, height, edgeColor);
    }
  }
}
