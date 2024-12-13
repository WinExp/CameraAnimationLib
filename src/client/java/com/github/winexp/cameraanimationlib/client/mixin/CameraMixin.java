package com.github.winexp.cameraanimationlib.client.mixin;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.Animation;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.AnimationHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin implements AnimationHandler {
    @Shadow
    private boolean thirdPerson;

    @Unique
    @Nullable
    private Animation animation;

    @Unique
    private void ensureIsOnThread() {
        if (!MinecraftClient.getInstance().isOnThread()) throw new IllegalStateException("Method is called by non-rendering thread");
    }

    @Inject(method = "update", at = @At("RETURN"))
    private void updateAnimation(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (this.animation != null) {
            this.animation.update((Camera) (Object) this, tickDelta);
            this.thirdPerson = this.animation.shouldRenderSelfEntity();
        }
    }

    @Override
    public Animation cameraAnimationLib$getAnimation() {
        return this.animation;
    }

    @Override
    public boolean cameraAnimationLib$isAnimating() {
        return this.animation != null;
    }

    @Override
    public void cameraAnimationLib$startAnimation(Animation animation) {
        this.ensureIsOnThread();
        if (this.animation != null && this.animation != animation) throw new IllegalStateException("There is already an animation");
        this.animation = animation;
    }

    @Override
    public void cameraAnimationLib$stopAnimation() {
        this.ensureIsOnThread();
        this.animation = null;
    }

    @Override
    public void cameraAnimationLib$tick() {
        this.ensureIsOnThread();
        if (this.animation != null) this.animation.tick((Camera) (Object) this);
    }
}
