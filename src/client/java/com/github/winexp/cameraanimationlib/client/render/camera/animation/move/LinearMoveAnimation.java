package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import com.github.winexp.cameraanimationlib.client.util.ModMathHelper;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class LinearMoveAnimation extends MoveAnimation {
    private final int moveTime;
    private final int totalTime;
    private boolean ready;
    private int animatedTime;
    private Vec3d prevPos;
    private Vec3d pos;
    private Vec2f prevRotation;
    private Vec2f rotation;

    public LinearMoveAnimation(Vec3d startPos, Vec3d targetPos, Vec2f startRotation, Vec2f targetRotation, int moveTime, int totalTime) {
        super(startPos, targetPos, startRotation, targetRotation);
        this.moveTime = moveTime;
        this.totalTime = totalTime;
        this.prevPos = this.pos = startPos;
        if (startRotation != null) this.prevRotation = this.rotation = startRotation;
    }

    public LinearMoveAnimation(Vec3d startPos, Vec3d targetPos, Vec2f startRotation, int moveTime, int totalTime) {
        this(startPos, targetPos, startRotation, null, moveTime, totalTime);
    }

    public LinearMoveAnimation(Vec3d startPos, Vec3d targetPos, int moveTime, int totalTime) {
        this(startPos, targetPos, null, null, moveTime, totalTime);
    }

    public int getMoveTime() {
        return this.moveTime;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public int getAnimatedTime() {
        return this.animatedTime;
    }

    public int getRemainingTime() {
        return this.totalTime - this.animatedTime;
    }

    @Override
    public void update(Camera camera, float tickDelta) {
        camera.setPos(this.prevPos.lerp(this.pos, tickDelta));
        if (this.getStartRotation() != null) {
            Vec2f rotation = ModMathHelper.lerp(tickDelta, this.prevRotation, this.rotation);
            camera.setRotation(rotation.y, rotation.x);
        }
    }

    @Override
    public void tick(Camera camera) {
        if (this.ready) this.animatedTime++;
        float delta = (float) Math.min(this.animatedTime, this.moveTime) / this.moveTime;
        this.prevPos = this.pos;
        this.pos = this.getStartPos().lerp(this.getTargetPos(), delta);
        this.prevRotation = this.rotation;
        if (this.getTargetRotation() != null) {
            assert this.getStartRotation() != null;
            this.rotation = ModMathHelper.lerp(delta, this.getStartRotation(), this.getTargetRotation());
        }
        this.ready = true;
    }

    @Override
    public boolean shouldStop() {
        return this.getRemainingTime() <= 0;
    }

    @Override
    public boolean shouldRenderSelfEntity() {
        return true;
    }
}
