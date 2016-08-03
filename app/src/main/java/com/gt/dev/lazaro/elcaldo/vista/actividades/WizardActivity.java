package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.CirclePageIndicator;
import com.gt.dev.lazaro.elcaldo.utilidades.ColorShades;

/**
 * Created by Lazaro on 11/09/2015
 */
public class WizardActivity extends AppCompatActivity {

    protected static final long TIEMPO = 10000;
    private static final String SAVING_STATE_SLIDER_ANIMATION = "SliderAnimationSavingState";
    private boolean isSliderAnimation = false;
    private Button finish;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new ViewPagerAdapter(R.array.icons, R.array.titles, R.array.hints, R.array.buttons));

        //Mostrar solo una vez

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(WizardActivity.this, TabsMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }


        CirclePageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);


        viewPager.setPageTransformer(true, new CustomPageTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * Cuando la pagina se desplaza
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

                View landingBGView = findViewById(R.id.landing_backgrond);
                int colorBg[] = getResources().getIntArray(R.array.landing_bg);


                ColorShades shades = new ColorShades();
                shades.setFromColor(colorBg[position % colorBg.length])
                        .setToColor(colorBg[(position + 1) % colorBg.length])
                        .setShade(positionOffset);

                landingBGView.setBackgroundColor(shades.generate());

            }

            /**
             * Cuando la pagina es seleccionada
             * @param position
             */
            public void onPageSelected(int position) {
                Button button = (Button) findViewById(R.id.btnFinish);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(WizardActivity.this, TabsMainActivity.class));
                    }
                });

            }

            /**
             *
             * @param state
             */
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    /**
     * Clase interna que extiende de la clase abstracta
     */
    public class ViewPagerAdapter extends PagerAdapter {

        private int iconResId, titleArrayResId, hintArrayResId, buttonsId;

        public ViewPagerAdapter(int iconResId, int titleArrayResId, int hintArrayResId, int buttonsId) {

            this.iconResId = iconResId;
            this.titleArrayResId = titleArrayResId;
            this.hintArrayResId = hintArrayResId;
            this.buttonsId = buttonsId;
        }

        /**
         * @return la vista del item
         */
        @Override
        public int getCount() {
            return getResources().getIntArray(iconResId).length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Drawable icon = getResources().obtainTypedArray(iconResId).getDrawable(position);
            String title = getResources().getStringArray(titleArrayResId)[position];
            String hint = getResources().getStringArray(hintArrayResId)[position];


            View itemView = getLayoutInflater().inflate(R.layout.viewpager_item, container, false);


            ImageView iconView = (ImageView) itemView.findViewById(R.id.landing_img_slide);
            TextView titleView = (TextView) itemView.findViewById(R.id.landing_txt_title);
            TextView hintView = (TextView) itemView.findViewById(R.id.landing_txt_hint);

            iconView.setImageDrawable(icon);
            titleView.setText(title);
            hintView.setText(hint);

            container.addView(itemView);

            return itemView;
        }

        /**
         * Destruye el item
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);

        }
    }

    public class CustomPageTransformer implements ViewPager.PageTransformer {

        /**
         * @param view
         * @param position
         */
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            View imageView = view.findViewById(R.id.landing_img_slide);
            View contentView = view.findViewById(R.id.landing_txt_hint);
            View txt_title = view.findViewById(R.id.landing_txt_title);

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left
            } else if (position <= 0) { // [-1,0]
                // This page is moving out to the left

                // Counteract the default swipe
                setTranslationX(view, pageWidth * -position);
                if (contentView != null) {
                    // But swipe the contentView
                    setTranslationX(contentView, pageWidth * position);
                    setTranslationX(txt_title, pageWidth * position);

                    setAlpha(contentView, 1 + position);
                    setAlpha(txt_title, 1 + position);
                }

                if (imageView != null) {
                    // Fade the image in
                    setAlpha(imageView, 1 + position);
                }

            } else if (position <= 1) { // (0,1]
                // This page is moving in from the right

                // Counteract the default swipe
                setTranslationX(view, pageWidth * -position);
                if (contentView != null) {
                    // But swipe the contentView
                    setTranslationX(contentView, pageWidth * position);
                    setTranslationX(txt_title, pageWidth * position);

                    setAlpha(contentView, 1 - position);
                    setAlpha(txt_title, 1 - position);

                }
                if (imageView != null) {
                    // Fade the image out
                    setAlpha(imageView, 1 - position);
                }

            }
        }
    }

    /**
     * Establecemos el alpha para la vista. El alpha sera aplicado solo si el dispositivo android es mayor que honeycomb.
     *
     * @param view  - vista que se le aplicara alpha
     * @param alpha - valor alpha.
     */
    private void setAlpha(View view, float alpha) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && !isSliderAnimation) {
            view.setAlpha(alpha);
        }
    }

    /**
     * Establecer la traslacion en horizon en el eje X para la vista. El valor de la traslacion se aplicara solo si el dispositivo movil android
     * es mayor que honeycomb.
     *
     * @param view         - vista que se le aplicara el alpha
     * @param translationX - valor de traslación horizontal en el eje X
     */
    private void setTranslationX(View view, float translationX) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && !isSliderAnimation) {
            view.setTranslationX(translationX);
        }
    }

    /**
     * @param outstate
     */
    public void onSaveInstanceState(Bundle outstate) {

        if (outstate != null) {
            outstate.putBoolean(SAVING_STATE_SLIDER_ANIMATION, isSliderAnimation);
        }

        super.onSaveInstanceState(outstate);
    }

    /**
     * @param inState
     */
    public void onRestoreInstanceState(Bundle inState) {

        if (inState != null) {
            isSliderAnimation = inState.getBoolean(SAVING_STATE_SLIDER_ANIMATION, false);
        }
        super.onRestoreInstanceState(inState);

    }

    /**
     * en pausa
     * Ciclo de vida
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
