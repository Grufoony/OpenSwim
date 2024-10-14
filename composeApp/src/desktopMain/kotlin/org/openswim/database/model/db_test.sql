INSERT INTO athletes (id, name, surname, sex, cf, fin_code, uisp_code, country, email, birthdate, club_id, category_id)
VALUES (1,  'John',      'Doe',             'M', 'ABCDEFGHIJKLMNOP', '12345',  '67890',  'United States', 'johndoe@example.com',     '1995-01-01', 1, 1),
       (2,  'Jane',      'Smith',           'F', 'ABCDEFGHIJKLMNOP', '54321',  '09876',  'Canada',        'janesmith@example.com',   '1998-05-15', 2, 2),
       (3,  'Alex',      'Lee',             'M', 'ABCDEFGHIJKLMNOP', '78901',  '23456',  'Australia',     'alexlee@example.com',     '2002-11-28', 3, 3),
       (4,  'Emily',     'Chen',            'F', 'ABCDEFGHIJKLMNOP', '98765',  '43210',  'China',         'emilychen@example.com',   '2000-03-07', 1, 1),
       (5,  'Michael',   'Johnson',         'M', 'ABCDEFGHIJKLMNOP', '11111',  '22222',  'Jamaica',       '[email address removed]', '1968-06-12', 2, 2),
       (6,  'Florence',  'Griffith-Joyner', 'F', 'ABCDEFGHIJKLMNOP', '33333',  '44444',  'United States', '[email address removed]', '1962-12-21', 3, 3),
       (7,  'Usain',     'Bolt',            'M', 'ABCDEFGHIJKLMNOP', '55555',  '66666',  'Jamaica',       '[email address removed]', '1986-08-21', 1, 1),
       (8,  'Jackie',    'Joyner-Kersee',   'F', 'ABCDEFGHIJKLMNOP', '77777',  '88888',  'United States', '[email address removed]', '1962-03-03', 2, 2),
       (9,  'Carl',      'Lewis',           'M', 'ABCDEFGHIJKLMNOP', '99999',  '00000',  'United States', '[email address removed]', '1961-07-04', 3, 3),
       (10, 'Heike',     'Drechsler',       'F', 'ABCDEFGHIJKLMNOP', '111111', '222222', 'Germany',       '[email address removed]', '1969-12-16', 1, 1),
       (11, 'Sebastian', 'Coe',             'M', 'ABCDEFGHIJKLMNOP', '333333', '444444', 'Great Britain', '[email address removed]', '1956-09-27', 2, 2),
       (12, 'Marita',    'Koch',            'F', 'ABCDEFGHIJKLMNOP', '555555', '666666', 'East Germany',  '[email address removed]', '1957-05-19', 3, 3);

INSERT INTO clubs (id, name)
VALUES (1, 'Club A'),
       (2, 'Club B'),
       (3, 'Club C');

INSERT INTO categories (id, code, name, sex, min_age, max_age, min_year, max_year, custom)
VALUES (1,  'M01', 'Male - Under 10',   'M', 0,  9,  2023, 2024, 0),
       (2,  'M02', 'Male - 10-13',      'M', 10, 13, 2023, 2024, 0),
       (3,  'M03', 'Male - 14-15',      'M', 14, 15, 2023, 2024, 0),
       (4,  'M04', 'Male - 16-17',      'M', 16, 17, 2023, 2024, 0),
       (5,  'M05', 'Male - 18-20',      'M', 18, 20, 2023, 2024, 0),
       (6,  'F01', 'Female - Under 10', 'F', 0,  9,  2023, 2024, 0),
       (7,  'F02', 'Female - 10-13',    'F', 10, 13, 2023, 2024, 0),
       (8,  'F03', 'Female - 14-15',    'F', 14, 15, 2023, 2024, 0),
       (9,  'F04', 'Female - 16-17',    'F', 16, 17, 2023, 2024, 0),
       (10, 'F05', 'Female - 18-20',    'F', 18, 20, 2023, 2024, 0),
       (11, 'C01', 'Custom Category 1', 'M', 0,  99, 2023, 2024, 1),
       (12, 'C02', 'Custom Category 2', 'F', 0,  99, 2023, 2024, 1);

INSERT INTO committees (id, name)
VALUES (1, 'Technical Committee'   ),
       (2, 'Disciplinary Committee'),
       (3, 'Appeals Committee'     );

INSERT INTO judges (id, name, surname)
VALUES (1, 'John',    'Doe'    ),
       (2, 'Jane',    'Smith'  ),
       (3, 'Alex',    'Lee'    ),
       (4, 'Emily',   'Chen'   ),
       (5, 'Michael', 'Johnson');

