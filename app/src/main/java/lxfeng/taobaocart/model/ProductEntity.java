package lxfeng.taobaocart.model;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2015/10/19
 */
public class ProductEntity {
    private String productId;
    private String productName;
    private String productColor;
    private String productStyle;
    private String productSize;
    private float productPrice;
    private int productCount;
    private String productStock;
    private boolean isChildCheck;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public boolean isChildCheck() {
        return isChildCheck;
    }

    public void setChildCheck(boolean isChildCheck) {
        this.isChildCheck = isChildCheck;
    }
}
