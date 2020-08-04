package analyzeVer5;
//////수정필요
public class SolidityPrintVisitor5 extends SolidityBaseVisitor<String>{
    @Override public String visitSourceUnit(SolidityParser.SourceUnitContext ctx) { return visitChildren(ctx); }

    @Override public String visitPragmaDirective(SolidityParser.PragmaDirectiveContext ctx) { return visitChildren(ctx); }

    @Override public String visitPragmaName(SolidityParser.PragmaNameContext ctx) { return visitChildren(ctx); }

    @Override public String visitPragmaValue(SolidityParser.PragmaValueContext ctx) { return visitChildren(ctx); }

    @Override public String visitVersion(SolidityParser.VersionContext ctx) { return visitChildren(ctx); }

    @Override public String visitVersionOperator(SolidityParser.VersionOperatorContext ctx) { return visitChildren(ctx); }

    @Override public String visitVersionConstraint(SolidityParser.VersionConstraintContext ctx) { return visitChildren(ctx); }

    @Override public String visitImportDeclaration(SolidityParser.ImportDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public String visitImportDirective(SolidityParser.ImportDirectiveContext ctx) { return visitChildren(ctx); }

    @Override public String visitNatSpec(SolidityParser.NatSpecContext ctx) { return visitChildren(ctx); }

    @Override public String visitContractDefinition(SolidityParser.ContractDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitInheritanceSpecifier(SolidityParser.InheritanceSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public String visitContractPart(SolidityParser.ContractPartContext ctx) { return visitChildren(ctx); }

    @Override public String visitStateVariableDeclaration(SolidityParser.StateVariableDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public String visitUsingForDeclaration(SolidityParser.UsingForDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public String visitStructDefinition(SolidityParser.StructDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitConstructorDefinition(SolidityParser.ConstructorDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitModifierDefinition(SolidityParser.ModifierDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitModifierInvocation(SolidityParser.ModifierInvocationContext ctx) { return visitChildren(ctx); }

    @Override public String visitFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitReturnParameters(SolidityParser.ReturnParametersContext ctx) { return visitChildren(ctx); }

    @Override public String visitModifierList(SolidityParser.ModifierListContext ctx) { return visitChildren(ctx); }

    @Override public String visitEventDefinition(SolidityParser.EventDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitEnumValue(SolidityParser.EnumValueContext ctx) { return visitChildren(ctx); }

    @Override public String visitEnumDefinition(SolidityParser.EnumDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitParameterList(SolidityParser.ParameterListContext ctx) { return visitChildren(ctx); }

    @Override public String visitParameter(SolidityParser.ParameterContext ctx) { return visitChildren(ctx); }

    @Override public String visitEventParameterList(SolidityParser.EventParameterListContext ctx) { return visitChildren(ctx); }

    @Override public String visitEventParameter(SolidityParser.EventParameterContext ctx) { return visitChildren(ctx); }

    @Override public String visitFunctionTypeParameterList(SolidityParser.FunctionTypeParameterListContext ctx) { return visitChildren(ctx); }

    @Override public String visitFunctionTypeParameter(SolidityParser.FunctionTypeParameterContext ctx) { return visitChildren(ctx); }

    @Override public String visitVariableDeclaration(SolidityParser.VariableDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public String visitTypeName(SolidityParser.TypeNameContext ctx) { return visitChildren(ctx); }

    @Override public String visitUserDefinedTypeName(SolidityParser.UserDefinedTypeNameContext ctx) { return visitChildren(ctx); }

    @Override public String visitMapping(SolidityParser.MappingContext ctx) { return visitChildren(ctx); }

    @Override public String visitFunctionTypeName(SolidityParser.FunctionTypeNameContext ctx) { return visitChildren(ctx); }

    @Override public String visitStorageLocation(SolidityParser.StorageLocationContext ctx) { return visitChildren(ctx); }

    @Override public String visitStateMutability(SolidityParser.StateMutabilityContext ctx) { return visitChildren(ctx); }

    @Override public String visitBlock(SolidityParser.BlockContext ctx) { return visitChildren(ctx); }

    @Override public String visitStatement(SolidityParser.StatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitExpressionStatement(SolidityParser.ExpressionStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitIfStatement(SolidityParser.IfStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitWhileStatement(SolidityParser.WhileStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitSimpleStatement(SolidityParser.SimpleStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitForStatement(SolidityParser.ForStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitInlineAssemblyStatement(SolidityParser.InlineAssemblyStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitDoWhileStatement(SolidityParser.DoWhileStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitContinueStatement(SolidityParser.ContinueStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitBreakStatement(SolidityParser.BreakStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitReturnStatement(SolidityParser.ReturnStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitThrowStatement(SolidityParser.ThrowStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitEmitStatement(SolidityParser.EmitStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitVariableDeclarationStatement(SolidityParser.VariableDeclarationStatementContext ctx) { return visitChildren(ctx); }

    @Override public String visitVariableDeclarationList(SolidityParser.VariableDeclarationListContext ctx) { return visitChildren(ctx); }

    @Override public String visitIdentifierList(SolidityParser.IdentifierListContext ctx) { return visitChildren(ctx); }

    @Override public String visitElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) { return visitChildren(ctx); }

    @Override public String visitExpression(SolidityParser.ExpressionContext ctx) { return visitChildren(ctx); }

    @Override public String visitPrimaryExpression(SolidityParser.PrimaryExpressionContext ctx) { return visitChildren(ctx); }

    @Override public String visitExpressionList(SolidityParser.ExpressionListContext ctx) { return visitChildren(ctx); }

    @Override public String visitNameValueList(SolidityParser.NameValueListContext ctx) { return visitChildren(ctx); }

    @Override public String visitNameValue(SolidityParser.NameValueContext ctx) { return visitChildren(ctx); }

    @Override public String visitFunctionCallArguments(SolidityParser.FunctionCallArgumentsContext ctx) { return visitChildren(ctx); }

    @Override public String visitFunctionCall(SolidityParser.FunctionCallContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyBlock(SolidityParser.AssemblyBlockContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyItem(SolidityParser.AssemblyItemContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyExpression(SolidityParser.AssemblyExpressionContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyCall(SolidityParser.AssemblyCallContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyLocalDefinition(SolidityParser.AssemblyLocalDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyAssignment(SolidityParser.AssemblyAssignmentContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyIdentifierOrList(SolidityParser.AssemblyIdentifierOrListContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyIdentifierList(SolidityParser.AssemblyIdentifierListContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyStackAssignment(SolidityParser.AssemblyStackAssignmentContext ctx) { return visitChildren(ctx); }

    @Override public String visitLabelDefinition(SolidityParser.LabelDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblySwitch(SolidityParser.AssemblySwitchContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyCase(SolidityParser.AssemblyCaseContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyFunctionDefinition(SolidityParser.AssemblyFunctionDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyFunctionReturns(SolidityParser.AssemblyFunctionReturnsContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyFor(SolidityParser.AssemblyForContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyIf(SolidityParser.AssemblyIfContext ctx) { return visitChildren(ctx); }

    @Override public String visitAssemblyLiteral(SolidityParser.AssemblyLiteralContext ctx) { return visitChildren(ctx); }

    @Override public String visitSubAssembly(SolidityParser.SubAssemblyContext ctx) { return visitChildren(ctx); }

    @Override public String visitTupleExpression(SolidityParser.TupleExpressionContext ctx) { return visitChildren(ctx); }

    @Override public String visitTypeNameExpression(SolidityParser.TypeNameExpressionContext ctx) { return visitChildren(ctx); }

    @Override public String visitNumberLiteral(SolidityParser.NumberLiteralContext ctx) { return visitChildren(ctx); }

    @Override public String visitIdentifier(SolidityParser.IdentifierContext ctx) { return visitChildren(ctx); }
}
