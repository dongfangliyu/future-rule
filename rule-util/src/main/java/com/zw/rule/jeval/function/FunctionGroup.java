package com.zw.rule.jeval.function;

import com.zw.rule.jeval.Evaluator;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public interface FunctionGroup {
    String getName();

    List getFunctions();

    void load(Evaluator var1);

    void unload(Evaluator var1);
}
