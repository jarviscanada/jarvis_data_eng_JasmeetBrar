\c host_agent

-- This function returns the given timestamp rounded to the nearest 5 minute interval.
CREATE OR REPLACE FUNCTION round5(ts TIMESTAMP) RETURNS TIMESTAMP AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

-- This function returns the free memory percentage for the given used memory amount and host id.
CREATE OR REPLACE FUNCTION get_used_memory_percentage(free_memory INTEGER, host_id INTEGER) RETURNS NUMERIC AS
$$
DECLARE
    total_memory INTEGER;
BEGIN
    SELECT (total_mem / 1000) INTO total_memory FROM host_info WHERE id = host_id;
    RETURN (total_memory - free_memory) * 100 / total_memory;
END;
$$
    LANGUAGE PLPGSQL;

-- Group hosts by hardware info
SELECT
    FIRST_VALUE(cpu_number) OVER (
    PARTITION BY cpu_number
    ORDER BY
      total_mem DESC
  ) AS cpu_number,
    id AS host_id,
    total_mem
FROM
    host_info;


-- Average memory usage
SELECT
  host_id,
  round5(
    CAST(timestamp AS timestamp)
  ) AS timestamp,
  AVG(
    get_used_memory_percentage(memory_free, host_id)
  ) AS avg_used_mem_percentage
FROM
  host_usage
GROUP BY
  host_id,
  round5(
    CAST(timestamp AS timestamp)
  );

-- Detect host failure
SELECT
  host_id,
  round5(
    CAST(timestamp AS timestamp)
  ) AS timestamp,
  COUNT(timestamp) AS num_data_points
FROM
  host_usage
GROUP BY
  host_id,
  round5(
    CAST(timestamp AS timestamp)
  )
HAVING
  COUNT(timestamp) < 3;
