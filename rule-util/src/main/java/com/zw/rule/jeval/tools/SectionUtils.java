package com.zw.rule.jeval.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class SectionUtils {
    public static final float max_float = 2.14748365E9F;
    public static final float min_float = -2.14748365E9F;

    public SectionUtils() {
    }

    private static void SecSort(Section[] secs, int low, int high) {
        int i = low;
        int j = high;
        Section temp = secs[low];

        while(i < j) {
            while(i < j && temp.x < secs[j].x) {
                --j;
            }

            if(i < j) {
                secs[i] = secs[j];
                ++i;
            }

            while(i < j && temp.x > secs[i].x) {
                ++i;
            }

            if(i < j) {
                secs[j] = secs[i];
                --j;
            }
        }

        secs[i] = temp;
        if(low < i) {
            SecSort(secs, low, i - 1);
        }

        if(high > j) {
            SecSort(secs, j + 1, high);
        }

    }

    private static boolean inSection(Section[] secs, Section s, int offset) {
        boolean ret = false;

        for(int k = 0; k < secs.length; ++k) {
            if(secs[k].x != secs[k].y && s.x != s.y && secs[k].x != s.x && secs[k].y != s.y) {
                if(secs[k].x <= s.x && secs[k].y >= s.y && offset != k) {
                    ret = true;
                    break;
                }

                if(secs[k].x <= s.x && s.x < secs[k].y && offset != k) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    public static boolean checkSectionCoincide(List<String> section_list) {
        boolean ret = false;
        String temp = "";
        float min = 0.0F;
        float max = 0.0F;
        Section[] sections = new Section[section_list.size()];

        int n;
        for(n = 0; n < section_list.size(); ++n) {
            temp = (String)section_list.get(n);
            String[] i = temp.split(",");
            if(i.length != 2) {
                sections[n] = new Section(parseFloat(temp), parseFloat(temp));
            } else {
                if(!"(".equals(i[0]) && !"[".equals(i[0])) {
                    if(i[0].indexOf("(") == 0) {
                        temp = i[0].replace("(", "");
                        min = parseFloat(temp);
                    } else if(i[0].indexOf("[") == 0) {
                        temp = i[0].replace("[", "");
                        min = parseFloat(temp);
                    }
                } else {
                    min = -2.14748365E9F;
                }

                if(!")".equals(i[1]) && !"]".equals(i[1])) {
                    if(i[1].indexOf(")") > 0) {
                        temp = i[1].replace(")", "");
                        max = parseFloat(temp);
                    } else if(i[1].indexOf("]") > 0) {
                        temp = i[1].replace("]", "");
                        max = parseFloat(temp);
                    }
                } else {
                    max = 2.14748365E9F;
                }

                sections[n] = new Section(min, max);
            }
        }

        n = sections.length;
        SecSort(sections, 0, n - 1);

        for(int var8 = 0; var8 < n; ++var8) {
            ret = inSection(sections, sections[var8], var8);
            if(ret) {
                break;
            }
        }

        return ret;
    }

    public static boolean checkSectionValid(List<String> section_list) {
        boolean ret_min = false;
        boolean ret_max = false;
        boolean ret_lr = false;
        String temp = "";
        HashMap left_hp = new HashMap();
        HashMap right_hp = new HashMap();
        HashMap collection = new HashMap();

        int ct1;
        for(ct1 = 0; ct1 < section_list.size(); ++ct1) {
            temp = (String)section_list.get(ct1);
            String[] ct2 = temp.split(",");
            if(ct2.length != 2) {
                if(right_hp.get(String.format("%s]", new Object[]{temp})) != null) {
                    right_hp.put(String.format("%s]", new Object[]{temp}), Integer.valueOf(((Integer)right_hp.get(String.format("%s]", new Object[]{temp}))).intValue() + 1));
                } else {
                    right_hp.put(String.format("%s]", new Object[]{temp}), Integer.valueOf(1));
                }

                if(left_hp.get(String.format("[%s", new Object[]{temp})) != null) {
                    left_hp.put(String.format("[%s", new Object[]{temp}), Integer.valueOf(((Integer)left_hp.get(String.format("[%s", new Object[]{temp}))).intValue() + 1));
                } else {
                    left_hp.put(String.format("[%s", new Object[]{temp}), Integer.valueOf(1));
                }
            } else {
                if(!"(".equals(ct2[0]) && !"[".equals(ct2[0])) {
                    if(ct2[0].indexOf("(") == 0) {
                        temp = ct2[0].replace("(", "");
                        collection.put(temp, temp);
                        if(left_hp.get(ct2[0]) != null) {
                            left_hp.put(ct2[0], Integer.valueOf(((Integer)left_hp.get(ct2[0])).intValue() + 1));
                        } else {
                            left_hp.put(ct2[0], Integer.valueOf(1));
                        }
                    } else if(ct2[0].indexOf("[") == 0) {
                        temp = ct2[0].replace("[", "");
                        collection.put(temp, temp);
                        if(left_hp.get(ct2[0]) != null) {
                            left_hp.put(ct2[0], Integer.valueOf(((Integer)left_hp.get(ct2[0])).intValue() + 1));
                        } else {
                            left_hp.put(ct2[0], Integer.valueOf(1));
                        }
                    }
                } else if("(".equals(ct2[0])) {
                    ret_min = true;
                }

                if(!")".equals(ct2[1]) && !"]".equals(ct2[1])) {
                    if(ct2[1].indexOf(")") > 0) {
                        temp = ct2[1].replace(")", "");
                        collection.put(temp, temp);
                        if(right_hp.get(ct2[1]) != null) {
                            right_hp.put(ct2[1], Integer.valueOf(((Integer)right_hp.get(ct2[1])).intValue() + 1));
                        } else {
                            right_hp.put(ct2[1], Integer.valueOf(1));
                        }
                    } else if(ct2[1].indexOf("]") > 0) {
                        temp = ct2[1].replace("]", "");
                        collection.put(temp, temp);
                        if(right_hp.get(ct2[1]) != null) {
                            right_hp.put(ct2[1], Integer.valueOf(((Integer)right_hp.get(ct2[1])).intValue() + 1));
                        } else {
                            right_hp.put(ct2[1], Integer.valueOf(1));
                        }
                    }
                } else if(")".equals(ct2[1])) {
                    ret_max = true;
                }
            }
        }

        ct1 = 0;
        int var13 = 0;
        Iterator var11 = left_hp.entrySet().iterator();

        while(var11.hasNext()) {
            Entry entry = (Entry)var11.next();
            String key = (String)entry.getKey();
            ++ct1;
            if(key.indexOf("(") == 0) {
                key = key.replace("(", "");
                key = String.format("%s]", new Object[]{key});
                if(right_hp.get(key) != null && ((Integer)right_hp.get(key)).intValue() == 1) {
                    ++var13;
                }
            } else if(key.indexOf("[") == 0) {
                key = key.replace("[", "");
                key = String.format("%s)", new Object[]{key});
                if(right_hp.get(key) != null && ((Integer)right_hp.get(key)).intValue() == 1) {
                    ++var13;
                }
            }
        }

        if(ct1 == var13) {
            ret_lr = true;
        }

        if(ret_min && ret_max && ret_lr) {
            return true;
        } else {
            return false;
        }
    }

    public static float parseFloat(String s) {
        float i = 0.0F;

        try {
            i = Float.parseFloat(s);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return i;
    }

    public static void main(String[] args) {
        ArrayList section_list = new ArrayList();
        section_list.add("[0,3)");
        section_list.add("(4,)");
        section_list.add("(,0.5)");
        section_list.add("[3,4]");
        System.out.println("区间是否完整:" + checkSectionValid(section_list));
        System.out.println("区间是否重叠:" + checkSectionCoincide(section_list));
    }
}

