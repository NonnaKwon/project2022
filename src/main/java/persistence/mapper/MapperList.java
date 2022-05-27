package persistence.mapper;

import persistence.mapper.analysis.*;

public class MapperList {
    public static Class[] mapperList = {
            ArabMapper.class,
            AustraliaMapper.class,
            BahrainMapper.class,
            CanadaMapper.class,
            SuisseMapper.class,
            YuanMapper.class,
            DenmarkMapper.class,
            EuroMapper.class,
            UkMapper.class,
            HongkongMapper.class,
            IndonesiaMapper.class,
            JapanMapper.class,
            KoreaMapper.class,
            KuwaitMapper.class,
            MalaysiaMapper.class,
            NorwayMapper.class,
            NewzealandMapper.class,
            SaudiMapper.class,
            SwedenMapper.class,
            SingaporeMapper.class,
            ThailandMapper.class,
            AmericaMapper.class
    };


    /* 인덱스
    highs/lows : 0
    macd : 1
    rsi : 2
    */
    public static Class[] getAnalysisMapperList = {
            HighLowsMapper.class,
            MacdMapper.class,
            RsiMapper.class
    };

}
