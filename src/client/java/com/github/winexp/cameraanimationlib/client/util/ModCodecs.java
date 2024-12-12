package com.github.winexp.cameraanimationlib.client.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec2f;

import java.util.List;

public class ModCodecs {
    public static final Codec<Vec2f> VEC2F = Codec.FLOAT.listOf().comapFlatMap((coordinates) ->
            Util.decodeFixedLengthList(coordinates, 2).map((coords) ->
                    new Vec2f(coords.getFirst(), coords.getLast())), (vec) ->
            List.of(vec.x, vec.y));
}
