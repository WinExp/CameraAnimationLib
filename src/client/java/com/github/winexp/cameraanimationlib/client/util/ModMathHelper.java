package com.github.winexp.cameraanimationlib.client.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

public class ModMathHelper {
    public static Vec2f swap(Vec2f vec2f) {
        return new Vec2f(vec2f.y, vec2f.x);
    }

    public static Vec2f lerp(float delta, Vec2f from, Vec2f to) {
        return new Vec2f(MathHelper.lerpAngleDegrees(delta, from.x, to.x),
                MathHelper.lerpAngleDegrees(delta, from.y, to.y));
    }

    public static float easeInSine(float progress) {
        return 1.0F - MathHelper.cos(MathHelper.PI * progress / 2);
    }

    public static float easeOutSine(float progress) {
        return MathHelper.sin(MathHelper.PI * progress / 2);
    }

    public static float easeInOutSine(float progress) {
        return -(MathHelper.cos(MathHelper.PI * progress) - 1) / 2;
    }
}
