-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT 
  * 
FROM 
  retail 
limit 
  10;

-- Check # of records
SELECT 
  COUNT(*) 
FROM 
  retail;

-- number of clients (e.g. unique client ID)
SELECT 
  COUNT(DISTINCT customer_id) 
FROM 
  retail;

-- invoice date range (e.g. max/min dates)
SELECT 
  MAX(invoice_date), 
  MIN(invoice_date) 
FROM 
  retail;

-- number of SKU/merchants (e.g. unique stock code)
SELECT 
  COUNT(DISTINCT stock_code) 
FROM 
  retail;

-- average invoice amount excluding invoices with a negative amount (e.g. canceled orders have a negative amount)
SELECT 
  AVG(total_amount) 
FROM 
  (
    SELECT 
      SUM(quantity * unit_price) AS total_amount 
    FROM 
      retail 
    GROUP BY 
      invoice_no 
    HAVING 
      SUM(quantity * unit_price) > 0
  ) AS customer_total_amount;
-- total revenue (e.g. sum of unit_price * quantity)
SELECT 
  SUM(quantity * unit_price) 
FROM 
  retail;

-- function that returns yyyymm of a given timestamp as an integer
CREATE OR REPLACE FUNCTION pg_temp.get_year_month(ts TIMESTAMP) RETURNS INT AS
$$
DECLARE
  year_value TEXT;
  month_value TEXT;
BEGIN
  SELECT EXTRACT(YEAR FROM ts) INTO year_value;
  SELECT LPAD(EXTRACT(MONTH FROM ts)::text, 2, '0') INTO month_value;
  RETURN (SELECT CONCAT(year_value, month_value)) AS INT;
END;
$$
  LANGUAGE PLPGSQL;

-- total revenue by YYYYMM
SELECT 
  pg_temp.get_year_month(invoice_date) AS yyyymm, 
  SUM(quantity * unit_price) 
FROM 
  retail 
GROUP BY 
  yyyymm 
ORDER BY 
  yyyymm;
