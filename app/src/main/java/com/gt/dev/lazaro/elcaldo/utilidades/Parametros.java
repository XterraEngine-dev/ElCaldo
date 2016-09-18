package com.gt.dev.lazaro.elcaldo.utilidades;

/**
 * Created by Lazarus on 29/07/2016.
 */
public class Parametros {


    private String coquiCrazy;
    public static final String URL_SHOW_TIMELINE = "http://dev.elcaldogt.com/timeline";
    public static final String URL_SHOW_TAMALES = "http://dev.elcaldogt.com/tamales";
    public static final String URL_SHOW_CALDOS = "http://dev.elcaldogt.com/caldos";
    public static final String URL_SHOW_POSTRES = "http://dev.elcaldogt.com/postres";
    public static final String URL_SHOW_BEBIDAS_FRIAS = "http://dev.elcaldogt.com/frias";
    public static final String URL_SHOW_BEBIDAS_CALIENTES = "http://dev.elcaldogt.com/calientes";
    public static final String URL_SHOW_OTRAS = "http://dev.elcaldogt.com/otras";
    public static final String URL_SHOW_TMELINE = "http://dev.elcaldogt.com/timeline";
    public static final String URL_SHOW_TOP = "http://dev.elcaldogt.com/top";
    public static final String URL_SHOW_BUSCAR = "http://dev.elcaldogt.com/buscar";
    public static final String URL_SHOW_COMENT = "http://dev.elcaldogt.com/comentario";
    //Constante de TRACKER en Google Analytics
    public static final String TRACKER_ANALYTICS = "UA-69747362-1";
    //Constante de la api publicitaria de Facebook
    //Este es un PLACEMENT-ID-PANTALLA-COMPLETA
    public static final String FB_PLACEMENT_ID = "472492076284805_568720113328667";
    //PLACEMENT ID BANNER
    public static final String FB_PLACEMENT_BANNER = "472492076284805_569083949958950";
    //Link de ElCaldo en Facebook
    public static final String URL_FB_ELCALDO = "http://www.facebook.com/elcaldoapp";
    //URL DE ELCALDO EN TWITTER
    public static final String URL_TW_ELCALDO = "http://www.twitter.com/caldogt";
    //URL DE LAZARO EN TWITTER
    public static final String URL_TW_LAZARO = "http://www.twiter.com/luiscomodo";
    //CONSTANTE PARA USUARIO
    public static final String USER = "dev@elcaldogt.com";
    public static final String PASS = (new Object() {

        int t;

        public String toString() {
            byte[] buf = new byte[8];
            t = -2006875776;
            buf[0] = (byte) (t >>> 16);
            t = 1331627761;
            buf[1] = (byte) (t >>> 21);
            t = 1264859843;
            buf[2] = (byte) (t >>> 1);
            t = 159051630;
            buf[3] = (byte) (t >>> 16);
            t = 992602924;
            buf[4] = (byte) (t >>> 19);
            t = -1918934552;
            buf[5] = (byte) (t >>> 21);
            t = 784084351;
            buf[6] = (byte) (t >>> 15);
            t = 1366705370;
            buf[7] = (byte) (t >>> 12);
            return new String(buf);
        }
    }.toString());


}
