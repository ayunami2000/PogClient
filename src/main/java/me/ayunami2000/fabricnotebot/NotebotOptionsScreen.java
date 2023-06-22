package me.ayunami2000.fabricnotebot;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.io.File;
import java.util.Locale;

public class NotebotOptionsScreen extends Screen {
    private Screen prevScreen;
    private TextFieldWidget summonInput;
    private String[] builderModeStrings=new String[]{"all blocks","just concrete","banners","item lore","armor stands","armor stands (pos)","signs"};
    //private TextFieldWidget gotoCoords;

    public NotebotOptionsScreen(Screen prevScreen)
    {
        super(Text.of("FabricNotebot Options"));
        this.prevScreen = prevScreen;
    }

    @Override
    protected void init()
    {
        //todo: use this
        //mc.setCameraEntity(mc.player);
        //separate buttons with 24 height each
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 - 48 - 16, 200,20, Text.of("make funny among us references"), b -> {
			Main.sendChatOrCmd(new String[]{"amogus","among us","sus","mogus","sugoma","AMONGUS","stop posting about among us","im tired of seeing it","sussy baka","S U S","A M O G U S"}[(int)Math.floor(Math.random()*11)]);
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 224, height / 4 + 24 - 16, 100,20, Text.of("TruePlayerList"), b -> {
            //bleachhack makes bookreader useless
            //client.openScreen(new BookScreen(BookScreen.Contents.create(client.player.getMainHandStack())));
            SignNbt.playerList();
        }));
        addDrawableChild(new ButtonWidget(width / 2 + 124, height / 4 + 24 - 16, 100,20, Text.of("GetInventory"), b -> {
            SignNbt.getInventory();
        }));
        addDrawableChild(new ButtonWidget(width / 2 + 124, height / 4 + 0 - 16, 100,20, Text.of("GetEnderchest"), b -> {
            SignNbt.getEnderchest();
        }));
        addDrawableChild(new ButtonWidget(width / 2 + 124, height / 4 - 24 - 16, 100,20, Text.of("Toggle Random Armor"), b -> {
            Main.randomArmor=!Main.randomArmor;
            Main.inGameLog("Random armor slots are now "+(Main.randomArmor?"enabled":"disabled")+".");
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 + 168 - 16, 200,20, Text.of("Back"), b -> client.setScreen(prevScreen)));
        ButtonWidget notebotToggleButton=new ButtonWidget(width / 2 - 100, height / 4 - 24 - 16, 112,20, Text.of("Notebot (§cDisabled§r)"), b -> NotebotPlayer.playASong());
        NotebotPlayer.updateNotebotToggleText(notebotToggleButton);
        NotebotPlayer.notebotToggleBtn=notebotToggleButton;
        addDrawableChild(notebotToggleButton);
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 114, height / 4 - 24 - 16, 20,20, Text.of("⇄"), b -> NotebotPlayer.loop()));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 136, height / 4 - 24 - 16, 20,20, Text.of("⌚"), b -> NotebotPlayer.skip(20)));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 158, height / 4 - 24 - 16, 20,20, Text.of("↗"), b -> {
            File newFile=Main.chooseFile(new String[]{"txt","nbs","mid","midi"},"Notebot Songs");
            if(newFile!=null){
                if(newFile.getName().toLowerCase(Locale.ROOT).endsWith(".nbs")){
                    NotebotPlayer.dontReadFromFile=true;
                    NotebotPlayer.theSong=ConvertNBS.doLiveConvert(newFile);
                }else if (newFile.getName().toLowerCase(Locale.ROOT).endsWith(".mid")||newFile.getName().toLowerCase(Locale.ROOT).endsWith(".midi")){
                        NotebotPlayer.dontReadFromFile=true;
                        String midiTxtSong = MidiConverter.midiToTxt(newFile);
                        System.out.println(midiTxtSong);
                        NotebotPlayer.theSong=midiTxtSong;
                }else{
                    NotebotPlayer.dontReadFromFile=false;
                    NotebotPlayer.defaultNotebotFile=newFile;
                }
                Main.inGameLog("Loaded file "+newFile.getName());
            }
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 180, height / 4 - 24 - 16, 20,20, Text.of("⌫"), b -> {
            NotebotPlayer.dontReadFromFile=true;
            NotebotPlayer.theSong=NotebotPlayer.theDefaultSong;
        }));
        ButtonWidget imageBuilderButton=new ButtonWidget(width / 2 - 100, height / 4 + 0 - 16, 134,20, Text.of("Build an Image ("+builderModeStrings[ImageBuilder.blockMode]+")"), b -> ImageBuilder.buildAnImage());
        addDrawableChild(imageBuilderButton);
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 136, height / 4 + 0 - 16, 20,20, Text.of("↗"), b -> {
            File newFile=Main.chooseFile(new String[]{"png","jpg","jpeg","bmp"},"Image files");
            if(newFile!=null){
                ImageBuilder.defaultImageFile=newFile;
                Main.inGameLog("Loaded file "+newFile.getName());
            }
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 158, height / 4 + 0 - 16, 20,20, Text.of("⇄"), b -> {
            if(ImageBuilder.isBuildingImage){
                Main.inGameLog("Error: You cannot change block mode while ImageBuilder is enabled!");
            }else{
                ImageBuilder.blockMode=(ImageBuilder.blockMode+1)%builderModeStrings.length;
                imageBuilderButton.setMessage(Text.of("Build an Image ("+builderModeStrings[ImageBuilder.blockMode]+")"));
                Main.inGameLog("Image Builder block mode: "+builderModeStrings[ImageBuilder.blockMode]+".");
            }
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 202, height / 4 + 0 - 16, 20,20, Text.of("#"), b -> {
            ImageBuilder.maxSize=ImageBuilder.imgSizes[(++ImageBuilder.currImgSize)%ImageBuilder.imgSizes.length];
            Main.inGameLog("Image size is now "+ImageBuilder.maxSize);
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 180, height / 4 + 0 - 16, 20,20, Text.of("⌫"), b -> ImageBuilder.defaultImageFile=ImageBuilder.realDefaultImageFile));
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 + 24 - 16, 156,20, Text.of("Crossbow Spammer"), b -> CrossbowSpammer.spamCrossbow()));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 158, height / 4 + 24 - 16, 20,20, Text.of("⇲"), b -> CrossbowSpammer.setCrossbow()));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 180, height / 4 + 24 - 16, 20,20, Text.of("⌫"), b -> CrossbowSpammer.resetCrossbow()));
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 + 48 - 16, 200,20, Text.of("Convert nbs/mid/midi to txt"), b -> ConvertNBS.doConvert()));
        ButtonWidget autoRepairButton=new ButtonWidget(width / 2 - 100, height / 4 + 72 - 16, 99,20, Text.of("Autorepair (§aEnabled§r)"), b -> {
            NotebotPlayer.autoRepair=!NotebotPlayer.autoRepair;
            b.setMessage(Text.of("Autorepair (§"+(NotebotPlayer.autoRepair?"aEnabled":"cDisabled")+"§r)"));
        });
        autoRepairButton.setMessage(Text.of("Autorepair (§"+(NotebotPlayer.autoRepair?"aEnabled":"cDisabled")+"§r)"));
        addDrawableChild(autoRepairButton);
        ButtonWidget commandModeButton=new ButtonWidget(width / 2 - 100 + 101, height / 4 + 72 - 16, 99,20, Text.of("Essentials Mode"), b -> {
            NotebotPlayer.essOrVanilla=!NotebotPlayer.essOrVanilla;
            b.setMessage(Text.of((NotebotPlayer.essOrVanilla?"Essentials":"Vanilla")+" Mode"));
        });
        commandModeButton.setMessage(Text.of((NotebotPlayer.essOrVanilla?"Essentials":"Vanilla")+" Mode"));
        addDrawableChild(commandModeButton);
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 + 144 - 16, 200,20, Text.of("Open Mod Folder"), b -> Util.getOperatingSystem().open(Main.modpath.toFile())));
        summonInput = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, height / 4 + 96 - 16, 156, 20, Text.of("Summon Command"));
        summonInput.setMaxLength(Integer.MAX_VALUE);
        summonInput.setText(SummonCommand.theSummonCommand);
        summonInput.setChangedListener(text->SummonCommand.onChange(text));
        addDrawableChild(summonInput);
        //this.addDrawable(summonInput);
        //MinecraftClient.getInstance().getNetworkHandler().sendPacket(new SpectatorTeleportC2SPacket(this.gameProfile.getId()));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 158, height / 4 + 96 - 16, 20,20, Text.of("⌚"), b -> SummonCommand.doSummon(Items.ENDERMAN_SPAWN_EGG)));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 180, height / 4 + 96 - 16, 20,20, Text.of("⇨"), b -> SummonCommand.doSummon(Items.PIGLIN_SPAWN_EGG)));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 202, height / 4 + 96 - 16, 20,20, Text.of("😂"), b -> SummonCommand.doSummon(Items.AXOLOTL_SPAWN_EGG)));
        /*
        gotoCoords = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, height / 4 + 120 - 16, 178, 20, new LiteralText("Goto Location"));
        gotoCoords.setMaxLength(Integer.MAX_VALUE);
        gotoCoords.setText(GotoLocation.lastInput);
        gotoCoords.setChangedListener(text->GotoLocation.lastInput=text);
        this.children.add(gotoCoords);
        addButton(new ButtonWidget(width / 2 - 100 + 180, height / 4 + 120 - 16, 20,20, new LiteralText("⇨"), b -> GotoLocation.parseLocation()));
        */
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 + 120 - 16, 99,20, Text.of("Toggle BasicHacks"), b -> Main.inGameLog("BasicHacks are now "+((BasicHacks.basicHacksEnabled=!BasicHacks.basicHacksEnabled)?"en":"dis")+"abled.")));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 101, height / 4 + 120 - 16, 77,20, Text.of("Toggle TabletDraw"), b -> TabletDraw.toggleTabletDraw()));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 180, height / 4 + 120 - 16, 20,20, Text.of("✎"), b -> Main.inGameLog("TabletDraw will no"+((TabletDraw.playerPen=!TabletDraw.playerPen)?"w ":" longer")+" use the player as the pen.")));
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 + 192 - 16, 99,20, Text.of("Open ToolPeek"), b -> ToolbarPeek.openToolbarMenu()));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 101, height / 4 + 192 - 16, 77,20, Text.of("refresh hotbar"), b -> ToolbarPeek.refreshHotbar()));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 180, height / 4 + 192 - 16, 20,20, Text.of("sex"), b -> Amogus.becomeSus()));
        addDrawableChild(new ButtonWidget(width / 2 - 100, height / 4 + 216 - 16, 99,20, Text.of("bed"), b -> Amogus.bruhMoment(0)));
        addDrawableChild(new ButtonWidget(width / 2 - 100 + 101, height / 4 + 216 - 16, 99,20, Text.of("stair"), b -> Amogus.bruhMoment(1)));
    }
    public void tick() {
        summonInput.tick();
        //gotoCoords.tick();
    }
    public void resize(MinecraftClient client, int width, int height) {
        String string1=summonInput.getText();
        //String string2=gotoCoords.getText();
        this.init(client, width, height);
        summonInput.setText(string1);
        //gotoCoords.setText(string2);
    }
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        summonInput.render(matrices, mouseX, mouseY, delta);
        //gotoCoords.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
}