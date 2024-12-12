package com.github.winexp.cameraanimationlib.client.render.camera.animation.segment;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.DeltaProvider;
import com.github.winexp.cameraanimationlib.client.util.ModCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class KeepSegment extends Segment {
    public static final Codec<KeepSegment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("length").forGetter(KeepSegment::getLength),
            Vec3d.CODEC.fieldOf("pos").forGetter(KeepSegment::getBeginPos),
            ModCodecs.VEC2F.fieldOf("rotation").forGetter(KeepSegment::getBeginRotation)
    ).apply(instance, KeepSegment::new));

    public KeepSegment(int length, Vec3d pos, Vec2f rotation) {
        super(length, DeltaProvider.NONE, pos, pos, rotation, rotation);
    }
}
