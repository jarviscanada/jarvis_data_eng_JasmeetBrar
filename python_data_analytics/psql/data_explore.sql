-- Show table schema 
-- \d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT COUNT(*) FROM retail;

-- number of clients (e.g. unique client ID)
SELECT COUNT(DISTINCT customer_id) FROM retail;

-- invoice date range (e.g. max/min dates)
SELECT MAX(invoice_date), MIN(invoice_date) FROM retail;

-- number of SKU/merchants (e.g. unique stock code)
SELECT COUNT(DISTINCT stock_code) FROM retail;

-- average invoice amount excluding invoices with a negative amount (e.g. canceled orders have a negative amount)
-- SELECT AVG(total_amount) FROM (SELECT SUM(quantity * unit_price) AS total_amount FROM retail GROUP BY customer_id HAVING SUM(quantity * unit_price) > 0) AS customer_total_amount;
