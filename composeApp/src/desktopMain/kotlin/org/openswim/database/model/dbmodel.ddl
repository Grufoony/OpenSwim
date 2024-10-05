CREATE TABLE IF NOT EXISTS athletes
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        VARCHAR(255) NOT NULL,
    surname     VARCHAR(255) NOT NULL,
    sex         VARCHAR(1)   NOT NULL CHECK ( sex IN ('M', 'F') ),
    cf          VARCHAR(16),
    fin_code    VARCHAR(50),
    uisp_code   VARCHAR(50),
    country     VARCHAR(255),
    email       VARCHAR(255),
    birthdate   DATE         NOT NULL,
    club_id     INTEGER      NOT NULL,
    category_id INTEGER      NOT NULL,

    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL ,
    FOREIGN KEY (club_id) REFERENCES clubs (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS clubs
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS relays
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    name          VARCHAR(255) NOT NULL,
    category_id   INTEGER      NOT NULL,
    relay_type_id INTEGER      NOT NULL,

    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (relay_type_id) REFERENCES relay_types (id)
);

CREATE TABLE IF NOT EXISTS relay_teams
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS relay_types
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS relay_team_athletes
(
    relay_team_id INTEGER NOT NULL,
    athlete_id    INTEGER NOT NULL,

    FOREIGN KEY (relay_team_id) REFERENCES relay_teams (id),
    FOREIGN KEY (athlete_id) REFERENCES athletes (id),
    UNIQUE (relay_team_id, athlete_id) ON CONFLICT ABORT
);

CREATE TABLE IF NOT EXISTS categories
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    code     VARCHAR(20)  NOT NULL,
    name     VARCHAR(255) NOT NULL,
    sex      VARCHAR(1)   NOT NULL CHECK ( sex IN ('M', 'F') ),
    min_age  INTEGER      NOT NULL,
    max_age  INTEGER      NOT NULL,
    min_year INTEGER      NOT NULL,
    max_year INTEGER      NOT NULL,
    custom   INTEGER      NOT NULL CHECK ( custom IN (0, 1) )
);

CREATE TABLE IF NOT EXISTS races
(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    distance       INTEGER      NOT NULL,
    style          VARCHAR(255) NOT NULL,
    category_id    INTEGER      NOT NULL,
    competition_id INTEGER      NOT NULL,

    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS race_subscriptions
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    athlete_id INTEGER NOT NULL,
    race_id    INTEGER NOT NULL,
    time       INTEGER DEFAULT 0,

    FOREIGN KEY (athlete_id) REFERENCES athletes (id),
    FOREIGN KEY (race_id) REFERENCES races (id)
);

CREATE TABLE IF NOT EXISTS relay_subscriptions
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    relay_team_id INTEGER NOT NULL,
    relay_id      INTEGER NOT NULL,
    total_time    INTEGER DEFAULT 0,

    FOREIGN KEY (relay_team_id) REFERENCES relay_teams (id),
    FOREIGN KEY (relay_id) REFERENCES relays (id)
);

-- check on athlete_id it should be in relay_team_athletes associated
-- with the subscription
CREATE TABLE IF NOT EXISTS relay_sub_times
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    relay_sub_id INTEGER NOT NULL,
    athlete_id   INTEGER NOT NULL,
    time         INTEGER NOT NULL,

    FOREIGN KEY (relay_sub_id) REFERENCES relay_subscriptions (id),
    FOREIGN KEY (athlete_id) REFERENCES relay_team_athletes (athlete_id)
);

CREATE TABLE IF NOT EXISTS series
(
    id          INTEGER PRIMARY KEY,
    series_name VARCHAR(50) NOT NULL,
    race_id     INTEGER     NOT NULL,

    FOREIGN KEY (race_id) REFERENCES races (id)
);

CREATE TABLE IF NOT EXISTS series_athletes
(
    series_id  INTEGER NOT NULL,
    athlete_id INTEGER NOT NULL,

    FOREIGN KEY (series_id) REFERENCES series (id),
    FOREIGN KEY (athlete_id) REFERENCES athletes (id),
    UNIQUE (series_id, athlete_id) ON CONFLICT ABORT
);

