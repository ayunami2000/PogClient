package me.ayunami2000.fabricnotebot.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer<T extends Entity> {
    private Text truncateIterate(Text text){
        return truncateIterate(text,300);
    }
    //this sucks rn
    private Text truncateIterate(Text text,int length){
        if(length<=0)return Text.of("");
        if(text.getString().length()<=length)return text;
        List<Text> origSibl=text.getSiblings().stream().toList();
        if(origSibl.size()>0)text.getSiblings().removeIf(bruh -> true);
        MutableText mutableText = ((MutableText)Text.of(text.asTruncatedString(Math.min(text.getString().length(), length)))).setStyle(text.getStyle());
        //faster, not perfect
        if(origSibl.size()>0){
            origSibl.forEach(text1 -> {
                mutableText.getSiblings().add(truncateIterate(text1,length-mutableText.getString().length()));
            });
            mutableText.getSiblings().removeIf(text1 -> text1.getString()=="");
            while(mutableText.getSiblings().size()>0&&mutableText.getString().length()>length)mutableText.getSiblings().remove(mutableText.getSiblings().size()-1);
        }
        //this is slow, very slow. in fact, too slow.
        /*
        if(origSibl.size()>0)mutableText.getSiblings().addAll(origSibl);
        while(mutableText.getSiblings().size()>0&&mutableText.getString().length()>length){
            Text siblText=mutableText.getSiblings().get(mutableText.getSiblings().size()-1);
            if(siblText.getString().length()==0){
                mutableText.getSiblings().remove(mutableText.getSiblings().size()-1);
            }else{
                mutableText.getSiblings().set(mutableText.getSiblings().size()-1,truncateIterate(siblText,siblText.getString().length()-1));
            }
        }
        */
        return mutableText;
    }
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"), index = 1)
    private Text mixin(Text text) {
        return truncateIterate(text);
    }
}
