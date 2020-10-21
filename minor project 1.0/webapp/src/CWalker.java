import java.util.ArrayList;

import org.antlr.v4.runtime.tree.TerminalNode;

import de.minorproject.Parser.CBaseListener;
import de.minorproject.Parser.CParser.AssignmentExpressionContext;
import de.minorproject.Parser.CParser.CompilationUnitContext;
import de.minorproject.Parser.CParser.DeclarationContext;
import de.minorproject.Parser.CParser.DeclaratorContext;
import de.minorproject.Parser.CParser.FunctionDefinitionContext;
import de.minorproject.Parser.CParser.IterationStatementContext;
import de.minorproject.Parser.CParser.MultiplicativeExpressionContext;
import de.minorproject.Parser.CParser.ParameterListContext;
import de.minorproject.Parser.CParser.SelectionStatementContext;
import de.minorproject.Parser.CParser.StatementContext;

public class CWalker extends CBaseListener {
	String currentMethod;
	Object prevStr;
	Object obj="",objvar="";
	int count=0;
	int flag1=0,Count_IF=0,check_for_switch=-1,SelecStmtloc=0,Func_defin=0,var_Decl=0,Iter_Stmt=0;
	int AssignExpr=0,Stmt=0,Decl=0,ParaList=0,Assign_Stmt=0;
	int for_Exp=0,while_Exp=0,do_While_Exp=0;
	ArrayList list_Selec_Stmt = new ArrayList();
	ArrayList list_Func_Defin = new ArrayList();
	ArrayList list_Func_Name = new ArrayList();
	ArrayList list_Var_Decl = new ArrayList();
	ArrayList list_Var_Used = new ArrayList();
	ArrayList list_of_Tokens = new ArrayList();
	ArrayList ToCheckVar = new ArrayList();
	ArrayList LHSOfAssign = new ArrayList();
	ArrayList Temp_List = new ArrayList();
	ArrayList list_Global_Var_Decl = new ArrayList();
	ArrayList list_Pointer_Var_Decl = new ArrayList();
	ArrayList list_Pointer_Var_usage = new ArrayList();
	ArrayList Count_Global_Var_Used = new ArrayList();
	ArrayList Var_Used = new ArrayList();
	ArrayList Var_Data_Type = new ArrayList();
	Object dataType="";
	public void enterStatement(StatementContext ctx){
		if(Iter_Stmt>0){
			Stmt=1;	
		}
	}
	public void exitStatement(StatementContext ctx){
		if(Iter_Stmt>0){
			Stmt=0;	
		}
	}
	public void enterAssignmentExpression(AssignmentExpressionContext ctx){
		int count,i,rem,div,intob=1,index=0;
		count= ctx.getChildCount();
		//System.out.println("count: "+count);
		//for(i=0;i<count;i++){
		//	System.out.println("node: "+ctx.getChild(i).getText());
		//}
		if(count>2){
			if((ctx.getChild(2).getText().contains("malloc"))||(ctx.getChild(2).getText().contains("calloc"))||(ctx.getChild(2).getText().contains("realloc"))||(ctx.getChild(2).getText().contains("fopen"))){
				if(list_Pointer_Var_Decl.contains(ctx.getChild(0).getText())){
					index=list_Pointer_Var_Decl.indexOf(ctx.getChild(0).getText());
					list_Pointer_Var_usage.set(index, (int)list_Pointer_Var_usage.get(index)+1);
				}		
			}
		}
		if(Stmt>0){
			for(i=0;i<count;i++){
				LHSOfAssign.add(ctx.getChild(i).getText());
			}
		}
		for(i=0;i<count;i++){
			Object ob=ctx.getChild(i).getText();
			if((ob.equals("*="))||(ob.equals("/="))){
				ob=(ctx.getChild(i+1).getText());
				//System.out.println(ob);
				if (ob instanceof String){
					String str=(String) ob;
					boolean isValidInteger = false;
				     try{
				         intob = Integer.parseInt(str);
				         isValidInteger = true;
				      }
				      catch (NumberFormatException ex)
				      {
				         // s is not an integer
				      }
				      if(isValidInteger)
				    	  if((Integer.bitCount(intob)==1)&&(intob>1)){
					         //System.out.println("can be written as shift exp");
				    		  Constants.mulDivExpNotEff++;
						  }else{
						   	 Constants.mulDivExpEff++;
						  }
				}
			}
		}
	}
	
