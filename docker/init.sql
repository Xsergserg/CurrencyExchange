CREATE TABLE IF NOT EXISTS USER_LOGS
(
  id serial PRIMARY KEY,
  value float, 
  sourceCurrencyCharCode VARCHAR(10), 
  targetCurrencyCharCode VARCHAR(10),
  REQUEST_TIME TIMESTAMP NOT NULL
);