INSERT INTO committees_judges (committee_id, judge_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (3, 1),
       (3, 4);

INSERT INTO chronos (id, name, version, serial_baud, serial_char_size, serial_parity, serial_stopbits, protocol_desc)
VALUES (1, 'Chronos 1', 'v1.0', 9600,   8, 'none', 1, 'RS-232'    ),
       (2, 'Chronos 2', 'v2.0', 115200, 8, 'odd',  2, 'RS-485'    ),
       (3, 'Chronos 3', 'v3.0', 19200,  7, 'even', 1, 'RS-422'    ),
       (4, 'Chronos 4', 'v4.0', 38400,  8, 'mark', 2, 'Modbus RTU'),
       (5, 'Chronos 5', 'v5.0', 57600,  8, 'none', 1, 'Canbus'    );

INSERT INTO addresses (id, street, number, city, state, country)
VALUES (1, 'Via Roma',      '1', 'Rome',     'Lazio',    'Italy'),
       (2, 'Via Nazionale', '2', 'Florence', 'Tuscany',  'Italy'),
       (3, 'Via del Corso', '3', 'Milan',    'Lombardy', 'Italy'),
       (4, 'Via Cavour',    '4', 'Venice',   'Veneto',   'Italy'),
       (5, 'Via Garibaldi', '5', 'Naples',   'Campania', 'Italy');

INSERT INTO locations (id, name, address_id, lanes, lanes_length)
VALUES (1, 'Location 1', 1, 4,  100),
       (2, 'Location 2', 2, 6,  150),
       (3, 'Location 3', 3, 8,  200),
       (4, 'Location 4', 4, 2,  50 ),
       (5, 'Location 5', 5, 10, 250);

INSERT INTO relay_types (id, name)
VALUES (1, '4x100m Relay'),
       (2, '4x200m Relay'),
       (3, '4x400m Relay');

INSERT INTO relay_teams (id, name)
VALUES (1, 'Team A'),
       (2, 'Team B'),
       (3, 'Team C'),
       (4, 'Team D');

INSERT INTO relays (id, name, category_id, relay_type_id)
VALUES (1, 'Relay 1', 1, 1),
       (2, 'Relay 2', 2, 2),
       (3, 'Relay 3', 3, 3),
       (4, 'Relay 4', 4, 1);

INSERT INTO relay_team_athletes (relay_team_id, athlete_id)
VALUES (1, 1 ),
       (1, 4 ),
       (1, 7 ),
       (1, 10),
       (2, 2 ),
       (2, 5 ),
       (2, 8 ),
       (2, 11),
       (3, 3 ),
       (3, 6 ),
       (3, 9 ),
       (3, 12);

INSERT INTO relay_subscriptions (id, relay_team_id, relay_id, total_time)
VALUES (1, 1, 1, 0),
       (2, 2, 2, 0),
       (3, 3, 3, 0),
       (4, 4, 4, 0);

INSERT INTO relay_sub_times (id, relay_sub_id, athlete_id, time)
VALUES (1, 1, 1,  10),
       (2, 1, 4,  11),
       (3, 1, 7,  12),
       (4, 1, 10,  13),
       (5, 2, 2,  20),
       (6, 2, 5,  21),
       (7, 2, 8,  22),
       (8, 2, 11,  23),
       (9, 3, 3,  30),
       (10, 3, 6, 31),
       (11, 3, 9, 32),
       (12, 3, 12, 33);

INSERT INTO races (id, distance, style, category_id, competition_id)
VALUES (1, 100,   'Sprint',          1, 1),
       (2, 200,   'Sprint',          2, 1),
       (3, 400,   'Sprint',          3, 1),
       (4, 800,   'Middle Distance', 1, 1),
       (5, 1500,  'Middle Distance', 2, 1),
       (6, 5000,  'Long Distance',   3, 1),
       (7, 10000, 'Long Distance',   1, 1);

INSERT INTO race_subscriptions (id, athlete_id, race_id)
VALUES (1,  1,  1),
       (2,  4,  1),
       (3,  7,  1),
       (4,  2,  2),
       (5,  5,  2),
       (6,  8,  2),
       (7,  3,  3),
       (8,  6,  3),
       (9,  9,  3),
       (10, 4, 4),
       (11, 7, 4),
       (12, 10, 4);

INSERT INTO competitions (id, name, start_date, end_date, committee_id, chronos_id, location_id)
VALUES (1, 'Competition 1', '2023-01-01', '2023-01-07', 1, 1, 1),
       (2, 'Competition 2', '2023-02-01', '2023-02-07', 2, 2, 2),
       (3, 'Competition 3', '2023-03-01', '2023-03-07', 3, 3, 3),
       (4, 'Competition 4', '2023-04-01', '2023-04-07', 1, 4, 4),
       (5, 'Competition 5', '2023-05-01', '2023-05-07', 2, 5, 5);

INSERT INTO series (id, series_name, race_id)
VALUES (1, 'Series 1', 1),
       (2, 'Series 2', 1);

INSERT INTO series_athletes (series_id, athlete_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8);