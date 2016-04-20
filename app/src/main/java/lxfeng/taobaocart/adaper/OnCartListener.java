package lxfeng.taobaocart.adaper;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2015/10/19
 */
public interface OnCartListener {
    public void groupCheck(int groupPosition);
    public void groupClick();
    public void groupEdit(int groupPosition);
    public void childCheck(int groupPosition,int childPosition);
    public void childDelete(int groupPosition,int childPosition);
    public void childIncrease(int groupPosition,int childPosition);
    public void childReduce(int groupPosition,int childPosition);
    public void childClick();
    public void childEdit();
}
