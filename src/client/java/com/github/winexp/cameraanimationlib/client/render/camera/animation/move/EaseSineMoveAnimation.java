package com.github.winexp.cameraanimationlib.client.render.camera.animation.move;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.DeltaProvider;
import com.github.winexp.cameraanimationlib.client.util.ModMathHelper;
import net.minecraft.util.math.Vec3d;

public class EaseSineMoveAnimation extends InterpolationMoveAnimation {
    private final Type type;

    public EaseSineMoveAnimation(Vec3d from, Vec3d to, int moveTime, int totalTime, Type type) {
        super(from, to, moveTime, totalTime);
        this.type = type;
    }

    @Override
    protected float getDelta(float progress) {
        return this.type.deltaProvider.getDelta(progress);
    }

    public enum Type {
        EASE_IN(ModMathHelper::easeInSine),
        EASE_OUT(ModMathHelper::easeOutSine),
        EASE_IN_OUT(ModMathHelper::easeInOutSine);

        private final DeltaProvider deltaProvider;

        Type(DeltaProvider deltaProvider) {
            this.deltaProvider = deltaProvider;
        }
    }
}
