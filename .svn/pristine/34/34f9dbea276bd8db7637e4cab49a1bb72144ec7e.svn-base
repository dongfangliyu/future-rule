package com.zw.rule.jeval.function.math;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */
public class MathFunctions implements FunctionGroup {
    private List functions = new ArrayList();

    public MathFunctions() {
        this.functions.add(new Abs());
        this.functions.add(new Acos());
        this.functions.add(new Asin());
        this.functions.add(new Atan());
        this.functions.add(new Atan2());
        this.functions.add(new Ceil());
        this.functions.add(new Cos());
        this.functions.add(new Exp());
        this.functions.add(new Floor());
        this.functions.add(new IEEEremainder());
        this.functions.add(new Log());
        this.functions.add(new Pow());
        this.functions.add(new Random());
        this.functions.add(new Rint());
        this.functions.add(new Round());
        this.functions.add(new Sin());
        this.functions.add(new Sqrt());
        this.functions.add(new Tan());
        this.functions.add(new ToDegrees());
        this.functions.add(new ToRadians());
        this.functions.add(new Max());
        this.functions.add(new Min());
        this.functions.add(new Sum());
        this.functions.add(new Ln());
        this.functions.add(new Average());
    }

    public String getName() {
        return "numberFunctions";
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
