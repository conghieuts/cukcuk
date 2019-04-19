package vn.com.misa.hieudc.cukcuklite.screen.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.menuscreen.MenuFragment;
import vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen.AddFoodItemsActivity;
import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.listorderscreen.ListOrderFragment;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.ReportFragment;
import vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen.SelectFoodItemActivity;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Màn hình chính của ứng dụng
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTION_REFRESH = "vn.com.misa.hieudc.cukcuklite.REFRESH_ACTION";
    private static final String SCREEN_MENU = "menu";
    private static final String SCREEN_ORDER = "order";
    private static final String SCREEN_REPORT = "report";
    private FragmentManager mFragmentManager;
    private String mOnScreen;
    private TextView tvToolbarTitle;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);

            tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
            mFragmentManager = getSupportFragmentManager();
            initOrderListFragment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * hiển thị fragment danh sách món ăn
     */
    private void initMenuFragment() {
        try {
            mOnScreen = SCREEN_MENU;
            tvToolbarTitle.setText(R.string.text_menu_list);
            if (mMenu != null) {
                mMenu.clear();
                getMenuInflater().inflate(R.menu.menu_create, mMenu);
            }
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new MenuFragment(), null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị fragment đơn hàng
     */
    private void initOrderListFragment() {
        try {
            mOnScreen = SCREEN_ORDER;
            tvToolbarTitle.setText(R.string.text_sell);
            if (mMenu != null) {
                mMenu.clear();
                getMenuInflater().inflate(R.menu.menu_create, mMenu);
            }
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new ListOrderFragment(), null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị fragment báo cáo
     */
    private void initReportFragment() {
        try {
            mOnScreen = SCREEN_REPORT;
            tvToolbarTitle.setText(R.string.text_report);
            if (mMenu != null) {
                mMenu.clear();
                getMenuInflater().inflate(R.menu.menu_empty, mMenu);
            }
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new ReportFragment(), null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            mMenu = menu;
            getMenuInflater().inflate(R.menu.menu_create, mMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                switch (mOnScreen) {
                    case SCREEN_MENU: {
                        try {
                            Intent intent = new Intent(this, AddFoodItemsActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                    case SCREEN_ORDER: {
                        try {
                            Intent intent = new Intent(this, SelectFoodItemActivity.class);
                            intent.putExtra("isStart", false);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                    case SCREEN_REPORT:
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sale:
                try {
                    initOrderListFragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu:
                try {
                    initMenuFragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.report:
                try {
                    initReportFragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
