// import java.util.*;

// public class SymbolTable {
    
//     private Map<String, List<Symbol>> symbols;
//     private int currentScope;
    
//     public SymbolTable() {
//         symbols = new HashMap<>();
//         currentScope = 0;
//     }
    
//     public void enterScope() {
//         currentScope++;
//     }
    
//     public void exitScope() {
//         if (currentScope == 0) {
//             throw new RuntimeException("Cannot exit global scope");
//         }
//         currentScope--;
//     }
    
//     public void insert(String name, String type) {
//         if (symbols.containsKey(name)) {
//             List<Symbol> symbolList = symbols.get(name);
//             // Check if symbol is already defined in current scope
//             boolean flag = false;
//             for (Symbol symbol : symbolList) {
//                 if (symbol.getScope() == currentScope){
//                     modify(name, type);
//                     flag=true;
//                 }
//                 else{
//                     symbolList.add(new Symbol(name, type, currentScope));
//                 }
//             }
//             if(flag) symbolList.add(new Symbol(name, type, currentScope));
//         } else {
//             List<Symbol> symbolList = new ArrayList<>();
//             symbolList.add(new Symbol(name, type, currentScope));
//             symbols.put(name, symbolList);
//         }
//     }
    
//     public void modify(String name, String newValue) {
//         if (!symbols.containsKey(name)) throw new RuntimeException("Symbol not found: " + name);
//         List<Symbol> symbolList = symbols.get(name);
//         Symbol lastSymbol = symbolList.get(symbolList.size() - 1);
//         if (lastSymbol.getScope() <= currentScope) {
//             lastSymbol.setValue(newValue);
//         } else {
//             throw new RuntimeException("Cannot modify symbol in outer scope: " + name);
//         }
//     }
    
//     public Symbol lookup(String name) {
//         if (!symbols.containsKey(name)) return null;
//         List<Symbol> symbolList = symbols.get(name);
//         for (int i = symbolList.size()-1; i >=0 ; i--) {
//             if (symbolList.get(i).getScope() <= currentScope) {
//                 return symbolList.get(i);
//             }
//         }
//         return null;
//     }
//     public Symbol lookupInScope(String name) {
//         if (!symbols.containsKey(name)) return null;
//         List<Symbol> symbolList = symbols.get(name);
//         for (int i = symbolList.size()-1; i >=0 ; i--) {
//             if (symbolList.get(i).getScope() == currentScope) {
//                 return symbolList.get(i);
//             }
//         }
//         return null;
//     }

//     public void display() {
//         System.out.println("Symbol Table:");
//         for (String name : symbols.keySet()) {
//             List<Symbol> symbolList = symbols.get(name);
//             for (Symbol symbol : symbolList) {
//                 System.out.println(symbol.getName() + " = ( type : "+symbol.getType()+", value : " + symbol.getValue() + ", scope: " + symbol.getScope() + ")");
//             }
//         }
//     }

//     public static SymbolTable generateFromTokens(List<Token> tokens) {
//         SymbolTable symbolTable = new SymbolTable();
//         Iterator<Token> iterator = tokens.iterator();

//         Token token1 = null;
//         Token token2 = null;
//         Token token3 = null;

//         while (iterator.hasNext()) {
//             token1 = token2;
//             token2 = token3;
//             token3 = iterator.next();

//             if (token1 != null && token2 != null && token3 != null) {
//                 if (token1.token_type.equals("identifier") && token2.token_type.equals("operator") && token2.text.equals("=") && token3.token_type.equals("constant")) {
//                     // Handle identifier = constant
//                     String identifier = token1.value;
//                     String constantValue = token3.value;
//                     if (symbolTable.lookupInScope(identifier) == null) {
//                         symbolTable.insert(identifier, "int"); // Assuming type is int for this example
//                     }
//                     else if (symbolTable.lookup(identifier) == null) {
//                         symbolTable.insert(identifier, "int"); // Assuming type is int for this example
//                     }
//                     else symbolTable.modify(identifier, constantValue);
//                 } else if (token1.token_type.equals("punctuator") && token1.text.equals("{")) {
//                     // Enter scope
//                     symbolTable.enterScope();
//                 } else if (token1.token_type.equals("punctuator") && token1.text.equals("}")) {
//                     // Exit scope
//                     symbolTable.exitScope();
//                 }
//             }
//         }

//         return symbolTable;
//     }
//     private class Symbol {
//         private String name;
//         private String value;
//         private String type;
//         private int scope;
        
//         public Symbol(String name, String type, int scope) {
//             this.name = name;
//             this.type = type;
//             this.scope = scope;
//             if(type.equals("int")){
//                 this.value = "0";
//             }else if(type.equals("char") || type.equals("string")){
//                 this.value = "";
//             }else{
//                 this.value = null;
//             }
//         }
        
//         public String getName() {
//             return name;
//         }
        
