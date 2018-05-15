package com.zw.rule.jeval.function.string;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */
public class StringFunctions implements FunctionGroup {
    private List functions = new ArrayList();

    public StringFunctions() {
        this.functions.add(new CharAt());
        this.functions.add(new CompareTo());
        this.functions.add(new CompareToIgnoreCase());
        this.functions.add(new Concat());
        this.functions.add(new EndsWith());
        this.functions.add(new Equals());
        this.functions.add(new EqualsIgnoreCase());
        this.functions.add(new Eval());
        this.functions.add(new IndexOf());
        this.functions.add(new LastIndexOf());
        this.functions.add(new Length());
        this.functions.add(new Replace());
        this.functions.add(new StartsWith());
        this.functions.add(new Substring());
        this.functions.add(new ToLowerCase());
        this.functions.add(new ToUpperCase());
        this.functions.add(new Trim());
        this.functions.add(new Contains());
        this.functions.add(new NotContains());
        this.functions.add(new NotEquals());
    }

    public String getName() {
        return "stringFunctions";
    }

    public List getFunctions() {
        return this.functions;
    }

    public void load(Evaluator evaluator) {
        Iterator functionIterator = this.functions.iterator();

        while(functionIterator.hasNext()) {
            evaluator.putFunction((Function)functionIterator.next());
        }

    }

    public void unload(Evaluator evaluator) {
        Iterator functionIterator = this.functions.iterator();

        while(functionIterator.hasNext()) {
            evaluator.removeFunction(((Function)functionIterator.next()).getName());
        }

    }
}
