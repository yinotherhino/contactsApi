CREATE TABLE IF NOT EXISTS contacts (
  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  NAME                   VARCHAR      NOT NULL,
  PHONE_NO               VARCHAR      NOT NULL,
  ADDRESS                VARCHAR
);