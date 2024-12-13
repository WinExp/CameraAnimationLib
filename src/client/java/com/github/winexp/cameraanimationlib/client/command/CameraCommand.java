package com.github.winexp.cameraanimationlib.client.command;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.Animation;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.Animator;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.move.EaseSineMoveAnimation;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.move.LinearMoveAnimation;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.segment.EmptySegment;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.segment.InterpolationSegment;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.segment.SegmentAnimation;
import com.github.winexp.cameraanimationlib.client.util.ModMathHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class CameraCommand implements ClientCommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        var cRoot = literal("camera");
        var cStop = literal("stop").executes(this::executeStop);

        var cTest = literal("segment")
                .then(argument("pos", Vec3ArgumentType.vec3()).executes(this::executeTest));
        var cLinear = literal("linear")
                .then(argument("pos", Vec3ArgumentType.vec3())
                        .then(argument("moveTime", IntegerArgumentType.integer(1))
                                .then(argument("totalTime", IntegerArgumentType.integer(1)).executes(context -> this.executeMove(context, "linear")))));
        var cEaseInSine = literal("easeInSine")
                .then(argument("pos", Vec3ArgumentType.vec3())
                        .then(argument("moveTime", IntegerArgumentType.integer(1))
                                .then(argument("totalTime", IntegerArgumentType.integer(1)).executes(context -> this.executeMove(context, "easeInSine")))));
        var cEaseOutSine = literal("easeOutSine")
                .then(argument("pos", Vec3ArgumentType.vec3())
                        .then(argument("moveTime", IntegerArgumentType.integer(1))
                                .then(argument("totalTime", IntegerArgumentType.integer(1)).executes(context -> this.executeMove(context, "easeOutSine")))));
        var cEaseInOutSine = literal("easeInOutSine")
                .then(argument("pos", Vec3ArgumentType.vec3())
                        .then(argument("moveTime", IntegerArgumentType.integer(1))
                                .then(argument("totalTime", IntegerArgumentType.integer(1)).executes(context -> this.executeMove(context, "easeInOutSine")))));
        var cPosition = literal("position").then(cLinear).then(cEaseInSine).then(cEaseOutSine).then(cEaseInOutSine);
        dispatcher.register(cRoot.then(cStop).then(cPosition).then(cTest));
    }

    private int executeTest(CommandContext<FabricClientCommandSource> context) {
        Vec3d pos = context.getArgument("pos", PosArgument.class).toAbsolutePos(context.getSource().getPlayer().getCommandSource());
        ClientPlayerEntity player = context.getSource().getPlayer();
        Animation animation = new SegmentAnimation.Builder()
                .add(new InterpolationSegment(30, ModMathHelper::easeInSine, player.getEyePos(), pos.add(2, -2, -2), ModMathHelper.swap(player.getRotationClient()), new Vec2f(45, -45)))
                .add(new InterpolationSegment(30, ModMathHelper::easeOutSine, pos.add(2, -2, -2), pos.add(-2, 2, 2), new Vec2f(45, -45), new Vec2f(-136, 45)))
                .add(new EmptySegment(30))
                .build();
        Animator.INSTANCE.enqueue(animation);

        return 1;
    }

    private int executeMove(CommandContext<FabricClientCommandSource> context, String type) throws CommandSyntaxException {
        Vec3d pos = context.getArgument("pos", PosArgument.class).toAbsolutePos(context.getSource().getPlayer().getCommandSource());
        int moveTime = IntegerArgumentType.getInteger(context, "moveTime");
        int totalTime = IntegerArgumentType.getInteger(context, "totalTime");
        Animation animation = switch (type) {
            case "linear" -> new LinearMoveAnimation(context.getSource().getPlayer().getEyePos(), pos, moveTime, totalTime);
            case "easeInSine" -> new EaseSineMoveAnimation(context.getSource().getPlayer().getEyePos(), pos, moveTime, totalTime, EaseSineMoveAnimation.Type.EASE_IN);
            case "easeOutSine" -> new EaseSineMoveAnimation(context.getSource().getPlayer().getEyePos(), pos, moveTime, totalTime, EaseSineMoveAnimation.Type.EASE_OUT);
            case "easeInOutSine" -> new EaseSineMoveAnimation(context.getSource().getPlayer().getEyePos(), pos, moveTime, totalTime, EaseSineMoveAnimation.Type.EASE_IN_OUT);
            default -> throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
        };
        Animator.INSTANCE.enqueue(animation);

        return 1;
    }

    private int executeStop(CommandContext<FabricClientCommandSource> context) {
        Animator.INSTANCE.stop();
        Animator.INSTANCE.clear();

        return 1;
    }
}
