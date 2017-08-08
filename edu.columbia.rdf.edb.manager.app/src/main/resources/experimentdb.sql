-- Login tables

DROP TABLE IF EXISTS user_types CASCADE;
CREATE TABLE user_types (id SERIAL NOT NULL PRIMARY KEY,
name VARCHAR(255) NOT NULL UNIQUE, 
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
CREATE INDEX user_types_name_index ON user_types(name bpchar_pattern_ops);
DELETE FROM user_types;
INSERT INTO user_types (name) VALUES ('Non Login');
INSERT INTO user_types (name) VALUES ('User');
INSERT INTO user_types (name) VALUES ('Global User');
INSERT INTO user_types (name) VALUES ('Administrator');

DROP TABLE IF EXISTS persons CASCADE;
CREATE TABLE persons (id SERIAL NOT NULL PRIMARY KEY, 
public_id CHAR(32) NOT NULL UNIQUE,
first_name VARCHAR(255) NOT NULL, 
last_name VARCHAR(255) NOT NULL, 
affiliation VARCHAR(255) NOT NULL, 
phone VARCHAR(255) NOT NULL, 
address VARCHAR(255) NOT NULL, 
email VARCHAR(255) NOT NULL,
user_type_id INTEGER NOT NULL DEFAULT 2,
password_hash_salted CHAR(128),
salt CHAR(128) NOT NULL,
public_uuid CHAR(32) NOT NULL UNIQUE,
api_key CHAR(64) UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
CREATE INDEX persons_first_name_index ON persons(first_name varchar_pattern_ops);
CREATE INDEX persons_last_name_index ON persons(last_name varchar_pattern_ops);
CREATE INDEX persons_email_index ON persons(email varchar_pattern_ops);
CREATE INDEX persons_public_uuid_index ON persons(public_uuid bpchar_pattern_ops);
ALTER TABLE persons ADD FOREIGN KEY (user_type_id) REFERENCES user_types(id) ON DELETE CASCADE;


DROP TABLE IF EXISTS login_persons CASCADE;
CREATE TABLE login_persons (id SERIAL NOT NULL PRIMARY KEY,
person_id INTEGER NOT NULL UNIQUE, 
user_name VARCHAR(255) NOT NULL UNIQUE, 
password_hash_salted CHAR(128) NOT NULL, 
salt CHAR(128) NOT NULL,
key CHAR(64) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE login_persons ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;


DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (id SERIAL NOT NULL PRIMARY KEY,
name VARCHAR(255) NOT NULL UNIQUE, 
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
INSERT INTO groups (name) VALUES ('Administrator');
INSERT INTO groups (name) VALUES ('Superuser');
INSERT INTO groups (name) VALUES ('Normal');

DROP TABLE IF EXISTS groups_persons CASCADE;
CREATE TABLE groups_persons (id SERIAL NOT NULL PRIMARY KEY,
group_id INTEGER NOT NULL, 
person_id INTEGER NOT NULL, 
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE groups_persons ADD FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE;
ALTER TABLE groups_persons ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;
ALTER TABLE groups_persons ADD CONSTRAINT group_person_uq UNIQUE (group_id, person_id);
CREATE INDEX groups_persons_group_id_index ON groups_persons (group_id);
CREATE INDEX groups_persons_persons_id_index ON groups_persons (person_id);

-- user tables

--DROP TABLE IF EXISTS users CASCADE;
--CREATE TABLE users (id SERIAL NOT NULL PRIMARY KEY,
--person_id INTEGER NOT NULL UNIQUE,
--user_type user_type NOT NULL UNIQUE,
--password_hash_salted CHAR(128) NOT NULL, 
--salt CHAR(128) NOT NULL,
--public_uuid CHAR(32) NOT NULL UNIQUE,
--api_key CHAR(64) NOT NULL UNIQUE,
--created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
--CREATE INDEX users_person_index ON users(person_id);
--CREATE INDEX users_public_uuid_index ON users(public_uuid bpchar_pattern_ops);
--CREATE INDEX users_api_key_index ON users(api_key bpchar_pattern_ops);
--ALTER TABLE users ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;

-- Stores the ip addresses a user can login in from
DROP TABLE IF EXISTS user_ip_addresses CASCADE;
CREATE TABLE user_ip_addresses (id SERIAL NOT NULL PRIMARY KEY,
ip_address VARCHAR(255) NOT NULL,
user_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP WITH TIME ZONE NOT NULL);
ALTER TABLE user_ip_addresses ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
CREATE INDEX user_ip_addresses_ip_address_index ON user_ip_addresses (ip_address);


DROP TABLE IF EXISTS sample_permissions CASCADE;
CREATE TABLE sample_permissions (id SERIAL NOT NULL PRIMARY KEY,
person_id INTEGER NOT NULL, 
sample_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE sample_permissions ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE sample_permissions ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;
CREATE INDEX sample_permissions_sample_id_index ON sample_permissions (sample_id);
CREATE INDEX sample_permissions_person_id_index ON sample_permissions (person_id);


DROP TABLE IF EXISTS login_sessions CASCADE;
CREATE TABLE login_sessions (id SERIAL NOT NULL PRIMARY KEY,
key CHAR(64) NOT NULL UNIQUE,
person_id INTEGER NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE login_sessions ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;


--DROP TABLE IF EXISTS login_ip_address CASCADE;
--CREATE TABLE login_ip_address (id SERIAL NOT NULL PRIMARY KEY,
--ip_address VARCHAR(255) NOT NULL,
--person_id INTEGER NOT NULL,
--created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP WITH TIME ZONE NOT NULL);
--ALTER TABLE login_ip_address ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;
--CREATE INDEX login_ip_address_ip_address_index ON login_ip_address (ip_address);

--DROP TABLE IF EXISTS api_key_persons CASCADE;
--CREATE TABLE api_key_persons (id SERIAL NOT NULL PRIMARY KEY,
--key CHAR(64) NOT NULL UNIQUE,
--person_id INTEGER NOT NULL,
--created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP WITH TIME ZONE NOT NULL);

--DROP TABLE IF EXISTS api_keys CASCADE;
--CREATE TABLE api_keys (id SERIAL NOT NULL PRIMARY KEY,
--key CHAR(64) NOT NULL,
--ip_address VARCHAR(255) NOT NULL,
--created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP WITH TIME ZONE NOT NULL);


-- Support tables

DROP TABLE IF EXISTS version CASCADE;
CREATE TABLE version (id SERIAL NOT NULL PRIMARY KEY, 
version INTEGER NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS organisms CASCADE;
CREATE TABLE organisms (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
scientific_name varchar(255) NOT NULL UNIQUE,
tax_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS providers CASCADE;
CREATE TABLE providers (id SERIAL NOT NULL PRIMARY KEY,
name varchar(255) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS microarray_assays CASCADE;
CREATE TABLE microarray_assays (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS microarray_platforms CASCADE;
CREATE TABLE microarray_platforms (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
assay_id INTEGER NOT NULL,
provider_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE microarray_platforms ADD FOREIGN KEY (assay_id) REFERENCES microarray_assays(id) ON DELETE CASCADE;
ALTER TABLE microarray_platforms ADD FOREIGN KEY (provider_id) REFERENCES providers(id) ON DELETE CASCADE;

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS file_types CASCADE;
CREATE TABLE file_types (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

-- Experiments

DROP TABLE IF EXISTS experiments CASCADE;
CREATE TABLE experiments (id SERIAL NOT NULL PRIMARY KEY,
public_id varchar(255) NOT NULL UNIQUE,
name varchar(255) NOT NULL UNIQUE, 
description TEXT NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS experiment_permissions CASCADE;
CREATE TABLE experiment_permissions (id SERIAL NOT NULL PRIMARY KEY,
experiment_id INTEGER NOT NULL, 
person_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE experiment_permissions ADD FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE;
ALTER TABLE experiment_permissions ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;
CREATE INDEX experiment_permissions_experiment_id_index ON experiment_permissions (experiment_id);
CREATE INDEX experiment_permissions_person_id_index ON experiment_permissions (person_id);


-- Samples --

DROP TABLE IF EXISTS data_types CASCADE;
CREATE TABLE data_types (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS samples CASCADE;
CREATE TABLE samples (id SERIAL NOT NULL PRIMARY KEY,
experiment_id INTEGER NOT NULL,
data_type_id INTEGER NOT NULL,
name varchar(255) NOT NULL,
description varchar(255) NOT NULL DEFAULT '',
organism_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE samples ADD FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE;
ALTER TABLE samples ADD FOREIGN KEY (organism_id) REFERENCES organisms(id) ON DELETE CASCADE;
ALTER TABLE samples ADD FOREIGN KEY (data_type_id) REFERENCES data_types(id) ON DELETE CASCADE;
CREATE INDEX samples_experiment_id_index ON samples USING btree(experiment_id);
CREATE INDEX samples_expression_type_id_index ON samples USING btree(expression_type_id);
CREATE INDEX samples_organism_id_index ON samples(organism_id);


DROP TABLE IF EXISTS groups_samples CASCADE;
CREATE TABLE groups_samples (id SERIAL NOT NULL PRIMARY KEY,
group_id INTEGER NOT NULL, 
sample_id INTEGER NOT NULL, 
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE groups_samples ADD FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE;
ALTER TABLE groups_samples ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE groups_samples ADD CONSTRAINT group_sample_uq UNIQUE (group_id, sample_id);
CREATE INDEX groups_samples_group_id_index ON groups_samples (group_id);
CREATE INDEX groups_samples_persons_id_index ON groups_samples (sample_id);



-- Unfortunately samples can have various names so take account of this --
DROP TABLE IF EXISTS sample_aliases CASCADE;
CREATE TABLE sample_aliases (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
name varchar(255) NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE sample_alt_names ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
CREATE INDEX sample_aliases_name_index ON sample_aliases(name varchar_pattern_ops);



DROP TABLE IF EXISTS microarray_sample_platform CASCADE;
CREATE TABLE microarray_sample_platform (id SERIAL NOT NULL PRIMARY KEY, 
sample_id INTEGER NOT NULL UNIQUE, 
platform_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE microarray_sample_platform ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE microarray_sample_platform ADD FOREIGN KEY (array_design_id) REFERENCES microarray_platforms(id) ON DELETE CASCADE;

DROP TABLE IF EXISTS sample_persons CASCADE;
CREATE TABLE sample_persons (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL, 
person_id INTEGER NOT NULL,
role_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE sample_persons ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE sample_persons ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;
ALTER TABLE sample_persons ADD FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;
CREATE INDEX sample_persons_person_id_index ON sample_persons(person_id);

DROP TABLE IF EXISTS json_sample_persons CASCADE;
CREATE TABLE json_sample_persons (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
json json NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE json_sample_persons ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
CREATE INDEX json_sample_persons_sample_id_index ON json_sample_persons (sample_id);



-- DROP TABLE IF EXISTS directories CASCADE;
-- CREATE TABLE directories (id SERIAL NOT NULL PRIMARY KEY,
-- sample_id INTEGER NOT NULL,
-- name varchar(255) NOT NULL,
-- created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
-- ALTER TABLE directories ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
-- CREATE INDEX directories_sample_id_index ON directories (sample_id);

-- DROP TABLE IF EXISTS files CASCADE;
-- CREATE TABLE files (id SERIAL NOT NULL PRIMARY KEY,
-- parent_id INTEGER NOT NULL DEFAULT -1,
-- name varchar(255) NOT NULL,
-- file_type_id INTEGER NOT NULL,
-- created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
-- ALTER TABLE files ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
-- ALTER TABLE files ADD FOREIGN KEY (file_type_id) REFERENCES file_types(id) ON DELETE CASCADE;
-- CREATE INDEX files_sample_id_index ON files (sample_id);

--- File System

DROP TABLE IF EXISTS vfs_types CASCADE;
CREATE TABLE vfs_types (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
INSERT INTO vfs_types (name) VALUES ('Directory');
INSERT INTO vfs_types (name) VALUES ('File');

DROP TABLE IF EXISTS vfs CASCADE;
CREATE TABLE vfs (id SERIAL NOT NULL PRIMARY KEY,
parent_id INTEGER NOT NULL DEFAULT -1,
name varchar(255) NOT NULL,
type_id INTEGER NOT NULL,
path varchar(255),
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
CREATE INDEX vfs_name_index ON vfs(lower(name) varchar_pattern_ops);
ALTER TABLE vfs ADD FOREIGN KEY (type_id) REFERENCES vfs_types(id) ON DELETE CASCADE;
INSERT INTO vfs (name, type_id) VALUES ('/', 1);
INSERT INTO vfs (name, parent_id, type_id) VALUES ('Experiments', 1, 1);


DROP TABLE IF EXISTS experiment_files CASCADE;
CREATE TABLE experiment_files (id SERIAL NOT NULL PRIMARY KEY,
experiment_id INTEGER NOT NULL,
vfs_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE experiment_files ADD FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE;
ALTER TABLE experiment_files ADD FOREIGN KEY (vfs_id) REFERENCES vfs(id) ON DELETE CASCADE;
CREATE INDEX experiment_files_experiment_id_index ON experiment_files (experiment_id);
CREATE INDEX experiment_files_vfs_id_index ON experiment_files (vfs_id);

DROP TABLE IF EXISTS sample_files CASCADE;
CREATE TABLE sample_files (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
vfs_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE sample_files ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE sample_files ADD FOREIGN KEY (vfs_id) REFERENCES vfs(id) ON DELETE CASCADE;
ALTER TABLE sample_files ADD CONSTRAINT sample_vfs_unique UNIQUE (sample_id, vfs_id);
CREATE INDEX sample_files_sample_id_index ON sample_files (sample_id);
CREATE INDEX sample_files_vfs_id_index ON sample_files (vfs_id);

-- tags

DROP TABLE IF EXISTS tags CASCADE;
CREATE TABLE tags (id SERIAL NOT NULL PRIMARY KEY,
name varchar(255) NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS tags_sample CASCADE;
CREATE TABLE tags_sample (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
tag_id INTEGER NOT NULL,
value varchar(255) NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE tags_sample ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE tags_sample ADD FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE;
CREATE INDEX tags_sample_sample_id_index ON tags_sample (sample_id);
CREATE INDEX tags_sample_tag_id_index ON tags_sample (tag_id);
CREATE INDEX tags_sample_value_index ON tags_sample (value varchar_pattern_ops);

-- assign integer numerical data to a sample
DROP TABLE IF EXISTS tags_sample_int CASCADE;
CREATE TABLE tags_sample_int (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
tag_id INTEGER NOT NULL, 
value INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE tags_sample_int ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE tags_sample_int ADD FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE;
CREATE INDEX tags_sample_int_sample_id_index ON tags_sample_int(sample_id);
CREATE INDEX tags_sample_int_tag_id_index ON tags_sample_int(tag_id);
CREATE INDEX tags_sample_int_value_index ON tags_sample_int (value);

DROP TABLE IF EXISTS tags_sample_float CASCADE;
CREATE TABLE tags_sample_float (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
tag_id INTEGER NOT NULL, 
value FLOAT NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE tags_sample_float ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE tags_sample_float ADD FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE;
CREATE INDEX tags_sample_float_sample_id_index ON tags_sample_float(sample_id);
CREATE INDEX tags_sample_float_tag_id_index ON tags_sample_float(tag_id);
CREATE INDEX tags_sample_float_value_index ON tags_sample_float (value);

DROP TABLE IF EXISTS tags_keywords_search CASCADE;
CREATE TABLE tags_keywords_search (id SERIAL NOT NULL PRIMARY KEY, 
tag_id INTEGER NOT NULL,
keyword_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE tags_keywords_search ADD CONSTRAINT tag_keyword_unique UNIQUE (tag_id, keyword_id);
ALTER TABLE tags_keywords_search ADD FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE;
ALTER TABLE tags_keywords_search ADD FOREIGN KEY (keyword_id) REFERENCES keywords(id) ON DELETE CASCADE;
CREATE INDEX tags_keywords_search_tag_id_index ON tags_keywords_search(tag_id);
CREATE INDEX tags_keywords_search_keyword_id_index ON tags_keywords_search(keyword_id);

DROP TABLE IF EXISTS tags_samples_search CASCADE;
CREATE TABLE tags_samples_search (id SERIAL NOT NULL PRIMARY KEY,
tag_keyword_search_id INTEGER NOT NULL,
sample_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE tags_samples_search ADD FOREIGN KEY (tag_keyword_search_id) REFERENCES tags_keywords_search(id) ON DELETE CASCADE;
ALTER TABLE tags_samples_search ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
CREATE INDEX tags_samples_search_tag_keyword_search_id_index ON tags_samples_search(tag_keyword_search_id);


-- tags


DROP TABLE IF EXISTS sample_permissions CASCADE;
CREATE TABLE sample_permissions (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL, 
person_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE sample_permissions ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE sample_permissions ADD FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE;
CREATE INDEX sample_permissions_sample_id_index ON sample_permissions (sample_id);
CREATE INDEX sample_permissions_person_id_index ON sample_permissions (person_id);


DROP TABLE IF EXISTS json_tags_sample CASCADE;
CREATE TABLE json_tags_sample (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
json json NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE json_tags_sample ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
CREATE INDEX json_tags_sample_sample_id_index ON json_tags_sample (sample_id);


-- geo db functions

DROP TABLE IF EXISTS geo_platforms CASCADE;
CREATE TABLE geo_platforms (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(16) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
CREATE INDEX geo_platforms_name_index ON geo_platforms USING btree(name varchar_pattern_ops);

DROP TABLE IF EXISTS geo_series CASCADE;
CREATE TABLE geo_series (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(16) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
CREATE INDEX geo_series_name_index ON geo_series USING btree(name varchar_pattern_ops);

DROP TABLE IF EXISTS geo_samples CASCADE;
CREATE TABLE geo_samples (id SERIAL NOT NULL PRIMARY KEY,
geo_series_id INTEGER NOT NULL,
sample_id INTEGER NOT NULL,
geo_platform_id INTEGER NOT NULL,
name varchar(16) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE geo_samples ADD FOREIGN KEY (geo_series_id) REFERENCES geo_series(id) ON DELETE CASCADE;
ALTER TABLE geo_samples ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
ALTER TABLE geo_samples ADD FOREIGN KEY (geo_platform_id) REFERENCES geo_platforms(id) ON DELETE CASCADE;
CREATE INDEX geo_samples_name_index ON geo_samples USING btree(name varchar_pattern_ops);
CREATE INDEX geo_samples_sample_id_index ON geo_samples USING btree(sample_id);
CREATE INDEX geo_samples_geo_series_id_index ON geo_samples USING btree(geo_series_id);


DROP TABLE IF EXISTS samples_geo CASCADE;
CREATE TABLE samples_geo (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
geo_series_accession varchar(16) NOT NULL,
geo_accession varchar(16) NOT NULL UNIQUE,
geo_platform varchar(16) NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE samples_geo ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
CREATE INDEX samples_geo_sample_id_index ON samples_geo USING btree(sample_id);
CREATE INDEX samples_geo_series_accession_index ON samples_geo USING btree(geo_series_accession varchar_pattern_ops);
CREATE INDEX samples_geo_accession_index ON samples_geo USING btree(geo_accession varchar_pattern_ops);
CREATE INDEX samples_geo_platform_index ON samples_geo USING btree(geo_platform varchar_pattern_ops);

DROP TABLE IF EXISTS json_sample_geo CASCADE;
CREATE TABLE json_sample_geo (id SERIAL NOT NULL PRIMARY KEY,
sample_id INTEGER NOT NULL,
json json NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE json_sample_geo ADD FOREIGN KEY (sample_id) REFERENCES samples(id) ON DELETE CASCADE;
CREATE INDEX json_sample_geo_sample_id_index ON json_sample_geo (sample_id);



-- extended design tables

DROP TABLE IF EXISTS genes CASCADE;
CREATE TABLE genes (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(255) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS chromosomes CASCADE;
CREATE TABLE chromosomes (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(5) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());

DROP TABLE IF EXISTS genomes CASCADE;
CREATE TABLE genomes (id SERIAL NOT NULL PRIMARY KEY, 
name varchar(5) NOT NULL UNIQUE,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
CREATE INDEX genomes_name_index ON genomes (name);

-- ChiP-Seq tables

DROP TABLE IF EXISTS chip_seq_reads CASCADE;
CREATE TABLE chip_seq_reads (id SERIAL NOT NULL PRIMARY KEY, 
sample_id INTEGER NOT NULL,
genome_id INTEGER NOT NULL,
read_length INTEGER NOT NULL,
read_count	INTEGER NOT NULL,
mapped_read_count INTEGER NOT NULL,
duplicate_read_count	INTEGER NOT NULL,
percent_duplicate_reads	FLOAT NOT NULL,
unique_read_count INTEGER NOT NULL, 
percent_unique_reads FLOAT NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE chip_seq_reads ADD FOREIGN KEY (sample_id) REFERENCES samples(id);
ALTER TABLE chip_seq_reads ADD FOREIGN KEY (genome_id) REFERENCES genomes(id);
CREATE INDEX chip_seq_reads_sample_id_index ON chip_seq_reads (sample_id);

DROP TABLE IF EXISTS json_chip_seq_peaks CASCADE;
CREATE TABLE json_chip_seq_peaks (id SERIAL NOT NULL PRIMARY KEY, 
sample_id INTEGER NOT NULL,
name varchar(255) NOT NULL UNIQUE,
genome varchar(5) NOT NULL,
read_length INTEGER NOT NULL,
peak_caller varchar(255),
peak_caller_parameters varchar(255),
json json NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE json_chip_seq_peaks ADD FOREIGN KEY (sample_id) REFERENCES samples(id);
CREATE INDEX json_chip_seq_name_index ON json_chip_seq_peaks(name varchar_pattern_ops);
CREATE INDEX json_chip_seq_genome_index ON json_chip_seq_peaks(genome varchar_pattern_ops);




-- rna seq tables

DROP TABLE IF EXISTS rna_seq CASCADE;
CREATE TABLE rna_seq (id SERIAL NOT NULL PRIMARY KEY, 
sample_id INTEGER NOT NULL,
gene_id INTEGER NOT NULL,
chromosome_id INTEGER NOT NULL,
start_pos INTEGER NOT NULL,
end_pos INTEGER NOT NULL,
locus varchar(255) NOT NULL,
genome_id INTEGER NOT NULL,
fpkm DOUBLE PRECISION NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE rna_seq ADD FOREIGN KEY (sample_id) REFERENCES samples(id);
ALTER TABLE rna_seq ADD FOREIGN KEY (gene_id) REFERENCES genes(id);
ALTER TABLE rna_seq ADD FOREIGN KEY (chromosome_id) REFERENCES chromosomes(id);
ALTER TABLE rna_seq ADD FOREIGN KEY (genome_id) REFERENCES genomes(id);

DROP TABLE IF EXISTS mutations CASCADE;
CREATE TABLE mutations (id SERIAL NOT NULL PRIMARY KEY, 
sample_id INTEGER NOT NULL,
gene_id INTEGER NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
ALTER TABLE mutations ADD FOREIGN KEY (sample_id) REFERENCES samples(id);
ALTER TABLE mutations ADD FOREIGN KEY (gene_id) REFERENCES genes(id);
CREATE INDEX mutations_sample_id_index ON mutations(sample_id);
CREATE INDEX mutations_gene_id_index ON mutations(gene_id);

--- Search tables ---
DROP TABLE IF EXISTS keywords CASCADE;
CREATE TABLE keywords (id SERIAL NOT NULL PRIMARY KEY, 
name VARCHAR(255) NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now());
CREATE INDEX search_keywords_name_index ON keywords(lower(name) varchar_pattern_ops);


--- Link categories to words ---


-- Some useful default entries


DELETE FROM sample_types;
INSERT INTO sample_types (name) VALUES ('Microarray');
INSERT INTO sample_types (name) VALUES ('RNA-Seq');
INSERT INTO sample_types (name) VALUES ('ChIP-Seq');

DELETE FROM section_types;
INSERT INTO section_types (name) VALUES ('Sample');
INSERT INTO section_types (name) VALUES ('Source');
INSERT INTO section_types (name) VALUES ('Extract');
INSERT INTO section_types (name) VALUES ('Labeled Extract');
INSERT INTO section_types (name) VALUES ('Hybridization');

DELETE FROM providers;
INSERT INTO providers (name) VALUES ('Affymetrix');
INSERT INTO providers (name) VALUES ('Illumina');

DELETE FROM assays;
INSERT INTO assays (name) VALUES ('Gene Expression');

DELETE FROM roles;
INSERT INTO roles (name) VALUES ('Submitter');
INSERT INTO roles (name) VALUES ('Investigator');