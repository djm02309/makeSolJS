package analyzeVer5;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.StringTokenizer;

//////수정필요
public class SolidityPrintVisitor5 extends SolidityBaseVisitor<String>{
    ArrayList array = new ArrayList();  //남길거만 남겨두는 리스트

    @Override
    public String visitSourceUnit(SolidityParser.SourceUnitContext ctx) {
        for (int i = 0; i < ctx.children.size(); i++) {
            if (ctx.getChild(i).equals(ctx.EOF())) {
                break;
            }
            if (ctx.getChild(i) instanceof analyzeVer4.SolidityParser.ContractDefinitionContext) {
            }
        }
        for (int i = 0; i < ctx.children.size(); i++) {
            if (ctx.getChild(i).equals(ctx.EOF())) {
                break;
            }
            System.out.println(visit(ctx.getChild(i)));
        }
        return "";
    }

    @Override
    public String visitPragmaDirective(SolidityParser.PragmaDirectiveContext ctx) {
        String pragma = "pragma", pragmaName, pragmaValue;
        pragmaName = visit(ctx.pragmaName());
        pragmaValue = ctx.getChild(2).getText();

        return pragma + " " + pragmaName + " " + pragmaValue + ";";
    }

    @Override
    public String visitPragmaName(SolidityParser.PragmaNameContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitPragmaValue(SolidityParser.PragmaValueContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitVersion(SolidityParser.VersionContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            visit(ctx.getChild(i));
        }
        return "";
    }

    @Override
    public String visitVersionOperator(SolidityParser.VersionOperatorContext ctx) {
        return ctx.getChild(0).getText();
    }

    @Override
    public String visitVersionConstraint(SolidityParser.VersionConstraintContext ctx) {
        String versionOperator, versionLiteral;
        if (ctx.versionOperator().isEmpty()) {
            versionLiteral = ctx.VersionLiteral().getText();
            return versionLiteral;
        } else {
            versionOperator = visit(ctx.versionOperator());
            versionLiteral = ctx.VersionLiteral().getText();
            return versionOperator + versionLiteral;
        }
    }

    @Override
    public String visitImportDeclaration(SolidityParser.ImportDeclarationContext ctx) {
        if (ctx.getChildCount() == 1) {
            return visit(ctx.identifier(0));
        } else {
            String identify1 = visit(ctx.identifier(0));
            String identify2 = visit(ctx.identifier(1));
            return identify1 + " as " + identify2;
        }
    }

    @Override
    public String visitImportDirective(SolidityParser.ImportDirectiveContext ctx) {
        String importwd = "import ";

        if (ctx.getChild(0).equals(ctx.StringLiteral())) {
            if (ctx.identifier().isEmpty()) {
                return importwd + " " + ctx.StringLiteral().getText() + ";";
            } else {
                return importwd + ctx.StringLiteral().getText() + " as " + visit(ctx.identifier(0)) + ";";
            }
        } else if (ctx.getChild(1).toString().equals("{")) {
            return importwd + "{ " + ctx.importDeclaration().stream()
                    .map(t -> ", " + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.importDeclaration(0)), (acc, importDeclaration) -> acc + importDeclaration) + "}" + " from " + ctx.StringLiteral().getText() + ";";
        } else {  //import * | identifier
            String child1 = ctx.getChild(1).getText();
            if (ctx.identifier(1) == null) {
                return importwd + child1 + " from " + ctx.StringLiteral().getText() + ";";
            } else {
                return importwd + child1 + " as " + visit(ctx.identifier(0)) + " from " + ctx.StringLiteral().getText() + ";";
            }
        }
    }

    @Override
    public String visitNatSpec(SolidityParser.NatSpecContext ctx) {
        return ctx.getChild(0).getText();
    }

