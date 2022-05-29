package readAPI;

import controller.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.AnalysisDAO;
import persistence.dao.DAO;
import persistence.dto.AnalysisDTO;
import persistence.dto.DTO;
import service.Country;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RSI {
    static SqlSessionFactory sqlSessionFactory;
    private static DAO[] daos;
    private static AnalysisDAO analysisDAO;

    private static final int COUNTRY_COUNT = 22;
    private static final int DAY_MAX = 31;
    private static final int MONTH_MAX = 12;

    private int period;
    private EMA ema;

    public RSI(final int period) {
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        daos = new DAO[COUNTRY_COUNT];
        analysisDAO = new AnalysisDAO(sqlSessionFactory,2);
        for (int i = 0; i < daos.length; i++) {
            daos[i] = new DAO(sqlSessionFactory, i);
        }
        this.period = period;
        this.ema = new EMA(2 * period - 1);
    }

    public void doRsi(){
        for(int i=0;i<COUNTRY_COUNT;i++){ //나라별로
            double[] bkprList = putListData(15,i);
            double[] result = count(bkprList);

            AnalysisDTO analysisDTO = new AnalysisDTO();
            analysisDTO.setForex(Country.getUnit(i));
            analysisDTO.setFigure(result[0]);
            analysisDAO.update(analysisDTO);
        }
    }

    private double[] count(final double[] prizes) {
        final double[] up = new double[prizes.length - 1];
        final double[] down = new double[prizes.length - 1];
        for (int i = 0; i < prizes.length - 1; i++) {
            if (prizes[i] > prizes[i + 1]) {
                up[i] = prizes[i] - prizes[i + 1];
                down[i] = 0;
            }
            if (prizes[i] < prizes[i + 1]) {
                down[i] = Math.abs(prizes[i] - prizes[i + 1]);
                up[i] = 0;
            }
        }

        final int emaLength = prizes.length - 2 * period;
        double[] rsis = new double[0];
        if (emaLength > 0) {
            final double[] emus = new double[emaLength];
            final double[] emds = new double[emaLength];
            ema.count(up, 0, emus);
            ema.count(down, 0, emds);

            rsis = new double[emaLength];
            for (int i = 0; i < rsis.length; i++) {
                rsis[i] = 100 - (100 / (double) (1 + emus[i] / emds[i]));
            }
        }
        return rsis;
    }

    private double[] putListData(int count,int country){
        ArrayList<DTO> dtos = ReadData.dayTimeRead(sqlSessionFactory); // 오늘데이터 불러오기
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

        double[] list = new double[count];
        String todayString = dtos.get(country).getBkpr();
        todayString = todayString.replace(",","");
        double today = Double.parseDouble(todayString);

        list[0] = today; //오늘 데이터 넣기
        count--; d--;
        while(count > 0){
            if(d < 1){
                d = DAY_MAX;
                m -= 1;
            }else if(m < 1){
                int y = Integer.parseInt(year);
                m = MONTH_MAX;
                y -= 1;
                year = Integer.toString(y);
            } //날짜 계산
            String date = ReadData.getDate(year,m,d);
            String bkpr = daos[country].selectOneBkpr(date);
            if(bkpr != null){
                bkpr = bkpr.replace(",","");
                list[count] = Double.parseDouble(bkpr);
                count--;
            }
            d--;
        }
        return list;
    }

}
