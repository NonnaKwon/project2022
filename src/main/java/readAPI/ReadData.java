package readAPI;

import controller.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import persistence.dao.*;
import persistence.dto.*;
import service.Country;

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

    //private static String authKey = "hV5ckkLjhPQvfzPw5eZOyVUM7acbHBFp"; //코드1
    private static String authKey = "AkkYyhYUysxtNQABF1j9pfh7wrjT5Pc6"; //코드2
    private static String dataType = "AP01";
    private static String searchDate;
    private static String apiURL;

    private static final int COUNTRY_COUNT = 23;
    private static final int DAY_MAX = 31;
    private static final int MONTH_MAX = 12;

    public static void allDataRead(String year) { //과거데이터 읽기
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        daos = new DAO[COUNTRY_COUNT];
        for (int i = 0; i < daos.length; i++) {
            daos[i] = new DAO(sqlSessionFactory, i);
        }

        DTO dto = new DTO();
        JSONParser parser = new JSONParser();

        for (int month = 1; month <= MONTH_MAX; month++) {
            for (int day = 1; day <= DAY_MAX; day++) {
                searchDate = getDate(year, month, day);
                System.out.println(searchDate);
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
                        if(((long)tutorials.get("result")==4)){
                            System.out.println("인증횟수 제한");
                            return;
                        }
                        dto.setDate(searchDate);
                        dto.setUnit((String) tutorials.get("cur_unit"));
                        dto.setTtb((String) tutorials.get("ttb"));
                        dto.setTts((String) tutorials.get("tts"));
                        dto.setDeal((String) tutorials.get("deal_bas_r"));
                        dto.setBkpr((String) tutorials.get("bkpr"));

                        int countryCode = Country.getCodeOriginalVer((String) tutorials.get("cur_unit"));
                        //System.out.println(dto.getUnit() + "( "+(String) tutorials.get("cur_nm")+" )"+", "+countryCode);

                        if(countryCode != -1) {
                            daos[countryCode].insert(dto);
                        }
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("없는날짜");
                    //e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<DTO> dayTimeRead(SqlSessionFactory sqlSessionFactory) { //오늘꺼 받아오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();

        String year = sdf.format(calendar.getTime());
        sdf = new SimpleDateFormat("MM");
        int m = Integer.parseInt(sdf.format(calendar.getTime()));
        sdf = new SimpleDateFormat("dd");
        int d = Integer.parseInt(sdf.format(calendar.getTime()));

        sdf = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(sdf.format(calendar.getTime()));

        if(hour < 10){ //오전 10시 전이면
            d -= 1; //전날 데이터 불러오기
        }

        ArrayList<DTO> list = dayRead(year,m,d); //데이터 불러오기
        return list;
    }

    private static ArrayList<DTO> dayRead(String year,int m,int d){
        if(d < 1){
            d = 31;
            m -= 1;
        }else if(m < 1){
            int y = Integer.parseInt(year);
            m = 12;
            y -= 1;
            year = Integer.toString(y);
        }

        searchDate = getDate(year,m,d);
        ArrayList<DTO> list = new ArrayList<DTO>();
        JSONParser parser = new JSONParser();
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
            if(a == null){ //만약 그 날짜에 데이터가 없으면
                d -= 1;  //전날 데이터 불러오기
                return dayRead(year,m,d);
            }else{
                for (Object o : a) {
                    DTO dto = new DTO();
                    JSONObject tutorials = (JSONObject) o;
                    dto.setDate(searchDate);
                    dto.setUnit((String) tutorials.get("cur_unit"));
                    dto.setTtb((String) tutorials.get("ttb"));
                    dto.setTts((String) tutorials.get("tts"));
                    dto.setDeal((String) tutorials.get("deal_bas_r"));
                    dto.setBkpr((String) tutorials.get("bkpr"));
                    if(dto.getUnit().equals("BND")){
                        continue;
                    }else{
                        list.add(dto);
                    }
                }
                in.close();
            }
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

