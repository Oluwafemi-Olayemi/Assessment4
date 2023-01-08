import java.io.File;
import java.io.IOException;
import java.lang.constant.Constable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

/**
 * Tokenizer, aka Scanner, aka Lexical analyser
 * for DOPL files.
 */
public class Tokenizer {
    private static final String[] keyword = {"character", "do", "else", "endif",
            "endloop", "finish", "if", "integer", "logical", "loopif", "print", "start", "then"};

    private static final String[] arithmeticOp = {".plus.", ".minus.", ".mul.", ".div."};
    private static final String[] logicalOp = {".and.", ".or."};
    private static final String[] relationalOp = {".eq.", ".ne.", ".lt.", ".gt.", ".le.", ".ge."};
    private static final String[] unaryOp = {".minus.", ".not."};

    private List keys = new ArrayList();;
    public Tokenizer()
    {
        keys.addAll(Arrays.asList(keyword));
        keys.addAll(Arrays.asList(arithmeticOp));
        keys.addAll(Arrays.asList(logicalOp));
        keys.addAll(Arrays.asList(relationalOp));
        keys.addAll(Arrays.asList(unaryOp));
    }

    public Boolean tokenIsDatatype(String token){
        List dataType = new ArrayList();
        dataType.add("integer");
        dataType.add("character");
        dataType.add(".not.");

        dataType.add(Arrays.asList(logicalOp));
        dataType.add(Arrays.asList(relationalOp));

        if(dataType.contains(token)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean tokenExist(String token){

        if(keys.contains(token)){
            return true;
        }
        return false;
    }

}
