package analyzeVer5;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class testPrintVisitor5 {
    public static void main( String[] args) throws Exception
    {
        SolidityLexer lexer = new SolidityLexer(new ANTLRFileStream("analyzeVer4/test.txt"));
        long start = System.currentTimeMillis();

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        SolidityParser parser = new SolidityParser(tokens);
        ParseTree tree = parser.sourceUnit();

        new SolidityPrintVisitor5().visit(tree);
        long end = System.currentTimeMillis();

        System.out.println( "실행 시간 : " + ( end - start )/1000.0 );
        //
    }
}
