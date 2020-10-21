
import java.util.ArrayList;

import de.minorproject.Parser.CBaseVisitor;
import de.minorproject.Parser.CParser.*;
public class MyVisitor extends CBaseVisitor <String> {
	ArrayList list_Func_Defin = new ArrayList();
	String funcDefinition;
	@Override
	public String visitAssignmentExpression(AssignmentExpressionContext ctx){		
		int count = ctx.getChildCount();
		if(count==3){
		if(ctx.getChild(2).getText().contains(ctx.getChild(0).getText())&&(ctx.getChild(1).getText().equals("="))&&(ctx.getChild(2).getText().length()==3)){
			Constants.assignNotEff++;
		}
		else{
			Constants.assignEff++;
		}
		}
		return null; 
	}
}
