package de.minorproject.compiler;
import java.util.ArrayList;

import de.minorproject.Parser.CBaseVisitor;
import de.minorproject.Parser.CParser.*;

public class MyVisitor extends CBaseVisitor <String> {
	ArrayList list_Func_Defin = new ArrayList();
	String funcDefinition;
	@Override
	//visits assignment expression and identifies any inefficiencies present
	public String visitAssignmentExpression(AssignmentExpressionContext ctx){		
		int count = ctx.getChildCount();
		//checks if length is equal to 3 and if variable on lhs of assignment expression is also present in rhs(a=a+1)
		if(count==3){
			if(ctx.getChild(2).getText().contains(ctx.getChild(0).getText())&&(ctx.getChild(1).getText().equals("="))&&(ctx.getChild(2).getText().length()==3)){
				System.out.println("Assignment not eff "+ctx.getStart().getLine());
				Constants.assignNotEff++;
			}
			else{
				Constants.assignEff++;
			}
		}
		return null; 
	}
}
