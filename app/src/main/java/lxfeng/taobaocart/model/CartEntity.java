package lxfeng.taobaocart.model;

import java.util.ArrayList;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2015/10/19
 */
public class CartEntity {
    private ArrayList<ProductEntity> productEntityList;
    private ShopEntity shopEntity;
    private boolean isGroupCheck;
    private boolean isGroupEdit;

    public ArrayList<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(ArrayList<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }

    public ShopEntity getShopEntity() {
        return shopEntity;
    }

    public void setShopEntity(ShopEntity shopEntity) {
        this.shopEntity = shopEntity;
    }

    public boolean isGroupCheck() {
        return isGroupCheck;
    }

    public void setGroupCheck(boolean isGroupCheck) {
        this.isGroupCheck = isGroupCheck;
    }

    public boolean isGroupEdit() {
        return isGroupEdit;
    }

    public void setGroupEdit(boolean isGroupEdit) {
        this.isGroupEdit = isGroupEdit;
    }
}
