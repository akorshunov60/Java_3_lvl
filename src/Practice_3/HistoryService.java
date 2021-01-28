package Practice_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryService {
    private static ResultSet resultSet;

    static Map<String, String> censVocabulary = new HashMap<>();
    static {
        censVocabulary.put("спб", "СПб");
    }

    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String username = reader.readLine();

        if (authUser(username)) {
            int userId = getUserId(username);
            startChart(userId);
        }
    }

    private static String isCens(String message) {
        return censVocabulary.getOrDefault(message, message);
    }

    private static void startChart(int userId) {
        String fileName = prepareFileName(userId);
        readLastMSG(fileName);
    }

    private static void readLastMSG(String fileName) {

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String str;
                List<String> msgList = new ArrayList<>();
                while ((str = reader.readLine()) != null) {
                    String censStr = isCens(str);
                    msgList.add(censStr);
                }
                if (msgList.size() > 100) {
                    msgList = msgList.subList(msgList.size() - 100, msgList.size());
                } else {
                    msgList = msgList.subList(0, msgList.size() - 1);
                }
            }
            catch (IOException e) {
                System.out.println("Сообщение об ошибке");
                e.printStackTrace();
            }
    }

    private static String prepareFileName(int userId) {
        String fileName = userId + ".his";
        return fileName;
    }

    private static int getUserId(String username) throws SQLException {
        return resultSet.getInt("id");
    }

    private static boolean authUser(String username) {
        return true;
    }

    public static void setResultSet(ResultSet resultSet) {
        HistoryService.resultSet = resultSet;
    }
}
