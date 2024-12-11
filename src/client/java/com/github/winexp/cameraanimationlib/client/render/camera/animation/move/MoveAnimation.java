package com.github.winexp.cameraanimationlib.client.render.camera.animation;

import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public abstract class MoveAnimation implements Animation {
    private final Vec3d startPos;
    private final Vec3d targetPos;
    private final Vec2f startRotation;
    private final Vec2f targetRotation;

    protected MoveAnimation(Vec3d startPos, Vec3d targetPos, Vec2f startRotation, Vec2f targetRotation) {
        this.startPos = startPos;
        this.targetPos = targetPos;
        this.startRotation = startRotation;
        this.targetRotation = targetRotation;
    }

    public final Vec3d getStartPos() {
        return this.startPos;
    }

    public final Vec3d getTargetPos() {
        return this.targetPos;
    }

    public final Vec2f getStartRotation() {
        return this.startRotation;
    }

    public final Vec2f getTargetRotation() {
        return this.targetRotation;
    }
}
