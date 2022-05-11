
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.*;

public class Country {
    SqlSessionFactory sqlSessionFactory;
    static YuanDAO yuanDAO;
    static KoreaDAO koreaDAO;
    static AmericaDAO americaDAO;
    static JapanDAO japanDAO;
    static ArabDAO arabDAO;
    static AustraliaDAO australiaDAO;
    static AustriaDAO austriaDAO;
    static BahrainDAO bahrainDAO;
    static BelgiumDAO belgiumDAO;
    static CanadaDAO canadaDAO;
    static CfaDAO cfaDAO;
    static DenmarkDAO denmarkDAO;
    static EuroDAO euroDAO;
    static FinlandDAO finlandDAO;
    static FranceDAO franceDAO;
    static GermanyDAO germanyDAO;
    static HongkongDAO hongkongDAO;
    static IndonesiaDAO indonesiaDAO;
    static ItalyDAO italyDAO;
    static KuwaitDAO kuwaitDAO;
    static MalaysiaDAO malaysiaDAO;
    static NetherlandsDAO netherlandsDAO;
    static NewzealandDAO newzealandDAO;
    static NorwayDAO norwayDAO;
    static SaudiDAO saudiDAO;
    static SingaporeDAO singaporeDAO;
    static SpainDAO spainDAO;
    static SuisseDAO suisseDAO;
    static SwedenDAO swedenDAO;
    static ThailandDAO thailandDAO;
    static UkDAO ukDAO;

    Country(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        yuanDAO = new YuanDAO(sqlSessionFactory);
        koreaDAO = new KoreaDAO(sqlSessionFactory);
        americaDAO = new AmericaDAO(sqlSessionFactory);
        japanDAO = new JapanDAO(sqlSessionFactory);
        arabDAO = new ArabDAO(sqlSessionFactory);
        australiaDAO = new AustraliaDAO(sqlSessionFactory);
        austriaDAO = new AustriaDAO(sqlSessionFactory);
        bahrainDAO = new BahrainDAO(sqlSessionFactory);
        belgiumDAO = new BelgiumDAO(sqlSessionFactory);
        canadaDAO = new CanadaDAO(sqlSessionFactory);
        cfaDAO = new CfaDAO(sqlSessionFactory);
        denmarkDAO = new DenmarkDAO(sqlSessionFactory);
        euroDAO = new EuroDAO(sqlSessionFactory);
        finlandDAO = new FinlandDAO(sqlSessionFactory);
        franceDAO = new FranceDAO(sqlSessionFactory);
        germanyDAO = new GermanyDAO(sqlSessionFactory);
        hongkongDAO = new HongkongDAO(sqlSessionFactory);
        indonesiaDAO = new IndonesiaDAO(sqlSessionFactory);
        italyDAO = new ItalyDAO(sqlSessionFactory);
        kuwaitDAO = new KuwaitDAO(sqlSessionFactory);
        malaysiaDAO = new MalaysiaDAO(sqlSessionFactory);
        netherlandsDAO = new NetherlandsDAO(sqlSessionFactory);
        newzealandDAO = new NewzealandDAO(sqlSessionFactory);
        norwayDAO = new NorwayDAO(sqlSessionFactory);
        saudiDAO = new SaudiDAO(sqlSessionFactory);
        singaporeDAO = new SingaporeDAO(sqlSessionFactory);
        spainDAO = new SpainDAO(sqlSessionFactory);
        suisseDAO = new SuisseDAO(sqlSessionFactory);
        swedenDAO = new SwedenDAO(sqlSessionFactory);
        thailandDAO = new ThailandDAO(sqlSessionFactory);
        ukDAO = new UkDAO(sqlSessionFactory);
    }


    private DAO[] daos = {arabDAO, austriaDAO,australiaDAO,belgiumDAO,bahrainDAO,canadaDAO,suisseDAO,yuanDAO,germanyDAO
                            ,denmarkDAO,spainDAO,euroDAO,finlandDAO,franceDAO,ukDAO,hongkongDAO,indonesiaDAO,italyDAO
                            ,japanDAO,koreaDAO,kuwaitDAO,malaysiaDAO,netherlandsDAO,norwayDAO,newzealandDAO,saudiDAO,swedenDAO
                            ,singaporeDAO,thailandDAO,americaDAO,cfaDAO};


    public DAO getDAO(int num){
        return daos[num];
    }






}