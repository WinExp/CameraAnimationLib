package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import net.minecraft.util.math.Vec3d;

public class LinearMoveAnimation extends InterpolationMoveAnimation {
    public LinearMoveAnimation(Vec3d from, Vec3d to, int moveTime, int totalTime) {
        super(from, to, moveTime, totalTime);
    }

    @Override
    protected float getDelta(float progress) {
        return progress;
    }
}
