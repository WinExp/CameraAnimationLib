package com.github.winexp.cameraanimationlib.client.render.camera.animation.segment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;

public class EmptySegment extends Segment {
    public static final Codec<EmptySegment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("length").forGetter(EmptySegment::getLength)
    ).apply(instance, EmptySegment::new));

    public EmptySegment(int length) {
        super(length, null, null, null, null, null);
    }
}
