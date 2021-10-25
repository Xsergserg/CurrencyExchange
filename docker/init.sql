CREATE TABLE IF NOT EXISTS USER_LOGS
(
  id serial PRIMARY KEY,
  value float, 
  source_currency_char_code VARCHAR(10), 
  target_urrency_char_code VARCHAR(10),
  value result,
  request_time TIMESTAMP NOT NULL
);