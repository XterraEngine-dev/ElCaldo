package com.gt.dev.lazaro.elcaldo.utilidades;

import android.graphics.Color;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class ColorShades {
    private int mFromColor;
    private int mToColor;
    private float mShade;

    /**
     * Establecemos de donde viene el color
     *
     * @param fromColor
     * @return
     */
    public ColorShades setFromColor(int fromColor) {
        this.mFromColor = fromColor;
        return this;
    }

    /**
     * Establecemos para el color
     *
     * @param toColor
     * @return
     */
    public ColorShades setToColor(int toColor) {
        this.mToColor = toColor;
        return this;
    }

    /**
     * Sombras de los colores
     *
     * @param fromColor
     * @return
     */
    public ColorShades setFromColor(String fromColor) {

        this.mFromColor = Color.parseColor(fromColor);
        return this;
    }

    /**
     * Establecer a ColorShades el color
     *
     * @param toColor
     * @return
     */
    public ColorShades setToColor(String toColor) {
        this.mToColor = Color.parseColor(toColor);
        return this;
    }

    /**
     * establecer a ColorShades sombras claras
     *
     * @param color
     * @return
     */
    public ColorShades forLightShade(int color) {
        setFromColor(Color.WHITE);
        setToColor(color);
        return this;
    }

    /**
     * establecer a ColorShades sombras oscuras
     *
     * @param color
     * @return
     */
    public ColorShades forDarkShare(int color) {
        setFromColor(color);
        setToColor(Color.BLACK);
        return this;
    }

    /**
     * establecer a ColorShades la sombra
     *
     * @param mShade
     * @return
     */
    public ColorShades setShade(float mShade) {
        this.mShade = mShade;
        return this;
    }


    /**
     * Generamos la sombra for el color dado
     *
     * @return retornamos el valor int the la sombra
     */
    public int generate() {

        int fromR = (Color.red(mFromColor));
        int fromG = (Color.green(mFromColor));
        int fromB = (Color.blue(mFromColor));

        int toR = (Color.red(mToColor));
        int toG = (Color.green(mToColor));
        int toB = (Color.blue(mToColor));

        int diffR = toR - fromR;
        int diffG = toG - fromG;
        int diffB = toB - fromB;


        int R = fromR + (int) ((diffR * mShade));
        int G = fromG + (int) ((diffG * mShade));
        int B = fromB + (int) ((diffB * mShade));

        return Color.rgb(R, G, B);

    }


    /**
     * Asume desde y hacia el color donde se invierten antes de generar la sombra.
     *
     * @return el valor int de la sombra invertida.
     */
    public int generateInverted() {

        int fromR = (Color.red(mFromColor));
        int fromG = (Color.green(mFromColor));
        int fromB = (Color.blue(mFromColor));

        int toR = (Color.red(mToColor));
        int toG = (Color.green(mToColor));
        int toB = (Color.blue(mToColor));


        int diffR = toR - fromR;
        int diffG = toG - fromG;
        int diffB = toB - fromB;

        int R = toR - (int) ((diffR * mShade));
        int G = toG - (int) ((diffG * mShade));
        int B = toB - (int) ((diffB * mShade));

        return Color.rgb(R, G, B);

    }

    /**
     * Consigue el String equivalente de la sombra generada
     *
     * @return Valor String de la sombra
     */
    public String generateInvertedString() {
        return String.format("#%06X", 0xFFFFFF & generateInverted());
    }

    /**
     * Consigue el string equivalente de la sombra generada
     *
     * @return El valor String de la sombra
     */
    public String generateString() {
        return String.format("#%06X", 0xFFFFFF & generate());
    }

}
