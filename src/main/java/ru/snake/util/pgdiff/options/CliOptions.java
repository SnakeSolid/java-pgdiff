package ru.snake.util.pgdiff.options;

import java.io.File;
import java.util.List;

/**
 * Structure contains all options.
 *
 * @author snake
 *
 */
public class CliOptions {

	private final String user1;

	private final String user2;

	private final String password1;

	private final String password2;

	private final String host1;

	private final String host2;

	private final short port1;

	private final short port2;

	private final String dbName1;

	private final String dbName2;

	private final File configFile;

	private final List<String> tableNames;

	private CliOptions(String user1, String user2, String password1, String password2, String host1, String host2,
			short port1, short port2, String dbName1, String dbName2, File configFile, List<String> tableNames) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.password1 = password1;
		this.password2 = password2;
		this.host1 = host1;
		this.host2 = host2;
		this.port1 = port1;
		this.port2 = port2;
		this.dbName1 = dbName1;
		this.dbName2 = dbName2;
		this.configFile = configFile;
		this.tableNames = tableNames;
	}

	/**
	 * Returns user1 option value.
	 *
	 * @return user1 value
	 */
	public String getUser1() {
		return user1;
	}

	/**
	 * Returns user2 option value.
	 *
	 * @return user2 value
	 */
	public String getUser2() {
		return user2;
	}

	/**
	 * Returns password1 option value.
	 *
	 * @return password1 value
	 */
	public String getPassword1() {
		return password1;
	}

	/**
	 * Returns password2 option value.
	 *
	 * @return password2 value
	 */
	public String getPassword2() {
		return password2;
	}

	/**
	 * Returns host1 option value.
	 *
	 * @return host1 value
	 */
	public String getHost1() {
		return host1;
	}

	/**
	 * Returns host2 option value.
	 *
	 * @return host2 value
	 */
	public String getHost2() {
		return host2;
	}

	/**
	 * Returns port1 option value.
	 *
	 * @return port1 value
	 */
	public short getPort1() {
		return port1;
	}

	/**
	 * Returns port2 option value.
	 *
	 * @return port2 value
	 */
	public short getPort2() {
		return port2;
	}

	/**
	 * Returns dbName1 option value.
	 *
	 * @return dbName1 value
	 */
	public String getDbName1() {
		return dbName1;
	}

	/**
	 * Returns dbName2 option value.
	 *
	 * @return dbName2 value
	 */
	public String getDbName2() {
		return dbName2;
	}

	/**
	 * Returns configFile option value.
	 *
	 * @return configFile value
	 */
	public File getConfigFile() {
		return configFile;
	}

	/**
	 * Returns table names option value.
	 *
	 * @return table names value
	 */
	public List<String> getTableNames() {
		return tableNames;
	}

	@Override
	public String toString() {
		return "CliOptions [user1=" + user1 + ", user2=" + user2 + ", password1=" + password1 + ", password2="
				+ password2 + ", host1=" + host1 + ", host2=" + host2 + ", port1=" + port1 + ", port2=" + port2
				+ ", dbName1=" + dbName1 + ", dbName2=" + dbName2 + ", configFile=" + configFile + ", tableNames="
				+ tableNames + "]";
	}

	/**
	 * Options builder.
	 *
	 * @author snake
	 *
	 */
	public static class Builder {

		private String user1;

		private String user2;

		private String password1;

		private String password2;

		private String host1;

		private String host2;

		private short port1;

		private short port2;

		private String dbName1;

		private String dbName2;

		private File configFile;

		private List<String> tableNames;

		/**
		 * Create new empty builder.
		 */
		public Builder() {
		}

		/**
		 * Set user1 value. Returns this builder.
		 *
		 * @param user1
		 *            user1 value
		 * @return this builder
		 */
		public Builder setUser1(String user1) {
			this.user1 = user1;

			return this;
		}

		/**
		 * Set user2 value. Returns this builder.
		 *
		 * @param user2
		 *            user2 value
		 * @return this builder
		 */
		public Builder setUser2(String user2) {
			this.user2 = user2;

			return this;
		}

		/**
		 * Set password1 value. Returns this builder.
		 *
		 * @param password1
		 *            password1 value
		 * @return this builder
		 */
		public Builder setPassword1(String password1) {
			this.password1 = password1;

			return this;
		}

		/**
		 * Set password2 value. Returns this builder.
		 *
		 * @param password2
		 *            password2 value
		 * @return this builder
		 */
		public Builder setPassword2(String password2) {
			this.password2 = password2;

			return this;
		}

		/**
		 * Set host1 value. Returns this builder.
		 *
		 * @param host1
		 *            host1 value
		 * @return this builder
		 */
		public Builder setHost1(String host1) {
			this.host1 = host1;

			return this;
		}

		/**
		 * Set host2 value. Returns this builder.
		 *
		 * @param host2
		 *            host2 value
		 * @return this builder
		 */
		public Builder setHost2(String host2) {
			this.host2 = host2;

			return this;
		}

		/**
		 * Set port1 value. Returns this builder.
		 *
		 * @param port1
		 *            port1 value
		 * @return this builder
		 */
		public Builder setPort1(short port1) {
			this.port1 = port1;

			return this;
		}

		/**
		 * Set port2 value. Returns this builder.
		 *
		 * @param port2
		 *            port2 value
		 * @return this builder
		 */
		public Builder setPort2(short port2) {
			this.port2 = port2;

			return this;
		}

		/**
		 * Set dbName1 value. Returns this builder.
		 *
		 * @param dbName1
		 *            dbName1 value
		 * @return this builder
		 */
		public Builder setDbName1(String dbName1) {
			this.dbName1 = dbName1;

			return this;
		}

		/**
		 * Set dbName2 value. Returns this builder.
		 *
		 * @param dbName2
		 *            dbName2 value
		 * @return this builder
		 */
		public Builder setDbName2(String dbName2) {
			this.dbName2 = dbName2;

			return this;
		}

		/**
		 * Set configFile value. Returns this builder.
		 *
		 * @param configFile
		 *            configFile value
		 * @return this builder
		 */
		public Builder setConfigFile(File configFile) {
			this.configFile = configFile;

			return this;
		}

		/**
		 * Set table names value. Returns this builder.
		 *
		 * @param tableNames
		 *            table names
		 * @return this builder
		 */
		public Builder setTableNames(List<String> tableNames) {
			this.tableNames = tableNames;

			return this;
		}

		/**
		 * Build new initialized instance of {@link CliOptions}.
		 *
		 * @return new options
		 */
		public CliOptions build() {
			return new CliOptions(user1, user2, password1, password2, host1, host2, port1, port2, dbName1, dbName2,
					configFile, tableNames);
		}

		@Override
		public String toString() {
			return "Builder [user1=" + user1 + ", user2=" + user2 + ", password1=" + password1 + ", password2="
					+ password2 + ", host1=" + host1 + ", host2=" + host2 + ", port1=" + port1 + ", port2=" + port2
					+ ", dbName1=" + dbName1 + ", dbName2=" + dbName2 + ", configFile=" + configFile + ", tableNames="
					+ tableNames + "]";
		}

	}

}
