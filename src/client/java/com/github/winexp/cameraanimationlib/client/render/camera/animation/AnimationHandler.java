package com.github.winexp.cameraanimationlib.client.render.camera.animation;

public interface AnimationHandler {
    Animation cameraAnimationLib$getAnimation();

    boolean cameraAnimationLib$isAnimating();

    void cameraAnimationLib$startAnimation(Animation animation);

    void cameraAnimationLib$stopAnimation();

    void cameraAnimationLib$tick();
}
