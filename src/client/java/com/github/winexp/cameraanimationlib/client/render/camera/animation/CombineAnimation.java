package com.github.winexp.cameraanimationlib.client.render.camera.animation;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.Camera;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CombineAnimation implements Animation {
    private final ImmutableList<Animation> animations;
    private int index = 0;

    public CombineAnimation(Collection<Animation> animations) {
        this.animations = ImmutableList.copyOf(animations);
    }

    @Nullable
    public Animation getCurrentAnimation() {
        return this.index < this.animations.size() ? this.animations.get(this.index) : null;
    }

    @Override
    public void update(Camera camera, float tickDelta) {
        Animation animation = this.getCurrentAnimation();
        if (animation != null) {
            animation.update(camera, tickDelta);
            if (animation.shouldStop()) {
                this.index++;
            }
        }
    }

    @Override
    public void tick(Camera camera) {
        Animation animation = this.getCurrentAnimation();
        if (animation != null) animation.tick(camera);
    }

    @Override
    public boolean shouldStop() {
        return this.index >= animations.size();
    }

    @Override
    public boolean shouldRenderSelfEntity() {
        Animation animation = this.getCurrentAnimation();
        if (animation != null) return animation.shouldRenderSelfEntity();
        else return true;
    }
}
