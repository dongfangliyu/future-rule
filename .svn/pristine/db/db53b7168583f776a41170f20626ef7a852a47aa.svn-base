package com.zw.rule.jeval.tools;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtil {
    public CollectionUtil() {
    }

    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static Set<Object> getUnion(List<List<Object>> list) {
        HashSet set = new HashSet();
        if(list == null) {
            list = new ArrayList();
        }

        int size = ((List)list).size();
        if(size > 1) {
            for(int subList = 0; subList < size; ++subList) {
                int j = subList + 1;
                if(j < size) {
                    ((List)((List)list).get(0)).removeAll((Collection)((List)list).get(j));
                    ((List)((List)list).get(0)).addAll((Collection)((List)list).get(j));
                    if(subList == size - 2) {
                        List result = (List)((List)list).get(0);
                        Iterator var7 = result.iterator();

                        while(var7.hasNext()) {
                            Object result1 = var7.next();
                            set.add(result1);
                        }
                    }
                }
            }
        } else {
            Iterator var9 = ((List)list).iterator();

            while(var9.hasNext()) {
                List var8 = (List)var9.next();
                Iterator var11 = var8.iterator();

                while(var11.hasNext()) {
                    Object var10 = var11.next();
                    set.add(var10);
                }
            }
        }

        return set;
    }

    public static Set<Object> getIntersection(List<List<Object>> list) {
        HashSet set = new HashSet();
        int size = list.size();
        if(size > 1) {
            for(int subList = 0; subList < size; ++subList) {
                int j = subList + 1;
                if(j < size) {
                    ((List)list.get(0)).retainAll((Collection)list.get(j));
                    if(subList == size - 2) {
                        List result = (List)list.get(0);
                        Iterator var7 = result.iterator();

                        while(var7.hasNext()) {
                            Object result1 = var7.next();
                            set.add(result1);
                        }
                    }
                }
            }
        } else {
            Iterator var9 = list.iterator();

            while(var9.hasNext()) {
                List var8 = (List)var9.next();
                Iterator var11 = var8.iterator();

                while(var11.hasNext()) {
                    Object var10 = var11.next();
                    set.add(var10);
                }
            }
        }

        return set;
    }
}

