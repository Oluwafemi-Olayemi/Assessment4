import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Parse a DOPL source file and report either that the file is ok
 * or it contains an error.
 * Parsing terminates on the first error.
 *
 * @author
 */
public class Parser {

    File file;
    Scanner sc;
    private final Tokenizer tokenizer;
    private String parseResult = "error";
    private String prevToken;

    Hashtable<String, String> dataTypeTable = new Hashtable<String, String>();
    Hashtable<String, Object> varTable = new Hashtable<String, Object>();

    /**
     * Create a parser.
     *
     * @param filename The file to be translated.
     * @throws IOException on any input issue.
     */
    public Parser(String filename)
            throws IOException {
        this.file = new File(filename);
        this.sc = new Scanner(file);
        tokenizer = new Tokenizer();
    }

    /**
     * Parse the DOPL source file.
     *
     * @throws IOException on any input issue.
     */
    public void parse()
            throws IOException {
        int lineNumber = 0;
        while (this.sc.hasNext()) {
            lineNumber++;
            String lineOfText = sc.nextLine();
            //takes care of when a line is empty by skipping to next line of dopl
            if(lineOfText.isEmpty()){
                continue;
            }
            //split dopl line
            String[] tokens;
            tokens = lineOfText.trim().split("\\s+");

            for (String token : tokens) {
                if(tokenizer.tokenExist(token)){
                    for(int i=1; i<tokens.length; i++ ){

                        //@todo: test if the dopl variable-naming conforms with specification document
                        //if the variable declaration line doesn't separate variables with , or end line with ;
                        if(tokens[i].charAt(tokens[i].length()-1) == ',' || tokens[i].charAt(tokens[i].length()-1) == ';'){
                            this.dataTypeTable.put(tokens[i].substring(0,tokens[i].length()-1),tokens[0]);
                        }else{
                            this.parseResult = "error";
                            printResult();
                        }


                    }
                    this.parseResult = "ok";
                    break;
                }else if(!dataTypeTable.containsKey(token)){
                    //assignment section e.g sum <- 0
                    for(int i= 0; i<tokens.length;i++){
                        if(Objects.equals(tokens[i + 1], "<-") & tokens[i+2].charAt(tokens[i+2].length() - 1) == ';'){
                            this.varTable.put(tokens[i],tokens[i+2].substring(0,tokens[i+2].length()-1));
                        }else{
                            this.parseResult = "error";
                            printResult();
                        }
                        break;
                    }
                }
            }
        }
        System.out.println("varTable: "+varTable.toString());
    }

    public void printResult(){
        System.out.println(parseResult);
        if(Objects.equals(parseResult, "error")){
            System.exit(0);
        }

    }
}
