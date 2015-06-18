package com.donbaka.reklameuser.app;

/**
 * Created by brlnt-super on 5/6/2015.
 */
public class AppConfig {
    // Server url
    public static String URL = "http://reklame.cktr.web.id/reklame_api/index.php/";

    // Server user login url
    public static String URL_LOGIN = URL+"user";

    // list reklame
	public static String URL_LIST = URL+"reklame_inti";

    // detail reklame
    public static String URL_DETAIL_REKLAME = URL_LIST +"/detail_rek";

    // image src
    public static String URL_IMG = URL + "gambar?url=";
}
