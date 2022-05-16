package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.DTO;
import persistence.dto.SearchRequestDTO;
import persistence.dto.SearchResponseDTO;
import readAPI.ReadData;
import service.SearchService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SearchController {
    private DataInputStream dis;
    private DataOutputStream dos;
    private SearchService searchService;

    public SearchController(SqlSessionFactory sqlSessionFactory,DataInputStream dis,DataOutputStream dos){
        searchService = new SearchService(sqlSessionFactory);
        this.dis = dis;
        this.dos = dos;
    }

    //1~n 나라 코드 정하기
    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case 1: // 날짜로 환율검색
                searchFromDate(data);
                break;
            case 2: // 외화 -> 한국돈
                break;
            default:

        }

    }

    public void searchFromDate(byte[] data) throws IOException, ClassNotFoundException {
        SearchRequestDTO searchRequestDTO = (SearchRequestDTO) Protocol.convertBytesToObject(data);
        SearchResponseDTO result = searchService.searchFromDate(searchRequestDTO);
        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }

}
