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
        var cEaseSine = literal("easeSine")
                .then(argument("pos", Vec3ArgumentType.vec3())
                        .then(argument("moveTime", IntegerArgumentType.integer(1))
                                .then(argument("totalTime", IntegerArgumentType.integer(1)).executes(context -> this.executeMove(context, "easeSine")))));
        var cPosition = literal("position").then(cLinear).then(cEaseSine);
        dispatcher.register(cRoot.then(cStop).then(cPosition).then(cTest));
    }

    private int executeTest(CommandContext<FabricClientCommandSource> context) {
        Vec3d pos = context.getArgument("pos", PosArgument.class).toAbsolutePos(context.getSource().getPlayer().getCommandSource());
        Animation animation = new SegmentAnimation.Builder()
                .add(new InterpolationSegment(40, ModMathHelper::easeInOutSine, context.getSource().getPlayer().getEyePos(), pos.add(0, 0, 5), new Vec2f(-45, 0), new Vec2f(0, 90)))
                .add(new InterpolationSegment(40, ModMathHelper::easeInOutSine, pos.add(0, 0, 5), pos, new Vec2f(0, 90), new Vec2f(45, 180)))
                .add(new EmptySegment(40))
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
            case "easeSine" -> new EaseSineMoveAnimation(context.getSource().getPlayer().getEyePos(), pos, moveTime, totalTime);
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
