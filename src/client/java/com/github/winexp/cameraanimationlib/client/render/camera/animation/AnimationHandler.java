package com.github.winexp.cameraanimationlib.client.render.camera;

public interface CameraAddon {
    <T extends Animation> T getAnimation();

    boolean isAnimating();

    <T extends Animation> void startAnimation(T animation);

    void stopAnimation();
}
