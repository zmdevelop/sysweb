package cn.com.taiji.spiderconf.web;

import net.kernal.spiderman.worker.download.Downloader.Request;
import net.kernal.spiderman.worker.download.Downloader.Response;
import net.kernal.spiderman.worker.download.impl.HttpClientDownloader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class SpiderWebController {

	@RequestMapping("/getHtmlStr")
	public String page(Model model, String url) {
		HttpClientDownloader cd = new HttpClientDownloader();
		Response rs = cd.download(new Request(url));
		if (rs != null) {
			String bodyStr = rs.getBodyStr();
			/*bodyStr ="<div>123</div>"+
					"<div>234</div>"+
					"<div>345</div>"+
					"<div>456</div>"+
					"<div>567</div>"+
					"<div>789</div>"+
					"<div>222</div>";*/
			
			bodyStr = bodyStr==null?null:bodyStr.replaceAll("<script[^>]*>[\\d\\D]*?</script>", "");
//			bodyStr = bodyStr==null?null:bodyStr.replaceAll("<script type=\"text/javascript\" src=.*jquery.*</script>", "");
			model.addAttribute("bodyStr", bodyStr);
		}
		return "spider/web/page";
	}
public static void main(String[] args) {
	String bodyStr = "<script type=\"text/javascript\" src=\"http://himg2.jquery.huanqiu.com/statics/hq2013/js/section/section.js\" charset=\"utf-8\" ></script>"+
    "<script type=\"text/javascript\" src=\"http://himg2.huanqiu.com/statics/js/adapter.js\" charset=\"utf-8\" ></script>"+
    "<script src=\"http://himg2.huanqiu.com/statics/hq2013/js/lib/jquery1.9.1.js\" type=\"text/javascript\"></script>"+
    "sdfsd<script type=\"text/javascript\"></script>dsf";
	//bodyStr="asssw";
	//bodyStr = bodyStr.replaceAll("(<script src=.*jquery.*</script>?)|(<script type=\"text/javascript\" src=.*jquery.*</script>?)", "");
	bodyStr = bodyStr.replaceAll("<script[^>]*>[\\d\\D]*?</script>","");
	System.out.println(bodyStr);
}
}
