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
       String contents = request.getParameter("myTextArea");
       
       
      
      String content="int a,b,c,d,e; File *fp1; ";
       
   //    ANTLRInputStream input = new ANTLRFileStream("assign.c");
       
	CharStream input = new ANTLRInputStream(content);
       CLexer lexer = new CLexer(input);
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
       
       //out.println(contents);

     }catch(Exception e){System.out.println(e);}   
       
      
      }
      
    }  