package me.ayunami2000.fabricnotebot;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Hand;

import static me.ayunami2000.fabricnotebot.Main.inGameLog;
import static me.ayunami2000.fabricnotebot.Main.mc;

public class CrossbowSpammer {
    public static Boolean cbSpammerEnabled=false;
    private static ItemStack customCrossbow=null;
    public static void spamCrossbow(){
        if(Main.areAnyHacksEnabled()) {
            if (cbSpammerEnabled) {
                cbSpammerEnabled = false;
                inGameLog("CrossbowSpammer is now disabled.");
            } else {
                inGameLog("Error: You must disable other hacks before using CrossbowSpammer!");
            }
            return;
        }
        if(!mc.player.getAbilities().creativeMode){
            cbSpammerEnabled=false;
            inGameLog("Error: You are not in creative mode! Disabling Crossbow Spammer.");
            return;
        }
        addCrossbow();
        inGameLog("Crossbow Spammer is now enabled.");
        cbSpammerEnabled=true;
    }
    private static ItemStack addCrossbow(){
        ItemStack crossbowItem=new ItemStack(Items.CROSSBOW);
        try{
            if(customCrossbow==null) {
                crossbowItem.setNbt(StringNbtReader.parse("{Enchantments:[{id:\"minecraft:multishot\",lvl:2147483647},{id:\"minecraft:piercing\",lvl:2147483647},{id:\"minecraft:quick_charge\",lvl:2147483647}],ChargedProjectiles:[{id:\"minecraft:tipped_arrow\",Count:1b,tag:{CustomPotionColor:" + Math.floor(Math.random() * 16777216) + "}},{id:\"minecraft:tipped_arrow\",Count:1b,tag:{CustomPotionColor:" + Math.floor(Math.random() * 16777216) + "}},{id:\"minecraft:tipped_arrow\",Count:1b,tag:{CustomPotionColor:" + Math.floor(Math.random() * 16777216) + "}}],Charged:1b}"));
            }else{
                crossbowItem=customCrossbow;
            }
        }catch(Exception e){}
        Main.loadItem(crossbowItem);
        return crossbowItem;
    }
    public static void tick(){
        try {
            if (cbSpammerEnabled) {
                if (!mc.player.getAbilities().creativeMode) {
                    cbSpammerEnabled = false;
                    inGameLog("Error: You are not in creative mode! Disabling Crossbow Spammer.");
                    return;
                }
                ItemStack heldItem = mc.player.getMainHandStack();
                if (heldItem.getItem() instanceof CrossbowItem) {
                    addCrossbow();
                    heldItem.use(mc.world,mc.player,Hand.MAIN_HAND);
                    mc.interactionManager.interactItem(mc.player,Hand.MAIN_HAND);
                    mc.interactionManager.stopUsingItem(mc.player);
                }
            }
        }catch(Exception e){
            cbSpammerEnabled=false;
        }
    }
    public static void setCrossbow(){
        ItemStack heldItem = mc.player.getMainHandStack();
        if(heldItem.getItem() instanceof CrossbowItem){
            if(CrossbowItem.isCharged(heldItem)){
                customCrossbow=heldItem.copy();
                inGameLog("Custom crossbow has been set!");
            }else{
                inGameLog("Error: You must charge/load the crossbow first!");
            }
        }else{
            inGameLog("Error: You are not holding a crossbow!");
        }
    }
    public static void resetCrossbow(){
        customCrossbow=null;
        inGameLog("Custom crossbow has been reset to default!");
    }
}
