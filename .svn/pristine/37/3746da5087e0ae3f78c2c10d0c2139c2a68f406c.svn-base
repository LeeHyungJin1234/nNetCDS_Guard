package nnsp.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import nnsp.common.Config;
import nnsp.mappers.NcMenuMapper;
import nnsp.vo.NcMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Component
public class NcMenuService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcMenuService.class);
	@Autowired
	private NcMenuMapper ncMenuMapper;

	@Autowired
	SessionLocaleResolver localeResolver;
	
	// top 메뉴 만들기
	public String getTopMenu() {
		String menu = "", addclass="", chgeclass="";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		menu += "<div id='nav'>";
		
		ArrayList<NcMenu> top = new ArrayList<NcMenu>();
		if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
			top = ncMenuMapper.getTopEnMenu();
		}else{
			top = ncMenuMapper.getTopMenu();
		}
		
		for(int i=0; i<top.size(); i++){ // top 메뉴
			menu += "<div id='menu0"+(i+1)+"' class='nav-menu'>";
			menu += "<ul>";
			
			ArrayList<NcMenu> sub = ncMenuMapper.getSubMenu(top.get(i).getNtm_id()); // 대메뉴의 자식메뉴
			int nowtid = ncMenuMapper.getNowTopMenu(request.getRequestURI()); // 현재 메뉴의 대메뉴 ID
			if(nowtid==top.get(i).getNtm_id()){
				addclass=" active";
				chgeclass="text-white text-shadow-light";
			}else{
				addclass="";
				chgeclass="text-top-color text-shadow";
			}
			menu += "<li class='pad"+addclass+"'><a href='"+sub.get(0).getNsm_control()+"'><img src='/img/trans.png' class='icon'><span class='text-large "+chgeclass+"'>"+top.get(i).getNtm_name()+"</span></a></li>";
			menu += "</ul>";
			menu += "</div>";
		}
		
		menu += "</div>"; // div nav 닫기
		return menu;
    }
	
	// left 메뉴 만들기
	public String getLeftMenu() {
		String menu = "<ul>";
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		int nowtid = ncMenuMapper.getNowTopMenu(request.getRequestURI()); // 현재 메뉴의 대메뉴 ID
		String style="", addclass = "";
		
		ArrayList<NcMenu> sub = new ArrayList<NcMenu>();
		if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
			sub = ncMenuMapper.getSubEnMenu(nowtid);
		}else{
			sub = ncMenuMapper.getSubMenu(nowtid);
		}
		
		NcMenu menuinfo = ncMenuMapper.getNowMenuInfo(request.getRequestURI());
		for(int i=0; i<sub.size(); i++){ // sub 메뉴
			
			if(i>0) style= " style='margin-top:14px;'";
			if(menuinfo.getNsm_id()==sub.get(i).getNsm_id() || menuinfo.getNsm_pr_id()==sub.get(i).getNsm_id()) {
				addclass=" active";
			}else{
				addclass="";
			}
			menu += "<li class='left-menu0"+(i+1)+"'"+style+">";
			menu += "<a href='"+sub.get(i).getNsm_control()+"' class='text-large text-left-color font-weight-900"+addclass+"'><img src='/img/trans.png' class='left-icon'><span>"+sub.get(i).getNsm_name()+"</span><img src='/img/right.png' class='right-icon'></a>";
			menu += "</li>";
		}
		
		menu += "</ul>";
		return menu;
	}
	
	// top 메뉴 만들기
	public String getTopMenu(String url) {
		String menu = "", addclass="", chgeclass="";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		menu += "<div id='nav'>";
		
		ArrayList<NcMenu> top = new ArrayList<NcMenu>();
		if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
			top = ncMenuMapper.getTopEnMenu();
		}else{
			top = ncMenuMapper.getTopMenu();
		}
		
		for(int i=0; i<top.size(); i++){ // top 메뉴
			menu += "<div id='menu0"+(i+1)+"' class='nav-menu'>";
			menu += "<ul>";
			
			ArrayList<NcMenu> sub = ncMenuMapper.getSubMenu(top.get(i).getNtm_id());
			int nowtid = ncMenuMapper.getNowTopMenu(url); // 현재 메뉴의 대메뉴 ID
			if(nowtid==top.get(i).getNtm_id()){
				addclass=" active";
				chgeclass="text-white text-shadow-light";
			}else{
				addclass="";
				chgeclass="text-top-color text-shadow";
			}
			menu += "<li class='pad"+addclass+"'><a href='"+sub.get(0).getNsm_control()+"'><img src='/img/trans.png' class='icon'><span class='text-large "+chgeclass+"'>"+top.get(i).getNtm_name()+"</span></a></li>";
			menu += "</ul>";
			menu += "</div>";
		}
		
		menu += "</div>"; // div nav 닫기
		return menu;
    }
	
	// left 메뉴 만들기
	public String getLeftMenu(String url) {
		String menu = "<ul>";
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		int nowtid = ncMenuMapper.getNowTopMenu(url);
		String style="", addclass = "";
		
		ArrayList<NcMenu> sub = new ArrayList<NcMenu>();
		if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
			sub = ncMenuMapper.getSubEnMenu(nowtid);
		}else{
			sub = ncMenuMapper.getSubMenu(nowtid);
		}
		
		NcMenu menuinfo = ncMenuMapper.getNowMenuInfo(url);
		for(int i=0; i<sub.size(); i++){ // sub 메뉴
			
			if(i>0) style= " style='margin-top:14px;'";
			if(menuinfo.getNsm_id()==sub.get(i).getNsm_id() || menuinfo.getNsm_pr_id()==sub.get(i).getNsm_id()) {
				addclass=" active";
			}else{
				addclass="";
			}
			menu += "<li class='left-menu0"+(i+1)+"'"+style+">";
			menu += "<a href='"+sub.get(i).getNsm_control()+"' class='text-large text-left-color font-weight-900"+addclass+"'><img src='/img/trans.png' class='left-icon'><span>"+sub.get(i).getNsm_name()+"</span><img src='/img/right.png' class='right-icon'></a>";
			menu += "</li>";
		}
		
		menu += "</ul>";
		return menu;
	}
	
	// tab 메뉴 만들기
	public String getTabMenu(){
		String menu = "<ul class='clearfix' style='margin:0;'>";
		String addclass="", addclass1="", addclass2="";
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String uri = request.getRequestURI();
		
		int prid = ncMenuMapper.getPrId(uri); // 현재 메뉴의 서브메뉴 ID
		ArrayList<NcMenu> tab = new ArrayList<NcMenu>();
		if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
			tab = ncMenuMapper.getSubEnMenu(prid);
		}else{
			tab = ncMenuMapper.getSubMenu(prid);
		}
		
		for(int i=0; i<tab.size(); i++){ // tab 메뉴
			if(uri.equals(tab.get(i).getNsm_control())){
				addclass=" text-point-color";
				addclass1="padding-bottom:13px;border-bottom:2px solid #cc1b3c;";
			}else{
				addclass="";
				addclass1="";
			}
			if(i<tab.size()-1) {
				addclass2="border-right:1px solid #d3d8e0;";
			}else{
				addclass2="";
			}
			menu += "<li style='padding:9px 0'><a href='"+tab.get(i).getNsm_control()+"' class='text-large text-left-color font-weight-900"+addclass+"' style='padding:0 20px;"+addclass2+"'><span style='"+addclass1+"'>"+tab.get(i).getNsm_name()+"</span></a></li>";
		}
		//menu += "<li style='float:right; padding:11px 0'><a href='#' class='info' style='font-size:13px; color:rgba(4,9,17,.65); margin-right:20px;'><img src='/img/info.png' style='margin:4px 5px 0 0;'>도움말</a><span>TEST</span></li>";
		menu += "</ul>";
		return menu;
	}
	
	// tab 메뉴 만들기
	@SuppressWarnings("unused")
	public String getTabMenu(String url){
		String menu = "<ul class='clearfix' style='margin:0;'>";
		String addclass="", addclass1="", addclass2="";
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String uri = request.getRequestURI();
		
		int prid = ncMenuMapper.getPrId(url); // 현재 메뉴의 서브메뉴 ID
		ArrayList<NcMenu> tab = new ArrayList<NcMenu>();
		if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
			tab = ncMenuMapper.getSubEnMenu(prid);
		}else{
			tab = ncMenuMapper.getSubMenu(prid);
		}
		
		for(int i=0; i<tab.size(); i++){ // tab 메뉴
			if(url.equals(tab.get(i).getNsm_control())){
				addclass=" text-point-color";
				addclass1="padding-bottom:13px;border-bottom:2px solid #cc1b3c;";
			}else{
				addclass="";
				addclass1="";
			}
			if(i<tab.size()-1) {
				addclass2="border-right:1px solid #d3d8e0;";
			}else{
				addclass2="";
			}

			menu += "<li style='padding:9px 0'><a href='"+tab.get(i).getNsm_control()+"' class='text-large text-left-color font-weight-900"+addclass+"' style='padding:0 20px;"+addclass2+"'><span style='"+addclass1+"'>"+tab.get(i).getNsm_name()+"</span></a></li>";
		}
		//menu += "<li style='float:right; padding:11px 0'><a href='#' class='info' style='font-size:13px; color:rgba(4,9,17,.65); margin-right:20px;'><img src='/img/info.png' style='margin:4px 5px 0 0;'>도움말</a><span>TEST</span></li>";
		menu += "</ul>";
		return menu;
	}
	
	// 이 이후는 나중에 지울자..
	// 전체 메뉴 조회 - 메뉴 만들기
		public String getTotalMenu() {
			String menu = "";
			String link_url = "";
			//String class_name[] = {"fa-bar-chart-o","fa-sitemap","fa-desktop","fa-search","fa-edit"};
			String class_name[] = {"fa-bar-chart-o","fa-desktop","fa-database","fa-edit"};
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    	if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
				ArrayList<NcMenu> top = ncMenuMapper.getTopEnMenu();
				
				for(int i=0; i<top.size(); i++){ // top 메뉴
					
					if(i>0) menu += "</ul></li>"; // top 닫기
						
					menu += "<li><a><i class='fa "+class_name[i]+"'></i>"+top.get(i).getNtm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>"; // top 열기
					
					ArrayList<NcMenu> sub = ncMenuMapper.getSubEnMenu(top.get(i).getNtm_id());
					for(int j=0; j<sub.size(); j++){ // sub 메뉴
						link_url = sub.get(j).getNsm_control();
						
						if("dual".equals(Config.getInstance().getModel()) && "/stat_traffic.do".equals(link_url)){
							continue;
						}
						if("single".equals(Config.getInstance().getModel()) && "/stat_sndrcv.do".equals(link_url)){
							continue;
						}
						if(sub.get(j).getPr_cnt()>0) {
							menu += "<li><a>"+sub.get(j).getNsm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>";
						
							ArrayList<NcMenu> sub1 = ncMenuMapper.getSubEnMenu(sub.get(j).getNsm_id());
							for(int k=0; k<sub1.size(); k++){ // sub2 메뉴
								link_url = sub1.get(k).getNsm_control();
								
								if(sub1.get(k).getPr_cnt()>0) {
									menu += "<li><a>"+sub1.get(k).getNsm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>";
									
									ArrayList<NcMenu> sub2 = ncMenuMapper.getSubEnMenu(sub1.get(k).getNsm_id());
									for(int l=0; l<sub2.size(); l++){ // sub3 메뉴
										link_url = sub2.get(l).getNsm_control();								
										
										if(sub2.get(l).getPr_cnt()>0) {
											menu += "<li><a>"+sub2.get(l).getNsm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>";
											
											ArrayList<NcMenu> sub3 = ncMenuMapper.getSubEnMenu(sub2.get(l).getNsm_id());
											for(int m=0; m<sub3.size(); m++){ // sub4 메뉴
												link_url = sub3.get(m).getNsm_control();
												menu += "<li><a href='"+link_url+"' id='"+sub3.get(m).getNsm_id()+"'>"+sub3.get(m).getNsm_name()+"</a></li>";
											}
											
											menu += "</ul>";
											
										}else{
											menu += "<li><a href='"+link_url+"' id='"+sub2.get(l).getNsm_id()+"'>"+sub2.get(l).getNsm_name()+"</a></li>";
										}
									}
									
									menu += "</ul>";
									
								}else{
									menu += "<li><a href='"+link_url+"' id='"+sub1.get(k).getNsm_id()+"'>"+sub1.get(k).getNsm_name()+"</a></li>";
								}
							}
							
							menu += "</ul>";
						}else{
							menu += "<li><a href='"+link_url+"' id='"+sub.get(j).getNsm_id()+"'>"+sub.get(j).getNsm_name()+"</a></li>";
						}
					}
				}
				
				menu += "</ul></li>"; // top 닫기 - 마지막으로 열린 태그
				return menu;
			}else{
				ArrayList<NcMenu> top = ncMenuMapper.getTopMenu();
				
				for(int i=0; i<top.size(); i++){ // top 메뉴
					
					if(i>0) menu += "</ul></li>"; // top 닫기
						
					menu += "<li><a><i class='fa "+class_name[i]+"'></i>"+top.get(i).getNtm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>"; // top 열기
					
					ArrayList<NcMenu> sub = ncMenuMapper.getSubMenu(top.get(i).getNtm_id());
					for(int j=0; j<sub.size(); j++){ // sub 메뉴
						link_url = sub.get(j).getNsm_control();
						
						if("dual".equals(Config.getInstance().getModel()) && "/stat_traffic.do".equals(link_url)){
							continue;
						}
						if("single".equals(Config.getInstance().getModel()) && "/stat_sndrcv.do".equals(link_url)){
							continue;
						}
						if(sub.get(j).getPr_cnt()>0) {
							menu += "<li><a>"+sub.get(j).getNsm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>";
						
							ArrayList<NcMenu> sub1 = ncMenuMapper.getSubMenu(sub.get(j).getNsm_id());
							for(int k=0; k<sub1.size(); k++){ // sub2 메뉴
								link_url = sub1.get(k).getNsm_control();
								
								if(sub1.get(k).getPr_cnt()>0) {
									menu += "<li><a>"+sub1.get(k).getNsm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>";
									
									ArrayList<NcMenu> sub2 = ncMenuMapper.getSubMenu(sub1.get(k).getNsm_id());
									for(int l=0; l<sub2.size(); l++){ // sub3 메뉴
										link_url = sub2.get(l).getNsm_control();								
										
										if(sub2.get(l).getPr_cnt()>0) {
											menu += "<li><a>"+sub2.get(l).getNsm_name()+"<span class='fa fa-chevron-down'></span></a><ul class='nav child_menu'>";
											
											ArrayList<NcMenu> sub3 = ncMenuMapper.getSubMenu(sub2.get(l).getNsm_id());
											for(int m=0; m<sub3.size(); m++){ // sub4 메뉴
												link_url = sub3.get(m).getNsm_control();
												menu += "<li><a href='"+link_url+"' id='"+sub3.get(m).getNsm_id()+"'>"+sub3.get(m).getNsm_name()+"</a></li>";
											}
											
											menu += "</ul>";
											
										}else{
											menu += "<li><a href='"+link_url+"' id='"+sub2.get(l).getNsm_id()+"'>"+sub2.get(l).getNsm_name()+"</a></li>";
										}
									}
									
									menu += "</ul>";
									
								}else{
									menu += "<li><a href='"+link_url+"' id='"+sub1.get(k).getNsm_id()+"'>"+sub1.get(k).getNsm_name()+"</a></li>";
								}
							}
							
							menu += "</ul>";
							
						}else{
							menu += "<li><a href='"+link_url+"' id='"+sub.get(j).getNsm_id()+"'>"+sub.get(j).getNsm_name()+"</a></li>";
						}
					}
				}
				
				menu += "</ul></li>"; // top 닫기 - 마지막으로 열린 태그
				return menu;
			}
	    }
			
		// 전체 메뉴 조회 - 메뉴 만들기
		public String getTotalMenu_bak() {
			String menu = "";
			String temp2 = "";
			String temp3 = "";
			String link_url = "";
			
			ArrayList<NcMenu> top = ncMenuMapper.getTopMenu();
			
			String uri = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
			ArrayList<NcMenu> now = ncMenuMapper.getNowMenu(uri);
			
			for(int i=0; i<top.size(); i++){ // top 메뉴
				
				if(i>0) menu += "</ul></li></ul>"; // top 닫기
				
				temp2="";
				temp3="class='categories-arr-down'";
				if(top.get(i).getNtm_id() != now.get(0).getNtm_id()) {
					temp2="class='hide'";
					temp3="class='categories-arr-black'";
				}
					
				menu += "<ul class='root-nav-categories'><li><h3 "+temp3+">"+top.get(i).getNtm_name()+"</h3><ul "+temp2+">"; // top 열기
				
				ArrayList<NcMenu> sub = ncMenuMapper.getSubMenu(top.get(i).getNtm_id());
				int temp=0, temp1=0;
				for(int j=0; j<sub.size(); j++){ // sub 메뉴
					link_url = sub.get(j).getNsm_control();
					
					if(sub.get(j).getNsm_depth()==1){
						if(j>0) menu += "</li>"; // sub 닫기
						
						if(link_url==null || "".equals(link_url)) link_url = sub.get(j+1).getNsm_control();
						
						menu += "<li><a href='"+link_url+"'>"+sub.get(j).getNsm_name()+"</a>"; // sub 열기
						
						if(sub.get(j).getPr_cnt()>0) {
							menu += "<ul class='root-nav-sub'>"; // sub2 열기
							temp=sub.get(j).getPr_cnt();
							temp1=0;
						}
					
					}else if(sub.get(j).getNsm_depth()>1){
						menu += "<li><a href='"+link_url+"'>"+sub.get(j).getNsm_name()+"</a></li>";
						temp1++;
						if(temp==temp1) menu += "</ul>"; // sub2 닫기
					}
				}
				
				menu += "</li>"; // sub 닫기 - 마지막으로 열린 태그
			}
			
			menu += "</ul></li></ul>"; // top 닫기 - 마지막으로 열린 태그
			
			return menu;
	    }
		
		// 현재 메뉴 조회 - 메뉴 만들기
		public String[] getNowMenu() {
			String result[] = new String[2];
			String menu = "";
			String link_url = "";
			
			String uri = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
			ArrayList<NcMenu> now = ncMenuMapper.getNowMenu(uri);
		
			int temp=0, temp1=0;
			menu += "<h3>"+now.get(0).getNtm_name()+"</h3><ul>";
			for(int i=0; i<now.size(); i++){
				link_url = now.get(i).getNsm_control();
						
				if(now.get(i).getNsm_depth()==1){
					if(i>0) menu += "</li>";
					
					if(link_url==null || "".equals(link_url)) link_url = now.get(i+1).getNsm_control();
					
					menu += "<li><a href='"+link_url+"'>"+now.get(i).getNsm_name()+"</a>";
					
					
					if(now.get(i).getPr_cnt()>0) {
						menu += "<ul class='example-nav-sub'>";
						temp=now.get(i).getPr_cnt();
						temp1=0;
					}
				}else if(now.get(i).getNsm_depth()>1){
					menu += "<li><a href='"+link_url+"'>"+now.get(i).getNsm_name()+"</a></li>";
					temp1++;
					if(temp==temp1) menu += "</ul>";
				}
			}
			menu += "</li></ul>";
			
			result[0]=menu;
			result[1]=now.get(0).getNtm_name();
			
			return result;
	    }
		
		// 현재 메뉴명
		public String[] getNowTitle() {
			String result[] = new String[2];
			String uri = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    	if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
	    		result[0] = ncMenuMapper.getNowETitle(uri);
			}else{
				result[0] = ncMenuMapper.getNowTitle(uri);
			}
	    	result[1] = ncMenuMapper.getMenuDesc(uri);
	    	return result;
		}
		
		// 전체 메뉴 조회 - 메뉴 만들기(지정하고 싶을 때 사용)
		public String getTotalMenu(int sub_id) {
			String menu = "";
			String temp2 = "";
			String temp3 = "";
			String link_url = "";
			
			ArrayList<NcMenu> top = ncMenuMapper.getTopMenu();
			
			NcMenu sub_info = ncMenuMapper.getSubMenuInfo(sub_id);
			ArrayList<NcMenu> now = ncMenuMapper.getNowMenu(sub_info.getNsm_control());
			
			for(int i=0; i<top.size(); i++){ // top 메뉴
				
				if(i>0) menu += "</ul></li></ul>"; // top 닫기
				
				temp2="";
				temp3="class='categories-arr-down'";
				if(top.get(i).getNtm_id() != now.get(0).getNtm_id()) {
					temp2="class='hide'";
					temp3="class='categories-arr-black'";
				}
					
				menu += "<ul class='root-nav-categories'><li><h3 "+temp3+">"+top.get(i).getNtm_name()+"</h3><ul "+temp2+">"; // top 열기
				
				ArrayList<NcMenu> sub = ncMenuMapper.getSubMenu(top.get(i).getNtm_id());
				int temp=0, temp1=0;
				for(int j=0; j<sub.size(); j++){ // sub 메뉴
					link_url = sub.get(j).getNsm_control();
					
					if(sub.get(j).getNsm_depth()==1){
						if(j>0) menu += "</li>"; // sub 닫기
						
						if(link_url==null || "".equals(link_url)) link_url = sub.get(j+1).getNsm_control();
						
						menu += "<li><a href='"+link_url+"'>"+sub.get(j).getNsm_name()+"</a>"; // sub 열기
						
						if(sub.get(j).getPr_cnt()>0) {
							menu += "<ul class='root-nav-sub'>"; // sub2 열기
							temp=sub.get(j).getPr_cnt();
							temp1=0;
						}
					
					}else if(sub.get(j).getNsm_depth()>1){
						menu += "<li><a href='"+link_url+"'>"+sub.get(j).getNsm_name()+"</a></li>";
						temp1++;
						if(temp==temp1) menu += "</ul>"; // sub2 닫기
					}
				}
				
				menu += "</li>"; // sub 닫기 - 마지막으로 열린 태그
			}
			
			menu += "</ul></li></ul>"; // top 닫기 - 마지막으로 열린 태그
			
			return menu;
	    }
		
		// 현재 메뉴 조회 - 메뉴 만들기(지정하고 싶을 때 사용)
		public String[] getNowMenu(int sub_id) {
			/*NdMenu sub_info = ncMenuMapper.getSubMenuInfo(sub_id);
			
			String result[] = new String[2];
			String menu = "";
			String link_url = "";
			
			ArrayList<NdMenu> now = ncMenuMapper.getNowMenu(sub_info.getNsm_control());
			
			int temp=0, temp1=0;
			menu += "<h3>"+now.get(0).getNtm_name()+"</h3><ul>";
			for(int i=0; i<now.size(); i++){
				link_url = now.get(i).getNsm_control();
						
				if(now.get(i).getNsm_depth()==1){
					if(i>0) menu += "</li>";
					
					if(link_url==null || "".equals(link_url)) link_url = now.get(i+1).getNsm_control();
					
					menu += "<li><a href='"+link_url+"'>"+now.get(i).getNsm_name()+"</a>";
					
					
					if(now.get(i).getPr_cnt()>0) {
						menu += "<ul class='example-nav-sub'>";
						temp=now.get(i).getPr_cnt();
						temp1=0;
					}
				}else if(now.get(i).getNsm_depth()>1){
					menu += "<li><a href='"+link_url+"'>"+now.get(i).getNsm_name()+"</a></li>";
					temp1++;
					if(temp==temp1) menu += "</ul>";
				}
			}
			menu += "</li></ul>";
			
			result[0]=menu;
			result[1]=now.get(0).getNtm_name();
			
			return result;*/
			return new String[2];
		}
		
		// 현재 메뉴명 - 메뉴 만들기(지정하고 싶을 때 사용)
		public String[] getNowTitle(int sub_id) {
			String result[] = new String[2];
			NcMenu sub_info = ncMenuMapper.getSubMenuInfo(sub_id);
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    	if("en".equals(localeResolver.resolveLocale(request).getLanguage())){
	    		result[0] = ncMenuMapper.getNowETitle(sub_info.getNsm_control());
			}else{
				result[0] = ncMenuMapper.getNowTitle(sub_info.getNsm_control());
			}
	    	result[1] = ncMenuMapper.getMenuDesc(sub_info.getNsm_control());
	    	return result;
		}
}