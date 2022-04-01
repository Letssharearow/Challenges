package net.codingarea.challenges.plugin.challenges.implementation.challenge;

import javax.annotation.Nonnull;
import net.codingarea.challenges.plugin.challenges.type.abstraction.Setting;
import net.codingarea.challenges.plugin.content.Message;
import net.codingarea.challenges.plugin.management.menu.MenuType;
import net.codingarea.challenges.plugin.spigot.events.EntityDamageByPlayerEvent;
import net.codingarea.challenges.plugin.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ReversedDamageChallenge extends Setting {

	public ReversedDamageChallenge() {
		super(MenuType.CHALLENGES);
	}

	@Nonnull
	@Override
	public ItemBuilder createDisplayItem() {
		return new ItemBuilder(Material.GOLDEN_SWORD, Message.forName("item-reversed-damage-challenge"));
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onDamageByPlayer(@Nonnull EntityDamageByPlayerEvent event) {
		if (!shouldExecuteEffect()) return;
		if (ignorePlayer(event.getDamager())) return;

		double damage = event.getFinalDamage();
		event.getDamager().damage(damage);
	}

}