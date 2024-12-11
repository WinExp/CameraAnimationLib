package com.github.winexp.cameraanimationlib.client.render.camera.animation;

import net.minecraft.client.render.Camera;

public interface Animation {
    void update(Camera camera, float tickDelta);

    void tick(Camera camera);

    boolean shouldStop();

    boolean shouldRenderSelfEntity();
}
