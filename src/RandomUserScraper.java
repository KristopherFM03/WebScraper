import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

public class RandomUserScraper {

    public static void main(String[] args) {
        try {
            //construct an URI to request 100 users from the RandomUser API
            //results parameter tells the API to return X user entries
            URI uri = new URI("https", "randomuser.me", "/api/", "results=100", null);
            URL url = uri.toURL();

            //open a http connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //request method to GET, with 5 second timeout for both connecting and reading the response
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            //read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            //reading response line by line, appending each line to response string
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            //close reader after the entire response is read
            reader.close();

            //parse the JSON response 
            JSONObject jsonResponse = new JSONObject(response.toString());
            //we only care about the "results" array, which contains the user data
            JSONArray results = jsonResponse.getJSONArray("results");

            //loop through all users in the 'results' array
            for (int i = 0; i < results.length(); i++) {
                JSONObject user = results.getJSONObject(i);
                String name = user.getJSONObject("name").getString("first") + " " + user.getJSONObject("name").getString("last");
                String rawBirthday = user.getJSONObject("dob").getString("date");

                //trim the timestamp and get the formatted date
                String trimmedBirthday = trimTimestamp(rawBirthday);

                //write to text file
                FileWriter fileWriter = new FileWriter("user_data.txt", true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write("Name: " + name + ", Birthday: " + trimmedBirthday + "\n");
                writer.close();
            }

            //success!
            System.out.println("User data saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //trim the timestamp
    private static String trimTimestamp(String timestamp) throws ParseException {
        //format of the timestamp
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //we dont want to allow invalid dates
        inputFormat.setLenient(false);
        
        //parse the raw timestamp
        Date date = inputFormat.parse(timestamp);
        
        //define the output format for the trimmed timestamp
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        return outputFormat.format(date);
    }
}
