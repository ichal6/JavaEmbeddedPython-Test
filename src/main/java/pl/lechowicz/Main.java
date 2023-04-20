package pl.lechowicz;

import jep.*;
import jep.MainInterpreter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String pythonFolder = System.getenv("LD_LIBRARY_PATH");
        String jepPath = pythonFolder + "/jep/libjep.jnilib";
        if (!Files.exists(Path.of(jepPath))){
            jepPath = pythonFolder + "/jep/libjep.so";
        }
        //create the interpreter for python executing
        MainInterpreter.setJepLibraryPath(jepPath);
        jep.JepConfig jepConf = new JepConfig();
        File javaDirectory = new File("src/main/java");
        jepConf.addIncludePaths(javaDirectory.getAbsolutePath()  + "/pl/lechowicz");
        jepConf.addIncludePaths(pythonFolder);
        SharedInterpreter.setConfig(jepConf);
        SharedInterpreter subInterp = new SharedInterpreter();
        // run each function from the .py doc I
        subInterp.eval("import jep_path as p");
        subInterp.eval("res_spacy = p.run_spacy_nlp('Apple is looking at buying U.K. startup for $1 billion')");
        ArrayList result = subInterp.getValue("res_spacy", ArrayList.class);
        for (Object item:result) {
            System.out.println(item.toString());
        }
    }
}