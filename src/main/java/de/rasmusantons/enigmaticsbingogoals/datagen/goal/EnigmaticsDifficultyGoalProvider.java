package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoGoals;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.NumberOfEffectsCondition;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.datagen.goal.BingoGoalGeneratorUtils.WolfVariantCollector;
import de.rasmusantons.enigmaticsbingogoals.triggers.*;
import io.github.gaming32.bingo.Bingo;
import io.github.gaming32.bingo.conditions.WearingDifferentArmorCondition;
import io.github.gaming32.bingo.data.BingoDifficulty;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.goal.GoalBuilder;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.subs.BingoSub;
import io.github.gaming32.bingo.fabric.datagen.goal.DifficultyGoalProvider;
import io.github.gaming32.bingo.triggers.*;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.inventory.SlotRange;
import net.minecraft.world.inventory.SlotRanges;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.BingoGoalGeneratorUtils.getAllEffectsIcon;

public abstract class EnigmaticsDifficultyGoalProvider extends DifficultyGoalProvider {
    public EnigmaticsDifficultyGoalProvider(ResourceKey<BingoDifficulty> difficulty,
                                            BiConsumer<ResourceLocation, BingoGoal> goalAdder,
                                            HolderLookup.Provider registries) {
        super(difficulty, goalAdder, registries);
    }

