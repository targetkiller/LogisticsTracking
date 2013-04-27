package page.abs;

import page.impl.Page;

/**
 * 数据预处理
 * @author volador
 *
 */
public abstract class AbstractPage implements Page {
	int DEFAULT_PAGE=1;		//第一页
	int DEFAULT_PAGE_SIZE=12;		//每页显示12条
	int DEFAULT_PAGE_NUM=10;		//分页栏上的动态显示页数为10条
	
	public int getDEFAULT_PAGE() {
		return DEFAULT_PAGE;
	}
	public void setDEFAULT_PAGE(int dEFAULT_PAGE) {
		DEFAULT_PAGE = dEFAULT_PAGE;
	}
	public int getDEFAULT_PAGE_SIZE() {
		return DEFAULT_PAGE_SIZE;
	}
	public void setDEFAULT_PAGE_SIZE(int dEFAULT_PAGE_SIZE) {
		DEFAULT_PAGE_SIZE = dEFAULT_PAGE_SIZE;
	}
	public int getDEFAULT_PAGE_NUM() {
		return DEFAULT_PAGE_NUM;
	}
	public void setDEFAULT_PAGE_NUM(int dEFAULT_PAGE_NUM) {
		DEFAULT_PAGE_NUM = dEFAULT_PAGE_NUM;
	}
	
	
	
	
}
