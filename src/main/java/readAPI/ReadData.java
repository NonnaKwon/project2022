package readAPI;

import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import persistence.dao.*;
import persistence.dto.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.System.in;

public class ReadData {
    static SqlSessionFactory sqlSessionFactory;
    private static DAO[] daos;

    private static String authKey = "hV5ckkLjhPQvfzPw5eZOyVUM7acbHBFp";
    private static String dataType = "AP01";
    private static String searchDate;
    private static String apiURL;

    private static final int COUNTRY_COUNT = 31;
    private static final int DAY_MAX = 31;
    private static final int MONTH_MAX = 12;

    public ReadData(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        daos = new DAO[COUNTRY_COUNT];
        for (int i = 0; i < daos.length; i++) {
            daos[i] = new DAO(sqlSessionFactory, i);
        }
    }

    public static void allDataRead(String year) { //과거데이터 읽기
        DTO dto = new DTO();
        JSONParser parser = new JSONParser();

        for (int month = 1; month <= MONTH_MAX; month++) {
            for (int day = 1; day <= DAY_MAX; day++) {
                searchDate = getDate(year, month, day);
                apiURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + authKey + "&searchdate=" + searchDate + "&data=" + dataType;
                try {
                    URL oracle = new URL(apiURL);
                    URLConnection yc = oracle.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine;
                    JSONArray a = null;
                    while ((inputLine = in.readLine()) != null) {
                        a = (JSONArray) parser.parse(inputLine);
                    }

                    for (Object o : a) {
                        JSONObject tutorials = (JSONObject) o;
                        String key = (String) tutorials.get("cur_unit");
                        dto.setDate(searchDate);
                        dto.setUnit((String) tutorials.get("cur_unit"));
                        dto.setTtb((String) tutorials.get("ttb"));
                        dto.setTts((String) tutorials.get("tts"));
                        dto.setDeal((String) tutorials.get("deal_bas_r"));
                        dto.setBkpr((String) tutorials.get("bkpr"));
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<DTO> dayTimeRead() { //오늘꺼 받아오기
        ArrayList<DTO> list = new ArrayList<DTO>();
        JSONParser parser = new JSONParser();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String strToday = sdf.format(calendar.getTime());
        searchDate = "20220511";

        apiURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + authKey + "&searchdate=" + searchDate + "&data=" + dataType;
        try {
            URL oracle = new URL(apiURL);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            JSONArray a = null;
            while ((inputLine = in.readLine()) != null) {
                a = (JSONArray) parser.parse(inputLine);
                System.out.println(inputLine);
            }
            for (Object o : a) {
                DTO dto = new DTO();
                JSONObject tutorials = (JSONObject) o;
                dto.setDate(searchDate);
                dto.setUnit((String) tutorials.get("cur_unit"));
                dto.setTtb((String) tutorials.get("ttb"));
                dto.setTts((String) tutorials.get("tts"));
                dto.setDeal((String) tutorials.get("deal_bas_r"));
                dto.setBkpr((String) tutorials.get("bkpr"));
                System.out.println(dto.toString());
                list.add(dto);
            }

            in.close();
            ///없으면 insert, 있으면 update로 바꿔야함!
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static String getDate(String year, int month, int day) {
        String strMonth, strDay;
        strMonth = (month < 10) ? "0" + Integer.toString(month) : Integer.toString(month);
        strDay = (day < 10) ? "0" + Integer.toString(day) : Integer.toString(day);
        return year + strMonth + strDay;
    }
}

