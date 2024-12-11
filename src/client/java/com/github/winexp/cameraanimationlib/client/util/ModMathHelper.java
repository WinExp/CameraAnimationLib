package com.github.winexp.cameraanimationlib.client.util;

import net.minecraft.util.math.Vec2f;

public class MathHelper {
    public static Vec2f lerp(float delta, Vec2f from, Vec2f to) {
        return new Vec2f(net.minecraft.util.math.MathHelper.lerpAngleDegrees(delta, from.x, to.x),
                net.minecraft.util.math.MathHelper.lerpAngleDegrees(delta, from.y, to.y));
    }
}
