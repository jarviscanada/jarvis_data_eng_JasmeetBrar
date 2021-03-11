#! /bin/bash

# Run docker if it is not already running
systemctl status docker || systemctl start docker

# Get command line arguments
command=$1
db_username=$2
db_password=$3

case $command in

  create)
    # Check if jrvs-psql already exist
    if [[ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 2 ]]; then
      echo "Error: jrvs-psql container is already created"
      echo "USAGE: ./psql_docker.sh start|stop|create [db_username][db_password]"
      exit 1
    fi

    # Check if username or password is not set
    if [[ -z "$db_username" || -z "$db_password" ]]; then
      echo "Error: Username or password is not specified"
      echo echo "USAGE: ./psql_docker.sh start|stop|create [db_username][db_password]"
      exit 1
    fi

    # Create a pgdata volume and jrvs-psql container
    docker volume create pgdata
    docker run --name jrvs-psql -e POSTGRES_USER="${db_username}" -e POSTGRES_PASSWORD="${db_password}" \
           -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres

    exit $?
    ;;

  start | stop)
    # Check if jrvs-psql does not exist
    if [[ $(docker container ls -a -f name=jrvs-psql | wc -l) -ne 2 ]]; then
      echo "Error: jrvs-psql container is not created"
      echo "USAGE: ./psql_docker.sh start|stop|create [db_username][db_password]"
      exit 1
    fi

    # Start or stop the jrvs-psql container
    if [ "$command" == "start" ]; then
      docker container start jrvs-psql
    else
      docker container stop jrvs-psql
    fi

    exit $?
    ;;

  *)
    echo "Error: valid arguments not given"
    echo "USAGE: ./psql_docker.sh start|stop|create [db_username][db_password]"
    exit 1
    ;;
esac

exit 0