package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;

public abstract class InterpolationMoveAnimation extends MoveAnimation {
    private final int moveTime;
    private final int totalTime;
    private int animatedTime;
    private Vec3d prevPos;
    private Vec3d nextPos;

    protected InterpolationMoveAnimation(Vec3d from, Vec3d to, int moveTime, int totalTime) {
        super(from, to);
        this.moveTime = moveTime;
        this.totalTime = totalTime;
        this.prevPos = this.nextPos = from;
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
    public void tick(Camera camera) {
        this.animatedTime++;
        float progress = (float) Math.min(this.animatedTime, this.moveTime) / this.moveTime;
        this.prevPos = this.nextPos;
        this.nextPos = this.getFrom().lerp(this.getTo(), this.getDelta(progress));
    }

    @Override
    public boolean shouldStop() {
        return this.getRemainingTime() <= 0;
    }

    @Override
    protected Vec3d getPrevPos() {
        return this.prevPos;
    }

    @Override
    protected Vec3d getNextPos() {
        return this.nextPos;
    }

    protected abstract float getDelta(float progress);
}
