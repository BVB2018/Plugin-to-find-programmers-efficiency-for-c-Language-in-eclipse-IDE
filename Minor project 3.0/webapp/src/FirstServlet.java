import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

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
        out.print("welcome");
       String contents = null; 
              contents= request.getParameter("myTextArea");
       
       
      
     // String content="int a,b,c,d,e; File *fp1; ";
       
       //ANTLRFileStream input = new ANTLRFileStream("assign.c");//msp edit
       
	//CharStream input = (CharStream) new ANTLRFileStream("array7.c");//msp edit
       //CLexer lexer = new CLexer(input);
      CLexer lexer = new CLexer(new ANTLRInputStream(contents));
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
		double ifToTernIneff=Constants.wtifToTernNotEff*Constants.ifToTernNotEff;
		double ifToSwitchIneff=Constants.wtifToSwitchNotEff*Constants.ifToSwitchNotEff;
		double mulDivIneff=Constants.wtmulDivExpNotEff*Constants.mulDivExpNotEff;
		double pointerIneff=Constants.wtpointerUsageNotEff*Constants.pointerUsageNotEff;
		double typecastIneff=Constants.wttypeCastNotEff*Constants.typeCastNotEff;
		/*
		out.println("");
		out.println("assignIneff "+assignIneff);
		out.println("<BR>loopsIneff "+loopsIneff);
		out.println("<BR>scopeOfVarIneff "+scopeOfVarIneff);
		out.println("<BR>varDeclNoyUsedIneff "+varDeclNoyUsedIneff);
		out.println("<BR>ifToTernIneff "+ifToTernIneff);
		out.println("<BR>ifToSwitchIneff "+ifToSwitchIneff);
		out.println("<BR>mulDivIneff "+mulDivIneff);
		out.println("<BR>pointerIneff "+pointerIneff);
		out.println("<BR>typecastIneff "+typecastIneff);
                   */     
                        
                
                
                
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
       out.print("<div id='columnchart_material' style='width: 1400px; height: 500px;'></div>");
       //out.print(" <form action='http://localhost:8084/programmerEfficiencyVenu/'>  ");
      // out.print("<button type='submit' >Submit another program</button>");
      
       out.print("</form>");
       //for(int i=0 ; i<Constants.numOfmistakes;i++)
           // out.print(Constants.MistakeLines[i]);
       out.print("</body>");
       out.print("</html>");
       
       Constants.assignNotEff=0;
       Constants.loopsNotEff=0;
       Constants.ifToSwitchNotEff=0;
       Constants.pointerUsageNotEff=0;
       Constants.varDeclNotUsedEff=0;
       Constants.scopeOfVarNotEff=0;
       Constants.ifToTernNotEff=0;
       Constants.mulDivExpNotEff=0;
       Constants.typeCastNotEff=0;
      // Constants.MistakeLines=null;
     //  FileInputStream file =  new FileInputStream(contents);
       
    //   out.println(contents);

     }catch(Exception e){System.out.println(e);}   
       
      
      }
      
    }  