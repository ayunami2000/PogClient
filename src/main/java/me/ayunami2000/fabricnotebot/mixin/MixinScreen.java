package me.ayunami2000.fabricnotebot.mixin;

import me.ayunami2000.fabricnotebot.Main;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(Screen.class)
public class MixinScreen {
    /**
     * @author ayunami2000
     */
    @Overwrite
    private void openLink(URI link) {
        if(link.toString().toLowerCase().startsWith("file:")){
            Util.getOperatingSystem().open(link);
        }else if(link.toString().toLowerCase().matches("(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpe?g|gif|png|bmp|webp))(?:\\?([^#]*))?(?:#(.*))?")) {
            try {
                Main.openImageInJFrame(link.toURL());
            } catch (MalformedURLException e) {
                Util.getOperatingSystem().open(link);
            }
            return;
        }else if(link.toString().toLowerCase().matches("(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:wmv|mp4|mov|mkv|m4v|webm|mp3|ogg|wav))(?:\\?([^#]*))?(?:#(.*))?")){
            /*try {
                Runtime.getRuntime().exec(new String[]{"C:\\Program Files\\VideoLAN\\VLC\\vlc.exe",link.toString()});
            }catch(IOException e){
                Util.getOperatingSystem().open(link);
            }*/
            try {
                Main.openVideoInJFrame(link.toURL());
            } catch (MalformedURLException e) {
                Util.getOperatingSystem().open(link);
            }
            return;
        }else{
            Matcher tenorMatcher = Pattern.compile("^https?://(?:www\\.)?tenor\\.com?/view/(?:.+-)?(\\d+)[^-]*").matcher(link.toString().toLowerCase());
            if(tenorMatcher.find()){
                try{
                    String tenorEmbed = "https://tenor.com/embed/"+tenorMatcher.group(1);
                    Scanner tenorScanner = new Scanner(new URL(tenorEmbed).openStream(), "UTF-8");
                    tenorScanner.useDelimiter("\\A");
                    String tenorEmbedSource = tenorScanner.next();
                    tenorScanner.close();
                    Matcher tenorEmbedMatcher = Pattern.compile("\"originalgif\":\\{\"url\":\"([^\"]*)\"").matcher(tenorEmbedSource);
                    if(tenorEmbedMatcher.find()){
                        String tenorFinalUrl = org.apache.commons.lang3.StringEscapeUtils.unescapeJava(tenorEmbedMatcher.group(1));
                        Main.openImageInJFrame(new URL(tenorFinalUrl));
                    }else{
                        Util.getOperatingSystem().open(link);
                    }
                }catch(IOException e){
                    Util.getOperatingSystem().open(link);
                }
            }else{
                Util.getOperatingSystem().open(link);
            }
        }
    }
}
