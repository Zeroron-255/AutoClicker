package autoclicker;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod(modid = AutoClickerMod.MOD_ID, name = AutoClickerMod.MOD_NAME, version = AutoClickerMod.MOD_VERSION, guiFactory = "autoclicker.AutoClickerModConfigGuiFactory")
public class AutoClickerMod {
	public static final String MOD_ID = "AutoLeftClicker";
	public static final String MOD_NAME = "Auto Left Clicker";
	public static final String MOD_VERSION = "1.1.0";
	@Metadata(MOD_ID)
	private static ModMetadata meta;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// first called
		AutoClickerModInfoCore.registerInfo(meta);
		AutoClickerModConfigCore.loadConfig(event);
	}

	private static final Minecraft mc = Minecraft.getMinecraft();

	public static final KeyBinding toggleLClick = new KeyBinding("toggle Auto LeftClick", Keyboard.KEY_F10,
			"autoLeftClicker");
	// auto Attacker
	private static boolean isValid = false;
	public static boolean isAutoAttackerValid = true;
	private static int clickTickInterval = 10;
	private static int leftCooldown = 0;
	// auto Breaker
	public static boolean isAutoBreakerValid = false;
	// auto Fishing
	public static boolean isAutoFishingValid = true;
	private static EntityPlayer player;
	private static int fishingTime = 0;
	public static int fishingInterval = 16;
	public static int maxFishingTime = 3000;

	// Initialize KeyBind
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// second called
		FMLCommonHandler.instance().bus().register(new AutoClickerModEventHandler());
		FMLCommonHandler.instance().bus().register(this);
		ClientRegistry.registerKeyBinding(toggleLClick);
		MinecraftForge.EVENT_BUS.register(this);
	}

	// Called when a keyboard input is detected
	@SubscribeEvent
	public void keyHandlingEvent(KeyInputEvent event) {
		// Key が押されることで Macro の On/Off を切り替える
		if (toggleLClick.isPressed()) {
			isValid = !isValid;
		}
	}

	// Called whenever the player is updated or ticked
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (isValid) {
			// auto Attacker
			if (isAutoAttackerValid) {
				if (autoAttacker(leftCooldown))
					// reset cool time on successful attack
					leftCooldown = clickTickInterval + 1;
				if (0 < leftCooldown)
					leftCooldown--;
			}

			if (isAutoBreakerValid)
				autoBreaker();

			// auto fishing
			if (isAutoFishingValid) {
				fishingTime++;
				if (player != null) {
					ItemStack itemOnHand = getFishingRod(player);
					if (itemOnHand != null) {
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
	}

	// Called when a new frame is displayed
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (isValid) {
			AutoClickerModGui.setGui();
		}
	}

	// try to attack mobs
	public boolean autoAttacker(int cooldown) {
		if (cooldown <= 0) {
			// auto attacker
			net.minecraft.entity.Entity target = mc.objectMouseOver.entityHit;
			if (target != null) {
				System.out.println("target");
				mc.playerController.attackEntity(Minecraft.getMinecraft().thePlayer, target);
				return true;
			}
		}
		return false;
	}

	// try to break block
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

	// auto fishing
	// from : https://sourceforge.net/p/rivoreo-mc-plugins/auto-fishing-code/ci/master/tree/
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
		if (name.contains("fishing") || name.contains("rod") || name.contains("釣")) {
			return itemOnHand;
		}
		return null;
	}

	public static void useFishingRod(ItemStack fishingRod) {
		if (fishingRod == null)
			return;
		mc.playerController.sendUseItem(player, mc.theWorld, fishingRod);
		fishingTime = 0;
	}
}