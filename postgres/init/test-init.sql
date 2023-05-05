-- DB作成
CREATE DATABASE test_db;

-- 作成したDBへ切り替え
\c test_db

-- スキーマ作成
CREATE SCHEMA private;

-- ロールの作成
CREATE ROLE admin WITH LOGIN PASSWORD 'P@ssw0rd';

-- 権限追加
GRANT ALL PRIVILEGES ON SCHEMA private TO admin;