	public void enterSelectionStatement(SelectionStatementContext ctx){
		SelecStmtloc=1;
		flag1++;
		Count_IF++;
	}
	public void exitSelectionStatement(SelectionStatementContext ctx){
		flag1--;SelecStmtloc=2;
		int curr=0,sw=0,Exp2=0;
		int count=ctx.getChildCount();
		if(count>6)
		if((ctx.getChild(0).getText().equals("if"))&&(ctx.getChild(5).getText().equals("else"))){
			
		if(flag1==0){
			if(Count_IF<2){
				int Exp1 = ctx.getChild(4).getText().length() - ctx.getChild(4).getText().replace(";", "").length();
				Exp2 = ctx.getChild(6).getText().length() - ctx.getChild(6).getText().replace(";", "").length();
				
				if((ctx.getChild(5).getText().equals("else"))&&(Exp1==1)&&(Exp2==1)){
					//System.out.println("can be replaced with ternary operator");
					Constants.ifToTernNotEff++;
				}
				else{
					//System.out.println("need not be optimised");
					Constants.ifEff++;
				}
				check_for_switch=-1;
			}
			else{
				curr=list_Selec_Stmt.indexOf("==");
				prevStr=list_Selec_Stmt.get(curr-1);
				list_Selec_Stmt.remove(curr);
				curr=list_Selec_Stmt.indexOf("==");
				while(curr!=-1){
					if(prevStr.equals(list_Selec_Stmt.get(curr-1)))
						sw=1;
					else{
						sw=-1;
						break;
					}
					list_Selec_Stmt.remove(curr);
					curr=list_Selec_Stmt.indexOf("==");
				}
				if(sw==1)
					Constants.ifToSwitchNotEff++;
				if(sw==-1)
					Constants.ifEff++;
			}
			Count_IF=0;
			list_Selec_Stmt.clear();
		}
	  }
	}
	public void enterFunctionDefinition(FunctionDefinitionContext ctx){
		Func_defin++;
	}
	public void exitFunctionDefinition(FunctionDefinitionContext ctx){
		int curr,j;
		Func_defin--;
		list_Func_Name.add("strlen");
		list_Func_Defin.clear();
		int size_of_decl=list_Var_Decl.size(),ForCondState=0;
		boolean search;
		//System.out.println(list_Var_Decl);
		for(j=0;j<size_of_decl;j++){
			search=list_Var_Used.contains(list_Var_Decl.get(j));
			if(!search)
				Constants.varDeclNotUsedEff++;
				//System.out.println("var "+list_Var_Decl.get(j)+" declared but not used");
			else
				Constants.varDeclUsedEff++;
				
		}
		boolean searchInUsedVar,searchInDeclVar;
		size_of_decl=list_Global_Var_Decl.size();
		for(j=0;j<size_of_decl;j++){
			searchInUsedVar=list_Var_Used.contains(list_Global_Var_Decl.get(j));
			searchInDeclVar=list_Var_Decl.contains(list_Global_Var_Decl.get(j));
			if((searchInUsedVar)&&(!searchInDeclVar))
				Count_Global_Var_Used.set(j,((int)Count_Global_Var_Used.get(j)+1));
		}
		list_Var_Decl.clear();
		list_Var_Used.clear();
		count=0;
	}
	public void enterDeclaration(DeclarationContext ctx){
		var_Decl++;
		String pointer_Var="";
		int count=ctx.getChildCount();
		int index=0;
		if(count>2){
			if((ctx.getChild(0).getText().equals("free"))||(ctx.getChild(0).getText().equals("fclose"))){
				pointer_Var=ctx.getChild(1).getText().substring(1, ctx.getChild(1).getText().length()-1);
				if(list_Pointer_Var_Decl.contains(pointer_Var)){
					index=list_Pointer_Var_Decl.indexOf(pointer_Var);
					list_Pointer_Var_usage.set(index, (int)list_Pointer_Var_usage.get(index)-1);
				}
			}
		}
	}
	public void exitDeclaration(DeclarationContext ctx){
		var_Decl--;
	}
	
