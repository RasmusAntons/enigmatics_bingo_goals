package de.rasmusantons.enigmaticsbingogoals;

import com.mojang.logging.LogUtils;
import de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalIds;
import io.github.gaming32.bingo.ext.MinecraftServerExt;
import io.github.gaming32.bingo.game.BingoBoard;
import io.github.gaming32.bingo.game.BingoGame;
import io.github.gaming32.bingo.game.GoalProgress;
import io.github.gaming32.bingo.util.BingoUtil;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.function.FailablePredicate;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class EnigmaticsBingoGoalsClientGameTest implements FabricClientGameTest {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int PLAYER_Y = -60;
    private static final List<String> failedTests = new ArrayList<>();
    private static boolean syncPacketReceived = false;

    @TestGoal
    private static void testEmptyHungerGoal(ClientGameTestContext context, TestSingleplayerContext singleplayerContext) {
        testGoal(context, singleplayerContext, EnigmaticsBingoGoalIds.VeryEasy.EMPTY_HUNGER, () -> {
            singleplayerContext.getServer().runOnServer(server -> {
                getServerPlayer(server).getFoodData().setFoodLevel(0);
                getServerPlayer(server).getFoodData().setSaturation(0);
            });
            context.waitTick();
            singleplayerContext.getServer().runOnServer(server -> {
                getServerPlayer(server).getFoodData().setFoodLevel(20);
                getServerPlayer(server).getFoodData().setSaturation(20);
            });
        });
    }

    @TestGoal
    private static void testObtainAllWoodenToolsGoal(ClientGameTestContext context, TestSingleplayerContext singleplayerContext) {
        testGoal(context, singleplayerContext, EnigmaticsBingoGoalIds.VeryEasy.OBTAIN_ALL_WOODEN_TOOLS, () -> {
            singleplayerContext.getServer().runCommand("give @a wooden_shovel");
            singleplayerContext.getServer().runCommand("give @a wooden_axe");
            singleplayerContext.getServer().runCommand("give @a wooden_pickaxe");
            singleplayerContext.getServer().runCommand("give @a wooden_hoe");
            singleplayerContext.getServer().runCommand("give @a wooden_sword");
            singleplayerContext.getServer().runCommand("give @a wooden_spear");
        });
    }

    private static void testGoal(ClientGameTestContext context, TestSingleplayerContext singleplayerContext, Identifier goalId, Runnable testRunner) {
        testGoal(context, singleplayerContext, goalId, true, testRunner);
    }

    private static void testGoal(
            ClientGameTestContext context,
            TestSingleplayerContext singleplayerContext,
            Identifier goalId,
            @Nullable Boolean expectGoalAchievement,
            Runnable testRunner
    ) {
        commonSetup(context, singleplayerContext);
        singleplayerContext.getServer().runCommand("bingo start --require-goal " + goalId + " --size 1 red");
        context.waitTick();

        boolean testFailed = false;
        try {
            testRunner.run();
        } catch (Throwable e) {
            testFailed = true;
            LOGGER.error("Test failed", e);
        }

        context.waitTick();
        if (expectGoalAchievement != null) {
            testFailed |= singleplayerContext.getServer().computeOnServer(server -> (((MinecraftServerExt) server).bingo$getGame() == null) != expectGoalAchievement);
        }

        reportTestResult(context, goalId, testFailed);
    }

    private static void testNeverGoal(ClientGameTestContext context, TestSingleplayerContext singleplayerContext, Identifier goalId, Runnable testRunner) {
        commonSetup(context, singleplayerContext);
        singleplayerContext.getServer().runCommand("bingo start --require-goal " + goalId + " --size 2 red");
        context.waitTick();

        boolean testFailed = false;
        try {
            testRunner.run();
        } catch (Throwable e) {
            testFailed = true;
            LOGGER.error("Test failed", e);
        }

        context.waitTick();
        testFailed |= singleplayerContext.getServer().computeOnServer(server -> {
            BingoGame game = Objects.requireNonNull(((MinecraftServerExt) server).bingo$getGame());
            return Arrays.stream(game.getBoard().getStates()).anyMatch(BingoBoard.Teams::any);
        });

        reportTestResult(context, goalId, testFailed);
    }

    private static void commonSetup(ClientGameTestContext context, TestSingleplayerContext singleplayerContext) {
        context.runOnClient(client -> Objects.requireNonNull(client.player).setDeltaMovement(Vec3.ZERO));
        singleplayerContext.getServer().runCommand("clear @a");
        singleplayerContext.getServer().runCommand("fill -16 " + PLAYER_Y + " -16 16 " + (PLAYER_Y + 16) + " 16 air");
        singleplayerContext.getServer().runCommand("tp @a 0 " + PLAYER_Y + " 0 0 0");
        singleplayerContext.getServer().runOnServer(server -> {
            for (Entity entity : server.overworld().getAllEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        });
    }

    private static void reportTestResult(ClientGameTestContext context, Identifier goalId, boolean testFailed) {
        String testName = getCallingTest().getName() + "(" + goalId + ")";

        if (testFailed) {
            failedTests.add(testName);
            context.runOnClient(client -> client.gui.getChat().addClientSystemMessage(Component.literal("Test failed: " + testName).withStyle(ChatFormatting.RED)));
        } else {
            context.runOnClient(client -> client.gui.getChat().addClientSystemMessage(Component.literal("Test passed: " + testName).withStyle(ChatFormatting.GREEN)));
        }
    }

    private static void waitClientbound(ClientGameTestContext context, TestSingleplayerContext singleplayerContext) {
        singleplayerContext.getServer().runOnServer(server -> server.getPlayerList().broadcastAll(ServerPlayNetworking.createClientboundPacket(GametestSyncPayload.INSTANCE)));
        context.waitFor(_ -> syncPacketReceived);
        syncPacketReceived = false;
    }

    private static void waitServerbound(ClientGameTestContext context) {
        context.runOnClient(_ -> ClientPlayNetworking.send(GametestSyncPayload.INSTANCE));
        context.waitFor(_ -> syncPacketReceived);
        syncPacketReceived = false;
    }

    private static <E extends Throwable> void waitFor(ClientGameTestContext context, TestServerContext serverContext, FailablePredicate<MinecraftServer, E> predicate) throws E {
        waitFor(context, serverContext, predicate, ClientGameTestContext.DEFAULT_TIMEOUT);
    }

    private static <E extends Throwable> void waitFor(ClientGameTestContext context, TestServerContext serverContext, FailablePredicate<MinecraftServer, E> predicate, int timeout) throws E {
        for (int i = 0; i < timeout; i++) {
            if (serverContext.computeOnServer(predicate::test)) {
                return;
            }
            context.waitTick();
        }

        if (!serverContext.computeOnServer(predicate::test)) {
            throw new AssertionError("Timed out waiting for predicate");
        }
    }

    private static ServerPlayer getServerPlayer(MinecraftServer server) {
        return server.getPlayerList().getPlayers().getFirst();
    }

    private static GoalProgress getGoalProgress(MinecraftServer server) {
        BingoGame game = Objects.requireNonNull(((MinecraftServerExt) server).bingo$getGame());
        return Objects.requireNonNull(game.getGoalProgress(getServerPlayer(server), game.getBoard().getGoals()[0]));
    }

    @Override
    public void runTest(@NonNull ClientGameTestContext context) {
        context.runOnClient(_ -> {
            PayloadTypeRegistry.serverboundPlay().register(GametestSyncPayload.TYPE, GametestSyncPayload.CODEC);
            PayloadTypeRegistry.clientboundPlay().register(GametestSyncPayload.TYPE, GametestSyncPayload.CODEC);
            ClientPlayNetworking.registerGlobalReceiver(GametestSyncPayload.TYPE, (_, _) -> syncPacketReceived = true);
            ServerPlayNetworking.registerGlobalReceiver(GametestSyncPayload.TYPE, (_, _) -> syncPacketReceived = true);
        });

        try (TestSingleplayerContext singleplayerContext = context.worldBuilder().create()) {
            singleplayerContext.getServer().runCommand("bingo teams create red");
            singleplayerContext.getServer().runCommand("bingo teams randomize");

            failedTests.clear();

            for (Method method : getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(TestGoal.class)) {
                    if (!Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalStateException("Goal test methods must be static");
                    }
                    Object[] args = new Object[method.getParameterCount()];
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    for (int i = 0; i < parameterTypes.length; i++) {
                        Class<?> paramType = parameterTypes[i];
                        if (paramType == ClientGameTestContext.class) {
                            args[i] = context;
                        } else if (paramType == TestSingleplayerContext.class) {
                            args[i] = singleplayerContext;
                        } else {
                            throw new IllegalStateException("Illegal parameter type " + paramType + " in " + method);
                        }
                    }
                    try {
                        method.invoke(null, args);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw BingoUtil.sneakyThrow(e.getCause());
                    }
                }
            }
        }

        if (!failedTests.isEmpty()) {
            throw new IllegalStateException("There were failing tests: " + failedTests);
        }
    }

    private static Method getCallingTest() {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(frames ->
                frames.map(frame -> {
                    Class<?> clazz = frame.getDeclaringClass();
                    try {
                        return clazz.getDeclaredMethod(frame.getMethodName(), frame.getMethodType().parameterArray());
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }).filter(method -> method.isAnnotationPresent(TestGoal.class)).findFirst().orElseThrow()
        );
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestGoal {
    }
}
