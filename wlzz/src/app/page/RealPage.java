package page;

import java.lang.reflect.InvocationTargetException;  
import java.util.ArrayList;
import java.util.List; 
import page.abs.AbstractPage;
import play.db.jpa.GenericModel;
import play.db.jpa.GenericModel.JPAQuery;


/**
 * 封装好的page
 * @author volador
 *
 */
public class RealPage extends AbstractPage{
	private Integer page=null;
	private Integer pageSize=null;
	private Class<? extends GenericModel> model;
	//private GenericModel model=null;

	public RealPage(Integer[] pages,Class<? extends GenericModel> model){
		if(pages!=null&&pages.length==1&&pages[0]>0){
			this.page=pages[0];
		}else if(pages!=null&&pages.length>=2){
			if(pages[0]>0)
				this.page=pages[0];
			if(pages[1]>0)
				this.pageSize=pages[1];
		}
		page=(page == null ? this.getDEFAULT_PAGE() : page);
		pageSize=(pageSize == null ? this.getDEFAULT_PAGE_SIZE() : pageSize);
		this.model=model;
	}
	
	
	
	@Override
	public List<? extends GenericModel> getAllObject() {
		// TODO Auto-generated method stub
		List<? extends GenericModel> allObject=null;
		try {
			JPAQuery jp=(JPAQuery) this.model.getDeclaredMethod("all", null).invoke(this.model, null);
			allObject=jp.fetch(this.page, this.pageSize);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allObject;
	}

	@Override
	public long getAllPage() {
		// TODO Auto-generated method stub
		long allPage = 0;
		allPage=this.getAll()/this.getPageSize();
		if(this.getAll()%this.getPageSize()!=0)
			allPage++;
		return allPage;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return this.page;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return this.getDEFAULT_PAGE_SIZE();
	}

	@Override
	public long getAll() {
		// TODO Auto-generated method stub
		long all=0;
		try {
			all= (Long) this.model.getMethod("count", null).invoke(this.model.newInstance(), null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all;
	}

	@Override
	public List<Integer> getPageList() {
		// TODO Auto-generated method stub
		List<Integer> pageList=new ArrayList<Integer>();
		//根据page，allPage，计算fromPage，toPage，并装载进pageList，供前台分页:当前页处于中间位置，页面数量的绝对值为pageNum
		int shouldLeftStep=this.getDEFAULT_PAGE_NUM()/2;					//应该向前检索几步,5
		int realLeftStep;
		
		
		if((this.page-1) <= shouldLeftStep){  				//实际应该向前检索的步数
			realLeftStep=this.page-1;
		}else{
			realLeftStep=shouldLeftStep;
		}
		
		
		
		int shouldRightStep=this.getDEFAULT_PAGE_NUM()-realLeftStep-1;		//应该向后检索几步
		int realRightStep;
		
		if(page+shouldRightStep <= this.getAllPage()){			//实际应该向后检索的步数
			realRightStep=shouldRightStep;
		}else{
			realRightStep=(int) (this.getAllPage()-page);
		}
		
		for(int step=page-realLeftStep;step<=page+realRightStep;step++){
			pageList.add(step);
		}
		
		
		return pageList;
	}



}
