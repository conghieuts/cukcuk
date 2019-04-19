package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.menuscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.MainActivity;
import vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen.AddFoodItemsActivity;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.menuscreen.adapter.MenuItemAdapter;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Fragment hiển thị toàn bộ danh sách món ăn
 */
public class MenuFragment extends Fragment implements IMenuView {
    RecyclerView mRecyclerView;
    ArrayList<FoodItem> mFoodItemArrayList;
    IMenuPresenter mIMenuPresenter;
    private BroadcastReceiver mRefreshReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mRecyclerView = view.findViewById(R.id.rv_menu);
            mIMenuPresenter = new MenuPresenter(this);

            initFoodItemList();
            registerBroadcast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        try {
            LocalBroadcastManager.getInstance(AppContext.getInstance()).unregisterReceiver(mRefreshReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Đăng ký broadcast lắng nghe thay đổi dữ liệu
     */
    private void registerBroadcast() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(MainActivity.ACTION_REFRESH);
            mRefreshReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent != null) {
                        initFoodItemList();
                    }
                }
            };

            LocalBroadcastManager.getInstance(AppContext.getInstance()).registerReceiver(mRefreshReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * <p>
     * Thực hiện hiển thị danh sách món ăn
     */
    private void initFoodItemList() {
        try {
            mIMenuPresenter.getAllFoodItem();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.getInstance(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);

            MenuItemAdapter menuItemAdapter = new MenuItemAdapter(mFoodItemArrayList);
            mRecyclerView.setAdapter(menuItemAdapter);
            menuItemAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    try {
                        Intent intent = new Intent(getContext(), AddFoodItemsActivity.class);
                        intent.putExtra("foodItem", mFoodItemArrayList.get(position));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     *
     * @param list danh sách món ăn
     */
    @Override
    public void getAllFoodItemDone(ArrayList<FoodItem> list) {
        try {
            mFoodItemArrayList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
