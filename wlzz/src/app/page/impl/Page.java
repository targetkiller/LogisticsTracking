package page.impl;

import java.util.List;

import play.db.jpa.GenericModel;

/**
 * 规范page所要拥有的操作
 * @author volador
 *
 */
public interface Page {
	
	
	/**
	 * 拿到对象集合
	 * @return 对象集合
	 */
	public List<? extends GenericModel> getAllObject();
	/**
	 * 拿到共有几页
	 * @return 总页数 
	 */
	public long getAllPage();
	
	/**
	 * 拿到当前是第几条
	 * @return 当前处于的条数
	 */
	public int getPage();
	
	/**
	 * 拿到每页要显示几条记录
	 * @return 每页要显示的
	 */
	public int getPageSize();
	
	/**
	 * 拿到全部记录数
	 * @return 全部记录数
	 */
	public long getAll();
	/**
	 * 拿到动态分页的元素集
	 * @return List<Integer> 动态元素集
	 */
	public List<Integer> getPageList();
}