    @Override
    public String visitContractDefinition(SolidityParser.ContractDefinitionContext ctx) {
        if (ctx.getChild(0).getText().equals("contract")) {  //이거 일때만 하게
            array.add(visit(ctx.identifier())); //contract 이름
        }
        if (!ctx.getChild(2).toString().equals("is")) {
            return ctx.getChild(0).getText() + " " + visit(ctx.identifier()) + "{\n" +
                    ctx.contractPart().stream()
                            .map(t -> visit(t) + "\n")
                            .skip(1)
                            .reduce(visit(ctx.contractPart(0)), (acc, contractPart) -> acc + contractPart) + "}";
        } else {  //is 가 있으면
            String is = ctx.inheritanceSpecifier().stream().map(t -> ", " + visit(t)).skip(1).reduce(visit(ctx.inheritanceSpecifier(0)), (acc, inheritanceSpecifier) -> acc + inheritanceSpecifier);
            StringTokenizer st = new StringTokenizer(is, ",");
            while (st.hasMoreTokens()) {
                for (int i = 0; i < array.size(); i++) {
                    if (st.nextToken().equals(array.get(i))) {
                        array.remove(i);
                    }
                } //interitance랑 관련된거만 남기고 그외는 다 지우기 ? 키워드 넣어줘야함
            }
            return ctx.getChild(0).getText() + " " + visit(ctx.identifier()) + " is " +
                    ctx.inheritanceSpecifier().stream()
                            .map(t -> ", " + visit(t))
                            .skip(1)
                            .reduce(visit(ctx.inheritanceSpecifier(0)), (acc, inheritanceSpecifier) -> acc + inheritanceSpecifier) + "{\n" +
                    ctx.contractPart().stream()
                            .map(t -> visit(t) + "\n")
                            .skip(1)
                            .reduce(visit(ctx.contractPart(0)), (acc, contractPart) -> acc + contractPart) + "}";
        }
    }

    @Override
    public String visitInheritanceSpecifier(SolidityParser.InheritanceSpecifierContext ctx) {
        if (ctx.getChildCount() >= 2) {
            return visit(ctx.userDefinedTypeName()) + "( " + visit(ctx.expressionList()) + ")";
        } else {
            return visit(ctx.userDefinedTypeName());
        }
    }

    @Override
    public String visitContractPart(SolidityParser.ContractPartContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitStateVariableDeclaration(SolidityParser.StateVariableDeclarationContext ctx) {
        String typeName = visit(ctx.getChild(0));
        String keywords = "";
        int i;
        for (i = 1; i < ctx.getChildCount() && ctx.getChild(i) instanceof TerminalNode; i++) {
            keywords += ctx.getChild(i).getText();
        }
        if (keywords.equals("") == false) {
            keywords += " ";
        }
        String identifier = visit(ctx.identifier());
        if (ctx.getChild(i + 1).getText().equals("=")) {
            return typeName + " " + keywords + identifier + " = " + visit(ctx.expression()) + ";\n";
        } else {
            return typeName + " " + keywords + identifier + ";";
        }
    }

    @Override
    public String visitUsingForDeclaration(SolidityParser.UsingForDeclarationContext ctx) {
        if (ctx.getChild(3).toString().equals("*")) {
            return "using " + visit(ctx.identifier()) + " for *;";
        } else {
            return "using " + visit(ctx.identifier()) + " for " + visit(ctx.getChild(3)) + ";";
        }
    }

    @Override
    public String visitStructDefinition(SolidityParser.StructDefinitionContext ctx) {
        if (ctx.getChildCount() == 4) {
            return "struct " + visit(ctx.identifier()) + " {}";
        } else {
            return "struct " + visit(ctx.identifier()) + " {\n" +//visit(ctx.variableDeclaration(0))+";\n"+
                    ctx.variableDeclaration().stream()
                            .map(t -> visit(t) + ";\n")
//					  .skip(1)
                            .reduce("", (acc, variableDeclaration) -> variableDeclaration) + "}";
        }
    }

    @Override public String visitConstructorDefinition(SolidityParser.ConstructorDefinitionContext ctx) {
        return "constructor " + visit(ctx.parameterList()) + visit(ctx.modifierList()) + visit(ctx.block());
    }

    @Override public String visitModifierDefinition(SolidityParser.ModifierDefinitionContext ctx) { if(ctx.getChildCount() == 3) {
        return "modifier "+visit(ctx.identifier())+visit(ctx.block());
    }
    else {
        return "modifier "+visit(ctx.identifier())+visit(ctx.parameterList())+visit(ctx.block());
    } }

    @Override public String visitModifierInvocation(SolidityParser.ModifierInvocationContext ctx) { if(ctx.getChildCount() == 1) {
        return visit(ctx.identifier());
    }
    else {
        return visit(ctx.identifier())+"( "+ visit(ctx.expressionList())+" )";
    }}

    @Override
    public String visitFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        if (ctx.getChildCount() == 4) { //Fallback함수임
            return "function " + visit(ctx.parameterList()) + visit(ctx.modifierList()) + visit(ctx.getChild(3));
        } else if (ctx.getChildCount() == 5) {
            if (ctx.getChild(1).equals(ctx.identifier())) {
                visit(ctx.getChild(4));
                return "function " + visit(ctx.identifier()) + visit(ctx.parameterList()) + visit(ctx.modifierList()) + visit(ctx.getChild(4));

            } else {
                return "function " + visit(ctx.parameterList()) + visit(ctx.modifierList()) + " " + visit(ctx.returnParameters()) + visit(ctx.getChild(4));
            }
        } else {
            return "function " + visit(ctx.identifier()) + visit(ctx.parameterList()) + visit(ctx.modifierList()) + visit(ctx.returnParameters()) + visit(ctx.getChild(5));
        }
    }

