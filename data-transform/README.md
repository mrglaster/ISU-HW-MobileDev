## Convert Google Play Store Dataset from CSV to JSON

### Description

As a simple programming assignment, you need to convert raw app data collected from the Google Play store from CSV format to JSON. At the same time, it is worth grouping applications by topic (PRODUCTIVITY, GAME, TOOLS, etc.), and providing translation of the names of topics into Russian.

The following fields are available in the file:

App, Category, Rating, Reviews, Size, Installs, Type, Price, Content Rating, Genres, Last Updated, Current Ver, Android Ver

- Android Ver needs to be brought to the minimum API (as a number), for example OS version 5.0 corresponds to API 21
- Installs (number of installations) convert to number (for later sorting)
- Price should be formatted as a logical field (true/false) (paid/free)
- Genres is a list of genres within a category.
- Convert date to ![ISO 8601 format](https://docs.jsonata.org/date-time)

