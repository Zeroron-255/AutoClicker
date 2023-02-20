package autoclicker;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod(modid = "AutoLeftClicker", name = "Auto Left Clicker", version = "1.1.0", guiFactory = "autoclicker.AutoClickerModConfigGuiFactory")
public class AutoClickerMod {
	public static final String MOD_ID = "AutoLeftClicker";

	public static final String MOD_NAME = "Auto Left Clicker";

	public static final String MOD_VERSION = "1.1.0";

	@Metadata("AutoLeftClicker")
	private static ModMetadata meta;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		AutoClickerModInfoCore.registerInfo(meta);
		AutoClickerModConfigCore.loadConfig(event);
	}

	private static final Minecraft mc = Minecraft.getMinecraft();

	public static final KeyBinding toggleLClick = new KeyBinding("toggle Auto LeftClick", 68, "autoLeftClicker");

	private static boolean isValid = false;

	public static boolean isAutoAttackerValid = true;

	private static int clickTickInterval = 10;

	private static int leftCooldown = 0;

	public static boolean isAutoBreakerValid = true;

	public static boolean isAutoFishingValid = true;

	private static EntityPlayer player;

	private static int fishingTime = 0;

	public static int fishingInterval = 16;

	public static int maxFishingTime = 3000;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new AutoClickerModEventHandler());
		FMLCommonHandler.instance().bus().register(this);
		ClientRegistry.registerKeyBinding(toggleLClick);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void keyHandlingEvent(InputEvent.KeyInputEvent event) {
		if (toggleLClick.isPressed())
			isValid = !isValid;
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (isValid) {
			if (isAutoAttackerValid) {
				if (autoAttacker(leftCooldown))
					leftCooldown = clickTickInterval + 1;
				if (0 < leftCooldown)
					leftCooldown--;
			}
			if (isAutoBreakerValid)
				autoBreaker();
			if (isAutoFishingValid) {
				fishingTime++;
				if (player != null) {
					ItemStack itemOnHand = getFishingRod(player);
					if (itemOnHand != null)
						if (player.fishEntity == null) {
							if (fishingInterval < fishingTime)
								useFishingRod(itemOnHand);
						} else if (maxFishingTime <= fishingTime) {
							useFishingRod(itemOnHand);
						}
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (isValid)
			AutoClickerModGui.setGui();
	}

	public boolean autoAttacker(int cooldown) {
		if (cooldown <= 0) {
			Entity target = mc.objectMouseOver.entityHit;
			if (target != null) {
				System.out.println("target");
				mc.playerController.attackEntity(mc.thePlayer, target);
				return true;
			}
		}
		return false;
	}

	public static String idString = "";

	public void autoBreaker() {
		int blockX = mc.objectMouseOver.blockX;
		int blockY = mc.objectMouseOver.blockY;
		int blockZ = mc.objectMouseOver.blockZ;
		if (Integer.valueOf(blockX) != null) {
			Block block = mc.theWorld.getBlock(blockX, blockY, blockZ);
			String id = Integer.valueOf(Block.getIdFromBlock(block)).toString();
			String[] idStrings = idString.split(",");
			for (int i = 0; i < idStrings.length; i++) {
				if (id.equals(idStrings[i])) {
					System.out.println("id");
					mc.playerController.clickBlock(blockX, blockY, blockZ, mc.objectMouseOver.sideHit);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		player = (EntityPlayer) event.entity;
	}

	@SubscribeEvent
	public void onPlaySound(PlaySoundSourceEvent event) {
		if (isValid) {
			if (player == null)
				return;
			if (player.fishEntity == null)
				return;
			ResourceLocation id = event.sound.getPositionedSoundLocation();
			if (!id.getResourceDomain().equals("minecraft"))
				return;
			if (!id.getResourcePath().equals("random.splash"))
				return;
			ItemStack item_on_hand = getFishingRod(player);
			if (item_on_hand == null)
				return;
			useFishingRod(item_on_hand);
		}
	}

	public static ItemStack getFishingRod(EntityPlayer player) {
    if (player == null)
      return null;
    ItemStack itemOnHand = player.inventory.getCurrentItem();
    if (itemOnHand == null)
      return null;
    String name = itemOnHand.getItem().getUnlocalizedName();
    if (name.contains("fishing") || name.contains("rod") || name.contains("釣"))
      return itemOnHand;
    return null;
  }

	public static void useFishingRod(ItemStack fishingRod) {
		if (fishingRod == null)
			return;
		mc.playerController.sendUseItem(player, (World) mc.theWorld, fishingRod);
		fishingTime = 0;
	}
}