    @Override
    public String visitReturnParameters(SolidityParser.ReturnParametersContext ctx) {
        return "returns " + visit(ctx.parameterList());
    }

    @Override
    public String visitModifierList(SolidityParser.ModifierListContext ctx) {
        String result = "";
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof TerminalNode) {
                result += ctx.getChild(i).getText() + " ";
            } else {
                result += visit(ctx.getChild(i)) + " ";
            }
        }
        return result;
    }

    @Override
    public String visitEventDefinition(SolidityParser.EventDefinitionContext ctx) {
        if (ctx.getChildCount() == 4) { //natSpec? 'event' identifier eventParameterList
            if(ctx.getChild(0) instanceof SolidityParser.NatSpecContext){
                return visit(ctx.natSpec()) + " event " + visit(ctx.identifier()) + visit(ctx.eventParameterList())+ ";" ;
            }
            else{ // 'event' identifier eventParameterList AnonymousKeyword? ';'
                return "event " + visit(ctx.identifier()) + visit(ctx.eventParameterList()) + ctx.getChild(3).getText() + ";";
            }
        }
        else if (ctx.getChildCount() == 5) {  //natSpec 'event' identifier eventParameterList AnonymousKeyword ';' ;
            return visit(ctx.natSpec()) +" event " + visit(ctx.identifier()) + visit(ctx.eventParameterList())+ ";";
        }
        else {  // 'event' identifier eventParameterList
            return "event " + visit(ctx.identifier()) + visit(ctx.eventParameterList()) + ";";
        }
    }

    @Override
    public String visitEnumValue(SolidityParser.EnumValueContext ctx) {
        return visit(ctx.identifier());
    }

    @Override
    public String visitEnumDefinition(SolidityParser.EnumDefinitionContext ctx) {
        if (ctx.getChildCount() == 4) {
            return "enum " + visit(ctx.identifier()) + "{ }";
        } else {
            String resultEnum = "enum " + visit(ctx.identifier()) + "{";
            resultEnum += ctx.enumValue().stream()
                    .map(t -> ", " + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.enumValue(0)), (acc, enumValue) -> acc + enumValue) + " )";
            return resultEnum;
        }
    }

    @Override
    public String visitParameterList(SolidityParser.ParameterListContext ctx) {
        if (ctx.getChildCount() == 2) {
            return "()";
        } else {
            return "( " + ctx.parameter().stream()
                    .map(t -> ", " + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.parameter(0)), (acc, parameter) -> acc + parameter) + " )";
        }
    }

    @Override
    public String visitParameter(SolidityParser.ParameterContext ctx) {
        if (ctx.getChildCount() == 1) {
            return visit(ctx.typeName());
        } else if (ctx.getChildCount() == 2) {
            return visit(ctx.typeName()) + " " + visit(ctx.getChild(1));
        } else {
            return visit(ctx.typeName()) + " " + visit(ctx.storageLocation()) + " " + visit(ctx.identifier());
        }
    }

    @Override
    public String visitEventParameterList(SolidityParser.EventParameterListContext ctx) {
        if (ctx.getChildCount() == 2) {
            return "()";
        } else {
            return "( " + ctx.eventParameter().stream()
                    .map(t -> "," + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.eventParameter(0)), (acc, eventParameter) -> acc + eventParameter) + " )";
        }
    }

    @Override
    public String visitEventParameter(SolidityParser.EventParameterContext ctx) {
        if(ctx.getChildCount() == 3){ //typeName IndexedKeyword? identifier? ;
            return visit(ctx.typeName()) + " " + ctx.getChild(1).getText() + " " +visit(ctx.identifier());
        }
        else if(ctx.getChildCount() == 2){
            if(ctx.getChild(1) instanceof SolidityParser.IdentifierContext){ //typeName identifier
                return visit(ctx.typeName()) + " " +visit(ctx.identifier());
            }
            else{ //typeName IndexedKeyword
                return visit(ctx.typeName()) + " " + ctx.getChild(1).getText();
            }
        }
        else{ //typeName
            return visit(ctx.typeName());
        }
    }

    @Override
    public String visitFunctionTypeParameterList(SolidityParser.FunctionTypeParameterListContext ctx) {
        if (ctx.getChildCount() == 2) {
            return "()";
        } else {
            return "( " + ctx.functionTypeParameter().stream()
                    .map(t -> "," + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.functionTypeParameter(0)), (acc, functionTypeParameter) -> acc + functionTypeParameter) + " )";
        }
    }

    @Override
    public String visitFunctionTypeParameter(SolidityParser.FunctionTypeParameterContext ctx) {
        if(ctx.getChildCount() ==2 ){ //typeName storageLocation?
            return visit(ctx.typeName())+ visit(ctx.storageLocation());
        }
        else{
            return visit(ctx.typeName());
        }
    }

    @Override
    public String visitVariableDeclaration(SolidityParser.VariableDeclarationContext ctx) {
        if (ctx.getChildCount() == 2) {
            return visit(ctx.typeName()) + " " + visit(ctx.identifier());
        } else {
            return visit(ctx.typeName()) + " " + visit(ctx.storageLocation()) + " " + visit(ctx.identifier());
        }
    }

    @Override
    public String visitTypeName(SolidityParser.TypeNameContext ctx) {
        if (ctx.getChildCount() == 1) {
            return visit(ctx.getChild(0));
        } else {
            if (ctx.getChildCount() == 3) {
                return visit(ctx.typeName()) + "[]";
            } else {
                return visit(ctx.typeName()) + "[" + visit(ctx.expression()) + "]";
            }
        }
    }

    @Override
    public String visitUserDefinedTypeName(SolidityParser.UserDefinedTypeNameContext ctx) {
        return ctx.identifier().stream()
                .map(t -> "." + visit(t))
                .skip(1)
                .reduce(visit(ctx.identifier(0)), (acc, identifier) -> acc + identifier);
    }

    @Override
    public String visitMapping(SolidityParser.MappingContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitFunctionTypeName(SolidityParser.FunctionTypeNameContext ctx) {
        String result = "function " + visit(ctx.typeNameList(0));

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i).toString().contains("returns")) {
                result += visit(ctx.typeNameList(1));
                return result;
            } else {
                if (ctx.getChild(i) instanceof TerminalNode) {
                    result += ctx.getChild(i).getText() + " ";
                } else {
                    result += visit(ctx.getChild(i)) + " ";
                }
            }
        }
        return result;
    }

    @Override
    public String visitStorageLocation(SolidityParser.StorageLocationContext ctx) {
        return ctx.getChild(0).getText();
    }

    @Override
    public String visitStateMutability(SolidityParser.StateMutabilityContext ctx) {
        return ctx.getChild(0).getText();
    }

    @Override
    public String visitBlock(SolidityParser.BlockContext ctx) {
        return "{\n" + ctx.statement().stream()
                .map(t -> visit(t))
                .reduce("", (acc, statement) -> acc + statement) + "\n}";
    }

    @Override
    public String visitStatement(SolidityParser.StatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitExpressionStatement(SolidityParser.ExpressionStatementContext ctx) {
        return visit(ctx.expression()) + ";\n";
    }

    @Override
    public String visitIfStatement(SolidityParser.IfStatementContext ctx) {
        if (ctx.getChildCount() < 6) {
            return "if (" + visit(ctx.expression()) + ")" + visit(ctx.statement(0));
        } else {
            return "if (" + visit(ctx.expression()) + ") " + visit(ctx.statement(0)) + "else " + visit(ctx.statement(1));
        }
    }

    @Override
    public String visitWhileStatement(SolidityParser.WhileStatementContext ctx) {
        return "while( " + visit(ctx.expression()) + " )" + visit(ctx.statement());
    }

    @Override
    public String visitSimpleStatement(SolidityParser.SimpleStatementContext ctx) {
        return visitChildren(ctx) + "\n";
    }

    @Override
    public String visitForStatement(SolidityParser.ForStatementContext ctx) {
        /////새로작성하기
        if (ctx.getChildCount() == 6) {    //expression이 둘다 없을때
            return "for (" + visit(ctx.getChild(2)) + "; )" + visit(ctx.statement());
        } else if (ctx.getChildCount() == 7) {  //expression이 둘중 하나만 있을때
            if (ctx.getChild(4).toString().equals(";")) {
                return "for (" + visit(ctx.getChild(2)) + "; " + visit(ctx.simpleStatement()) + " ) " + visit(ctx.statement());
            } else {
                return "for (" + visit(ctx.getChild(2)) + visit(ctx.simpleStatement(0)) + "; " + ") " + visit(ctx.statement());
            }

        } else {    //expression이 둘다 있을때
            return "for (" + visit(ctx.getChild(2)) + visit(ctx.expression(0)) + "; " + visit(ctx.expression(1)) + ")" + visit(ctx.statement());
        }
    }

    @Override
    public String visitInlineAssemblyStatement(SolidityParser.InlineAssemblyStatementContext ctx) {
        if (ctx.getChildCount() == 2) {
            return "assembly " + visit(ctx.assemblyBlock());
        } else {
            return "assembly " + ctx.StringLiteral().getText() + visit(ctx.assemblyBlock());
        }
    }

    @Override
    public String visitDoWhileStatement(SolidityParser.DoWhileStatementContext ctx) {
        return "do " + visit(ctx.statement()) + "while ( " + visit(ctx.expression()) + ");";
    }

    @Override
    public String visitContinueStatement(SolidityParser.ContinueStatementContext ctx) {
        return "continue;";
    }

    @Override
    public String visitBreakStatement(SolidityParser.BreakStatementContext ctx) {
        return "break;";
    }

    @Override
    public String visitReturnStatement(SolidityParser.ReturnStatementContext ctx) {
        if (ctx.getChildCount() == 2) {
            return "return;";
        } else {
            return "return " + visit(ctx.expression()) + ";";
        }
    }

    @Override
    public String visitThrowStatement(SolidityParser.ThrowStatementContext ctx) {
        return "throw;";
    }

    @Override
    public String visitEmitStatement(SolidityParser.EmitStatementContext ctx) {
       return "emit " + visit(ctx.functionCall()) +";";
    }

    @Override
    public String visitVariableDeclarationStatement(SolidityParser.VariableDeclarationStatementContext ctx) {
    }

    @Override
    public String visitVariableDeclarationList(SolidityParser.VariableDeclarationListContext ctx) {
    }

    @Override
    public String visitIdentifierList(SolidityParser.IdentifierListContext ctx) {
        if (ctx.getChildCount() == 2) {
            return "( )";
        } else {
            return "(" + ctx.identifier().stream()
                    .map(t -> visit(t) + ", ")
                    .reduce("", (acc, identifier) -> acc + identifier) + " )";
        }
    }

    @Override
    public String visitElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) {
        if (ctx.getChild(0) instanceof TerminalNode) {
            return ctx.getChild(0).getText();
        } else {
            return ctx.getText();
        }
    }

    @Override
    public String visitExpression(SolidityParser.ExpressionContext ctx) {
        if (ctx.expression().size() == 2) {
            if (ctx.getChild(1).toString().equals("[")) { //expression[expression]
                return visit(ctx.expression(0)) + "[ " + visit(ctx.expression(1)) + " ]";
            } else {  //expression 연산자 expression
                return visit(ctx.expression(0)) + " " + ctx.getChild(1).getText() + " " + visit(ctx.expression(1));
            }
        } else if (ctx.expression().size() == 1) {
            if (ctx.getChild(0).equals(ctx.fallbackExpression()) || ctx.getChild(0).equals(ctx.forInitExpreesion())) {
                return ctx.getChild(0).getText();
            } else if (ctx.getChild(0) instanceof analyzeVer4.SolidityParser.ExpressionContext) { //expression ++|-- expression (fucn)
                if (ctx.getChild(1).toString().equals("(")) { //expression( functionCallArguments)
                    String result = "";
                    result = visit(ctx.functionCallArguments());
                    return visit(ctx.expression(0)) + "( " + visit(ctx.functionCallArguments()) + " )";
                } else if (ctx.getChild(1).toString().equals(".")) { //expression . identifier
                    return visit(ctx.expression(0)) + "." + visit(ctx.identifier());
                } else { //expression ++|--
                    return visit(ctx.expression(0)) + ctx.getChild(1).getText();
                }
            } else { //1번째가 expression 이 아닌경우
                if (ctx.getChild(0).toString().equals("(")) {//(expression)
                    return "( " + visit(ctx.expression(0)) + " )";
                } else {//++expression 등
                    return ctx.getChild(0).getText() + " " + visit(ctx.expression(0));
                }
            }
        } else {  //삼항연산자 또는 new 또는 primaryExpression
            if (ctx.expression().size() == 3) {
                return visit(ctx.expression(0)) + " ? " + visit(ctx.expression(1)) + " : " + visit(ctx.expression(2));
            } else if (ctx.getChild(0) instanceof analyzeVer4.SolidityParser.PrimaryExpressionContext) {
                return visit(ctx.primaryExpression());
            } else { //new typename
                return "new " + visit(ctx.typeName());
            }
        }
    }

    @Override
    public String visitPrimaryExpression(SolidityParser.PrimaryExpressionContext ctx) {
        if (ctx.getChild(0) instanceof TerminalNode) {
            return ctx.getChild(0).getText();
        } else {
            return visit(ctx.getChild(0));
        }
    }

    @Override
    public String visitExpressionList(SolidityParser.ExpressionListContext ctx) {
        return ctx.expression().stream()
                .map(t -> ", " + visit(t))
                .skip(1)
                .reduce(visit(ctx.expression(0)), (acc, expression) -> acc + expression);
    }

    @Override
    public String visitNameValueList(SolidityParser.NameValueListContext ctx) {
        int lastitemIndex = ctx.getChildCount() - 1;
        if (ctx.getChild(lastitemIndex).toString().equals(",")) {
            return ctx.nameValue().stream().map(t -> ", " + visit(t)).skip(1).reduce(visit(ctx.nameValue(0)),
                    (acc, nameValue) -> acc + nameValue) + ",";
        } else {
            return ctx.nameValue().stream().map(t -> ", " + visit(t)).skip(1).reduce(visit(ctx.nameValue(0)),
                    (acc, nameValue) -> acc + nameValue);
        }
    }

    @Override
    public String visitNameValue(SolidityParser.NameValueContext ctx) {
        return visit(ctx.identifier()) + " : " + visit(ctx.expression());
    }

    @Override
    public String visitFunctionCallArguments(SolidityParser.FunctionCallArgumentsContext ctx) {
        if (ctx.getChildCount() >= 2) {
            if (ctx.nameValueList() != null) {
                return "{" + visit(ctx.nameValueList()) + "}";
            } else {
                return "{ }";
            }
        } else {
            if (ctx.expressionList() != null) {
                return visit(ctx.expressionList());
            } else {
                return "";
            }
        }
    }

    @Override
    public String visitFunctionCall(SolidityParser.FunctionCallContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitAssemblyBlock(SolidityParser.AssemblyBlockContext ctx) {
        return "{\n " + ctx.assemblyItem().stream()
                .map(t -> visit(t) + "\n")
                .reduce("", (acc, assemblyItem) -> acc + assemblyItem) + " \n}";
    }

    @Override
    public String visitAssemblyItem(SolidityParser.AssemblyItemContext ctx) {
        String result = "";
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof TerminalNode) {
                result += ctx.getText(); ////////////////////////// 오류가능성있음/////////////////////////////
            } else {
                result += visit(ctx.getChild(i));
            }
        }
        return result;
    }

    @Override
    public String visitAssemblyExpression(SolidityParser.AssemblyExpressionContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitAssemblyCall(SolidityParser.AssemblyCallContext ctx) {
        String firstChild = "";
        if (ctx.getChildCount() == 1) {
            if (ctx.getChild(0) instanceof analyzeVer4.SolidityParser.IdentifierContext) {
                firstChild = visit(ctx.identifier());
            } else {
                firstChild = ctx.getChild(0).getText();
            }
            return firstChild;
        } else {
            return firstChild + "( "
                    + ctx.assemblyExpression().stream()
                    .map(t -> ", " + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.assemblyExpression(0)), (acc, assemblyExpression) -> acc + assemblyExpression)   //맞는지 모르겠음!!!!!!
                    + " )"; //
        }
    }

    @Override
    public String visitAssemblyLocalDefinition(SolidityParser.AssemblyLocalDefinitionContext ctx) {
        if (ctx.getChildCount() == 2) {
            return "let" + visit(ctx.assemblyIdentifierOrList());
        } else {
            return "let " + visit(ctx.assemblyIdentifierOrList()) + " := " + visit(ctx.assemblyExpression());
        }
    }

    @Override
    public String visitAssemblyAssignment(SolidityParser.AssemblyAssignmentContext ctx) {
        return visit(ctx.assemblyIdentifierOrList()) + " := " + visit(ctx.assemblyExpression());
    }

    @Override
    public String visitAssemblyIdentifierOrList(SolidityParser.AssemblyIdentifierOrListContext ctx) {
        if (ctx.getChildCount() == 1) {
            return visit(ctx.identifier());
        } else {
            return "( " + visit(ctx.assemblyIdentifierList()) + " )";
        }
    }

    @Override
    public String visitAssemblyIdentifierList(SolidityParser.AssemblyIdentifierListContext ctx) {
        return ctx.identifier().stream()
                .map(t -> ", " + visit(t))
                .skip(1)
                .reduce(visit(ctx.identifier(0)), (acc, identifier) -> acc + identifier);
    }

    @Override
    public String visitAssemblyStackAssignment(SolidityParser.AssemblyStackAssignmentContext ctx) {
        return " =: " + visitChildren(ctx);
    }

    @Override
    public String visitLabelDefinition(SolidityParser.LabelDefinitionContext ctx) {
        return visit(ctx.identifier()) + ":";
    }

    @Override
    public String visitAssemblySwitch(SolidityParser.AssemblySwitchContext ctx) {
        String assem = null;
        if (ctx.assemblyCase() == null) {
            return "switch " + visit(ctx.assemblyExpression());
        } else {
            for (int i = 0; i < ctx.assemblyCase().size(); i++) {
                assem += visit(ctx.assemblyCase(i));
            }
            return "switch " + visit(ctx.assemblyExpression()) + assem;
        }
    }

    @Override public String visitAssemblyCase(SolidityParser.AssemblyCaseContext ctx) {
        if(ctx.getChild(0).toString().equals("case")) {
            return "case "+ visit(ctx.assemblyLiteral())+ visit(ctx.assemblyBlock());
        }
        else {
            return "default "+visit(ctx.assemblyBlock());
        } }

    @Override public String visitAssemblyFunctionDefinition(SolidityParser.AssemblyFunctionDefinitionContext ctx) { if(ctx.getChildCount() == 5) {
        return "function "+ visit(ctx.identifier())+" ( ) "+visit(ctx.assemblyBlock());
    }
    else if(ctx.getChildCount() == 6) {
        if(ctx.getChild(3).equals(ctx.assemblyIdentifierList())) {
            return "function "+ visit(ctx.identifier())+" ( "+visit(ctx.assemblyIdentifierList())+" ) "+visit(ctx.assemblyBlock());
        }
        else {
            return "function "+ visit(ctx.identifier())+" ( ) "+visit(ctx.assemblyFunctionReturns())+visit(ctx.assemblyBlock());
        }
    }
    else {
        return "function "+ visit(ctx.identifier())+" ( "+visit(ctx.assemblyIdentifierList())+" ) "+visit(ctx.assemblyFunctionReturns())+visit(ctx.assemblyBlock());
    } }

    @Override
    public String visitAssemblyFunctionReturns(SolidityParser.AssemblyFunctionReturnsContext ctx) {
        return "-> " + visit(ctx.assemblyIdentifierList());
    }

    @Override
    public String visitAssemblyFor(SolidityParser.AssemblyForContext ctx) {
        String result = "for ";
        for (int i = 1; i < ctx.getChildCount(); i++) {
            result += visit(ctx.getChild(i));
        }
        return result;
    }

    @Override
    public String visitAssemblyIf(SolidityParser.AssemblyIfContext ctx) {
        return "if " + visit(ctx.assemblyExpression()) + visit(ctx.assemblyBlock());
    }

    @Override
    public String visitAssemblyLiteral(SolidityParser.AssemblyLiteralContext ctx) {
        return ctx.getChild(0).getText();
    }

    @Override
    public String visitSubAssembly(SolidityParser.SubAssemblyContext ctx) {
        return "assembly " + visit(ctx.identifier()) + visit(ctx.assemblyBlock());
    }

    @Override
    public String visitTupleExpression(SolidityParser.TupleExpressionContext ctx) {
        if (ctx.getChild(0).getText().equals("(")) {
            return "(" + ctx.expression().stream()
                    .map(t -> ", " + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.expression(0)), (acc, expression) -> acc + expression) + ")";
        } else {
//			if(ctx.getChildCount() == )
            return "[" + ctx.expression().stream()
                    .map(t -> ", " + visit(t))
                    .skip(1)
                    .reduce(visit(ctx.expression(0)), (acc, expression) -> acc + expression) + "]";
        }
    }

    @Override
    public String visitTypeNameExpression(SolidityParser.TypeNameExpressionContext ctx) {
        ;
    }

    @Override
    public String visitNumberLiteral(SolidityParser.NumberLiteralContext ctx) {
        if (ctx.getChildCount() == 1) {
            return ctx.getChild(0).getText();
        } else {
            return ctx.getChild(0).getText() + ctx.NumberUnit().toString();
        }
    }

    @Override
    public String visitIdentifier(SolidityParser.IdentifierContext ctx) {
        if (ctx.getChild(0).toString().equals("from")) {
            return ctx.getChild(0).getText();
        } else {
            return ctx.Identifier().getText();
        }
    }
}
