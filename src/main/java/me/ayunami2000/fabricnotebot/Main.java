package me.ayunami2000.fabricnotebot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lecturestudio.stylus.StylusAxesData;
import org.lecturestudio.stylus.StylusEvent;
import org.lecturestudio.stylus.StylusListener;
import org.lecturestudio.stylus.awt.AwtStylusManager;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class Main implements ModInitializer {
	//public static ResourceCopy resourceCopy = new ResourceCopy();
	//public static KeyBinding keyBinding;
	public static MinecraftClient mc=MinecraftClient.getInstance();
	public static Path modpath=mc.runDirectory.toPath().normalize().resolve("FabricNotebot/");
	public static String clientTitle="";
	public static JFrame tabletDrawWindow=null;
	public static StylusAxesData tabletDrawAxes=null;
	public static boolean randomArmor=false;
	@Override
	public void onInitialize() {
		//DOESNT WORK BRURHRHRHRH
		/*
		File jarFileFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		if(jarFileFile.isFile()){
			try {
				JarFile jarFile = new JarFile(jarFileFile);
				resourceCopy.copyResourceDirectory(jarFile,"/lib/", new File(System.getenv("TEMP")+"\\JWinPointer\\"));
			} catch (IOException e) {}
		}
		*/
		System.setProperty("java.awt.headless", "false");
		ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
		//System.out.println(Arrays.toString(StylusManager.getInstance().getDevices()));
		tabletDrawWindow=new JFrame("TabletDraw");
		//tabletDrawWindow.setExtendedState(tabletDrawWindow.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		WindowListener exitListener=new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				if(TabletDraw.tabletDrawEnabled)TabletDraw.toggleTabletDraw();
			}
		};
		tabletDrawWindow.addWindowListener(exitListener);
		tabletDrawWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Component cd=new JComponent() {
			public void paintComponent(Graphics g) {
				//nah
			}
		};
		tabletDrawWindow.add(cd);
		tabletDrawWindow.setSize(800,450);
		tabletDrawWindow.setAlwaysOnTop(true);
		tabletDrawWindow.pack();
		//tabletDrawWindow.setVisible(false);
		AwtStylusManager.getInstance().attachStylusListener(cd,new StylusListener() {
			@Override
			public void onCursorChange(StylusEvent stylusEvent) {
				//fard
				//tabletDrawAxes=stylusEvent.getAxesData();
				TabletDraw.penEvent(stylusEvent.getAxesData());
			}

			@Override
			public void onCursorMove(StylusEvent stylusEvent) {
				//fard
				TabletDraw.penEvent(stylusEvent.getAxesData());
				//tabletDrawAxes=stylusEvent.getAxesData();
			}

			@Override
			public void onButtonDown(StylusEvent stylusEvent) {
				//fard
			}

			@Override
			public void onButtonUp(StylusEvent stylusEvent) {
				//fard
			}
		});
		System.out.println("Using FabricNotebot by ayunami2000!");
		/*
		keyBinding = new KeyBinding(
				"Toggle Notebot",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_UNKNOWN,//GLFW.GLFW_KEY_N
				"FabricNotebot"
		);
		KeyBindingHelper.registerKeyBinding(keyBinding);
		*/
		NotebotPlayer.updateFile();
		ImageBuilder.updateImage();
	}

	private void onTick(MinecraftClient minecraftClient) {
		ImageBuilder.tick();
		NotebotPlayer.tick();
		CrossbowSpammer.tick();
		//GotoLocation.tick();
		BasicHacks.tick();
		Amogus.tick();
		TabletDraw.tick();
		SignNbt.tick();
	}
	public static void rightClickBlock(BlockPos block, Direction side){
		mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(block), side, block, false));
		mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
	}
	public static void clearHotbar(){
		int prevSelectedSlot=mc.player.getInventory().selectedSlot;
		for (int o=0;o<9;o++) {
			mc.player.getInventory().selectedSlot=o;
			mc.player.clearActiveItem();
			mc.player.getInventory().setStack(o, new ItemStack(Blocks.AIR));
		}
		mc.player.getInventory().selectedSlot=prevSelectedSlot;
		//must be updated after using updateInventory()
	}
	public static void updateInventory() {
		mc.player.getInventory().updateItems();
		mc.setScreen(new InventoryScreen(mc.player));
		mc.player.closeScreen();
	}
	public static void loadItem(ItemStack itemStack){
		loadItem(itemStack,mc.player.getInventory().selectedSlot);
	}
	public static void loadItem(ItemStack itemStack,int slot){
		mc.interactionManager.clickCreativeStack(itemStack,slot+36);
		//mc.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(slot+36,itemStack));
	}
	public static void inGameLog(String message){
		mc.inGameHud.setOverlayMessage(Text.of(message), false);
	}
	public static File chooseFile(String filter,String filterName){
		return chooseFile(new String[]{filter},filterName);
	}
	public static File chooseFile(String[] filters,String filterName){
		File fileResult=showOpenFileDialog("Select File to Open",modpath.toFile(),filterName,filters);
		return fileResult;
	}
	//originally from LWJGUI at https://github.com/orange451/LWJGUI
	private static File showOpenFileDialog(String title, File defaultPath, String filterDescription, String[] acceptedFileExtensions){

		MemoryStack stack = MemoryStack.stackPush();

		PointerBuffer filters = stack.mallocPointer(acceptedFileExtensions.length);

		for(int i = 0; i < acceptedFileExtensions.length; i++){
			filters.put(stack.UTF8("*." + acceptedFileExtensions[i]));
		}

		filters.flip();

		defaultPath = defaultPath.getAbsoluteFile();
		String defaultString = defaultPath.getAbsolutePath();
		if(defaultPath.isDirectory() && !defaultString.endsWith(File.separator)){
			defaultString += File.separator;
		}

		String result = TinyFileDialogs.tinyfd_openFileDialog(title, defaultString, filters, filterDescription, false);

		stack.pop();

		return result != null ? new File(result) : null;
	}
	public static File saveFile(String ext,String desc){
		return showSaveFileDialog("Save file as...",modpath.toFile(),desc,ext,true);
	}
	public static File showSaveFileDialog(String title, File defaultPath, String filterDescription, String fileExtension, boolean forceExtension){

		MemoryStack stack = MemoryStack.stackPush();

		PointerBuffer filters = stack.mallocPointer(1);

		filters.put(stack.UTF8("*." + fileExtension)).flip();

		defaultPath = defaultPath.getAbsoluteFile();
		String defaultString = defaultPath.getAbsolutePath();
		if(defaultPath.isDirectory() && !defaultString.endsWith(File.separator)){
			defaultString += File.separator;
		}

		String result = TinyFileDialogs.tinyfd_saveFileDialog(title, defaultString, filters, filterDescription);

		stack.pop();

		if(result == null){
			return null;
		}

		if(forceExtension && !result.endsWith("." + fileExtension)){
			result += "." + fileExtension;
		}

		return new File(result);
	}
	public static String ultraTrim(String s){
		return s.trim().replaceAll("^\\s+|\\s+$","");
	}
	public static boolean doesBoxTouchBlock(Box box, Block block) {
		for (int x = (int) Math.floor(box.minX); x < Math.ceil(box.maxX); x++) {
			for (int y = (int) Math.floor(box.minY); y < Math.ceil(box.maxY); y++) {
				for (int z = (int) Math.floor(box.minZ); z < Math.ceil(box.maxZ); z++) {
					if (MinecraftClient.getInstance().world.getBlockState(new BlockPos(x, y, z)).getBlock() == block) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static Boolean areAnyHacksEnabled(){
		return NotebotPlayer.notebotEnabled||NotebotPlayer.notebotInit||GotoLocation.isGoingToLocation||ImageBuilder.isBuildingImage||CrossbowSpammer.cbSpammerEnabled||Amogus.isenable|| TabletDrawOlder.tabletDrawEnabled||SignNbt.isDoing;
	}
	public static String getWindowTitle(){
		StringBuilder stringBuilder=new StringBuilder("Minecraft");
		/*if(mc.isModded())*/stringBuilder.append("*");
		stringBuilder.append(" ");
		stringBuilder.append(SharedConstants.getGameVersion().getName());
		ClientPlayNetworkHandler clientPlayNetworkHandler=mc.getNetworkHandler();
		if(clientPlayNetworkHandler!= null&&clientPlayNetworkHandler.getConnection().isOpen()){
			stringBuilder.append(" - ");
			if(mc.getServer()!=null&&!mc.getServer().isRemote()){
				stringBuilder.append(I18n.translate("title.singleplayer",new Object[0]));
			}else if(mc.isConnectedToRealms()){
				stringBuilder.append(I18n.translate("title.multiplayer.realms",new Object[0]));
			}else if(mc.getServer()==null&&(mc.getCurrentServerEntry()==null||!mc.getCurrentServerEntry().isLocal())){
				stringBuilder.append(I18n.translate("title.multiplayer.other",new Object[0]));
			}else{
				stringBuilder.append(I18n.translate("title.multiplayer.lan",new Object[0]));
			}
		}
		return stringBuilder.toString();
	}
	public static void openImageInJFrame(URL imageUrl) throws MalformedURLException{
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		JFrame f = new JFrame(imageUrl.toString());
		ImageIcon defImg = new ImageIcon(imageUrl);
		Boolean widthIsLarger=defImg.getIconWidth()-screenSize.width/2>defImg.getIconHeight()-screenSize.height/2;
		Integer largestSize=Math.min(Math.min(screenSize.width,screenSize.height),Math.max(screenSize.width/2,screenSize.height/2));
		f.getContentPane().add(new JLabel(new ImageIcon(defImg.getImage().getScaledInstance(widthIsLarger?Math.min(largestSize,defImg.getIconWidth()):-1,widthIsLarger?-1:Math.min(largestSize,defImg.getIconHeight()),Image.SCALE_DEFAULT))));
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop(true);
		f.setResizable(false);
		f.setMaximumSize(new Dimension(largestSize,largestSize));
		f.setVisible(true);
	}
	public static void openVideoInJFrame(URL videoUrl) {
		final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		JFrame f = new JFrame(videoUrl.toString());
		Integer largestSize=Math.min(Math.min(screenSize.width,screenSize.height),Math.max(screenSize.width/2,screenSize.height/2));
		f.setSize(largestSize,largestSize);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
			}
		});
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop(true);
		//f.setResizable(false);
		//f.setMaximumSize(new Dimension(largestSize,largestSize));
		f.setContentPane(mediaPlayerComponent);
		f.setVisible(true);
		mediaPlayerComponent.mediaPlayer().controls().setRepeat(true);
		mediaPlayerComponent.mediaPlayer().media().play(videoUrl.toString());
	}

	public static void sendChatOrCmd(String msg) {
		if (msg.startsWith("/")) {
			mc.player.sendCommand(msg.substring(1));
		} else {
			mc.player.sendChatMessage(msg, null);
		}
	}
}
