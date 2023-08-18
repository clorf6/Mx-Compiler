import AST.rootNode;
import Frontend.ASTBuilder;
import Frontend.IRBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import IR.Program;
import Parser.MxLexer;
import Parser.MxParser;
import Utils.MxErrorListener;
import Utils.Scope.globalScope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Compiler {
    public static void main(String[] args) throws Exception{
        //        String inputName = "test.txt";
//        InputStream input = new FileInputStream(inputName);
        String outputName = "output.ll";
        OutputStream output = new FileOutputStream(outputName);

        InputStream input = System.in;

        try {
            rootNode ASTRoot;
            globalScope gScope = new globalScope(null);
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot = (rootNode)astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);
            Program program = new Program();
            new IRBuilder(program, gScope).visit(ASTRoot);
            output.write(program.toString().getBytes());
////            // new IRPrinter(System.out).visitFn(f);
////
////            AsmFn asmF = new AsmFn();
////            new InstSelector(asmF).visitFn(f);
////            new RegAlloc(asmF).work();
////            new AsmPrinter(asmF, System.out).print();
        } catch (Utils.Error.Error er) {
            System.err.println(er);
            throw new RuntimeException();
        }
        //TestIR.testIR();
    }
}