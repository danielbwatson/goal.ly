databaseChangeLog = {

	changeSet(author: "dbwatson (generated)", id: "1335108840837-1") {
		createTable(tableName: "auth_persistent_logins") {
			column(name: "series", type: "varchar(64)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "auth_persistePK")
			}

			column(name: "last_used", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "varchar(64)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(64)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-2") {
		createTable(tableName: "auth_role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "auth_rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-3") {
		createTable(tableName: "auth_user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "auth_userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}

			column(name: "enabled", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-4") {
		createTable(tableName: "auth_user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-5") {
		createTable(tableName: "question") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "questionPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_asked", type: "timestamp")

			column(name: "question_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "text", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "time", type: "tinyint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-6") {
		createTable(tableName: "registration_code") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "registration_PK")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-7") {
		createTable(tableName: "response") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "responsePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "question_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-8") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "auth_user_rolPK", tableName: "auth_user_role")
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-9") {
		createIndex(indexName: "authority_unique_1335108840784", tableName: "auth_role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-10") {
		createIndex(indexName: "email_unique_1335108840788", tableName: "auth_user", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-11") {
		createIndex(indexName: "username_unique_1335108840792", tableName: "auth_user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-12") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "auth_user_role", constraintName: "FK4BCFDA935C752406", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "auth_role", referencesUniqueColumn: "false")
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-13") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "auth_user_role", constraintName: "FK4BCFDA9319FE7E6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "auth_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-14") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "question", constraintName: "FKBA823BE619FE7E6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "auth_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "dbwatson (generated)", id: "1335108840837-15") {
		addForeignKeyConstraint(baseColumnNames: "question_id", baseTableName: "response", constraintName: "FKEBB714419A9D49F4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "question", referencesUniqueColumn: "false")
	}
}
