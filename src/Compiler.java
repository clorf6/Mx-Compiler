import AST.rootNode;
import Backend.InstSelector;
import Backend.RegAlloc;
import Frontend.ASTBuilder;
import Frontend.IRBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import IR.Program;
import Optimize.*;
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
        String ASMoutputName1 = "test1.s";
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
            new Global2Local(program);
            //output1.write(program.toString().getBytes());
            new Mem2Reg(program);
            new BlockMerger(program);
            //output.write(program.toString().getBytes());
            ASM.Program program1 = new ASM.Program();
            new InstSelector(program1, gScope).visit(program);
            new PeepholeOptimizer(program1);
            //ASMoutput1.write(program1.toString().getBytes());
            new LinearScan(program1);
            new PeepholeOptimizer(program1);
            //ASMoutput.write(program1.toString().getBytes());
            System.out.println(program1);
        } catch (Utils.Error.Error er) {
            System.err.println(er);
            throw new RuntimeException();
        }
    }
}