	public void enterMultiplicativeExpression(MultiplicativeExpressionContext ctx){
		int count= ctx.getChildCount();
		int i,intob=1;
		if(count>=3)
		for(i=0;i<count;i++){
			Object ob=ctx.getChild(i).getText();
			if(!(ob.equals("*")||(ob.equals("/"))))
			    if (ob instanceof String){
				String str=(String) ob;
				boolean isValidInteger = false;
			     try{
			         intob = Integer.parseInt(str);
			        isValidInteger = true;
			      }
			      catch (NumberFormatException ex)
			      {
			         // s is not an integer
			      }
			      if(isValidInteger)
			    	  if((Integer.bitCount(intob)==1)&&(intob>1)){
				         //System.out.println("can be written as shift exp");
			    		  Constants.mulDivExpNotEff++;
				     }else{
				    	 Constants.mulDivExpEff++;
				     }
			}
		}
	}
	

	public void enterIterationStatement(IterationStatementContext ctx){
		
		Iter_Stmt++;
		if(ctx.getChild(0).getText().equals("for")){
			for_Exp=1;while_Exp=0;do_While_Exp=0;
		}
		else if(ctx.getChild(0).getText().equals("while")){
			for_Exp=0;while_Exp=1;do_While_Exp=0;
		}
		else if(ctx.getChild(ctx.getChildCount()-5).getText().equals("while")){
			for_Exp=0;while_Exp=0;do_While_Exp=1;
		}
	}
	
