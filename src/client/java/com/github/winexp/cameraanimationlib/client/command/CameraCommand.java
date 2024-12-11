package com.github.winexp.cameraanimationlib.client.command;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.AnimationHandler;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.CombineAnimation;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.move.FadeOutMoveAnimation;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.move.LinearMoveAnimation;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class CameraCommand implements ClientCommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        var cRoot = literal("camera");
        var cSetPos = literal("moveToPos")
                .then(argument("pos", Vec3ArgumentType.vec3()).executes(this::executeMoveToPos));
        dispatcher.register(cRoot.then(cSetPos));
    }

    private int executeMoveToPos(CommandContext<FabricClientCommandSource> context) {
        Vec3d pos = context.getArgument("pos", PosArgument.class).toAbsolutePos(context.getSource().getPlayer().getCommandSource());
        AnimationHandler handler = (AnimationHandler) context.getSource().getClient().gameRenderer.getCamera();
        ClientPlayerEntity player = context.getSource().getPlayer();
        LinearMoveAnimation linearAnimation = new LinearMoveAnimation(player.getEyePos(), pos.add(0, -5, 0), player.getRotationClient(), player.getRotationClient().add(new Vec2f(0, 90)), 30, 80);
        FadeOutMoveAnimation fadeOutAnimation = new FadeOutMoveAnimation(pos.add(0, -5, 0), pos, player.getRotationClient().add(new Vec2f(0, 90)), player.getRotationClient().add(new Vec2f(0, 180)), 0.1F, 0.5F, 2.5F);
        handler.cameraAnimationLib$startAnimation(new CombineAnimation(List.of(linearAnimation, fadeOutAnimation)));

        return 1;
    }
}
