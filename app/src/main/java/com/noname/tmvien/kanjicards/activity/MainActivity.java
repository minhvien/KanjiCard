package com.noname.tmvien.kanjicards.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.fragment.KanjiFragment;
import com.noname.tmvien.kanjicards.fragment.LevelsFragment;
import com.noname.tmvien.kanjicards.fragment.MenuFragment;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    private static final String TAG = MainActivity.class.getSimpleName();



    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LevelsFragment levelsFragment = LevelsFragment.newInstance(R.color.colorPrimary);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, levelsFragment).commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, levelsFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(MenuFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(MenuFragment.KANJI, R.drawable.menu_kanji_icon);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(MenuFragment.QUIZ, R.drawable.menu_quiz_icon);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(MenuFragment.RATE, R.drawable.menu_rating_icon);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(MenuFragment.SETTING, R.drawable.menu_setting_icon);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(MenuFragment.ABOUT, R.drawable.menu_about_icon);
        list.add(menuItem5);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int position) {
        View contentFrame = findViewById(R.id.content_frame);
        int finalRadius = Math.max(contentFrame.getWidth(), contentFrame.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(contentFrame, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        return screenShotable;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        int primaryColorCanvas = R.color.colorPrimary;

        switch (slideMenuItem.getName()) {
            case MenuFragment.CLOSE:
                return screenShotable;
            case MenuFragment.KANJI:
                LevelsFragment levelsFragment = LevelsFragment.newInstance(primaryColorCanvas);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, levelsFragment).commit();
                return replaceFragment(levelsFragment, position);
            case MenuFragment.QUIZ:
                KanjiFragment kanjiFragment = KanjiFragment.newInstance(primaryColorCanvas);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, kanjiFragment).commit();
                return replaceFragment(kanjiFragment, position);
            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}

