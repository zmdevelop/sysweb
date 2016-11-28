package cn.com.taiji.sys.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cgj on 2015/10/30.
 */
public class PageConvertUtil {
    public static Map grid(Pagination<?> info){
        Map map = new HashMap();
        map.put("status",1);
        map.put("total",info.getPageTotal());
        map.put("data",info.getPageResult());
        return map;
    }
    public static Map emptyGrid(){
        Map map = new HashMap();
        map.put("status",1);
        map.put("total",0);
        map.put("data", Collections.emptyList());
        return map;
    }
}
