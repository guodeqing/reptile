package reptile.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtil {
	public static void setResponse(HttpServletRequest  request,HttpServletResponse response,String fileName){
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("application/octet-stream");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	};
}
