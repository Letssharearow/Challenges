package net.codingarea.challenges.plugin.utils.database.internal.sql.abstraction.query;

import net.codingarea.challenges.plugin.utils.config.Document;
import net.codingarea.challenges.plugin.utils.config.document.EmptyDocument;
import net.codingarea.challenges.plugin.utils.config.document.GsonDocument;
import net.codingarea.challenges.plugin.utils.config.document.readonly.ReadOnlyGsonDocument;
import net.codingarea.challenges.plugin.utils.database.Result;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public final class SQLResult implements Result {

	private final Map<String, Object> values;

	public SQLResult(@Nonnull Map<String, Object> values) throws SQLException {
		this.values = values;
	}

	@Nullable
	@Override
	public Object getObject(@Nonnull String path) {
		return values.get(path);
	}

	@Nullable
	@Override
	public String getString(@Nonnull String path) {
		Object value = getObject(path);
		return value == null ? null : value.toString();
	}

	@Nonnull
	@Override
	public String getString(@Nonnull String path, @Nonnull String def) {
		String value = getString(path);
		return value == null ? def : value;
	}

	@Override
	public char getChar(@Nonnull String path) {
		try {
			return getString(path).charAt(0);
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public long getLong(@Nonnull String path) {
		try {
			return Long.parseLong(getString(path));
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public int getInt(@Nonnull String path) {
		try {
			return Integer.parseInt(getString(path));
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public short getShort(@Nonnull String path) {
		try {
			return Short.parseShort(getString(path));
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public byte getByte(@Nonnull String path) {
		try {
			return Byte.parseByte(getString(path));
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public float getFloat(@Nonnull String path) {
		try {
			return Float.parseFloat(getString(path));
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public double getDouble(@Nonnull String path) {
		try {
			return Double.parseDouble(getString(path));
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public boolean getBoolean(@Nonnull String path) {
		try {
			return Boolean.getBoolean(getString(path));
		} catch (Exception ex) {
			return false;
		}
	}

	@Nonnull
	@Override
	public List<String> getList(@Nonnull String path) {
		throw new UnsupportedOperationException("SQLResult.getList(String)");
	}


	@Nullable
	@Override
	public UUID getUUID(@Nonnull String path) {
		try {
			return UUID.fromString(getString(path));
		} catch (Exception ex) {
			return null;
		}
	}

	@Nonnull
	@Override
	public UUID getUUID(@Nonnull String path, @Nonnull UUID def) {
		UUID value = getUUID(path);
		return value == null ? def : value;
	}

	@Nullable
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull Class<E> classOfEnum) {
		return null;
	}

	@Nonnull
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull E def) {
		E value = getEnum(path, (Class<E>) def.getClass());
		return value == null ? def : value;
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return values.containsKey(path);
	}

	@Override
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		try {
			return new ReadOnlyGsonDocument(getString(path));
		} catch (Exception ex) {
			return new EmptyDocument();
		}
	}

	@Nonnull
	@Override
	public Map<String, Object> values() {
		return Collections.unmodifiableMap(values);
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return values.keySet();
	}

	@Nonnull
	@Override
	public String toJson() {
		return new GsonDocument(values).toJson();
	}

}
