package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.AnalysisDAO;
import persistence.dao.DAO;
import persistence.dto.*;
import readAPI.ReadData;

import java.util.ArrayList;

public class AnalysisService {
    static final int KIND = 3;
    private SqlSessionFactory sqlSessionFactory;
    private AnalysisDAO[] daos = new AnalysisDAO[KIND];
    private ArrayList<DTO> nowData;
    private Double[] usdData = new Double[KIND];

    private static String[] typeArr = new String[]{"HIGHS/LOWS","MACD","RSI"};

    public AnalysisService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<KIND;i++){
            daos[i] = new AnalysisDAO(sqlSessionFactory,i);
            usdData[i] = daos[i].selectFigure("USD");
        }
        nowData = ReadData.dayTimeRead(sqlSessionFactory);
    }

    public ResTableDTO analysisService(ReqTableDTO requestDTO)  {
        ResTableDTO result = new ResTableDTO();
        TableData[] list = new TableData[KIND];

        for(int i=0;i<KIND;i++){
            TableData tb= new TableData();
            tb.setType(typeArr[i]); // 무슨기법 썼는지

            double curData = daos[i].selectFigure(requestDTO.getForex());
            analyze(usdData[i],curData,tb);

            list[i] = tb;
        }

        result.setTableData(list);
        return result;
    }

    public void analyze(double denominator, double molecular,TableData tableData) { //분모,분자,테이블데이터
        int score ;
        Double tmp = molecular/denominator * 100 -100d;

        if(tmp > 60) score=5;
        else if (tmp>30) score=4;
        else if (tmp>0) score=3;
        else if (tmp>-30) score=2;
        else score=1;

        tableData.setFigure(String.valueOf(tmp)); //값
        tableData.setAnalysis(String.valueOf(score)); //점수
    }
    public void insert(){

    }
}
