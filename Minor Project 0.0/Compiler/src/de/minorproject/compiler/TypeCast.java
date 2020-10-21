package de.minorproject.compiler;

import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.*;
import de.minorproject.Parser.CBaseListener;
import de.minorproject.Parser.CParser.CastExpressionContext;
import de.minorproject.Parser.CParser.CompilationUnitContext;
import de.minorproject.Parser.CParser.DeclarationContext;

public class TypeCast extends CBaseListener {
	//local declarations
	Object objVar="";
	ArrayList VarUsed = new ArrayList();
	ArrayList TypeCastTokens = new ArrayList();
	String currentMethod;
	int flag=0;
	int flag1=0;
	
	ArrayList ac = new ArrayList();
	String data="";
	String data1="";
	int int_type=3;
	int double_type=1;
	int float_type=2;
	
	int cast_value=0;
	int decl_value=0;
	Object cast_type;
	Object decl_type;
	
	public void enterCastExpression(CastExpressionContext ctx){
		int count= ctx.getChildCount();
		int i;
		//usually cast expression has 4 child nodes
		if(4 == count){
			Object castType = ctx.getChild(1).getText();
			//get the type cast value
			if(castType.equals("int")||castType.equals("char")||castType.equals("float")||castType.equals("double")){
				//assign integer value to each occurrence of type cast, which will be used further to find eff casting 
				if(castType.equals("int")){
					TypeCastTokens.add(2);
				}
				else if(castType.equals("char")){
					TypeCastTokens.add(1);
				}
				else if(castType.equals("float")){
					TypeCastTokens.add(3);
				}
				else if(castType.equals("double")){
					TypeCastTokens.add(4);
				}
				TypeCastTokens.add(ctx.getChild(3).getText());
			}
		}
	}
	public void enterDeclaration (DeclarationContext ctx){
		flag++;
	}
	public void exitDeclaration (DeclarationContext ctx){
		int count= ctx.getChildCount();
		flag--;
		if(flag==0){
		}
	}
	public void exitCompilationUnit(CompilationUnitContext ctx){
		System.out.println(VarUsed);
		System.out.println(TypeCastTokens);
		int sizeOfTypeCastTokens = TypeCastTokens.size();
		int i=0,index=0,res=0;
		Object ob="";
		for(i=1;i<sizeOfTypeCastTokens;i+=2){
			ob = TypeCastTokens.get(i);
			//for each variable present in cast expression, get it's actual type, and the type to which it is being converted
			//by subtracting it's representative integer values, we can get if it's being converted to it's lower type 
			if(VarUsed.contains(ob)){
				index = VarUsed.indexOf(ob);
				index--;
				while(index>0){
					ob = VarUsed.get(index);
					if(ob.equals("int")){
						if((int)TypeCastTokens.get(i-1)-2<0){
							//System.out.println("ineff");	
							System.out.println("Type cast ineff "+ctx.getStart().getLine());
							Constants.typeCastNotEff++;
						}
						break;
					}
					else if(ob.equals("float")){
						if((int)TypeCastTokens.get(i-1)-3<0){
							//System.out.println("Type cast ineff "+ctx.getStart().getLine());
							Constants.typeCastNotEff++;
						}
						break;
					}
					else if(ob.equals("double")){
						if((int)TypeCastTokens.get(i-1)-4<0){
							System.out.println("Type cast ineff "+ctx.getStart().getLine());
							Constants.typeCastNotEff++;
						}
						break;
					}
					else{
						index--;
					}
				}
			}
		}
	}
	public void visitTerminal(TerminalNode node)
	{
		if(flag>0){
			Object ob=node.getText();
			boolean isValidInteger = false;
			if (ob instanceof String){
				String str=(String) ob;
				
			     try{
			         int intob = Integer.parseInt(str);
			        isValidInteger = true;
			      }
			      catch (NumberFormatException ex)
			      {
			         // s is not an integer
			      }
			}
			//collect all variables used in type cast expression
			if(!(isValidInteger||ob.equals("*")||ob.equals(",")||ob.equals(";")||ob.equals("[")||ob.equals("]")||ob.equals("=")||ob.equals("File")||ob.equals("free")||ob.equals("fclose")||ob.equals("(")||ob.equals(")"))){
				if(!(objVar.equals("*"))||!(objVar.equals("("))){
					VarUsed.add(ob);
				}
			}
			objVar=ob;
		}
	}
}



