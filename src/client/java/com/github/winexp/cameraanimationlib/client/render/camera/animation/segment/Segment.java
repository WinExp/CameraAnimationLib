package com.github.winexp.cameraanimationlib.client.render.camera.animation.segment;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.DeltaProvider;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public abstract class Segment {
    private final int length;
    private final DeltaProvider deltaProvider;
    private final Vec3d beginPos;
    private final Vec3d endPos;
    private final Vec2f beginRotation;
    private final Vec2f endRotation;

    protected Segment(int length, DeltaProvider deltaProvider, Vec3d beginPos, Vec3d endPos, Vec2f beginRotation, Vec2f endRotation) {
        this.length = length;
        this.deltaProvider = deltaProvider;
        this.beginPos = beginPos;
        this.endPos = endPos;
        this.beginRotation = beginRotation;
        this.endRotation = endRotation;
    }

    public int getLength() {
        return this.length;
    }

    public DeltaProvider getDeltaProvider() {
        return this.deltaProvider;
    }

    public Vec3d getBeginPos() {
        return this.beginPos;
    }

    public Vec3d getEndPos() {
        return this.endPos;
    }

    public Vec2f getBeginRotation() {
        return this.beginRotation;
    }

    public Vec2f getEndRotation() {
        return this.endRotation;
    }
}
