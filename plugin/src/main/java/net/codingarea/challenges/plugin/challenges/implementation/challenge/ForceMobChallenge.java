package net.codingarea.challenges.plugin.challenges.implementation.challenge;

import net.codingarea.challenges.plugin.ChallengeAPI;
import net.codingarea.challenges.plugin.challenges.type.CompletableForceChallenge;
import net.codingarea.challenges.plugin.challenges.type.helper.ChallengeHelper;
import net.codingarea.challenges.plugin.language.Message;
import net.codingarea.challenges.plugin.language.Prefix;
import net.codingarea.challenges.plugin.management.menu.MenuType;
import net.codingarea.challenges.plugin.management.server.scoreboard.ChallengeBossBar.BossBarInstance;
import net.codingarea.challenges.plugin.utils.item.ItemBuilder;
import net.codingarea.challenges.plugin.utils.misc.NameHelper;
import net.codingarea.challenges.plugin.utils.misc.RandomizeUtils;
import net.codingarea.challenges.plugin.utils.misc.Utils;
import net.anweisen.utilities.commons.misc.StringUtils;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class ForceMobChallenge extends CompletableForceChallenge {

	private EntityType entity;

	public ForceMobChallenge() {
		super(MenuType.CHALLENGES, 2, 15);
	}

	@Nonnull
	@Override
	public ItemBuilder createDisplayItem() {
		return new ItemBuilder(Material.DIAMOND_BOOTS, Message.forName("item-force-mob-challenge"));
	}

	@Nullable
	@Override
	protected String[] getSettingsDescription() {
		return ChallengeHelper.getTimeRangeSettingsDescription(this, 60, 30);
	}

	@Override
	public void playValueChangeTitle() {
		ChallengeHelper.playChallengeSecondsRangeValueChangeTitle(this, getValue() * 60 - 30, getValue() * 60 + 30);
	}

	@Nonnull
	@Override
	protected BiConsumer<BossBarInstance, Player> setupBossbar() {
		return (bossbar, player) -> {
			if (ChallengeAPI.isPaused()) {
				bossbar.setTitle(Message.forName("bossbar-timer-paused").asString());
				bossbar.setColor(BarColor.RED);
				return;
			}
			if (getState() == WAITING) {
				bossbar.setTitle(Message.forName("bossbar-force-mob-waiting").asString());
				return;
			}

			bossbar.setColor(BarColor.GREEN);
			bossbar.setProgress(getProgress());
			bossbar.setTitle(Message.forName("bossbar-force-mob-instruction").asString(StringUtils.getEnumName(entity), ChallengeAPI.formatTime(getSecondsLeftUntilNextActivation())));
		};
	}

	@Override
	protected void broadcastFailedMessage() {
		Message.forName("force-mob-failed").broadcast(Prefix.CHALLENGES, StringUtils.getEnumName(entity));
	}

	@Override
	protected void broadcastSuccessMessage(@Nonnull Player player) {
		Message.forName("force-mob-success").broadcast(Prefix.CHALLENGES, NameHelper.getName(player), StringUtils.getEnumName(entity));
	}

	@Override
	protected void chooseForcing() {
		List<EntityType> entities = new ArrayList<>(Arrays.asList(EntityType.values()));
		entities.removeIf(type -> !type.isSpawnable());
		entities.removeIf(type -> !type.isAlive());
		Utils.removeEnums(entities, "ENDER_DRAGON", "ILLUSIONER", "ARMOR_STAND", "ZOMBIE_HORSE", "SKELETON_HORSE", "SHULKER", "WITHER");

		entity = RandomizeUtils.choose(random, entities);
	}

	@Override
	protected int getForcingTime() {
		return RandomizeUtils.randomInRange(random, 5*60, 8*60);
	}

	@Override
	protected int getSecondsUntilNextActivation() {
		return RandomizeUtils.randomAround(random, getValue() * 60, 30);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onKill(@Nonnull EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity.getType() != this.entity) return;
		Player killer = entity.getKiller();
		if (killer == null) return;
		completeForcing(killer);
	}

}
