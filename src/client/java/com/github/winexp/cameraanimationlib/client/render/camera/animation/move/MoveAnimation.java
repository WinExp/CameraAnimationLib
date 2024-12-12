package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.Animation;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;

public abstract class MoveAnimation implements Animation {
    private final Vec3d from;
    private final Vec3d to;

    protected MoveAnimation(Vec3d from, Vec3d to) {
        this.from = from;
        this.to = to;
    }

    public final Vec3d getFrom() {
        return this.from;
    }

    public final Vec3d getTo() {
        return this.to;
    }

    @Override
    public void update(Camera camera, float tickDelta) {
        camera.setPos(this.getPrevPos().lerp(this.getNextPos(), tickDelta));
    }

    @Override
    public boolean shouldRenderSelfEntity() {
        return true;
    }

    protected abstract Vec3d getPrevPos();

    protected abstract Vec3d getNextPos();
}
