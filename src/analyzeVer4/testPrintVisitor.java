package analyzeVer4;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class testPrintVisitor {
    public static void main( String[] args) throws Exception {
        CharStream codePointCharStream = CharStreams.fromFileName("/home/ether/IdeaProjects/makeJS/src/test");
        SolidityLexer lexer = new SolidityLexer(codePointCharStream);
        long start = System.currentTimeMillis();

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        SolidityParser parser = new SolidityParser(tokens);
        ParseTree tree = parser.sourceUnit();

        new SolidityPrintVisitor().visit(tree);
        long end = System.currentTimeMillis();

        System.out.println( "실행 시간 : " + ( end - start )/1000.0 );
        //
    }
}