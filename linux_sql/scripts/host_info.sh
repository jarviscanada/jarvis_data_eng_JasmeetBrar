#! /bin/bash

# Get command line arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Validate arguments
if [ "$#" -ne 5 ]; then
  echo "Illegal number of parameters"
  exit 1
fi

# Get CPU and memory info
lscpu_out=$(lscpu)
meminfo_out=$(cat /proc/meminfo)

# Function to retrieve the value from lscpu using the given regex
function get_lscpu_value {
  local regex=$1
  echo "$lscpu_out" | grep -E "${regex}" | sed -e 's/.*://' | xargs
}

# Parse hardware specifications for the desired info
hostname=$(hostname -f)
cpu_number=$(get_lscpu_value "^CPU\(s\):")
cpu_architecture=$(get_lscpu_value "^Architecture:")
cpu_model=$(get_lscpu_value "^Model\ name:")
cpu_mhz=$(get_lscpu_value "^CPU\ MHz:")
L2_cache=$(get_lscpu_value "^L2\ cache:" | sed 's/K//')
total_mem=$(echo "$meminfo_out" | grep -E "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date -u --rfc-3339=seconds)

# Construct the INSERT statement
insert_stmt="INSERT INTO host_info "
insert_stmt+="(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp) VALUES "
insert_stmt+="('${hostname}', '${cpu_number}', '${cpu_architecture}', '${cpu_model}', '${cpu_mhz}', '${L2_cache}', "
insert_stmt+="'${total_mem}', '${timestamp}');"

# Password needs to be exported in order to authenticate
export PGPASSWORD=$psql_password

# Insert host info into the database
psql -h "${psql_host}" -p "${psql_port}" -d "${db_name}" -U "${psql_user}" -c "${insert_stmt}"

exit $?
