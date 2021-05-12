FROM postgres:9.6-alpine
COPY sql_ddl/init_db.sql /docker-entrypoint-initdb.d/
COPY sql_ddl/schema.sql /docker-entrypoint-initdb.d/
