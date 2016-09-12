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
                .titlePage(R.drawable.caldoicon_white, getString(R.string.email), R.color.orange_background)
                .basicPage(R.drawable.tamales_cardview_main, getString(R.string.title_addrecipes_wizard), getString(R.string.text_addrecipes_wizard), R.color.red_background)
                .parallaxPage(R.layout.parallax_example, getString(R.string.title_interaction_recipes_wizard), getString(R.string.text_interaction_recipes_wizard), R.color.purple_background, 0.2f, 2f)
                .basicPage(R.drawable.plato, getString(R.string.recetas_semana_drawer), getString(R.string.text_recipes_week), R.color.blue_background)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    public static String welcomeKey() {
        return "welcomeScreen";
    }

}
