package cn.et.comtroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import cn.et.model.MyNews;

/**
 * Servlet implementation class NewServlet
 */
public class NewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public NewServlet() {
        super();
       
    }
    public static  final String HTML_DIR="E:\\myhtml";
	MyNews ms=new MyNews();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		//获取当前时间
		Date date=new Date();
		String dateStr=sdf.format(date);
		
		String uuid=UUID.randomUUID().toString();
		//生成html文件

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		//配置ftl查找目录
		cfg.setDirectoryForTemplateLoading(
		new File("src/main/resources"));
		//设置数据的抓取模式
		cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
		
		Map root = new HashMap();
		root.put("title", title);
		root.put("content", content);
		root.put("createtime", dateStr);
		//找到模板对象 实例模板对象 
		Template temp = cfg.getTemplate("newsdetail.ftl");
	
		//文件保存路径
		String saveFile=HTML_DIR+"/"+(uuid)+".html";
		
		//输出到目标生成html
		Writer out = new OutputStreamWriter(new FileOutputStream(saveFile));
		try {
			temp.process(root, out);
		} catch (TemplateException e) {
		
			e.printStackTrace();
		}
		out.flush();  
		out.close();
		
		
		
		ms.inserNews(title, content,(uuid)+".html", dateStr);
		response.getWriter().println("新闻发布成功！");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		doGet(request, response);
	}

}
