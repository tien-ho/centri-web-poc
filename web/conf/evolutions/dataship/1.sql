# --- Creates individual_lookup and individual_data tables for the first time.

# --- !Ups

CREATE TABLE IF NOT EXISTS individual_data(
  individual_id VARCHAR(255) UNIQUE NOT NULL,
  consumer_id VARCHAR(255) NOT NULL,
  schema_name VARCHAR(255) NOT NULL,
  schema_version VARCHAR(255) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  source_updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  encrypted_full_eligibility_record BYTEA NOT NULL
);

CREATE INDEX IF NOT EXISTS individual_id_idx ON individual_data(individual_id);

CREATE TABLE IF NOT EXISTS individual_lookup(
  id SERIAL PRIMARY KEY,
  individual_id VARCHAR(255) NOT NULL,
  consumer_id VARCHAR(255) NOT NULL,
  person_id BIGINT NOT NULL,
  xref_id VARCHAR(255),
  first_name_hash TEXT NOT NULL,
  last_name_hash TEXT NOT NULL,
  policy_number_hash TEXT NOT NULL,
  search_id_hash TEXT NOT NULL,
  search_id_type_hash TEXT NOT NULL,
  date_of_birth_hash TEXT NOT NULL,
  zipcode_hash TEXT NOT NULL,

  FOREIGN KEY (individual_id) REFERENCES individual_data(individual_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS primary_search_idx ON individual_lookup(first_name_hash, search_id_hash, date_of_birth_hash);
CREATE INDEX IF NOT EXISTS last_name_hash_idx ON individual_lookup(last_name_hash);
CREATE INDEX IF NOT EXISTS consumer_id_idx ON individual_lookup(consumer_id);
CREATE INDEX IF NOT EXISTS individual_id_idx ON individual_lookup(individual_id);
CREATE INDEX IF NOT EXISTS xref_id_idx ON individual_lookup(xref_id);
CREATE INDEX IF NOT EXISTS zipcode_idx ON individual_lookup(zipcode_hash);

# --- !Downs

DROP TABLE IF EXISTS individual_data CASCADE;
DROP TABLE IF EXISTS individual_lookup;