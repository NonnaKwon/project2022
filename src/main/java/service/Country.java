package service;

public class Country {
    static public int getCode(String unit){
        if(unit.equals("AED")){
            return 0;
        }else if(unit.equals("AUD")){
            return 1;
        }else if(unit.equals("BHD")) {
            return 2;
        }else if(unit.equals("CAD")){
            return 3;
        }else if(unit.equals("CHF")){
            return 4;
        }else if(unit.equals("CNH")){
            return 5;
        }else if(unit.equals("DKK")){
            return 6;
        }else if(unit.equals("EUR")){
            return 7;
        }else if(unit.equals("GBP")){
            return 8;
        }else if(unit.equals("HKD")){
            return 9;
        }else if(unit.equals("IDR")){
            return 10;
        }else if(unit.equals("JPY")){
            return 11;
        }else if(unit.equals("KRW")){
            return 12;
        }else if(unit.equals("KWD")){
            return 13;
        }else if(unit.equals("MYR")){
            return 14;
        }else if(unit.equals("NOK")){
            return 15;
        }else if(unit.equals("NZD")){
            return 16;
        }else if(unit.equals("SAR")){
            return 17;
        }else if(unit.equals("SEK")){
            return 18;
        }else if(unit.equals("SGD")){
            return 19;
        }else if(unit.equals("THB")){
            return 20;
        }else if(unit.equals("USD")){
            return 21;
        }else{
            System.out.println("unit 잘못입력(없는나라)");
            return -1;
        }
    }
}
