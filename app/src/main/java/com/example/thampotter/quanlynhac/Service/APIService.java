package com.example.thampotter.quanlynhac.Service;

public class APIService {

    /*private static String baseURL = "https://devandroi.000webhostapp.com/Server/";*/

    public static String baseURL = "https://androidzing.000webhostapp.com/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(baseURL).create(DataService.class);
    }
}
