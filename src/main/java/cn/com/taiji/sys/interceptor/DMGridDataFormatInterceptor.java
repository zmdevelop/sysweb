package cn.com.taiji.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.taiji.spiderconf.dto.SpiderSiteDto;
import cn.com.taiji.sys.util.PageConvertUtil;
import cn.com.taiji.sys.util.Pagination;

/**
 * 将返回值ModelAndView 中包含的"page" 如果是Pagination类型 做处理封装成topieGrid 可解析的json
 * @author Mr.Jin
 *
 */
public class DMGridDataFormatInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		if(arg3==null){
			return;
		}
		ModelMap m = arg3.getModelMap();
		boolean isgrid = m.containsKey("page");
		if(isgrid){
			Object page = m.get("page");
			if(page instanceof Pagination){
				Pagination p = (Pagination)page;
				m.addAttribute(PageConvertUtil.grid(p));
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		return true;
	}

}
