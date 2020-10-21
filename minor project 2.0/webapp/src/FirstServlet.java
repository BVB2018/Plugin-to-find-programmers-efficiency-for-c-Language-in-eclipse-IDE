import java.io.*;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import java.util.stream.*; 




import javax.servlet.*;  
import javax.servlet.http.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


import de.minorproject.Parser.CLexer;
import de.minorproject.Parser.CParser;  
      
      
    public class FirstServlet extends HttpServlet {  
      
      public void doPost(HttpServletRequest request, HttpServletResponse response){  
        try{  
      
       response.setContentType("text/html");  
      
        PrintWriter out = response.getWriter();  
    //   String contents = request.getParameter("myTextArea");
       
       
     /* 
      String content="int a,b,c,d,e; File *fp1; ";
       
   //    ANTLRInputStream input = new ANTLRFileStream("assign.c");
      
       
	char []list = content.toCharArray();//new ANTLRInputStream(content);
	
	//char[] list = {'a','c','e'};
	Stream<Character> charStream = new String(list).chars().mapToObj(i->(char)i);
	
	
	
       CLexer lexer = new CLexer((CharStream) charStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokens);
		
		ParseTree tree = parser.compilationUnit();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(new CWalker(), tree);
		new MyVisitor().visit(tree);	
		walker.walk(new TypeCast(),tree);	
		
		
		double assignIneff=Constants.wtassignNotEff*Constants.assignNotEff;
		double loopsIneff=Constants.wtloopsNotEff*Constants.loopsNotEff;
		double scopeOfVarIneff=Constants.wtscopeOfVarNotEff*Constants.scopeOfVarNotEff;
		double varDeclNoyUsedIneff=Constants.wtvarDeclNotUsedEff*Constants.varDeclNotUsedEff;
		double ifToTernIneff=Constants.wtifToTernNotEff*Constants.ifToSwitchNotEff;
		double ifToSwitchIneff=Constants.wtifToSwitchNotEff*Constants.ifToTernNotEff;
		double mulDivIneff=Constants.wtmulDivExpNotEff*Constants.mulDivExpNotEff;
		double pointerIneff=Constants.wtpointerUsageNotEff*Constants.pointerUsageNotEff;
		double typecastIneff=Constants.wttypeCastNotEff*Constants.typeCastNotEff;
		
		System.out.println("");
		System.out.println("assignIneff "+assignIneff);
		System.out.println("loopsIneff "+loopsIneff);
		System.out.println("scopeOfVarIneff "+scopeOfVarIneff);
		System.out.println("varDeclNoyUsedIneff "+varDeclNoyUsedIneff);
		System.out.println("ifToTernIneff "+ifToTernIneff);
		System.out.println("ifToSwitchIneff "+ifToSwitchIneff);
		System.out.println("mulDivIneff "+mulDivIneff);
		System.out.println("pointerIneff "+pointerIneff);
		System.out.println("typecastIneff "+typecastIneff);
     //  FileInputStream file =  new FileInputStream(contents);
      */
     //  out.println(content);
       
       double assignIneff=Constants.wtassignNotEff*3;
		double loopsIneff=Constants.wtloopsNotEff*8;
		double scopeOfVarIneff=Constants.wtscopeOfVarNotEff*0;
		double varDeclNoyUsedIneff=Constants.wtvarDeclNotUsedEff*7;
		double ifToTernIneff=Constants.wtifToTernNotEff*1;
		double ifToSwitchIneff=Constants.wtifToSwitchNotEff*5;
		double mulDivIneff=Constants.wtmulDivExpNotEff*8;
		double pointerIneff=Constants.wtpointerUsageNotEff*13;
		double typecastIneff=Constants.wttypeCastNotEff*2;
		
       out.println("<html>");
       out.println("<head>");
       out.println(" <script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
       out.print("<script type='text/javascript'>");
       out.print("google.charts.load('current', {'packages':['bar']});");
       out.print("google.charts.setOnLoadCallback(drawChart);");
       String var="function drawChart() { var data = google.visualization.arrayToDataTable([  "
       		+ "['Category','Not Efficient'], "
       		+ "['Loops', "+loopsIneff+"],"
       		+ "['Assignment statements', "+ assignIneff+ "],"
       		+ "['Scope of Variable', "+ scopeOfVarIneff+ "],"
       		+ "['Variable not used',"+varDeclNoyUsedIneff+"],"
       		+ "['if to Ternamry statements',"+ifToTernIneff+"],"
       		+ "['if to Switch statements',"+ifToSwitchIneff+"],"
       		+ "['Pointers',"+pointerIneff+"],"
       		+ "['Multiplication And Division',"+mulDivIneff+"],"
       		+ "['TypeCasting',"+typecastIneff+"]"
       		
       		+ "]);";
       
       out.print(var);
       String options="var options = {chart: { title: 'Programmers Efficiency', subtitle: 'C Programs',   }};";
       out.print(options);
       out.print("var chart = new google.charts.Bar(document.getElementById('columnchart_material'));");
       out.print("chart.draw(data, google.charts.Bar.convertOptions(options)); }");
       out.print("</script>");
       out.print("</head>");
       out.print("<body>");
       out.print("<div id='columnchart_material' style='width: 1600px; height: 800px;'></div>");
       out.print(" <form action='index.html'>  ");
       out.print("<input type='submit' value='Submit another program'>");
       out.print("</form>");
       //out.print("<button type='button' onclick=' window.location.href='index.html''>Submit another program</button>");
       out.print("</body>");
       out.print("</html>");
       
       
       
      

       
       
       

     }catch(Exception e){System.out.println(e);}   
       
      
      }
      
    }  