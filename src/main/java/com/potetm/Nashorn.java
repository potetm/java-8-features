package com.potetm;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStreamReader;

public class Nashorn {
    private static void onTheFlyScripting() throws ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine nashornEngine = factory.getEngineByName("nashorn");
        nashornEngine.eval("print('ON ZE FLY!');");
    }

    private static void runAScript() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine nashornEngine = factory.getEngineByName("nashorn");
        nashornEngine.eval(new InputStreamReader(Nashorn.class.getResourceAsStream("hello.js")));
    }

    private static void invokeFunction() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine nashornEngine = factory.getEngineByName("nashorn");
        nashornEngine.eval(new InputStreamReader(Nashorn.class.getResourceAsStream("hello.js")));

        Invocable invocable = (Invocable) nashornEngine;
        invocable.invokeFunction("hello", "NJUG!");
    }

    public static void main(String... args) throws ScriptException, NoSuchMethodException {
        onTheFlyScripting();
        runAScript();
        invokeFunction();
    }
}
