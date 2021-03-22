# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the master branch after Team Code Team.

# Introduction
In this project I had to implement a monitoring system that keeps track of the host hardware specifications and usage, and stores them in a database. The users of this project are anyone who has a cluster of machines as part of their project, and they need to determine whether they need to scale vertically or horizontally to fit their needs. I have used Bash, Docker, Postgres, and Git in my implementation. As for testing, it was done manually by on a CentOS virtual machine on the Google Cloud Platform.

# Quick Start
````bash
# Create and run a psql instance
./scripts/psql_docker.sh create [username] [password]

# Create the tables
export PGPASSWORD=[password]
psql -h localhost -U [username] -c "CREATE DATABASE host_agent;"
psql -h localhost -U [username] -d host_agent -f ./sql/ddl.sql

# Insert hardware specs data into the database
./scripts/host_info.sh localhost 5432 host_agent [username] [password]

# Insert hardware usage data into the database
./scripts/host_usage.sh localhost 5432 host_agent [username] [password]

# Crontab setup
crontab -l > /tmp/myCronFile
echo "* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log" >> /tmp/myCronFile
crontab /tmp/myCronFile
rm /tmp/myCronFile
````

# Implementation

The project was implemented in 4 main steps:

1. First, I had to create a Bash script that can create a Docker container running Postgres and it can start or stop the container. The Postgres container would run in the background, and it will store host hardware specification and their hardware usage.
2. Then, I had to make a DDL file using PostgreSQL that defines two tables inside the `host_agent` database: `host_info` and `host_usage`. The `host_info` table would store information about the hosts' hardware specs, while the `host_usage` table stores information about hosts' hardware usage, such as how much of CPU is being used, and how much free memory does it have. The file assumes that I have created the `host_agent` database beforehand.
3. Next, I had to make the `host_info.sh` and `host_usage.sh` scripts to actually fetch for the desired information and commit it to the database. This required me to use Bash and PostgreSQL.
4. Finally, I had to make some SQL queries to answer business related questions such as knowing whether we need to scale up servers. This can be determined from queries that return the average memory usage or hosts that failed. For this part, I had to use PostgreSQL.