    protected final ResourceLocation eid(String path) {
        return ResourceLocation.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, id(path).getPath());
    }

    protected static GoalBuilder obtainSomeItemsGoal(ResourceLocation id, HolderLookup<Item> items, Item item, int min, int max) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(min, max))
                .criterion("obtain", TotalCountInventoryChangeTrigger.builder().items(
                                ItemPredicate.Builder.item().of(items, item).withCount(MinMaxBounds.Ints.atLeast(0)).build()).build(),
                        subber -> subber.sub("conditions.items.0.count.min", "count"))
                .tags(BingoTags.ITEM)
                .name(Component.translatable("enigmaticsbingogoals.goal.get_some_items", 0, item.getName()),
                        subber -> subber.sub("with.0", "count"))
                .icon(ItemIcon.ofItem(item),
                        subber -> subber.sub("item.count", "count"));
    }

    protected static GoalBuilder obtainItemGoal(ResourceLocation id, HolderLookup<Item> items, ItemLike item) {
        return obtainItemGoal(id, items, item, 1);
    }

    protected static GoalBuilder obtainItemGoal(ResourceLocation id, HolderLookup<Item> items, ItemLike item, int count) {
        Component itemName = item.asItem().getName();
        GoalBuilder builder = BingoGoal.builder(id)
                .criterion("obtain", TotalCountInventoryChangeTrigger.builder().items(
                        ItemPredicate.Builder.item().of(items, item).withCount(MinMaxBounds.Ints.atLeast(count)).build()
                ).build())
                .tags(BingoTags.ITEM);
        if (count == 1)
            builder
                    .icon(ItemIcon.ofItem(item))
                    .name(Component.translatable("enigmaticsbingogoals.goal.obtain_item", itemName));
        else
            builder
                    .progress("obtain")
                    .icon(new ItemIcon(new ItemStack(item, count)))
                    .name(Component.translatable("enigmaticsbingogoals.goal.obtain_item_count", count, itemName));
        return builder;
    }

    protected static GoalBuilder obtainAllItemsFromTagGoal(ResourceLocation id, TagKey<Item> tag) {
        return BingoGoal.builder(id)
                .criterion("obtain", HasSomeItemsFromTagTrigger.builder().tag(tag).requiresAll().build())
                .progress("obtain")
                .tags(BingoTags.ITEM)
                .icon(new ItemTagCycleIcon(tag));
    }

    protected static GoalBuilder obtainSomeItemsFromTagGoal(ResourceLocation id, TagKey<Item> tag, int min, int max) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(min, max))
                .criterion("obtain", HasSomeItemsFromTagTrigger.builder().tag(tag).requiredCount(1).build(),
                        subber -> subber.sub("conditions.required_count", "count"))
                .progress("obtain")
                .tags(BingoTags.ITEM)
                .icon(
                        new ItemTagCycleIcon(tag, 0),
                        subber -> subber.sub("count", "count")
                );
    }

    protected static GoalBuilder killEntitiesFromTagGoal(ResourceLocation id, TagKey<EntityType<?>> typeTag, int min, int max, boolean unique) {
        GoalIcon goalIcon = BingoGoalGeneratorUtils.getEntityIcon(typeTag, 2);
        boolean nativeIcon = goalIcon instanceof EntityTypeTagCycleIcon;

        return BingoGoal.builder(id)
                .sub("amount", BingoSub.random(min, max))
                .criterion("kill", KillMobsTrigger.TriggerInstance.ofTag(typeTag, 1, unique),
                        subber -> subber.sub("conditions.amount", "amount"))
                .progress("kill")
                .icon(
                        IndicatorIcon.infer(
                                goalIcon,
                                Items.NETHERITE_SWORD
                        ),
                        subber -> subber.sub(nativeIcon ? "base.count" : "base.icons.*.item.count", "amount")
                );
    }

    protected static GoalBuilder dieToDamageTypeGoal(ResourceLocation id, TagKey<DamageType> damageType) {
        return BingoGoal.builder(id)
                .criterion("damage", BingoTriggers.DEATH.get().createCriterion(
                                DeathTrigger.TriggerInstance.death(
                                        DamageSourcePredicate.Builder.damageType()
                                                .tag(TagPredicate.is(damageType))
                                                .build()
                                )
                        )
                )
                .reactant(EnigmaticsBingoSynergies.TAKE_DAMAGE)
                .reactant(EnigmaticsBingoSynergies.DIE);
    }

    protected static GoalBuilder eatItemGoal(ResourceLocation id, HolderLookup<Item> items, Item item) {
        return BingoGoal.builder(id)
                .criterion("eat", ConsumeItemTrigger.TriggerInstance.usedItem(items, item))
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_something", item.getName()))
                .icon(ItemIcon.ofItem(item));
    }

    @SafeVarargs
    protected static GoalBuilder potionGoal(ResourceLocation id, HolderLookup<Item> items, Holder<Potion>... potions) {
        ItemStack potionItem = PotionContents.createItemStack(Items.POTION, potions[0]);
        return obtainItemGoal(
                id,
                items,
                potionItem,
                Arrays.stream(potions)
                        .map(potion -> ItemPredicate.Builder.item()
                                .of(items, net.minecraft.world.item.Items.POTION)
                                .withSubPredicate(
                                        ItemSubPredicates.POTIONS,
                                        new ItemPotionsPredicate(HolderSet.direct(potion))
                                )
                        )
                        .toArray(ItemPredicate.Builder[]::new)
        )
                .tags(EnigmaticsBingoTags.POTIONS)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_item", net.minecraft.world.item.Items.POTION.getName(potionItem)));
    }

    protected static GoalBuilder effectGoal(ResourceLocation id, Holder<MobEffect> effect) {
        return BingoGoal.builder(id)
                .criterion("effect", EffectsChangedTrigger.TriggerInstance.hasEffects(
                                MobEffectsPredicate.Builder.effects().and(effect)
                        )
                )
                .tags(EnigmaticsBingoTags.GET_EFFECT)
                .reactant(EnigmaticsBingoSynergies.GET_EFFECT)
                .name(Component.translatable("enigmaticsbingogoals.goal.get_effect", effect.value().getDisplayName()))
                .icon(EffectIcon.of(effect));
    }

    protected static GoalBuilder numberOfEffectsGoal(ResourceLocation id, int minEffects, int maxEffects) {
        ItemStack icon = new ItemStack(net.minecraft.world.item.Items.GLASS_BOTTLE);
        icon.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minEffects, maxEffects))
                .criterion("effects", CriteriaTriggers.EFFECTS_CHANGED.createCriterion(new EffectsChangedTrigger.TriggerInstance(
                        Optional.of(ContextAwarePredicate.create(new NumberOfEffectsCondition(MinMaxBounds.Ints.atLeast(0)))),
                        Optional.empty(),
                        Optional.empty()
                )), subber -> subber.sub("conditions.player.0.effects.min", "count"))
                .antisynergy(EnigmaticsBingoSynergies.GET_EFFECT_BATCH)
                .catalyst(EnigmaticsBingoSynergies.GET_EFFECT)
                .icon(IndicatorIcon.infer(new ItemIcon(icon), getAllEffectsIcon()),
                        subber -> subber.sub("base.item.count", "count"))
                .name(Component.translatable("enigmaticsbingogoals.goal.get_some_effects", 0),
                        subber -> subber.sub("with.0", "count"));
    }

    protected static GoalBuilder dieToMobEntityGoal(ResourceLocation id, HolderGetter<EntityType<?>> entityTypes, EntityType<?> entityType) {
        return BingoGoal.builder(id)
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(DeathTrigger.TriggerInstance.death(
                                DamageSourcePredicate.Builder.damageType()
                                        .source(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, entityType)))
                                        .build()
                        )
                ))
                .tags(EnigmaticsBingoTags.DIE_TO)
                .reactant(EnigmaticsBingoSynergies.TAKE_DAMAGE)
                .reactant(EnigmaticsBingoSynergies.DIE)
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.directly_killed.tooltip", entityType.getDescription()))
                .icon(IndicatorIcon.infer(entityType, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)));
    }

    protected static GoalBuilder dieToEntityGoal(ResourceLocation id, HolderGetter<EntityType<?>> entityTypes, EntityType<?> entityType, GoalIcon icon) {
        return BingoGoal.builder(id)
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(DeathTrigger.TriggerInstance.death(
                                DamageSourcePredicate.Builder.damageType()
                                        .direct(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, entityType)))
                                        .build()
                        )
                ))
                .tags(EnigmaticsBingoTags.DIE_TO)
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.directly_killed.tooltip", entityType.getDescription()))
                .icon(IndicatorIcon.infer(icon, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)));
    }

    protected static GoalBuilder killEntityGoal(ResourceLocation id, HolderGetter<EntityType<?>> entityTypes, EntityType<?> entityType) {
        return BingoGoal.builder(id)
                .criterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, entityType))))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .icon(IndicatorIcon.infer(entityType, net.minecraft.world.item.Items.NETHERITE_SWORD));
    }

    protected static GoalBuilder neverLevelsGoal(ResourceLocation id, int minLevels, int maxLevels) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minLevels, maxLevels))
                .criterion("obtain", ExperienceChangeTrigger.builder().levels(MinMaxBounds.Ints.atLeast(0)).build(),
                        subber -> subber.sub("conditions.levels.min", "count"))
                .tags(BingoTags.NEVER, BingoTags.STAT, EnigmaticsBingoTags.REACH_LEVEL, EnigmaticsBingoTags.LEVEL)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_levels", 0),
                        subber -> subber.sub("with.0", "count"))
                .icon(
                        new IndicatorIcon(ItemIcon.ofItem(net.minecraft.world.item.Items.EXPERIENCE_BOTTLE), ItemIcon.ofItem(net.minecraft.world.item.Items.BARRIER)),
                        subber -> subber.sub("base.item.count", "count"))
                .antisynergy("levels");
    }

    protected static GoalBuilder reachLevelsGoal(ResourceLocation id, int minLevels, int maxLevels) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minLevels, maxLevels))
                .criterion("obtain", ExperienceChangeTrigger.builder().levels(MinMaxBounds.Ints.atLeast(0)).build(),
                        subber -> subber.sub("conditions.levels.min", "count"))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.REACH_LEVEL, EnigmaticsBingoTags.LEVEL)
                .name(Component.translatable("enigmaticsbingogoals.goal.reach_levels", 0),
                        subber -> subber.sub("with.0", "count"))
                .icon(
                        ItemIcon.ofItem(net.minecraft.world.item.Items.EXPERIENCE_BOTTLE),
                        subber -> subber.sub("item.count", "count"))
                .antisynergy("levels");
    }

    protected static GoalBuilder neverDamageGoal(ResourceLocation id, int damage) {
        return BingoGoal.builder(id)
                .criterion("damage", RelativeStatsTrigger.builder()
                        .stat(Stats.DAMAGE_TAKEN, MinMaxBounds.Ints.atLeast(damage * 20)).build()
                )
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_some_hearts_damage", damage))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(net.minecraft.world.item.Items.BARRIER)))
                .progress(new CriterionProgressTracker("damage", 0.05f));
    }

    protected static GoalBuilder advancementProgressGoal(ResourceLocation id, ResourceLocation advancement, int minProgress, int maxProgress) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minProgress, maxProgress))
                .criterion("advance", AdvancementProgressTrigger.TriggerInstance.reach(
                                advancement,
                                MinMaxBounds.Ints.atLeast(0)
                        ),
                        subber -> subber.sub("conditions.count.min", "count")
                )
                .progress("advance");

    }

    protected static GoalBuilder advancementsGoal(ResourceLocation id, int minNumber, int maxNumber) {
        return BingoGoal.builder(id)
                .sub("number", BingoSub.random(minNumber, maxNumber))
                .criterion("achieve", AdvancementsTrigger.TriggerInstance.advancements(MinMaxBounds.Ints.atLeast(0)),
                        subber -> subber.sub("conditions.number.min", "number"))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.ADVANCEMENTS)
                .name(Component.translatable("enigmaticsbingogoals.goal.get_some_advancements", 0),
                        subber -> subber.sub("with.0", "number"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(net.minecraft.world.item.Items.ELYTRA), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)),
                        subber -> subber.sub("base.item.count", "number"))
                .progress("achieve");
    }

    protected static GoalBuilder advancementGoal(ResourceLocation id, @Nullable Component title, ResourceLocation... oneOfThese) {
        var builder = BingoGoal.builder(id);
        if (oneOfThese.length == 1) {
            builder.criterion("achieve", AdvancementsTrigger.TriggerInstance.advancement(oneOfThese[0]));
        } else if (oneOfThese.length > 1) {
            for (int i = 0; i < oneOfThese.length; i++) {
                builder.criterion("achieve_" + i, AdvancementsTrigger.TriggerInstance.advancement(oneOfThese[i]));
            }
            builder.requirements(AdvancementRequirements.Strategy.OR);
        }
        if (title != null)
            builder.name(Component.translatable("enigmaticsbingogoals.goal.get_advancement", title));
        return builder
                .tags(BingoTags.STAT, EnigmaticsBingoTags.ADVANCEMENTS);
    }

    protected static GoalBuilder breakBlockGoal(ResourceLocation id, HolderGetter<Block> blocks, Block... oneOfThese) {
        GoalBuilder builder = BingoGoal.builder(id);
        if (oneOfThese.length == 1) {
            builder
                    .criterion("break", BreakBlockTrigger.builder().block(blocks, oneOfThese[0]).build())
                    .icon(IndicatorIcon.infer(oneOfThese[0], net.minecraft.world.item.Items.NETHERITE_PICKAXE));
        } else {
            for (int i = 0; i < oneOfThese.length; i++)
                builder.criterion("break_" + i, BreakBlockTrigger.builder().block(blocks, oneOfThese[i]).build());
            builder.icon(
                    IndicatorIcon.infer(
                            CycleIcon.infer(Arrays.stream(oneOfThese).map(BlockIcon::ofBlock)),
                            net.minecraft.world.item.Items.NETHERITE_PICKAXE
                    )
            );
        }
        return builder
                .requirements(AdvancementRequirements.Strategy.OR)
                .name(Component.translatable("enigmaticsbingogoals.goal.break_block",
                        Component.translatable(oneOfThese[0].getDescriptionId())));
    }

    protected GoalBuilder wearDifferentMaterialsGoal(ResourceLocation id, int count) {
        return BingoGoal.builder(id)
                .criterion("armor", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                        new InventoryChangeTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(
                                        new WearingDifferentArmorCondition(
                                                MinMaxBounds.Ints.atLeast(count), MinMaxBounds.Ints.atLeast(count)
                                        ))
                                ),
                                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                                Collections.emptyList()
                        )
                ))
                .tags(EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_different_armor", count))
                .icon(BingoGoalGeneratorUtils.createAllDifferentMaterialsIcon(registries));
    }

    protected GoalBuilder wearArmorPiecesGoal(ResourceLocation id, HolderGetter<EntityType<?>> entityTypes, HolderGetter<Item> items,
                                              Item head, Item chest, Item legs, Item boots) {
        Map<SlotRange, ItemPredicate> armorItems = new HashMap<>();

        if (head != null) {
            armorItems.put(Objects.requireNonNull(SlotRanges.nameToIds("armor.head")),
                    ItemPredicate.Builder.item().of(items, head).build());
        }
        if (chest != null) {
            armorItems.put(Objects.requireNonNull(SlotRanges.nameToIds("armor.chest")),
                    ItemPredicate.Builder.item().of(items, chest).build());
        }
        if (legs != null) {
            armorItems.put(Objects.requireNonNull(SlotRanges.nameToIds("armor.legs")),
                    ItemPredicate.Builder.item().of(items, legs).build());
        }
        if (boots != null) {
            armorItems.put(Objects.requireNonNull(SlotRanges.nameToIds("armor.feet")),
                    ItemPredicate.Builder.item().of(items, boots).build());
        }

        return BingoGoal.builder(id)
                .criterion("wear",
                        CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                                new InventoryChangeTrigger.TriggerInstance(
                                        Optional.of(ContextAwarePredicate.create(
                                                LootItemEntityPropertyCondition.hasProperties(
                                                        LootContext.EntityTarget.THIS,
                                                        EntityPredicate.Builder.entity().of(entityTypes, EntityType.PLAYER)
                                                                .slots(new SlotsPredicate(armorItems))
                                                ).build()
                                        )),
                                        InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                                        Collections.emptyList()
                                )
                        )
                )
                .icon(CycleIcon.infer(Stream.of(head, chest, legs, boots).filter(Objects::nonNull).map(i -> new ItemStack(i, armorItems.size()))));
    }


    protected static GoalBuilder rideAbstractHorseWithSaddleGoal(ResourceLocation id, HolderGetter<EntityType<?>> entityTypes,
                                                                 HolderGetter<Item> items, EntityType<?> entityType) {
        var playerPredicate = Optional.of(ContextAwarePredicate.create(
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().of(entityTypes, EntityType.PLAYER).vehicle(
                                EntityPredicate.Builder.entity().of(entityTypes, EntityType.HORSE).slots(
                                        new SlotsPredicate(
                                                Map.of(
                                                        Objects.requireNonNull(SlotRanges.nameToIds("horse.saddle")),
                                                        ItemPredicate.Builder.item().of(items, net.minecraft.world.item.Items.SADDLE).build()
                                                )
                                        )
                                )
                        )
                ).build()
        ));
        return BingoGoal.builder(id)
                .criterion("change", EnigmaticsBingoGoalsTriggers.VEHICLE_INVENTORY_CHANGE.get().createCriterion(
                        new VehicleInventoryChangeTrigger.TriggerInstance(playerPredicate)
                ))
                .criterion("mount", CriteriaTriggers.START_RIDING_TRIGGER.createCriterion(
                        new StartRidingTrigger.TriggerInstance(playerPredicate)
                ))
                .requirements(AdvancementRequirements.Strategy.OR)
                .icon(IndicatorIcon.infer(entityType, net.minecraft.world.item.Items.SADDLE));
    }

    protected static GoalBuilder breedAnimalGoal(ResourceLocation id, HolderGetter<EntityType<?>> entityTypes, EntityType<?> entityType) {
        return BingoGoal.builder(id)
                .criterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(
                        EntityPredicate.Builder.entity().of(entityTypes, entityType)))
                .tags(EnigmaticsBingoTags.BREED_MOB)
                .reactant(EnigmaticsBingoSynergies.BREED_MOB)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_animal", entityType.getDescription()))
                .icon(IndicatorIcon.infer(entityType, EffectIcon.of(MobEffects.HEALTH_BOOST)));
    }

    protected static GoalBuilder tameAnimalGoal(ResourceLocation id, HolderGetter<EntityType<?>> entityTypes, EntityType<?> entityType) {
        return BingoGoal.builder(id)
                .criterion("tame", TameAnimalTrigger.TriggerInstance.tamedAnimal(
                        EntityPredicate.Builder.entity().of( entityTypes, entityType)))
                .tags(EnigmaticsBingoTags.TAME_ANIMAL)
                .name(Component.translatable("enigmaticsbingogoals.goal.tame_animal", entityType.getDescription()))
                .icon(IndicatorIcon.infer(entityType, ItemIcon.ofItem(net.minecraft.world.item.Items.BONE)));
    }

    protected static GoalBuilder tameSomeCatsGoal(ResourceLocation id, int minProgress, int maxProgress) {
        return advancementProgressGoal(id,
                ResourceLocation.withDefaultNamespace("husbandry/complete_catalogue"),
                minProgress,
                maxProgress
        )
                .name(Component.translatable("enigmaticsbingogoals.goal.tame_some_cats", 0,
                                EntityType.CAT.getDescription()),
                        subber -> subber.sub("with.0", "count")
                )
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.WITCH_HUT, BingoTags.VILLAGE)
                .antisynergy(EnigmaticsBingoSynergies.CAT)
                .icon(
                        IndicatorIcon.infer(
                                CycleIcon.infer(
                                        BuiltInRegistries.CAT_VARIANT.stream().map(h -> BingoGoalGeneratorUtils.getCatVariantIcon(
                                                BuiltInRegistries.CAT_VARIANT.wrapAsHolder(h).unwrapKey().orElseThrow()
                                        ))
                                ),
                                ItemIcon.ofItem(Items.COD)
                        ),
                        subber -> subber.sub("base.icons.*.item.count", "count")
                );
    }

    protected static GoalBuilder tameSomeWolvesGoal(ResourceLocation id, int minProgress, int maxProgress) {
        WolfVariantCollector wolfContext = new WolfVariantCollector();
        WolfVariants.bootstrap(wolfContext);
        return advancementProgressGoal(id,
                ResourceLocation.withDefaultNamespace("husbandry/whole_pack"),
                minProgress,
                maxProgress
        )
                .name(Component.translatable("enigmaticsbingogoals.goal.tame_some_wolves", 0,
                                EntityType.WOLF.getDescription()),
                        subber -> subber.sub("with.0", "count")
                )
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL)
                .antisynergy(EnigmaticsBingoSynergies.WOLF)
                .icon(
                        IndicatorIcon.infer(
                                CycleIcon.infer(
                                        wolfContext.variants.stream().map(BingoGoalGeneratorUtils::getWolfVariantIcon)
                                ),
                                ItemIcon.ofItem(Items.BONE)
                        ),
                        subber -> subber.sub("base.icons.*.item.count", "count")
                );
    }

    protected static GoalBuilder breedFrogVariantGoal(ResourceLocation id, ResourceKey<FrogVariant> variant) {
        return BingoGoal.builder(id)
                .criterion("hatch", TadpoleMaturesTrigger.TriggerInstance.ofVariant(variant))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.BREED_MOB, EnigmaticsBingoTags.SWAMP)
                .antisynergy(EnigmaticsBingoSynergies.FROG)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getFrogVariantIcon(variant), Items.SLIME_BALL))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.breed_frog.tooltip", EntityType.TADPOLE.getDescription()));
    }
    protected GoalBuilder makeBannerWithPatternItemGoal(ResourceLocation id, HolderGetter<Item> items, ItemLike patternItem, ResourceKey<BannerPattern> pattern, String patternName) {
        HolderLookup.RegistryLookup<BannerPattern> bannerPatterns = registries.lookupOrThrow(Registries.BANNER_PATTERN);
        return BingoGoal.builder(id)
                .criterion("use", EnigmaticsBingoGoalsTriggers.USE_LOOM.get().createCriterion(
                                new UseLoomTrigger.TriggerInstance(
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.of(ItemPredicate.Builder.item().of(items, patternItem).build())
                                )
                        )
                )
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.USE_WORKSTATION)
                .antisynergy(EnigmaticsBingoSynergies.LOOM)
                .name(Component.translatable("enigmaticsbingogoals.goal.use_loom_pattern", patternName))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.use_loom_pattern.tooltip"))
                .icon(IndicatorIcon.infer(
                        makeBannerWithPattern(Items.WHITE_BANNER, bannerPatterns.getOrThrow(pattern), DyeColor.BLACK),
                        BlockIcon.ofBlock(Blocks.LOOM)
                ));

    }
}
