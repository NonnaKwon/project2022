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
    private DAO[] daos;

    private static String authKey = "hV5ckkLjhPQvfzPw5eZOyVUM7acbHBFp";
    private static String dataType = "AP01";
    private static String searchDate;
    private static String apiURL;

    private static final int COUNTRY_COUNT = 31;
    private static final int DAY_MAX = 31;
    private static final int MONTH_MAX = 12;

    public ReadData(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        daos = new DAO[COUNTRY_COUNT];
        for(int i=0;i<daos.length;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
    }

    public static void allDataRead(String year){ //과거데이터 읽기
        DTO DTO = new DTO();
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
                        JSONObject tutorials = (JSONObject) o;
                        String key = (String)tutorials.get("cur_unit");
                        DTO.setDate(searchDate);
                        DTO.setUnit((String)tutorials.get("cur_unit"));
                        DTO.setTtb((String)tutorials.get("ttb"));
                        DTO.setTts((String)tutorials.get("tts"));
                        DTO.setDeal((String)tutorials.get("deal_bas_r"));
                        DTO.setBkpr((String)tutorials.get("bkpr"));

//                        if(key.equals("한국 원")){
//                            koreaDAO.insert(DTO);
//                        }else if(key.equals("위안화")){
//                            yuanDAO.insert(DTO);
//                        }else if(key.equals("일본 옌")){
//                            japanDAO.insert(DTO);
//                        }else if(key.equals("아랍에미리트 디르함")){
//                            arabDAO.insert(DTO);
//                        }else if(key.equals("오스트리아 실링")) {
//                            austriaDAO.insert(DTO);
//                        }else if(key.equals("호주 달러")) {
//                            australiaDAO.insert(DTO);
//                        }else if(key.equals("바레인 디나르")) {
//                            bahrainDAO.insert(DTO);
//                        }else if(key.equals("캐나다 달러")) {
//                            canadaDAO.insert(DTO);
//                        }else if(key.equals("스위스 프랑")) {
//                            suisseDAO.insert(DTO);
//                        }else if(key.equals("독일 마르크")) {//
//                            germanyDAO.insert(DTO);
//                        }else if(key.equals("덴마아크 크로네")) {
//                            denmarkDAO.insert(DTO);
//                        }else if(key.equals("스페인 페세타")) {//
//                            spainDAO.insert(DTO);
//                        }else if(key.equals("핀란드 마르카")) {//
//                            finlandDAO.insert(DTO);
//                        }else if(key.equals("유로")) {//
//                            euroDAO.insert(DTO);
//                        }else if(key.equals("프랑스 프랑")) {//
//                            franceDAO.insert(DTO);
//                        }else if(key.equals("영국 파운드")) {
//                            ukDAO.insert(DTO);
//                        }else if(key.equals("홍콩 달러")) {
//                            hongkongDAO.insert(DTO);
//                        }else if(key.equals("인도네시아 루피아")) {//
//                            indonesiaDAO.insert(DTO);
//                        }else if(key.equals("쿠웨이트 디나르")) {
//                            kuwaitDAO.insert(DTO);
//                        }else if(key.equals("말레이지아 링기트")) {
//                            malaysiaDAO.insert(DTO);
//                        }else if(key.equals("네델란드 길더")) {//
//                            netherlandsDAO.insert(DTO);
//                        }else if(key.equals("노르웨이 크로네")) {
//                            norwayDAO.insert(DTO);
//                        }else if(key.equals("뉴질랜드 달러")) {
//                            newzealandDAO.insert(DTO);
//                        }else if(key.equals("사우디 리얄")) {
//                            saudiDAO.insert(DTO);
//                        }else if(key.equals("스웨덴 크로나")) {
//                            swedenDAO.insert(DTO);
//                        }else if(key.equals("싱가포르 달러")) {
//                            singaporeDAO.insert(DTO);
//                        }else if(key.equals("태국 바트")) {
//                            thailandDAO.insert(DTO);
//                        }else if(key.equals("미국 달러")) {
//                            americaDAO.insert(DTO);
//                        }else if(key.equals("이태리 리라")) {//
//                            italyDAO.insert(DTO);
//                        }else if(key.equals("씨에프에이 프랑(비씨에이오)")) {//
//                            cfaDAO.insert(DTO);
//                        }else if(key.equals("벨기에 프랑")) {//
//                            belgiumDAO.insert(DTO);
//                        }
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

//    public static void dayTimeRead(){ //오늘꺼 받아오기
//        KoreaDTO koreaDTO = new KoreaDTO();
//        AmericaDTO americaDTO = new AmericaDTO();
//        JapanDTO japanDTO = new JapanDTO();
//        DTO DTO = new DTO();
//        JSONParser parser = new JSONParser();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Calendar calendar = Calendar.getInstance();
//        String strToday = sdf.format(calendar.getTime());
//        searchDate = strToday;
//
//        apiURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="+authKey+ "&searchdate="+searchDate+"&data="+dataType;
//        try {
//            URL oracle = new URL(apiURL);
//            URLConnection yc = oracle.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//            String inputLine;
//            JSONArray a=null;
//            while ((inputLine = in.readLine()) != null) {
//                a = (JSONArray) parser.parse(inputLine);
//            }
//
//
//            ///없으면 insert, 있으면 update로 바꿔야함!
//            for (Object o : a) {
//                System.out.println(searchDate); //들어가는 날짜
//                JSONObject tutorials = (JSONObject) o;
//                String key = (String)tutorials.get("cur_nm");
//                if(key.equals("한국 원")){
//                    koreaDTO.setDate(searchDate);
//                    koreaDTO.setUnit((String)tutorials.get("cur_unit"));
//                    koreaDTO.setTtb((String)tutorials.get("ttb"));
//                    koreaDTO.setTts((String)tutorials.get("tts"));
//                    koreaDTO.setDeal((String)tutorials.get("deal_bas_r"));
//                    koreaDTO.setBkpr((String)tutorials.get("bkpr"));
//                    koreaDAO.insert(koreaDTO);
//                }else if(key.equals("위안화")){
//                    DTO.setDate(searchDate);
//                    DTO.setUnit((String)tutorials.get("cur_unit"));
//                    DTO.setTtb((String)tutorials.get("ttb"));
//                    DTO.setTts((String)tutorials.get("tts"));
//                    DTO.setDeal((String)tutorials.get("deal_bas_r"));
//                    DTO.setBkpr((String)tutorials.get("bkpr"));
//                    yuanDAO.insert(DTO);
//                }else if(key.equals("일본 옌")){
//                    japanDTO.setDate(searchDate);
//                    japanDTO.setUnit((String)tutorials.get("cur_unit"));
//                    japanDTO.setTtb((String)tutorials.get("ttb"));
//                    japanDTO.setTts((String)tutorials.get("tts"));
//                    japanDTO.setDeal((String)tutorials.get("deal_bas_r"));
//                    japanDTO.setBkpr((String)tutorials.get("bkpr"));
//                    japanDAO.insert(japanDTO);
//                }else if(key.equals("미국 달러")){
//                    americaDTO.setDate(searchDate);
//                    americaDTO.setUnit((String)tutorials.get("cur_unit"));
//                    americaDTO.setTtb((String)tutorials.get("ttb"));
//                    americaDTO.setTts((String)tutorials.get("tts"));
//                    americaDTO.setDeal((String)tutorials.get("deal_bas_r"));
//                    americaDTO.setBkpr((String)tutorials.get("bkpr"));
//                    americaDAO.insert(americaDTO);
//                }
//            }
//            in.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (org.json.simple.parser.ParseException e) {
//            e.printStackTrace();
//        } catch (NullPointerException e){
//            e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    private static String getDate(String year,int month,int day){
        String strMonth,strDay;
        strMonth = (month<10) ? "0"+Integer.toString(month) : Integer.toString(month);
        strDay = (day<10) ? "0"+Integer.toString(day) : Integer.toString(day);
        return year + strMonth + strDay;
    }

}
