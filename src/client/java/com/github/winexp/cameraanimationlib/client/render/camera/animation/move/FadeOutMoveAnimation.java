package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import com.github.winexp.cameraanimationlib.client.util.ModMathHelper;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class FadeOutMoveAnimation extends MoveAnimation {
    private final float speed;
    private final float maxMoveSpeed;
    private final float maxRotationSpeed;
    private Vec3d prevPos;
    private Vec3d pos;
    private Vec2f prevRotation;
    private Vec2f rotation;

    public FadeOutMoveAnimation(Vec3d startPos, Vec3d targetPos, Vec2f startRotation, Vec2f targetRotation, float speed, float maxMoveSpeed, float maxRotationSpeed) {
        super(startPos, targetPos, startRotation, targetRotation);
        this.prevPos = this.pos = startPos;
        this.prevRotation = this.rotation = startRotation;
        this.speed = speed;
        this.maxMoveSpeed = maxMoveSpeed;
        this.maxRotationSpeed = maxRotationSpeed;
    }

    public FadeOutMoveAnimation(Vec3d startPos, Vec3d targetPos, Vec2f startRotation, float speed, float maxMoveSpeed, float maxRotationSpeed) {
        this(startPos, targetPos, startRotation, null, speed, maxMoveSpeed, maxRotationSpeed);
    }

    public FadeOutMoveAnimation(Vec3d startPos, Vec3d targetPos, float speed, float maxMoveSpeed, float maxRotationSpeed) {
        this(startPos, targetPos, null, null, speed, maxMoveSpeed, maxRotationSpeed);
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getMaxMoveSpeed() {
        return this.maxMoveSpeed;
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
        Vec3d moveDistance = this.getTargetPos().subtract(this.pos).multiply(this.speed);
        float blockSpeed = moveDistance.length() > this.maxMoveSpeed ? this.maxMoveSpeed / (float) moveDistance.length() : 1.0F;
        this.prevPos = this.pos;
        this.pos = this.pos.add(moveDistance.multiply(blockSpeed));
        this.prevRotation = this.rotation;
        if (this.getTargetRotation() != null) {
            assert this.getStartRotation() != null;
            Vec2f rotationDistance = this.getTargetRotation().add(this.rotation.negate()).multiply(this.speed);
            float rotationSpeed = rotationDistance.length() > this.maxRotationSpeed ? this.maxRotationSpeed / rotationDistance.length() : 1.0F;
            this.rotation = this.rotation.add(rotationDistance.multiply(rotationSpeed));
        }
    }

    @Override
    public boolean shouldStop() {
        return this.pos.distanceTo(this.getTargetPos()) <= 0.1 && Math.sqrt(this.rotation.distanceSquared(this.getTargetRotation())) <= 0.1;
    }

    @Override
    public boolean shouldRenderSelfEntity() {
        return true;
    }
}
