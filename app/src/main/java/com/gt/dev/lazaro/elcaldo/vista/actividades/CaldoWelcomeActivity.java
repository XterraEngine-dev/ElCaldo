package com.gt.dev.lazaro.elcaldo.vista.actividades;

import com.gt.dev.lazaro.elcaldo.R;
import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

/**
 * Created by Lazarus on 11/9/2016.
 */
public class CaldoWelcomeActivity extends WelcomeActivity {
    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                .theme(R.style.CustomWelcomeScreenTheme)
                .titlePage(R.drawable.pn2, getString(R.string.email), R.color.orange_background)
                .basicPage(R.drawable.navdrawer, "Agrega tus recetas!", "Ahora podras agregar tus propias recetas y compartirla con tus amigos!", R.color.red_background)
                .parallaxPage(R.layout.parallax_example, "Interactua con los demas", "Comenta, puntea y comparte las recetas de otros usuarios", R.color.purple_background, 0.2f, 2f)
                .basicPage(R.drawable.socialnetwork, "Recetas de la semana", "Cada semana te mostraremos las recetas más populares que han subido los usuarios", R.color.blue_background)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    public static String welcomeKey() {
        return "welcomeScreen";
    }

}