CREATE TABLE IF NOT EXISTS competitions
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    name         VARCHAR(255) NOT NULL,
    start_date   DATE         NOT NULL,
    end_date     DATE         NOT NULL,
    committee_id INTEGER      NOT NULL,
    chronos_id   INTEGER      NOT NULL,
    location_id  INTEGER      NOT NULL,

    FOREIGN KEY (committee_id) REFERENCES committees (id),
    FOREIGN KEY (chronos_id) REFERENCES chronos (id),
    FOREIGN KEY (location_id) REFERENCES locations (id)
);

CREATE TABLE IF NOT EXISTS committees
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS judges
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    name    VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS committees_judges
(
    committee_id INTEGER NOT NULL,
    judge_id     INTEGER NOT NULL,

    FOREIGN KEY (committee_id) REFERENCES committees (id) ON DELETE CASCADE,
    FOREIGN KEY (judge_id) REFERENCES judges (id) ON DELETE CASCADE,
    UNIQUE (committee_id, judge_id) ON CONFLICT IGNORE
);

CREATE TABLE IF NOT EXISTS chronos
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    name             VARCHAR(255) NOT NULL,
    version          VARCHAR(255) NOT NULL,
    serial_baud      INTEGER      NOT NULL,
    serial_char_size INTEGER      NOT NULL,
    serial_parity    VARCHAR(10)  NOT NULL check ( serial_parity IN ('none', 'odd', 'even', 'mark') ),
    serial_stopbits  INTEGER      NOT NULL,
    protocol_desc    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS locations
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    name         VARCHAR(255) NOT NULL,
    address_id   INTEGER      NOT NULL,
    lanes        INTEGER      NOT NULL,
    lanes_length INTEGER      NOT NULL,

    FOREIGN KEY (address_id) REFERENCES addresses (id)
);

CREATE TABLE IF NOT EXISTS addresses
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    street  VARCHAR(255) NOT NULL,
    number  VARCHAR(50)  NOT NULL,
    city    VARCHAR(255) NOT NULL,
    state   VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL
);

CREATE TRIGGER IF NOT EXISTS sub_category_check
    BEFORE INSERT
    ON race_subscriptions
    FOR EACH ROW
BEGIN
    SELECT RAISE(ABORT, 'The category of the referenced athlete should match the category of the race')
    WHERE (SELECT category_id
           FROM new
                    JOIN athletes a ON new.athlete_id = a.id) !=
          (SELECT category_id
           FROM new
                    JOIN races r ON new.race_id = r.id);
END;

CREATE TRIGGER IF NOT EXISTS relay_category_check
    BEFORE INSERT
    ON relay_team_athletes
    FOR EACH ROW
    WHEN (SELECT COUNT(*)
          FROM relay_team_athletes
          WHERE relay_team_id = new.relay_team_id) > 0
BEGIN
    SELECT RAISE(ABORT,
                 'The category code of the referenced athlete should match the category code of the other athletes in the relay team')
    WHERE (SELECT c.code
           FROM new
                    JOIN athletes a ON new.athlete_id = a.id
                    JOIN categories c on c.id = a.category_id) !=
          (SELECT c.code
           FROM relay_team_athletes rta
                    JOIN athletes a ON rta.athlete_id = a.id
                    JOIN categories c on c.id = a.category_id
           WHERE rta.relay_team_id = new.relay_team_id
           LIMIT 1);
END;

CREATE TRIGGER IF NOT EXISTS relay_category_check
    BEFORE INSERT
    ON relay_team_athletes
    FOR EACH ROW
    WHEN (SELECT COUNT(*)
          FROM relay_team_athletes
          WHERE relay_team_id = new.relay_team_id) > 0
BEGIN
    SELECT RAISE(ABORT,
                 'The club of the referenced athlete should match the club of other athletes in the relay team')
    WHERE (SELECT c.id
           FROM new
                    JOIN athletes a ON new.athlete_id = a.id
                    JOIN clubs c on c.id = a.club_id) !=
          (SELECT c.id
           FROM relay_team_athletes rta
                    JOIN athletes a ON rta.athlete_id = a.id
                    JOIN clubs c on c.id = a.club_id
           WHERE rta.relay_team_id = new.relay_team_id
           LIMIT 1);
END;