	public void exitIterationStatement(IterationStatementContext ctx){
		Iter_Stmt--;
		int curr=-1,i,j=0,k=0,CondEXP=0;
		Object funcName;
		int search=-1,p,ForCondState=0;
		if(do_While_Exp==1){
			Temp_List.remove(0);
			int index=Temp_List.indexOf("while");
			int size=Temp_List.size();
			Temp_List.remove(size-1);
			
			for(i=index+1,j=index;i<size;i++){
				list_of_Tokens.add(Temp_List.get(j));
				Temp_List.remove(j);
			}
			size=Temp_List.size();
			for(i=0;i<size;i++){
				list_of_Tokens.add(Temp_List.get(i));
			}
		}
		if(for_Exp==1){
			curr=list_of_Tokens.indexOf(";");
			curr+=3;
			funcName=list_of_Tokens.get(curr);
			search=list_Func_Name.indexOf(funcName);
		}
		else{
			funcName=list_of_Tokens.get(4);
			search=list_Func_Name.indexOf(funcName);
			curr=search;
		}
		if(search>=0){
			CondEXP=-1;
			j=list_of_Tokens.indexOf(")");
			for(i=curr+2;i<j;i++){
				//System.out.println(list_of_Tokens.get(i));
				search=list_Var_Decl.indexOf(list_of_Tokens.get(i));
				if(search>=0)
					ToCheckVar.add(list_of_Tokens.get(i));
				else{
					search=list_Global_Var_Decl.indexOf(list_of_Tokens.get(i));
					if(search>=0)
						ToCheckVar.add(list_of_Tokens.get(i));
				}
				}
			//System.out.println(list_Var_Decl);
			//System.out.println(ToCheckVar);
			//System.out.println(LHSOfAssign);
			}
		else{
			CondEXP=2;
			if(for_Exp==1){
				j=list_of_Tokens.indexOf(";");
				list_of_Tokens.remove(j);
				k=list_of_Tokens.indexOf(";");
			}else if(while_Exp==1){
				j=1;
				k=list_of_Tokens.indexOf(")");
			}
			else if(do_While_Exp==1){
				j=2;
				k=list_of_Tokens.indexOf(")");
			}
			if(k-j>1)
			for(i=j;i<k;i++){
				search=list_Var_Decl.indexOf(list_of_Tokens.get(i));
				if(search>=0)
					ToCheckVar.add(list_of_Tokens.get(i));
				else{
					search=list_Global_Var_Decl.indexOf(list_of_Tokens.get(i));
					if(search>=0)
						ToCheckVar.add(list_of_Tokens.get(i));
				}
				}
			if(ToCheckVar.size()!=1)
				ToCheckVar.remove(0);
			//System.out.println(list_Var_Decl);
			//System.out.println(ToCheckVar);
			//System.out.println(LHSOfAssign);
	
		}
		int size_Of_ToCheck=ToCheckVar.size();
		int size_of_LHSAssign=LHSOfAssign.size();
		//if(size_Of_ToCheck>0){
		for(p=0;p<size_Of_ToCheck;p++){
			
			if((CondEXP==2)&&(size_Of_ToCheck<2)){
				ForCondState=1;
				CondEXP=1;
				break;
			}
			
			search = LHSOfAssign.indexOf(ToCheckVar.get(p));
			
			while((search>-1)&&(search+1<size_of_LHSAssign)){
				Object Exp=LHSOfAssign.get(search+1);
				if((Exp.equals("="))||(Exp.equals("+="))||(Exp.equals("-="))||(Exp.equals("*="))||(Exp.equals("/="))||(Exp.equals("%="))||(Exp.equals("<<="))||(Exp.equals(">>="))||(Exp.equals("&="))||(Exp.equals("^="))||(Exp.equals("|="))){
					ForCondState=1;
					break;
				}
				LHSOfAssign.remove(search);
				size_of_LHSAssign--;
				size_Of_ToCheck=ToCheckVar.size();
				search = LHSOfAssign.indexOf(ToCheckVar.get(p));
			}
			
		}
		//}
		if(ForCondState==1){
			//System.out.println("loop eff");
			Constants.loopsEff++;
		}
		else {
			//System.out.println("loop ineff");
			Constants.loopsNotEff++;
		}
		LHSOfAssign.clear();
		ToCheckVar.clear();
		list_of_Tokens.clear();
		Temp_List.clear();
		if(for_Exp==1)
			for_Exp=0;
		else if(while_Exp==1)
			while_Exp=0;
		else
			do_While_Exp=0;
	}
	
	public void enterParameterList(ParameterListContext ctx){
		if(Func_defin>0){
			ParaList++;
		}
	}
	public void exitParameterList(ParameterListContext ctx){
		ParaList--;
	}
	public void enterDeclarator(DeclaratorContext ctx){
		if(Func_defin>0){
			if(ParaList>0){
				Decl++;
				int count=ctx.getChildCount();
				for(int i=0;i<count;i++){
						if(!ctx.getChild(i).getText().equals("*")){
							list_Var_Decl.add(ctx.getChild(i).getText());
							Var_Used.add(ctx.getChild(i).getText());
						}
				}
			}
		}
	}
	public void exitDeclarator(DeclaratorContext ctx){
		Decl--;
	}
	public void enterCompilationUnit(CompilationUnitContext ctx){
		list_Func_Name.add("strlen");
	}
	public void exitCompilationUnit(CompilationUnitContext ctx){
		//System.out.println(list_Pointer_Var_Decl);
		//System.out.println(Var_Used);
		//System.out.println(Var_Data_Type);
		int size=list_Pointer_Var_usage.size();
		int count=Count_Global_Var_Used.size();
		int i=0;
		for(i=0;i<size;i++){
			if((int)list_Pointer_Var_usage.get(i)==0){
				Constants.pointerUsageEff++;
			}else{
				Constants.pointerUsageNotEff++;
			}
		}
		for(i=0;i<count;i++){
			if((int)Count_Global_Var_Used.get(i)>1){
				Constants.scopeOfVarEff++;
			}else{
				Constants.scopeOfVarNotEff++;
			}
		}
		//System.out.println(list_Global_Var_Decl);
	}
	
