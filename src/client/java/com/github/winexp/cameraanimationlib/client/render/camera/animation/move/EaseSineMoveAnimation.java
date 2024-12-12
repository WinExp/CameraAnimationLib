package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import com.github.winexp.cameraanimationlib.client.util.ModMathHelper;
import net.minecraft.util.math.Vec3d;

public class EaseSineMoveAnimation extends InterpolationMoveAnimation {
    public EaseSineMoveAnimation(Vec3d from, Vec3d to, int moveTime, int totalTime) {
        super(from, to, moveTime, totalTime);
    }

    @Override
    protected float getDelta(float progress) {
        return ModMathHelper.easeInOutSine(progress);
    }
}
