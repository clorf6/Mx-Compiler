import AST.rootNode;
import Backend.InstSelector;
import Backend.RegAlloc;
import Frontend.ASTBuilder;
import Frontend.IRBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import IR.Program;
import Optimize.LinearScan;
import Optimize.Mem2Reg;
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
    public static void main(String[] args) throws Exception {
        //String inputName = "test.txt";
        //InputStream input = new FileInputStream(inputName);
        String outputName = "output.ll";
        OutputStream output = new FileOutputStream(outputName);
        String ASMoutputName = "test.s";
        OutputStream ASMoutput = new FileOutputStream(ASMoutputName);
        String ASMoutputName2 = "test1.s";
        OutputStream ASMoutput2 = new FileOutputStream(ASMoutputName2);
        String ASMoutputName1 = "test2.s";
        OutputStream ASMoutput1 = new FileOutputStream(ASMoutputName1);
        String outputName1 = "output1.ll";
        OutputStream output1 = new FileOutputStream(outputName1);
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
            ASTRoot = (rootNode) astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);
            Program program = new Program();
            new IRBuilder(program, gScope).visit(ASTRoot);
            //output1.write(program.toString().getBytes());
            new Mem2Reg(program);
            //output.write(program.toString().getBytes());
            ASM.Program program1 = new ASM.Program();
            new InstSelector(program1, gScope).visit(program);
            //ASMoutput2.write(program1.toString().getBytes());
            //new RegAlloc(program1);
            new LinearScan(program1);
            //ASMoutput.write(program1.toString().getBytes());
//            ASM.Program program2 = new ASM.Program();
//            new InstSelector(program2, gScope).visit(program);
//            new RegAlloc(program2);
//            ASMoutput1.write(program2.toString().getBytes());
            System.out.println(program1);
        } catch (Utils.Error.Error er) {
            System.err.println(er);
            throw new RuntimeException();
        }
    }
}
