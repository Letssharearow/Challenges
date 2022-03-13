package net.codingarea.challenges.plugin.content.loader;

import java.net.URL;
import net.anweisen.utilities.bukkit.utils.logging.Logger;
import net.anweisen.utilities.common.collection.IOUtils;
import net.anweisen.utilities.common.version.Version;
import net.codingarea.challenges.plugin.Challenges;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public final class UpdateLoader extends ContentLoader {

	public static final int RESOURCE_ID = 80548;

	private static boolean newestPluginVersion = true;
	private static boolean newestConfigVersion = true;

	@Override
	protected void load() {
		try {
			URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + RESOURCE_ID);
			String response = IOUtils.toString(url);
			Version plugin = Challenges.getInstance().getVersion();
			Version config = Version.parse(Challenges.getInstance().getConfigDocument().getString("config-version"));
			Version latestVersion = Version.parse(response);

			if (latestVersion.isNewerThan(plugin)) {
				Logger.info("A new version of Challenges is available: {}, you have {}", latestVersion, plugin);
				newestPluginVersion = false;
			}
			if (plugin.isNewerThan(config)) {
				Logger.info("A new version of the config (plugins/Challenges/config.yml) is available");
				newestConfigVersion = false;
			}

		} catch (Exception ex) {
			Logger.error("Could not check for update: {}", ex.getMessage());
		}
	}

	public static boolean isNewestConfigVersion() {
		return newestConfigVersion;
	}

	public static boolean isNewestPluginVersion() {
		return newestPluginVersion;
	}

}