	public void visitTerminal(TerminalNode node)
	{ 
		if(SelecStmtloc==1){
			list_Selec_Stmt.add(node.getText());
		}else if(SelecStmtloc==2){
			list_Selec_Stmt.clear();
		}
		
		if(Func_defin>0){
			list_Func_Defin.add(node.getText());
			if((node.getText().equals("("))&&(count==0)){
				if(!list_Func_Name.contains(obj))
					list_Func_Name.add(obj);
				count++;
			}
			obj = node.getText();
		}
		
		if(Iter_Stmt>0){
			if(do_While_Exp==1){
				Temp_List.add(node.getText());
			}
			else{
				list_of_Tokens.add(node.getText());
			}
		}
		if(var_Decl>0){
			if(Func_defin>0){
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
				if(ob.equals("int")||ob.equals("float")||ob.equals("char")||ob.equals("double")){
					dataType=ob;
				}
				if(!(isValidInteger||ob.equals(",")||ob.equals(";")||ob.equals("int")||ob.equals("float")||ob.equals("double")||ob.equals("char")||ob.equals("[")||ob.equals("]")||ob.equals("=")||ob.equals("File")||ob.equals("free")||ob.equals("fclose")||ob.equals("(")||ob.equals(")"))){
					if(objvar.equals("*")){
						list_Pointer_Var_Decl.add(ob);
						list_Pointer_Var_usage.add(0);
					}
					else{
						list_Var_Decl.add(ob);
						if((!ob.equals("*"))){
							Var_Used.add(ob);
							if(dataType.equals("char")){
								Var_Data_Type.add(1);
							}
							else if(dataType.equals("int")){
								Var_Data_Type.add(2);
							}
							else if(dataType.equals("float")){
								Var_Data_Type.add(3);
							}
							else if(dataType.equals("double")){
								Var_Data_Type.add(4);
							}
						}	
					}
				}
				objvar = node.getText();
				
			}else{
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
				if(!(isValidInteger||ob.equals(",")||ob.equals(";")||ob.equals("int")||ob.equals("float")||ob.equals("double")||ob.equals("char")||ob.equals("[")||ob.equals("]")||ob.equals("=")||ob.equals("File")||ob.equals("free")||ob.equals("fclose")||ob.equals("(")||ob.equals(")"))){
					if(objvar.equals("*")){
						list_Pointer_Var_Decl.add(ob);
						list_Pointer_Var_usage.add(0);
					}
					else{
						list_Global_Var_Decl.add(ob);
						if((!ob.equals("*"))){
							Var_Used.add(ob);
							if(dataType.equals("char")){
								Var_Data_Type.add(1);
							}
							else if(dataType.equals("int")){
								Var_Data_Type.add(2);
							}
							else if(dataType.equals("float")){
								Var_Data_Type.add(3);
							}
							else if(dataType.equals("double")){
								Var_Data_Type.add(4);
							}
							else{
								Var_Data_Type.add(0);
							}
						}	
						Count_Global_Var_Used.add(0);
					}
					objvar = node.getText();
				}
			}
		}
		else{
			Object ob1=node.getText();
			if(!(ob1.equals(",")||ob1.equals(";")))	{
				list_Var_Used.add(ob1);
			}
		}
	}
}

