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
import java.util.Calendar;

public class ReadData {
    static SqlSessionFactory sqlSessionFactory;
    static YuanDAO yuanDAO;
    static KoreaDAO koreaDAO;
    static AmericaDAO americaDAO;
    static JapanDAO japanDAO;

    private static String authKey = "AkkYyhYUysxtNQABF1j9pfh7wrjT5Pc6";
    private static String dataType = "AP01";
    private static String searchDate;
    private static String apiURL;

    private static final int DAY_MAX = 31;
    private static final int MONTH_MAX = 12;

    public ReadData(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        yuanDAO = new YuanDAO(sqlSessionFactory);
        koreaDAO = new KoreaDAO(sqlSessionFactory);
        americaDAO = new AmericaDAO(sqlSessionFactory);
        japanDAO = new JapanDAO(sqlSessionFactory);
    }

    public static void allDataRead(String year){ //과거데이터 읽기
        KoreaDTO koreaDTO = new KoreaDTO();
        AmericaDTO americaDTO = new AmericaDTO();
        JapanDTO japanDTO = new JapanDTO();
        YuanDTO yuanDTO = new YuanDTO();
        JSONParser parser = new JSONParser();

        for(int month = 1 ; month<=MONTH_MAX ; month++){
            for(int day = 1 ; day<=DAY_MAX ; day++){
                searchDate = getDate(year,month,day);
                apiURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="+authKey+ "&searchdate="+searchDate+"&data="+dataType;
                try {
                    URL oracle = new URL(apiURL);
                    URLConnection yc = oracle.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine;
                    JSONArray a=null;
                    while ((inputLine = in.readLine()) != null) {
                        a = (JSONArray) parser.parse(inputLine);
                    }

                    for (Object o : a) {
                        System.out.println(searchDate); //들어가는 날짜
                        JSONObject tutorials = (JSONObject) o;
                        String key = (String)tutorials.get("cur_nm");
                        if(key.equals("한국 원")){
                            koreaDTO.setDate(searchDate);
                            koreaDTO.setUnit((String)tutorials.get("cur_unit"));
                            koreaDTO.setTtb((String)tutorials.get("ttb"));
                            koreaDTO.setTts((String)tutorials.get("tts"));
                            koreaDTO.setDeal((String)tutorials.get("deal_bas_r"));
                            koreaDTO.setBkpr((String)tutorials.get("bkpr"));
                            koreaDAO.insert(koreaDTO);
                        }else if(key.equals("위안화")){
                            yuanDTO.setDate(searchDate);
                            yuanDTO.setUnit((String)tutorials.get("cur_unit"));
                            yuanDTO.setTtb((String)tutorials.get("ttb"));
                            yuanDTO.setTts((String)tutorials.get("tts"));
                            yuanDTO.setDeal((String)tutorials.get("deal_bas_r"));
                            yuanDTO.setBkpr((String)tutorials.get("bkpr"));
                            yuanDAO.insert(yuanDTO);
                        }else if(key.equals("일본 옌")){
                            japanDTO.setDate(searchDate);
                            japanDTO.setUnit((String)tutorials.get("cur_unit"));
                            japanDTO.setTtb((String)tutorials.get("ttb"));
                            japanDTO.setTts((String)tutorials.get("tts"));
                            japanDTO.setDeal((String)tutorials.get("deal_bas_r"));
                            japanDTO.setBkpr((String)tutorials.get("bkpr"));
                            japanDAO.insert(japanDTO);
                        }else if(key.equals("미국 달러")){
                            americaDTO.setDate(searchDate);
                            americaDTO.setUnit((String)tutorials.get("cur_unit"));
                            americaDTO.setTtb((String)tutorials.get("ttb"));
                            americaDTO.setTts((String)tutorials.get("tts"));
                            americaDTO.setDeal((String)tutorials.get("deal_bas_r"));
                            americaDTO.setBkpr((String)tutorials.get("bkpr"));
                            americaDAO.insert(americaDTO);
                        }
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void dayTimeRead(){ //오늘꺼 받아오기
        KoreaDTO koreaDTO = new KoreaDTO();
        AmericaDTO americaDTO = new AmericaDTO();
        JapanDTO japanDTO = new JapanDTO();
        YuanDTO yuanDTO = new YuanDTO();
        JSONParser parser = new JSONParser();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String strToday = sdf.format(calendar.getTime());
        searchDate = strToday;

        apiURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="+authKey+ "&searchdate="+searchDate+"&data="+dataType;
        try {
            URL oracle = new URL(apiURL);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            JSONArray a=null;
            while ((inputLine = in.readLine()) != null) {
                a = (JSONArray) parser.parse(inputLine);
            }

            
            ///없으면 insert, 있으면 update로 바꿔야함!
            for (Object o : a) {
                System.out.println(searchDate); //들어가는 날짜
                JSONObject tutorials = (JSONObject) o;
                String key = (String)tutorials.get("cur_nm");
                if(key.equals("한국 원")){
                    koreaDTO.setDate(searchDate);
                    koreaDTO.setUnit((String)tutorials.get("cur_unit"));
                    koreaDTO.setTtb((String)tutorials.get("ttb"));
                    koreaDTO.setTts((String)tutorials.get("tts"));
                    koreaDTO.setDeal((String)tutorials.get("deal_bas_r"));
                    koreaDTO.setBkpr((String)tutorials.get("bkpr"));
                    koreaDAO.insert(koreaDTO);
                }else if(key.equals("위안화")){
                    yuanDTO.setDate(searchDate);
                    yuanDTO.setUnit((String)tutorials.get("cur_unit"));
                    yuanDTO.setTtb((String)tutorials.get("ttb"));
                    yuanDTO.setTts((String)tutorials.get("tts"));
                    yuanDTO.setDeal((String)tutorials.get("deal_bas_r"));
                    yuanDTO.setBkpr((String)tutorials.get("bkpr"));
                    yuanDAO.insert(yuanDTO);
                }else if(key.equals("일본 옌")){
                    japanDTO.setDate(searchDate);
                    japanDTO.setUnit((String)tutorials.get("cur_unit"));
                    japanDTO.setTtb((String)tutorials.get("ttb"));
                    japanDTO.setTts((String)tutorials.get("tts"));
                    japanDTO.setDeal((String)tutorials.get("deal_bas_r"));
                    japanDTO.setBkpr((String)tutorials.get("bkpr"));
                    japanDAO.insert(japanDTO);
                }else if(key.equals("미국 달러")){
                    americaDTO.setDate(searchDate);
                    americaDTO.setUnit((String)tutorials.get("cur_unit"));
                    americaDTO.setTtb((String)tutorials.get("ttb"));
                    americaDTO.setTts((String)tutorials.get("tts"));
                    americaDTO.setDeal((String)tutorials.get("deal_bas_r"));
                    americaDTO.setBkpr((String)tutorials.get("bkpr"));
                    americaDAO.insert(americaDTO);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getDate(String year,int month,int day){
        String strMonth,strDay;
        strMonth = (month<10) ? "0"+Integer.toString(month) : Integer.toString(month);
        strDay = (day<10) ? "0"+Integer.toString(day) : Integer.toString(day);
        return year + strMonth + strDay;
    }

}
