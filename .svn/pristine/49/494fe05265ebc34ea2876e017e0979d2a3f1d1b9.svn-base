package nnsp.common;

import java.util.List;

public class PageInfo<T> {
	private int totalPage;
	private int totalArticle;
	private int perArticle;
	private int currentPage;
	private int startPage;
	private int endPage;
	private int offset;
	private int startArticleNo;
	
	private List<T> result;
		
	public PageInfo() {
		totalPage = 1;
		totalArticle = 0;
		perArticle = Config.getInstance().getPerPage();
		currentPage = 1;
		offset = 0;
		startPage = 1;
		endPage = 1;
		startArticleNo = 1;
		result = null;
	}
	
	public PageInfo(int total, int cpage) {
		totalArticle = total;
		currentPage = cpage;
		perArticle = Config.getInstance().getPerPage();
		result = null;
		set();
	}
	
	private void set() {
		if(currentPage == 0) currentPage = 1;
		int even = totalArticle % perArticle;
		if( even == 0 && totalArticle > 0)
			totalPage = (int)(totalArticle / perArticle);
		else 
			totalPage = (int)(totalArticle / perArticle) + 1;
		if( totalPage < currentPage) currentPage = totalPage;
		offset = ( perArticle * (currentPage - 1));
		startArticleNo = totalArticle - offset;
		even = currentPage % Config.getInstance().getPageBlockSize();
		startPage = (int)((currentPage-1) / Config.getInstance().getPageBlockSize()) * Config.getInstance().getPageBlockSize() + 1;
		endPage = startPage + Config.getInstance().getPageBlockSize() - 1;
		if( endPage > totalPage) endPage = totalPage;
	}

	public int getTotalArticle() {
		return totalArticle;
	}

	public void setPageInfo(int totalArticle , int currentPage) {
		this.totalArticle = totalArticle;
		this.currentPage = currentPage;
		set();
	}

	public int getPerArticle() {
		return perArticle;
	}

	public void setPerArticle(int perArticle) {
		if( perArticle == 0) return;
		this.perArticle = perArticle;
		set();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getOffset() {
		return offset;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
	
	public int[] getPagesArray() { 
	    int[] ar_page = new int[endPage - startPage + 1];
	    for(int i = 0; (i + startPage) <= endPage ; i++ )
	        ar_page[i] = i + startPage;
	    return ar_page;
	}

	public int getStartArticleNo() {
		return startArticleNo;
	}
}