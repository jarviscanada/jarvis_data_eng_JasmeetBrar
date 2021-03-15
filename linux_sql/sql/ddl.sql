\c host_agent

CREATE TABLE IF NOT EXISTS PUBLIC.host_info (
  id SERIAL NOT NULL,
  hostname VARCHAR NOT NULL,
  cpu_number INTEGER NOT NULL,
  cpu_architecture VARCHAR NOT NULL,
  cpu_model VARCHAR NOT NULL,
  cpu_mhz NUMERIC NOT NULL,
  L2_cache INTEGER NOT NULL,
  total_mem INTEGER NOT NULL,
  timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (hostname),
  CONSTRAINT positive_cpu_number CHECK (cpu_number > 0),
  CONSTRAINT positive_cpu_mhz CHECK (cpu_mhz > 0),
  CONSTRAINT non_negative_L2_cache CHECK (L2_cache >= 0),
  CONSTRAINT positive_total_mem CHECK (total_mem > 0)
);

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage (
  timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
  host_id INTEGER NOT NULL REFERENCES host_info (id) ON DELETE CASCADE,
  memory_free INTEGER NOT NULL,
  cpu_idle INTEGER NOT NULL,
  cpu_kernel INTEGER NOT NULL,
  disk_io INTEGER NOT NULL,
  disk_available INTEGER NOT NULL,
  PRIMARY KEY (timestamp, host_id),
  CONSTRAINT non_negative_memory_free CHECK (memory_free >= 0),
  CONSTRAINT cpu_idle_is_a_percentage CHECK (
    cpu_idle >= 0
    and cpu_idle <= 100
  ),
  CONSTRAINT cpu_kernel_is_a_percentage CHECK (
    cpu_kernel >= 0
    and cpu_kernel <= 100
  ),
  CONSTRAINT non_negative_disk_io CHECK (disk_io >= 0),
  CONSTRAINT non_negative_disk_available CHECK (disk_available >= 0)
);
