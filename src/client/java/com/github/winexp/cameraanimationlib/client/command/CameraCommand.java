package com.github.winexp.cameraanimationlib.client.command;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.AnimationHandler;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.move.FadeOutMoveAnimation;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.util.math.Vec3d;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class CameraCommand implements ClientCommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        var cRoot = literal("camera");
        var cStop = literal("stop").executes(this::executeStop);
        var cSetPos = literal("moveToPos")
                .then(argument("pos", Vec3ArgumentType.vec3()).executes(this::executeMoveToPos));
        dispatcher.register(cRoot.then(cStop).then(cSetPos));
    }

    private int executeMoveToPos(CommandContext<FabricClientCommandSource> context) {
        Vec3d pos = context.getArgument("pos", PosArgument.class).toAbsolutePos(context.getSource().getPlayer().getCommandSource());
        AnimationHandler handler = (AnimationHandler) context.getSource().getClient().gameRenderer.getCamera();
        FadeOutMoveAnimation fadeOutAnimation = new FadeOutMoveAnimation(context.getSource().getPlayer().getEyePos(), pos, 0.2F, 0.3F, 2.5F);
        handler.cameraAnimationLib$startAnimation(fadeOutAnimation);

        return 1;
    }

    private int executeStop(CommandContext<FabricClientCommandSource> context) {
        AnimationHandler handler = (AnimationHandler) context.getSource().getClient().gameRenderer.getCamera();
        handler.cameraAnimationLib$stopAnimation();

        return 1;
    }
}
