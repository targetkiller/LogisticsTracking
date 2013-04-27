package page;

import page.impl.Page;
import play.db.jpa.GenericModel;

/**
 * 产生RealPage的工厂
 * @author volador
 *
 */
public class PageFactory {
	/**
	 * 私有构造子
	 */
	private PageFactory(){}
	
	/**
	 * 基于接口，提供分页实例
	 * @param model 要用于分页的model
	 * @param pages	只接受前面两个参数，参数1：当前处于第几页 参数2：每页显示几条信息
	 * @return 分页实例
	 */
	public static Page getPage(Class<? extends GenericModel> model,Integer... pages){
		return new RealPage(pages,model);
	}
}
