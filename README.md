# Random User Scraper
A web scraper that fetches random user data from the [RandomUser.me](https://randomuser.me/) API. It requests 100 random users, extracting their name and birthdate, formats the birthdate by trimming the timestamp, and saves this information into a text file. The program uses HttpURLConnection to send requests, JSONObject and JSONArray for parsing the JSON response, and file handling to write the results to user_data.txt. This project is planned for use in a side project called Blaseball Reimagined.

## Credits
This project uses the [JSON-java Library](https://github.com/stleary/JSON-java)


## How to Run
To compile and run the project, use the following commands:
```java
javac -cp .\lib\json-20240303.jar .\src\RandomUserScraper.java
java -cp .\lib\json-20240303.jar .\src\RandomUserScraper.java
```