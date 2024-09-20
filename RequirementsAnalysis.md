# Project requirements

## Competition
This info will be print on the results PDF
- [ ] Date (a day or an interval of days)
- [ ] Location (Swimming Pool Name, address, number of lanes)
- [ ] Other information (Organizing Committee, Type of chrono, other comments)

## Database
The database must contain info about:
- [ ] Athletes (name, surname, year of birth, sex, team) + category (automatically determined by year/sex combination)
- [ ] Race (distance, style, athlete category, sex)
- [ ] Subscriptions (Athlete, Race, time) - each athlete can join multiple races basing on the competition
- [ ] Relay Teams (2+ athletes list, category, sex)
- [ ] Series - each race is composed by multiple series, usually of 6/8 athletes

## Chrono data
- [ ] Acquire data from many chrono by serial port (most important is the **Digitech Master 3**) - each data set is a serie
- [ ] Put data in a database, corresponding to the serie number registered

## GUI
- [ ] Modify in real time every part of the db without conflicting with the acquisition thread
- [ ] Print many types of rankings in PDF, i.e. best times/points for each race, category, all...
- [ ] Also team ranks by points are useful

## Others
- [ ] Make a rank of the races
- [ ] Assign custom points to athletes in races
- [ ] Ability to accumulate times in a *Combined* event. Which means every athlete should compete in all four styles for a given distance, the final rank will simply be the sum of the four times.