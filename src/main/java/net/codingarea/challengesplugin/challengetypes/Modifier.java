package net.codingarea.challengesplugin.challengetypes;

import net.codingarea.challengesplugin.manager.events.ChallengeEditEvent;
import net.codingarea.challengesplugin.utils.ItemBuilder;
import net.codingarea.challengesplugin.utils.AnimationUtil.AnimationSound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author anweisen & Dominik
 * Challenges developed on 05-29-2020
 * https://github.com/anweisen
 * https://github.com/Traolian
 */

public abstract class Modifier extends GeneralChallenge {

    protected AnimationSound sound = AnimationSound.STANDARD_SOUND;

    protected int value = 1;
    protected int maxValue = 2;
    protected int minValue = 1; // MinValue has to be 1 or higher!

    public abstract void onMenuClick(ChallengeEditEvent event);

    @Override
    public void handleClick(ChallengeEditEvent event) {
        if (sound != null) sound.play(event.getPlayer());
        if (event.wasRightClick()) {
            if ((value - 1) >= minValue) {
                value--;
            } else {
                value = maxValue;
            }
        } else {
            if (!((value + 1) > maxValue)) {
                value++;
            } else {
                value = minValue;
            }
        }
        onMenuClick(event);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public ItemStack getActivationItem() {
        return new ItemBuilder(Material.STONE_BUTTON, "§8" + value).setAmount(value).build();
    }
}