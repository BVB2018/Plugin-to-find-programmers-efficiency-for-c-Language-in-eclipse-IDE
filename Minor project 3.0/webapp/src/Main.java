
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import de.minorproject.Parser.CLexer;
import de.minorproject.Parser.CParser;

public class Main {
	public static void main(String[] args) throws Exception {
		//String content="int a,b,c,d,e; File *fp1; ";
		// CharStream input = new ANTLRInputStream(content);
		ANTLRInputStream input = new ANTLRFileStream("test.c");
		
		CLexer lexer = new CLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokens);
		
		ParseTree tree = parser.compilationUnit();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(new CWalker(), tree);
		new MyVisitor().visit(tree);	
		walker.walk(new TypeCast(),tree);	
		
		/*
		System.out.println("assignEff "+Constants.assignEff);
		System.out.println("assignNotEff "+Constants.assignNotEff);
		System.out.println("loopsEff "+Constants.loopsEff);
		System.out.println("loopsNotEff "+Constants.loopsNotEff);
		System.out.println("scopeOfVarEff "+Constants.scopeOfVarEff);
		System.out.println("scopeOfVarNotEff "+Constants.scopeOfVarNotEff);
		System.out.println("varDeclUsedEff "+Constants.varDeclUsedEff);
		System.out.println("varDeclNotUsedEff "+Constants.varDeclNotUsedEff);
		System.out.println("ifEff "+Constants.ifEff);
		System.out.println("ifToSwitchNotEff "+Constants.ifToSwitchNotEff);
		System.out.println("ifToTernNotEff "+Constants.ifToTernNotEff);
		System.out.println("mulDivExpEff "+Constants.mulDivExpEff);
		System.out.println("mulDivExpNotEff "+Constants.mulDivExpNotEff);
		System.out.println("pointerUsageEff "+Constants.pointerUsageEff);
		System.out.println("pointerUsageNotEff "+Constants.pointerUsageNotEff);
		System.out.println("typeCastNotEff "+Constants.typeCastNotEff);
		*/
		
		double assignIneff=Constants.wtassignNotEff*Constants.assignNotEff;
		double loopsIneff=Constants.wtloopsNotEff*Constants.loopsNotEff;
		double scopeOfVarIneff=Constants.wtscopeOfVarNotEff*Constants.scopeOfVarNotEff;
		double varDeclNoyUsedIneff=Constants.wtvarDeclNotUsedEff*Constants.varDeclNotUsedEff;
		double ifToTernIneff=Constants.wtifToTernNotEff*Constants.ifToTernNotEff;
		double ifToSwitchIneff=Constants.wtifToSwitchNotEff*Constants.ifToSwitchNotEff;
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
	}
}
