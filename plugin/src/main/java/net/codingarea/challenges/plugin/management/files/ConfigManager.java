package net.codingarea.challenges.plugin.management.files;

import net.anweisen.utilities.commons.config.document.wrapper.FileDocumentWrapper;
import net.anweisen.utilities.commons.misc.FileUtils;
import net.codingarea.challenges.plugin.utils.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ConfigManager {

	private FileDocumentWrapper sessionConfig;
	private FileDocumentWrapper gamestateConfig;
	private FileDocumentWrapper settingsConfig;

	public void loadConfigs() {
		sessionConfig   = load("internal/session.json");
		gamestateConfig = load("internal/gamestate.json");
		settingsConfig  = load("internal/settings.json");
	}

	@Nullable
	private FileDocumentWrapper load(@Nonnull String filename) {
		try {
			File file = FileManager.getFile(filename);
			FileUtils.createFilesIfNecessary(file);
			return FileDocumentWrapper.create(file);
		} catch (Exception ex) {
			Logger.severe("Could not load config '" + filename + "': " + ex.getMessage());
			return null;
		}
	}

	@Nonnull
	public FileDocumentWrapper getSessionConfig() {
		return sessionConfig;
	}

	@Nonnull
	public FileDocumentWrapper getGamestateConfig() {
		return gamestateConfig;
	}

	@Nonnull
	public FileDocumentWrapper getSettingsConfig() {
		return settingsConfig;
	}

}
