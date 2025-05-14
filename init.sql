CREATE TABLE IF NOT EXISTS players (
    player TEXT,
    nation TEXT,
    pos TEXT,
    age INTEGER,
    mp INTEGER,
    starts INTEGER,
    min INTEGER,
    gls INTEGER,
    ast INTEGER,
    pk INTEGER,
    crdy INTEGER,
    crdr INTEGER,
    xg NUMERIC,
    xag NUMERIC,
    team TEXT
);

COPY players(player, nation, pos, age, mp, starts, min, gls, ast, pk, crdy, crdr, xg, xag, team)
FROM '/docker-entrypoint-initdb.d/players.csv'
DELIMITER ','
CSV HEADER;
