package decaf;

import java.io.*;
import antlr.Token;
import java6G6Z1010.tools.CLI.*;

class Main {
    public static void main(String[] args) {
        try {
        	CLI.parse (args, new String[0]);
        	
        	InputStream inputStream = args.length == 0 ?
                    System.in : new java.io.FileInputStream(CLI.infile);

        	if (CLI.target == CLI.SCAN)
        	{
        		DecafScanner lexer = new DecafScanner(new DataInputStream(inputStream));
        		Token token;
        		boolean done = false;
        		while (!done)
        		{
        			try
        			{
		        		for (token=lexer.nextToken(); token.getType()!=DecafParserTokenTypes.EOF; token=lexer.nextToken())
		        		{
		        			String type = "";
		        			String text = token.getText();
		
		        			switch (token.getType())
		        			{
		        			case DecafScannerTokenTypes.ID:
		        				type = " IDENTIFIER ";
		        				break;
		        			case DecafScannerTokenTypes.CHAR_LITERAL:
		        				type = " CHARLITERAL ";
		        				break;
		        			case DecafScannerTokenTypes.DECIMAL_LITERAL:
		        			case DecafScannerTokenTypes.HEX_LITERAL:
		        				type = " INTLITERAL ";
		        				break;
		        			case DecafScannerTokenTypes.TK_true:
		        			case DecafScannerTokenTypes.TK_false:
		        				type = " BOOLLITERAL ";
		        				break;
		        			case DecafScannerTokenTypes.STRING:
		        				type = " STRINGLITERAL ";
		        				break;
		        			}
		        			System.out.println (token.getLine() + type + " " + text);
		        		}
		        		done = true;
        			} catch(Exception e) {
        	        	// print the error:
        	            System.out.println(CLI.infile+" "+e);
        	            lexer.consume ();
        	        }
        		}
        	}
        	else if (CLI.target == CLI.PARSE || CLI.target == CLI.DEFAULT)
        	{
        		DecafScanner lexer = new DecafScanner(new DataInputStream(inputStream));
        		DecafParser parser = new DecafParser (lexer);
                parser.program();
        	}
        	
        } catch(Exception e) {
        	// print the error:
            System.out.println(CLI.infile+" "+e);
        }
    }
}