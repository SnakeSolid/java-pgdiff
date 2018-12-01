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

	private final String port1;

	private final String port2;

	private final String dbname1;

	private final String dbname2;

	private final File configFile;

	private final List<String> tableNames;

	private CliOptions(String user1, String user2, String password1, String password2, String host1, String host2,
			String port1, String port2, String dbname1, String dbname2, File configFile, List<String> tableNames) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.password1 = password1;
		this.password2 = password2;
		this.host1 = host1;
		this.host2 = host2;
		this.port1 = port1;
		this.port2 = port2;
		this.dbname1 = dbname1;
		this.dbname2 = dbname2;
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
	public String getPort1() {
		return port1;
	}

	/**
	 * Returns port2 option value.
	 *
	 * @return port2 value
	 */
	public String getPort2() {
		return port2;
	}

	/**
	 * Returns dbname1 option value.
	 *
	 * @return dbname1 value
	 */
	public String getDbname1() {
		return dbname1;
	}

	/**
	 * Returns dbname2 option value.
	 *
	 * @return dbname2 value
	 */
	public String getDbname2() {
		return dbname2;
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
				+ ", dbname1=" + dbname1 + ", dbname2=" + dbname2 + ", configFile=" + configFile + ", tableNames="
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

		private String port1;

		private String port2;

		private String dbname1;

		private String dbname2;

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
		public Builder setPort1(String port1) {
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
		public Builder setPort2(String port2) {
			this.port2 = port2;

			return this;
		}

		/**
		 * Set dbname1 value. Returns this builder.
		 *
		 * @param dbname1
		 *            dbname1 value
		 * @return this builder
		 */
		public Builder setDbname1(String dbname1) {
			this.dbname1 = dbname1;

			return this;
		}

		/**
		 * Set dbname2 value. Returns this builder.
		 *
		 * @param dbname2
		 *            dbname2 value
		 * @return this builder
		 */
		public Builder setDbname2(String dbname2) {
			this.dbname2 = dbname2;

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
			return new CliOptions(user1, user2, password1, password2, host1, host2, port1, port2, dbname1, dbname2,
					configFile, tableNames);
		}

		@Override
		public String toString() {
			return "Builder [user1=" + user1 + ", user2=" + user2 + ", password1=" + password1 + ", password2="
					+ password2 + ", host1=" + host1 + ", host2=" + host2 + ", port1=" + port1 + ", port2=" + port2
					+ ", dbname1=" + dbname1 + ", dbname2=" + dbname2 + ", configFile=" + configFile + ", tableNames="
					+ tableNames + "]";
		}

	}

}
