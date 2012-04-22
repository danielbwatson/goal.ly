dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            loggingSql = true
            dbCreate = "none"
            url = "jdbc:h2:devDb;MVCC=TRUE"
        }
    }
    test {
        dataSource {
            dbCreate = "none"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
        }
    }
	production {
		dataSource {
			dbCreate = "none"
			driverClassName = "org.postgresql.Driver"
			dialect = org.hibernate.dialect.PostgreSQLDialect

			uri = new URI(System.env.DATABASE_URL ?: "postgres://test:test@localhost/test")
			pooled = true

			url = "jdbc:postgresql://" + uri.host + uri.path
			username = uri.userInfo.split(":")[0]
			password = uri.userInfo.split(":")[1]

			properties {
				maxActive = -1
				minEvictableIdleTimeMillis = 1800000
				timeBetweenEvictionRunsMillis = 1800000
				numTestsPerEvictionRun = 3
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = true
				validationQuery = "SELECT 1"
			}
		}
	}
}
