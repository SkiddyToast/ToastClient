package dev.toastmc.toastclient.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.toastmc.toastclient.impl.module.render.NoFog;
import dev.toastmc.toastclient.impl.module.render.NoRender;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {

    @Inject(method = {"applyFog"}, at = @At("HEAD"), cancellable = true)
    private static void applyFogModifyDensity(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        if (NoFog.INSTANCE.isEnabled()) {
            RenderSystem.setShaderFogStart(0);
            RenderSystem.setShaderFogEnd(10000);
        }
    }

}
