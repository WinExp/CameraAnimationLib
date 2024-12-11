package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.Animation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    public final Vec2f getStartRotation() {
        return this.startRotation;
    }

    @Nullable
    public final Vec2f getTargetRotation() {
        return this.targetRotation;
    }
}
