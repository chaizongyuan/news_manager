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
		//��ȡ��ǰʱ��
		Date date=new Date();
		String dateStr=sdf.format(date);
		
		String uuid=UUID.randomUUID().toString();
		//����html�ļ�

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		//����ftl����Ŀ¼
		cfg.setDirectoryForTemplateLoading(
		new File("src/main/resources"));
		//�������ݵ�ץȡģʽ
		cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
		
		Map root = new HashMap();
		root.put("title", title);
		root.put("content", content);
		root.put("createtime", dateStr);
		//�ҵ�ģ����� ʵ��ģ����� 
		Template temp = cfg.getTemplate("newsdetail.ftl");
	
		//�ļ�����·��
		String saveFile=HTML_DIR+"/"+(uuid)+".html";
		
		//�����Ŀ������html
		Writer out = new OutputStreamWriter(new FileOutputStream(saveFile));
		try {
			temp.process(root, out);
		} catch (TemplateException e) {
		
			e.printStackTrace();
		}
		out.flush();  
		out.close();
		
		
		
		ms.inserNews(title, content,(uuid)+".html", dateStr);
		response.getWriter().println("���ŷ����ɹ���");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		doGet(request, response);
	}

}