//         public String getValue() {
//             return value;
//         }
        
//         public void setValue(String value) {
//             this.value = value;
//         }
        
//         public int getScope() {
//             return scope;
//         }   

//         public String getType() {
//             return type;
//         }
//     }
    
// }

import java.util.*;

public class SymbolTable {
    
    private Map<String, List<Symbol>> symbols;
    private int currentScope;
    
    public SymbolTable() {
        symbols = new HashMap<>();
        currentScope = 0;
    }
    
    public void enterScope() {
        currentScope++;
    }
    
    public void exitScope() {
        if (currentScope == 0) {
            throw new RuntimeException("Cannot exit global scope");
        }
        currentScope--;
    }
    public Symbol lookupInScope(String name) {
                if (!symbols.containsKey(name)) return null;
                List<Symbol> symbolList = symbols.get(name);
                for (int i = symbolList.size()-1; i >=0 ; i--) {
                    if (symbolList.get(i).getScope() == currentScope) {
                        return symbolList.get(i);
                    }
                }
                return null;
            }
            public void insert(String name, String type) {
                if (symbols.containsKey(name)) {
                    List<Symbol> symbolList = symbols.get(name);
                    // Check if symbol is already defined in current scope
                    for (Symbol symbol : symbolList) {
                        if (symbol.getScope() == currentScope) throw new RuntimeException("Symbol already defined in current scope: " + name);
                    }
                    symbolList.add(new Symbol(name, type, currentScope));
                } else {
                    List<Symbol> symbolList = new ArrayList<>();
                    symbolList.add(new Symbol(name, type, currentScope));
                    symbols.put(name, symbolList);
                }
            }
    
    public void modify(String name, String newValue) {
        if (!symbols.containsKey(name)) throw new RuntimeException("Symbol not found: " + name);
        List<Symbol> symbolList = symbols.get(name);
        Symbol lastSymbol = symbolList.get(symbolList.size() - 1);
        if (lastSymbol.getScope() <= currentScope) {
            lastSymbol.setValue(newValue);
        } else {
            throw new RuntimeException("Cannot modify symbol in outer scope: " + name);
        }
    }
    
    public Symbol lookup(String name) {
        if (!symbols.containsKey(name)) return null;
        List<Symbol> symbolList = symbols.get(name);
        for (int i = symbolList.size()-1; i >=0 ; i--) {
            if (symbolList.get(i).getScope() <= currentScope) {
                return symbolList.get(i);
            }
        }
        return null;
    }

    public void display() {
        System.out.println("Symbol Table:");
        for (String name : symbols.keySet()) {
            List<Symbol> symbolList = symbols.get(name);
            for (Symbol symbol : symbolList) {
                System.out.println(symbol.getName() + " = ( type : "+symbol.getType()+", value : " + symbol.getValue() + ", scope: " + symbol.getScope() + ")");
            }
        }
    }

    
public static SymbolTable generateFromTokens(List<Token> tokens) {
    SymbolTable symbolTable = new SymbolTable();
    Iterator<Token> iterator = tokens.iterator();

    Token token1 = null;
    Token token2 = null;
    Token token3 = null;

    while (iterator.hasNext()) {
        token1 = token2;
        token2 = token3;
        token3 = iterator.next();

        if (token1 != null && token2 != null && token3 != null) {
            if (token1.token_type.equals("identifier") && token2.token_type.equals("operator") && token2.text.equals("=") && token3.token_type.equals("constant")) {
                // Handle identifier = constant
                String identifier = token1.value;
                String constantValue = token3.value;
                if (symbolTable.lookupInScope(identifier) == null) {
                    symbolTable.insert(identifier, "int"); // Assuming type is int for this example
                    symbolTable.modify(token1.value, token3.value);
                }
                else if (symbolTable.lookup(identifier) == null) {
                    symbolTable.insert(identifier, "int"); // Assuming type is int for this example
                    symbolTable.modify(token1.value, token3.value);
                }
                else symbolTable.modify(identifier, constantValue);
            } else if (token1.token_type.equals("punctuator") && token1.text.equals("{")) {
                // Enter scope
                symbolTable.enterScope();
            } else if (token1.token_type.equals("punctuator") && token1.text.equals("}")) {
                // Exit scope
                symbolTable.exitScope();
            }
        }
    }

    return symbolTable;
}

    
    private class Symbol {
        private String name;
        private String value;
        private String type;
        private int scope;
        
        public Symbol(String name, String type, int scope) {
            this.name = name;
            this.type = type;
            this.scope = scope;
            if(type.equals("int")){
                this.value = "0";
            }else if(type.equals("char") || type.equals("string")){
                this.value = "";
            }else{
                this.value = null;
            }
        }
        
        public String getName() {
            return name;
        }
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
        
        public int getScope() {
            return scope;
        }   

        public String getType() {
            return type;
        }
    }
    
}