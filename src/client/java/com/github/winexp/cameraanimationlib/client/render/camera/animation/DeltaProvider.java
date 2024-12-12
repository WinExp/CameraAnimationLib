package com.github.winexp.cameraanimationlib.client.render.camera.animation;

@FunctionalInterface
public interface DeltaProvider {
    DeltaProvider NONE = progress -> 1.0F;
    DeltaProvider LINEAR = progress -> progress;

    float getDelta(float progress);
}
