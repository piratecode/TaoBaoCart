package lxfeng.taobaocart.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lxfeng.taobaocart.R;
import lxfeng.taobaocart.adaper.CartAdapter;
import lxfeng.taobaocart.adaper.OnCartListener;
import lxfeng.taobaocart.model.CartEntity;
import lxfeng.taobaocart.model.ProductEntity;

public class MainActivity extends Activity implements OnCartListener, View.OnClickListener {

    private static final boolean DEBUG = true;
    private static final String TAG = MainActivity.class.getSimpleName();

    private ExpandableListView mCartExpandableListView;
    private TextView mAllCostView;
    private TextView mSettlementButton;
    private CheckBox mAllCheckView;
    private LinearLayout mLayoutBottom;

    private CartAdapter mCartAdapter;

    private ArrayList<CartEntity> mCartEntityList = new ArrayList<CartEntity>();
    private boolean isAllCheck;

    private void initView() {
        mAllCostView = (TextView) findViewById(R.id.all_cost);
        mSettlementButton = (TextView) findViewById(R.id.settlement);
        mCartExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        mAllCheckView = (CheckBox) findViewById(R.id.all_check);
        mLayoutBottom = (LinearLayout) findViewById(R.id.llayout_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupCart();

        mAllCheckView.setOnClickListener(this);
        mSettlementButton.setOnClickListener(this);
    }

    private void setupCart() {
        mCartAdapter = new CartAdapter(this, mCartEntityList);
        mCartExpandableListView.setAdapter(mCartAdapter);
        mCartAdapter.setOnCartListener(this);

        mCartEntityList.addAll(getData());
        mCartAdapter.notifyDataSetChanged();
        for (int i = 0; i < mCartEntityList.size(); i++) {
            mCartExpandableListView.expandGroup(i);
        }
    }

    private ArrayList<CartEntity> getData() {
        ArrayList<CartEntity> cartEntityList = new ArrayList<CartEntity>();
        for (int i = 0; i < 3; i++) {
            CartEntity cartEntity = new CartEntity();
            ArrayList<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
            for (int j = 0; j < Math.random() * 2 + 1; j++) {
                ProductEntity productEntity = new ProductEntity();
                productEntity.setProductPrice((float) (Math.random() * 100));
                productEntity.setProductCount(1);
                productEntityList.add(productEntity);
            }
            cartEntity.setProductEntityList(productEntityList);
            cartEntityList.add(cartEntity);
        }
        return cartEntityList;
    }

    private void setCheckGroup(int groupPosition, boolean check) {
        int len = mCartEntityList.get(groupPosition).getProductEntityList().size();
        mCartEntityList.get(groupPosition).setGroupCheck(check);
        for (int i = 0; i < len; i++) {
            mCartEntityList.get(groupPosition).getProductEntityList().get(i).setChildCheck(check);
        }
    }

    private void setCheckChild(int groupPosition, int childPosition, boolean check) {
        mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).setChildCheck(check);

        int len = mCartEntityList.get(groupPosition).getProductEntityList().size();
        boolean groupCheck = true;
        for (int i = 0; i < len; i++) {
            if (!mCartEntityList.get(groupPosition).getProductEntityList().get(i).isChildCheck()) {
                groupCheck = false;
            }
        }
        mCartEntityList.get(groupPosition).setGroupCheck(groupCheck);
    }

    private void handleAllCost() {
        float allCost = 0f;
        int allCount = 0;
        int groupLen = mCartEntityList.size();
        for (int i = 0; i < groupLen; i++) {
            int childLen = mCartEntityList.get(i).getProductEntityList().size();
            for (int j = 0; j < childLen; j++) {
                if (mCartEntityList.get(i).getProductEntityList().get(j).isChildCheck()) {
                    allCost += mCartEntityList.get(i).getProductEntityList().get(j).getProductPrice() * mCartEntityList.get(i).getProductEntityList().get(j).getProductCount();
                    allCount++;
                }
            }
        }
        mAllCostView.setText("￥" + String.format("%.2f", allCost));
        mSettlementButton.setText("结算(" + allCount + ")");
        if (mCartEntityList.isEmpty()){
            mLayoutBottom.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteProduct(int groupPosition, int childPosition) {
        mCartEntityList.get(groupPosition).getProductEntityList().remove(childPosition);
        if (mCartEntityList.get(groupPosition).getProductEntityList().isEmpty()) {
            mCartEntityList.remove(groupPosition);
        }
    }

    @Override
    public void groupCheck(int groupPosition) {
        log("---groupCheck()---" + "groupPosition:" + groupPosition);
        setCheckGroup(groupPosition, !mCartEntityList.get(groupPosition).isGroupCheck());
        handleAllCost();
        mCartAdapter.notifyDataSetChanged();
        updateAllState();
    }

    @Override
    public void groupClick() {
        log("---groupClick()---");
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void groupEdit(int groupPosition) {
        log("---groupEdit()---");
        mCartEntityList.get(groupPosition).setGroupEdit(
                !mCartEntityList.get(groupPosition).isGroupEdit());
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void childCheck(int groupPosition, int childPosition) {
        log("---childCheck()---" + "groupPosition:" + groupPosition + "，childPosition:" + childPosition);
        setCheckChild(groupPosition, childPosition,
                !mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition)
                        .isChildCheck());
        handleAllCost();
        mCartAdapter.notifyDataSetChanged();
        updateAllState();
    }

    @Override
    public void childDelete(int groupPosition, int childPosition) {
        log("---childDelete()---" + "groupPosition:" + groupPosition + "，childPosition:" + childPosition);
        deleteProduct(groupPosition, childPosition);
        handleAllCost();
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void childIncrease(int groupPosition, int childPosition) {
        log("---childIncrease()---");
        mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).setProductCount
                (mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition)
                        .getProductCount() + 1);
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void childReduce(int groupPosition, int childPosition) {
        log("---childReduce()---");
        if(mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).getProductCount() <= 1) return;
        mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).setProductCount
                (mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).getProductCount() - 1);
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void childClick() {
        log("---childClick()---");
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void childEdit() {
        log("---childEdit()---");
        mCartAdapter.notifyDataSetChanged();
    }

    private void log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_check:
                isAllCheck = !isAllCheck;
                setAllCheck(isAllCheck);
                break;
            case R.id.settlement:
                if (!verifyCart()) {
                    Toast.makeText(MainActivity.this, R.string.select_tip, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setAllCheck(boolean check) {
        int groupLen = mCartEntityList.size();
        for (int i = 0; i < groupLen; i++) {
            setCheckGroup(i, check);
        }
        handleAllCost();
        mCartAdapter.notifyDataSetChanged();
    }

    private boolean verifyCart() {
        int groupLen = mCartEntityList.size();
        for (int i = 0; i < groupLen; i++) {
            int childLen = mCartEntityList.get(i).getProductEntityList().size();
            for (int j = 0; j < childLen; j++) {
                if (mCartEntityList.get(i).getProductEntityList().get(j).isChildCheck()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateAllState(){
        int groupLen = mCartEntityList.size();
        isAllCheck = true;
        for (int i = 0; i < groupLen; i++) {
            if (!mCartEntityList.get(i).isGroupCheck()) {
                isAllCheck = false;
            }
        }
        mAllCheckView.setChecked(isAllCheck);
    }



}
