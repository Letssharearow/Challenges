package net.codingarea.challenges.plugin.challenges.implementation.challenge;

import net.codingarea.challenges.plugin.challenges.type.HydraChallenge;
import net.codingarea.challenges.plugin.language.Message;
import net.codingarea.challenges.plugin.management.menu.MenuType;
import net.codingarea.challenges.plugin.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 2.0
 */
public class HydraPlusChallenge extends HydraChallenge {

	final int limit = 512;

	public HydraPlusChallenge() {
		super(MenuType.CHALLENGES);
	}

	@Nonnull
	@Override
	public ItemBuilder createDisplayItem() {
		return new ItemBuilder(Material.BAT_SPAWN_EGG, Message.forName("item-hydra-plus-challenge"));
	}

	@Override
	public int getNewMobsCount(@Nonnull EntityType entityType) {
		int currentCount = getGameStateData().getInt(entityType.name());
		if (currentCount == 0) currentCount = 1;
		currentCount = currentCount*2;
		getGameStateData().set(entityType.name(), Math.min(currentCount, limit));
		return currentCount;
	}

}