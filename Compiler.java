import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args) throws Exception{
        // Code input from code.txt
        Path fileName = Path.of("./code.txt");
        String code = Files.readString(fileName);
       System.out.println(code); 
        // Generate tokens
       ArrayList<Token> tokens = Tokenizer.tokenize(code);
       System.out.println("\n> Tokens: ");
       Tokenizer.displayTokens(tokens);
    //    for (Token token : tokens) {
    //        System.out.print(token.text);
    //        System.out.print("|");
    //    }
       System.out.println("\n--------------------\n");
        // Generate symbol table
       SymbolTable symbolTable = SymbolTable.generateFromTokens(tokens);
       symbolTable.display();
       System.out.println("--------------------\n");

        SLR slr = new SLR();
        fileName = Path.of("./CFG_latest.txt");
        slr.readProductions(fileName);
        System.out.println("> Production Rules : \n");
        slr.displayProductionRules();
        System.out.println();
        SLR.START_SYMBOL = "prog";
        slr.computeFirstPos();
        System.out.println("> First / Follow Set \n");
        slr.displayFirstAndFollowPosTable();
        slr.generateItemSets();
        System.out.println("> LR(0) Item Sets : \n");
        slr.displayItemSets();
        System.out.println("\n> Goto Table : \n");
        slr.displayGotoTable();
        System.out.println("\n> Production Numbers : \n");
        slr.displayNumberedProductionRules();
        slr.generateParsingTable();
        System.out.println("\n> SLR Parsing Table : \n");
        slr.displayParsingTable();
        StringBuilder output = new StringBuilder();
        for (Token token : tokens) {
            output.append(token.text);
            output.append(" ");
        }
        System.out.println("\n> Parsing Tokens : \n");
        slr.parseInput(output.toString());
    }
}
