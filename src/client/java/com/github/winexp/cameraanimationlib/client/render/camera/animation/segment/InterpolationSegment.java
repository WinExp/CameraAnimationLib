package com.github.winexp.cameraanimationlib.client.render.camera.animation.segment;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.DeltaProvider;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class InterpolationSegment extends Segment {
    public InterpolationSegment(int length, DeltaProvider deltaProvider, Vec3d beginPos, Vec3d endPos, Vec2f beginRotation, Vec2f endRotation) {
        super(length, deltaProvider, beginPos, endPos, beginRotation, endRotation);
    }
}
