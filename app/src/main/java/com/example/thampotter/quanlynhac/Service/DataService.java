package com.example.thampotter.quanlynhac.Service;

import com.example.thampotter.quanlynhac.Model.Album;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.Model.ChuDe;
import com.example.thampotter.quanlynhac.Model.Playlist;
import com.example.thampotter.quanlynhac.Model.QuangCao;
import com.example.thampotter.quanlynhac.Model.TheLoai;
import com.example.thampotter.quanlynhac.Model.Theloaitrongngay;


import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataService {

    @GET("Server/songbanner.php")
    Call<List<QuangCao>> GetDataBanner();

    @GET("Server/playlistforcurrent.php")
    Call<List<Playlist>> GetplaylistCurrentDay();

    @GET("Server/chudeforday.php")
    Call<Theloaitrongngay> GetCategoryMusic();

    @GET("Server/albumforday.php")
    Call<List<Album>> GetAlbumHot();

    @GET("Server/baihatyeuthich.php")
    Call<List<Baihat>> GetBaiHatHot();

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoalbum(@Field("idalbum") String idalbum);

    @GET("Server/danhsachcacplaylist.php")
    Call<List<Playlist>> GetDanhsachcacplaylist();

    @FormUrlEncoded
    @POST("Server/danhsachbaihatcanhan.php")
    Call<List<Baihat>> Getdanhsachbaihatcanhan(@Field("username") String username);

    @Multipart
    @POST("Server/uploadnhac.php")
    Call<String> UpLoadNhac(@Part MultipartBody.Part filenhac);

    @FormUrlEncoded
    @POST("Server/insert.php")
    Call<String> InsertData(@Field("username") String username
                            ,@Field("chiase") int share
                            ,@Field("tenBaiHat") String songname
                            ,@Field("linkBaiHat") String songlink);
    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhSachBaiHatTheoTheLoai(@Field("idtheloai") String idtheloai);

    @GET("Server/tatcachude.php")
    Call<List<ChuDe>> GetTatCaChuDe();

    @FormUrlEncoded
    @POST("Server/theloaitheochude.php")
    Call<List<TheLoai>> GetTheLoaiTheoChuDe(@Field("idchude") String idchude);

    @GET("Server/allalbum.php")
    Call<List<Album>> GetAllDataAlbum();

    @FormUrlEncoded
    @POST("Server/themluotthich.php")
    Call<String> CapNhatLuotThich(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("Server/danhsachnhacchiase.php")
    Call<List<Baihat>> GetDanhSachBaiHatChiaSe(@Field("username") String username);

    @FormUrlEncoded
    @POST("Server/deletemusic.php")
    Call<String> XoaBaiHat(@Field("idbaihat") String idbaihat, @Field("baihat") String baihat);

    @FormUrlEncoded
    @POST("Server/editsong.php")
    Call<String> EditSong(@Field("idbaihat") String idbaihat, @Field("tenBaiHat") String tenbaihat);

    @FormUrlEncoded
    @POST("Server/selectallsong.php")
    Call<List<Baihat>> GetAllSong(@Field("tenBaiHat") String tenbaihat);

}
