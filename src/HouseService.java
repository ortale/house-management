import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HouseService {
    private static final String BASE_URL = "http://localhost:3000/api/houses";

    private String sendRequest(String endpoint, String method, String jsonInputString) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        if (jsonInputString != null) {
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            throw new RuntimeException("HTTP request failed with code: " + code);
        }
    }

    public List<House> getAllHouses() throws Exception {
        String response = sendRequest(BASE_URL + "/", "GET", null);
        List<House> houses = new ArrayList<>();

        // Basic manual parsing of JSON
        // Adjust parsing code according to your actual JSON structure
        String[] jsonObjects = response.substring(1, response.length() - 1).split("\\},\\{"); // Split the JSON array
        for (String jsonObject : jsonObjects) {
            jsonObject = jsonObject.replace("{", "").replace("}", "").replace("\"", "");
            String[] keyValuePairs = jsonObject.split(",");
            int id = 0;
            String name = "";
            String email = "";

            for (String pair : keyValuePairs) {
                String[] entry = pair.split(":");
                if (entry[0].trim().equals("id")) {
                    id = Integer.parseInt(entry[1].trim());
                } else if (entry[0].trim().equals("name")) {
                    name = entry[1].trim();
                } else if (entry[0].trim().equals("email")) {
                    email = entry[1].trim();
                }

                if (id != 0 && !name.isEmpty() && !email.isEmpty()) break;
            }
            House house = new House();
            house.setId(id);
            house.setName(name);
            house.setEmail(email);
            houses.add(house);
        }
        return houses;
    }

    public void addHouse(House house) throws Exception {
        String jsonInputString = "{\"name\":\"" + house.getName() + ",\"email\":\"" + house.getEmail() + "\"}";
        sendRequest(BASE_URL + "/", "POST", jsonInputString);
    }

    public void updateHouse(House house) throws Exception {
        String jsonInputString = "{\"id\":" + house.getId() + ",\"name\":\"" + house.getName() + "\",\"email\":\"" + house.getEmail() + "\"}";
        sendRequest(BASE_URL + "/" + house.getId(), "PUT", jsonInputString);
    }

    public void deleteHouse(int houseId) throws Exception {
        sendRequest(BASE_URL + "/delete?id=" + houseId, "DELETE", null);
    }

    public void addCertificate(int houseId, Certificate certificate) throws Exception {
        String jsonInputString = "{\"name\":\"" + certificate.getName() + "\",\"date\":\"" + certificate.getIssueDate() + "\",\"houseId\":\"" + houseId + "\",\"expireDate\":\"" + certificate.getExpireDate() + "\"}";
        sendRequest("http://localhost:3000/api" + "/certificates", "POST", jsonInputString);
    }

    public void updateCertificate(int houseId, Certificate certificate) throws Exception {
        String jsonInputString = "{\"id\":" + certificate.getId() + ",\"name\":\"" + certificate.getName() + "\",\"date\":\"" + certificate.getIssueDate() + "\",\"expiryDate\":\"" + certificate.getExpireDate() + "\"}";
        sendRequest(BASE_URL + "/" + houseId + "/certificates/update", "PUT", jsonInputString);
    }

    public void deleteCertificate(int houseId, int certificateId) throws Exception {
        sendRequest(BASE_URL + "/" + houseId + "/certificates/delete?id=" + certificateId, "DELETE", null);
    }

    public void addPayment(int houseId, Payment payment) throws Exception {
        String jsonInputString = "{\"description\":\"" + payment.getDescription() + "\",\"date\":\"" + payment.getPaymentDate() + "\",\"dueDate\":\"" + payment.getDueDate() + "\"}";
        sendRequest(BASE_URL + "/" + houseId + "/payments/add", "POST", jsonInputString);
    }

    public void updatePayment(int houseId, Payment payment) throws Exception {
        String jsonInputString = "{\"id\":" + payment.getId() + ",\"description\":\"" + payment.getDescription() + "\",\"date\":\"" + payment.getPaymentDate() + "\",\"dueDate\":\"" + payment.getDueDate() + "\"}";
        sendRequest(BASE_URL + "/" + houseId + "/payments/update", "PUT", jsonInputString);
    }

    public void deletePayment(int houseId, int paymentId) throws Exception {
        sendRequest(BASE_URL + "/" + houseId + "/payments/delete?id=" + paymentId, "DELETE", null);
    }
}
