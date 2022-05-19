package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.ReqSearchDTO;
import persistence.dto.ResSearchDTO;
import service.SearchService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SearchController {

    private SearchService searchService;

    public SearchController(){
        searchService = new SearchService(MyBatisConnectionFactory.getSqlSessionFactory());

    }

    //1~n 나라 코드 정하기
    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case Protocol.CODE_REQ_SEARCH: // 날짜로 환율검색
                searchFromDate(data);
                break;
            case 2: // 외화 -> 한국돈
                break;
            default:

        }

    }

    public void searchFromDate(byte[] data) throws IOException, ClassNotFoundException {
        ReqSearchDTO searchRequestDTO = (ReqSearchDTO) Protocol.convertBytesToObject(data);
        ResSearchDTO result = searchService.searchFromDate(searchRequestDTO);
        Protocol.responseToClient(Protocol.TYPE_RES_SEARCH,Protocol.CODE_RES_SEARCH,result);
    }

}
