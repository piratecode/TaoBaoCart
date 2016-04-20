package lxfeng.taobaocart.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lxfeng.taobaocart.R;
import lxfeng.taobaocart.model.CartEntity;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2015/10/19
 */
public class CartAdapter extends BaseExpandableListAdapter implements View.OnClickListener{

    private Context context;
    private ArrayList<CartEntity> mCartEntityList;

    public CartAdapter(Context context,ArrayList<CartEntity> cartEntitiyList){
        this.context = context;
        mCartEntityList = cartEntitiyList;
    }

    private OnCartListener mCartListener;
    public void setOnCartListener(OnCartListener cartListener){
        mCartListener = cartListener;
    }

    @Override
    public int getGroupCount() {
        return mCartEntityList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCartEntityList.get(groupPosition).getProductEntityList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCartEntityList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if(null == convertView){
            groupViewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart_group,parent,false);
            initGroupView(groupViewHolder,convertView);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        setupGroupView(groupPosition,groupViewHolder);
        return convertView;
    }

    private void initGroupView(GroupViewHolder groupViewHolder,View convertView){
        groupViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.group_checkBox);
        groupViewHolder.shopName = (TextView) convertView.findViewById(R.id.shop_name);
        groupViewHolder.shopIcon = (ImageView) convertView.findViewById(R.id.shop_icon);
        groupViewHolder.shoplayout = convertView.findViewById(R.id.shop_layout);
        groupViewHolder.groupEdit = (TextView) convertView.findViewById(R.id.group_edit);
        groupViewHolder.groupDividerView = convertView.findViewById(R.id.divider_group);
    }

    private void setupGroupView(int groupPosition,GroupViewHolder groupViewHolder){
        if(0 == groupPosition){
            groupViewHolder.groupDividerView.setVisibility(View.GONE);
        }else {
            groupViewHolder.groupDividerView.setVisibility(View.VISIBLE);
        }
        groupViewHolder.checkBox.setChecked(mCartEntityList.get(groupPosition).isGroupCheck());
        if(mCartEntityList.get(groupPosition).isGroupEdit()){
            groupViewHolder.groupEdit.setText(R.string.complete);
        }else {
            groupViewHolder.groupEdit.setText(R.string.edit);
        }
        groupViewHolder.checkBox.setTag(R.integer.view_group_tag, groupPosition);
        groupViewHolder.groupEdit.setTag(R.integer.view_group_tag, groupPosition);
        groupViewHolder.checkBox.setOnClickListener(this);
        groupViewHolder.shoplayout.setOnClickListener(this);
        groupViewHolder.groupEdit.setOnClickListener(this);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if(null == convertView){
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart_child,parent,false);
            initChildView(childViewHolder,convertView);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        setupChildView(groupPosition,childPosition,childViewHolder);
        return convertView;
    }

    private void initChildView(ChildViewHolder childViewHolder,View convertView){
        childViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.child_checkBox);
        childViewHolder.productName = (TextView) convertView.findViewById(R.id.product_name);
        childViewHolder.productIcon = (ImageView) convertView.findViewById(R.id.product_icon);
        childViewHolder.productCount = (TextView) convertView.findViewById(R.id.product_count);
        childViewHolder.productPrice = (TextView) convertView.findViewById(R.id.product_price);
        childViewHolder.productInfo = (TextView) convertView.findViewById(R.id.product_info);
        childViewHolder.childDeleteButton = (TextView) convertView.findViewById(R.id.child_delete);
        childViewHolder.viewEdit = convertView.findViewById(R.id.layout_edit);
        childViewHolder.viewComplete = convertView.findViewById(R.id.layout_complete);
        childViewHolder.increaseBtn = (ImageView) convertView.findViewById(R.id.btn_increase);
        childViewHolder.reduceBtn = (ImageView) convertView.findViewById(R.id.btn_reduce);
        childViewHolder.editProdcutCount = (TextView) convertView.findViewById(R.id.edit_product_count);
    }

    private void setupChildView(int groupPosition,int childPosition,ChildViewHolder childViewHolder){
        if(mCartEntityList.get(groupPosition).isGroupEdit()){
            childViewHolder.viewEdit.setVisibility(View.VISIBLE);
            childViewHolder.viewComplete.setVisibility(View.INVISIBLE);
        }else {
            childViewHolder.viewEdit.setVisibility(View.INVISIBLE);
            childViewHolder.viewComplete.setVisibility(View.VISIBLE);
        }
        childViewHolder.checkBox.setChecked(mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).isChildCheck());
        childViewHolder.productPrice.setText("￥" + String.format("%.2f", mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).getProductPrice()));
        childViewHolder.editProdcutCount.setText(mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).getProductCount()+"");
        childViewHolder.productCount.setText("x"+mCartEntityList.get(groupPosition).getProductEntityList().get(childPosition).getProductCount());

        childViewHolder.checkBox.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.checkBox.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.checkBox.setOnClickListener(this);

        childViewHolder.childDeleteButton.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.childDeleteButton.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.childDeleteButton.setOnClickListener(this);

        childViewHolder.increaseBtn.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.increaseBtn.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.increaseBtn.setOnClickListener(this);

        childViewHolder.reduceBtn.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.reduceBtn.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.reduceBtn.setOnClickListener(this);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if(null == mCartListener) return;
        switch (v.getId()){
            case R.id.group_checkBox:
                mCartListener.groupCheck((int) v.getTag(R.integer.view_group_tag));
                break;
            case R.id.child_checkBox:
                mCartListener.childCheck((int) v.getTag(R.integer.view_group_tag), (int) v.getTag(R.integer.view_child_tag));
                break;
            case R.id.shop_layout:
                mCartListener.groupClick();
                break;
            case R.id.group_edit:
                mCartListener.groupEdit((int) v.getTag(R.integer.view_group_tag));
                break;
            case R.id.child_delete:
                mCartListener.childDelete((int)v.getTag(R.integer.view_group_tag),(int)v.getTag(R.integer.view_child_tag));
                break;
            case R.id.btn_increase:
                mCartListener.childIncrease((int)v.getTag(R.integer.view_group_tag),(int)v.getTag(R.integer.view_child_tag));
                break;
            case R.id.btn_reduce:
                mCartListener.childReduce((int) v.getTag(R.integer.view_group_tag), (int) v.getTag(R.integer.view_child_tag));
                break;
        }
}

    private static class GroupViewHolder{
        CheckBox checkBox;
        ImageView shopIcon;
        TextView shopName;
        TextView groupEdit;
        View shoplayout;
        View groupDividerView;
    }

    private static class ChildViewHolder{
        CheckBox checkBox;
        ImageView productIcon;
        TextView productName;
        TextView productInfo;
        TextView productPrice;
        TextView productCount;
        TextView childDeleteButton;
        View viewEdit;
        View viewComplete;
        ImageView increaseBtn;
        ImageView reduceBtn;
        TextView editProdcutCount;
    }
}
