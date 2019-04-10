package co.ambystic.restorder.service;

import co.ambystic.restorder.model.serverResponse.login.LoginResponse;
import co.ambystic.restorder.model.serverResponse.menu.MenuResponse;
import co.ambystic.restorder.model.serverResponse.order.OrderResponse;
import co.ambystic.restorder.model.serverResponse.orderedMenu.OrderedResponse;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.model.serverResponse.report.ReportResponse;
import co.ambystic.restorder.model.serverResponse.table.TableResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataService {
    @FormUrlEncoded
    @POST("auth/register/register.php")
    Call<RegisterReponse> register(
            @Field("fullName") String fullName,
            @Field("userName") String userName,
            @Field("password") String password,
            @Field("level") int level
    );

    @FormUrlEncoded
    @POST("auth/register/register.php")
    Call<RegisterReponse> registerCostumer(
            @Field("fullName") String fullName,
            @Field("userName") String userName,
            @Field("password") String password,
            @Field("level") int level,
            @Field("tableNo") int tableNo
    );

    @FormUrlEncoded
    @POST("auth/login/login.php")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("admin/menu/insertNewMenu.php")
    Call<RegisterReponse> insertMenu(
            @Field("namaMasakan") String menuName,
            @Field("hargaMasakan") String menuPrice,
            @Field("jenisMasakan") String menuKind,
            @Field("deskripsiMasakan") String menuDesc,
            @Field("jumlahMasakan") int menuStock,
            @Field("statusMasakan") String menuStatus
    );

    @FormUrlEncoded
    @POST("admin/order/makeOrder.php")
    Call<RegisterReponse> makeOrder(
            @Field("noMeja") int tableNo,
            @Field("idUser") int userId,
            @Field("keteranganOrder") String orderDesc,
            @Field("idMasakan") int menuId,
            @Field("status") int menuStatus,
            @Field("jumlah") int orderQuantity,
            @Field("keteranganDetail") String detailDesc
    );

    @FormUrlEncoded
    @POST("cashier/transaction/makeTransaction.php")
    Call<RegisterReponse> makeTransaction(
            @Field("idUser") int idUser,
            @Field("idOrder") int idOrder,
            @Field("payAmount") int totalPrice
    );

    @FormUrlEncoded
    @POST("admin/menu/editStockMenu.php")
    Call<RegisterReponse> editStockMenu(
            @Field("idMasakan") int menuId,
            @Field("jumlah") int menuStock
    );

    @FormUrlEncoded
    @POST("admin/menu/deleteMenu.php")
    Call<RegisterReponse> deleteMenu(
            @Field("idMasakan") int menuId
    );

    @FormUrlEncoded
    @POST("admin/order/changeOrderStatus.php")
    Call<RegisterReponse> changeMenuStat(
            @Field("orderId") int orderId,
            @Field("idMasakan") int menuId,
            @Field("status") int menuStatus
    );

    @FormUrlEncoded
    @POST("admin/table/insertTable.php")
    Call<RegisterReponse> insertTable(
            @Field("noMeja") int tableNo
    );

    @GET("admin/menu/getFood.php")
    Call<MenuResponse> getFoodForAdmin();

    @GET("admin/menu/getBeverage.php")
    Call<MenuResponse> getBeverageForAdmin();

    @GET("admin/table/getTableList.php")
    Call<TableResponse> getTableForAdmin();

    @GET("admin/report/getReport.php")
    Call<ReportResponse> getReport(@Query("jenis_masakan") String menuKind);

    @GET("admin/report/getReportFiltered.php")
    Call<ReportResponse> getReportFiltered(@Query("jenis_masakan") String menuKind,
                                           @Query("bulan") String month,
                                           @Query("tahun") String year);

    @GET("costumer/order/getOrderedMenu.php")
    Call<OrderedResponse> getCostumerOrderedMenu(@Query("noMeja") int tableNo);

    @GET("admin/order/getMenuOrder.php")
    Call<OrderResponse> getMenuOrderPerTable(@Query("noMeja")int tableNo);
}
