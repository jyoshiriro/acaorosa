environments {
	development {
		grails {
			mongo {
				host = "localhost"
				port = 27017
				databaseName = "acaorosa"
				//		username = "acaorosa"
				//		password = "poioi"
				stateless = false
				options {
					autoConnectRetry = true
					connectTimeout = 1000
				}
			}
		}
	}
	test {
		grails {
			mongo {
				host = "localhostx"
				port = 27017
				databaseName = "acaorosa"
				//		username = "acaorosa"
				//		password = "poioi"
				options {
					autoConnectRetry = true
					connectTimeout = 1000
				}
			}
		}
	}
	production {
		grails {
			mongo {
				host = "localhost"
				port = 27017
				databaseName = "acaorosa"
				//		username = "acaorosa"
				//		password = "poioi"
				options {
					autoConnectRetry = true
					connectTimeout = 1000
				}
			}
		}
	}
}

dataSource_mysql {
	pooled = true
	jmxExport = true
    driverClassName = "com.mysql.jdbc.Driver"
	username = "root"
	password = "admin"
	dialect = "org.hibernate.dialect.MySQL5Dialect"
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = false
//	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
	cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
	singleSession = true // configure OSIV singleSession mode
}
// environment specific settings
environments {
	development {
		dataSource_mysql {
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3306/hackathon2014"
		}
	}
	test {
		dataSource_mysql {
			dbCreate = "update"
			url = "jdbc:mysql://localhost:3306/hackathon2014"
		}
	}
	production {
		dataSource_mysql {
			dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/hackathon2014"
			properties {
				// See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
				jmxEnabled = true
				initialSize = 5
				maxActive = 50
				minIdle = 5
				maxIdle = 25
				maxWait = 10000
				maxAge = 10 * 60000
				timeBetweenEvictionRunsMillis = 5000
				minEvictableIdleTimeMillis = 60000
				validationQuery = "SELECT 1"
				validationQueryTimeout = 3
				validationInterval = 15000
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = false
				jdbcInterceptors = "ConnectionState"
				defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
			}
		}
	}
}
