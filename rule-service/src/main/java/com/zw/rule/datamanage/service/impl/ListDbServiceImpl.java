package com.zw.rule.datamanage.service.impl;

import com.zw.base.util.ExcelUtil;
import com.zw.rule.datamanage.po.*;
import com.zw.rule.datamanage.service.ListDbService;
import com.zw.rule.mapper.datamanage.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * 黑白名单管理
 * @author zongpeng
 * @Time 2017-5-4
 */
@Service
public class ListDbServiceImpl implements ListDbService {

    @Resource
    private ListDbMapper listDbMapper;

    @Resource
    private CustListMapper custListMapper;

    @Resource
    private TblColumnMapper tblColumnMapper;

    @Resource
    private NodeListDbMapper nodeListDbMapper;
    
    @Resource
    private FieldMapper fieldMapper;

    /**
     * 根据用户id获取黑白名单集合 （测试通过）
     * @param   paramMap 包含一下参数：<br>
     * userId | Long | 必需 | 用户id <br>
     * listType | String | 必需 | 黑白名单类型 <br>
     * status | 可选 | 名单状态，启用:1，停用:0，删除:-1
     * @return
     * List: 黑白名单集合
     */
    public List<ListDb> queryByUser(Map<String, Object> paramMap) {
        return listDbMapper.findByUser(paramMap);
    }

    /**
     * 根据id修改黑白名单状态 （测试通过）
     * @param   paramMap 包含一下参数： 
     * userId | Long | 必需 | 用户id <br>
     * Ids | List | 必需 | 黑白名单id集合 <br>
     * status | Integer | 必需 | 状态
     * @return
     * boolean: true or false
     */
    public boolean updateStatus(Map<String, Object> paramMap) {
        return listDbMapper.updateStatus(paramMap);
    }

    /**
     * 根据名单库名称查询名单库id （测试通过）
     * @param   paramMap  包含一下参数：<br>
     * userId | Integer | 必需 | 用户id <br>
     * listType | String | 必需 | 黑白名单类型 <br>
     * listName | String | 必需 | 名单库名称
     * @return
     * id: 名单id
     */
    public Integer queryByListDbName(Map<String, Object> paramMap) {
        return listDbMapper.findByListDbName(paramMap);
    }

    /**
     * 添加黑白名单（测试通过）
     * @param   listDb | 必需 | 黑白名单信息
     * @param   paramMap  包含一下参数：<br>
     * listType | String | 必需 | 黑白名单类型  <br>
     * maintenance | String | 必需 | 名单库表中列字段 字段id逗号分隔  <br>
     * queryField | String | 必需 | 查询主键 字段编号逗号分割
     * @return
     * boolean: true or false
     */
    public boolean addListDb(ListDb listDb, Map<String, Object> paramMap) {
        //添加黑白名单
        listDbMapper.createListDb(listDb);
        Long id = listDb.getId();
        String listType = paramMap.get("listType")+"";
        String organId = paramMap.get("orgId")+"";
        String tableName = "organ_" + organId + "_" + listType + "_" + id;
        String sqlStr = "CREATE TABLE " + tableName + "(" + "  `id` int(11) NOT NULL AUTO_INCREMENT comment \'id\',";
        String tableColumn = String.valueOf(paramMap.get("maintenance"));
        String[] arrayTableColumn = tableColumn.split(",");
        int[] arrayTC = new int[arrayTableColumn.length];

        int start;
        for(start = 0; start < arrayTableColumn.length; start++) {
            arrayTC[start] = Integer.parseInt(arrayTableColumn[start]);
            sqlStr = sqlStr + "  `t" + start + "`" + " varchar(100) DEFAULT NULL comment \'" + arrayTC[start] + "\',";
        }
        //表字段数
        start = arrayTableColumn.length;

        for(int indexMap = start; indexMap < 20; indexMap++) {
            sqlStr = sqlStr + "  `t" + indexMap + "`" + " varchar(100) DEFAULT NULL comment \'\',";
        }

        sqlStr = sqlStr + "`user_id` int(11) NOT NULL COMMENT \'创建人编号\',`nick_name` varchar(50) NOT NULL COMMENT \'创建人昵称\',`created` datetime NOT NULL COMMENT \'创建时间\',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        paramMap.put("sqlStr", sqlStr);
        //创建新表
        listDbMapper.createTable(paramMap);
        HashMap param = new HashMap();
        String queryField = String.valueOf(paramMap.get("queryField"));
        String[] arrayQueryField = queryField.split(",");
        int[] arrayQF = new int[arrayQueryField.length];
        int[] arrayTC2 = new int[arrayTableColumn.length];
        String indexStr = "";

        for(int indexSql = 0; indexSql < arrayQueryField.length; indexSql++) {
            arrayQF[indexSql] = Integer.parseInt(arrayQueryField[indexSql]);

            for(int j = 0; j < arrayTableColumn.length; j++) {
                arrayTC2[j] = Integer.parseInt(arrayTableColumn[j]);
                if(arrayQF[indexSql] == arrayTC2[j]) {
                    if(indexStr.equals("")) {
                        indexStr = "  `t" + j + "`";
                    } else {
                        indexStr = indexStr + ",  `t" + j + "`";
                    }
                }
            }
        }
        //创建索引
        String indexSql = "ALTER TABLE " + tableName + " ADD INDEX `idx_top4` (" + indexStr + ") ;";
        param.put("indexSql", indexSql);
        listDbMapper.createIndex(param);
        return true;
    }

    /**
     * 根据id查询名单库 （测试通过）
     * @param   paramMap  包含一下参数：<br>
     * id | Long | 必需 | 名单库id <br>
     * userId | Long | 必需 | 用户id <br>
     * listType | String | 必需 | 黑白名单类型
     * @return
     * ListDb:名单库
     */
    public ListDb findById(Map<String, Object> paramMap) {
        return listDbMapper.findById(paramMap);
    }

    /**
     * 根据id修改名单库 （测试通过）
     * @param   paramMap  包含一下参数：<br>
     * id | Long | 必需 | 黑白名单id <br>
     * listType| String | 必需 | 黑白名单类型 黑名单:b,白名单:w <br>
     * listName | String | 必需 | 黑白名单库名称 <br>
     * dataSource | Integer | 必需 | 数据来源：外部黑(白)名单:1、内部黑(白)名单:2、待选:0 <br>
     * listAttribute | String | 必需 | 名单库类型属性 <br>
     * listDesc | String | 必需 | 名单库描述 <br>
     * maintenance | String | 必需 | 维护字段 <br>
     * matchType | Integer | 必需 | 检索匹配类型，精确匹配:1，模糊匹配:0 <br>
     * queryType | Integer | 必需 | 查询字段间逻辑，and:1，or:0 <br>
     * queryField | String | 必需 | 查询主键
     * @return
     * boolean: true or false
     */
    public boolean updateListDb(Map<String, Object> paramMap) {
        return listDbMapper.updateListDb(paramMap);
    }

    /**
     * 根据表名查询表单数据 （测试通过）
     * @param   paramMap  包含一下参数： <br>
     * tableName | String | 必需 | 数据库表名
     * @return
     * List<CustList>: 表单数据
     */
    public List<CustList> queryCustList(Map<String, Object> paramMap) {
        return custListMapper.findCustList(paramMap);
    }
    /**
     * 根据字段名查询表单数据 （测试通过）
     * @param   paramMap  包含一下参数： <br>
     * tableName | String | 必需 | 数据库表名 <br>
     * colTop4Array | String | 必需 | 字段名  <br>
     * searchKey | String | 必需 | 查询条件
     * @return
     * List<CustList>: 相应字段数据
     */
    public List<CustList> findTop4Column(Map<String, Object> paramMap) {
        return custListMapper.searchTop4Column(paramMap);
    }

    /**
     * 根据表名、数据库名获取表字段信息 （测试通过）
     * @param   paramMap  包含一下参数： <br>
     * tableName | String | 必需 | 数据库表名 <br>
     * schemaName | String | 必需 | 数据库名
     * @return
     * List<TblColumn>: 表字段信息实体(colName: 字段名  colComment: 字段描述  colOrder: 第几列)
     */
    public List<TblColumn> getColumnList(Map<String, Object> paramMap) {
        return tblColumnMapper.getColumnList(paramMap);
    }

    /**
     * 根据id获取表字段信息 （测试通过）
     * @param   paramMap  包含一下参数： <br>
     * id | Long | 必需 | id  <br>
     * ableName | String | 必需 | 表名  <br>
     * fieldName | String | 必需 | 字段名
     * @return
     * CustList: 字段数据
     */
    public CustList queryCustListById(Map<String, Object> paramMap) {
        return custListMapper.findById(paramMap);
    }

    /**
     * 根据字段名获取表字段信息集合
     * @param   paramMap  包含一下参数： <br>
     * tableName | String | 必需 | 表名  <br>
     * validColArray | String | 必需 | 字段名
     * @return
     * List<CustList>: 字段数据
     */
    public List<CustList> queryValidColumnData(Map<String, Object> paramMap) {
        return custListMapper.findValidColumnData(paramMap);
    }

    /**
     * 根据表名插入数据 （测试通过）
     * @param   paramMap  包含一下参数： <br>
     * tableName | String | 必需 | 表名  <br>
     * custList | String | 必需 | 表数据--将字段数据放入paramMap，不放实体 <br>
     * checkCol | String | 必需 | 查询条件
     * @return
     * int 1:成功  0:失败  2:已存在(不明确)
     */
    public int addCustList(Map<String, Object> paramMap) {
        return custListMapper.checkDupCustList(paramMap) == 0?(custListMapper.createCustList(paramMap)?1:0):2;
    }

    /**
     * 根据表名修改数据 （测试通过）
     * @param   paramMap  包含一下参数：<br>
     * tableName | String | 必需 | 表名<br>
     * custList | String | 必需 | 表数据--将字段数据放入paramMap，不放实体 <br>
     * checkCol | String | 必需 | 查询条件
     * @return  int
     * int 1:成功  0:失败  2:已存在(不明确)
     */
    public int updateCustList(Map<String, Object> paramMap) {
        return custListMapper.checkDupCustList(paramMap) == 0?(custListMapper.updateCustList(paramMap)?1:0):2;
    }

    /**
     * 根据id删除表数据 （测试通过）
     * @param   paramMap  包含一下参数：<br>
     * tableName | String | 必需 | 表名<br>
     * Ids | List | 必需 | id集合
     * @return
     * boolean  true:成功 false:失败
     */
    public boolean deleteCustList(Map<String, Object> paramMap) {
        return custListMapper.deleteCustList(paramMap);
    }

    /**
     * 根据查询条件获取数据数量 （测试通过）
     * @param   paramMap  包含一下参数：<br>
     * tableName | String | 必需 | 表名<br>
     * queryKey | String | 必需 | 查询条件
     * @return
     * Integer: 查询数量
     */
    public Integer queryCustListByQueryKey(Map<String, Object> paramMap) {
        return custListMapper.findByQueryKey(paramMap);
    }

    /**
     * 根据id获取黑白名单 （测试通过）
     * @param   paramMap 包含一下参数：<br>
     * userId | Long | 必需 | 用户id <br>
     * listDbId | Long | 必需 | 黑白名单id
     * @return
     * ListDb: 黑白名单信息
     */
    public ListDb queryListDbById(Map<String, Object> paramMap) {
        return listDbMapper.findListDbById(paramMap);
    }

    /**
     * 根据节点编号更新黑白名单节点 （测试通过）
     * @param   paramMap 包含一下参数：<br>
     * userId | Long | 必需 | 用户id <br>
     * listDbIds | Long | 必需 | 黑白名单id集合 <br>
     * nodeId | Long | 必需 | 节点编号 <br>
     * insideListDbs | String | 必需 | 内部黑白名单库编号,逗号分割 <br>
     * outsideListDbs | String | 必需 | 外部黑白名单库编号,逗号分割
     * @return
     * boolean  true:成功  false:失败
     */
    public boolean updateNodeListDb(Map<String, Object> paramMap) {
        Long engineNodeId = Long.valueOf(Long.parseLong(paramMap.get("nodeId").toString()));
        addFieldToRedis(paramMap);//将节点信息放入缓存
        return nodeListDbMapper.updateNodeListDb(paramMap);
    }

    /**
     * 添加黑白名单节点 （测试通过）
     * @param   paramMap 包含一下参数：<br>
     * userId | Long | 必需 | 用户id <br>
     * listDbIds | List | 必需 | 黑白名单id集合 <br>
     * nodeId | Long | 必需 | 节点编号 <br>
     * insideListDbs | String | 必需 | 内部黑白名单库编号,逗号分割 <br>
     * outsideListDbs | String | 必需 | 外部黑白名单库编号,逗号分割
     * @return
     * boolean  true:成功  false:失败
     */
    public boolean addNodeListDb(Map<String, Object> paramMap) {
        return nodeListDbMapper.createNodeListDb(paramMap);
    }
    /** 将节点信息放入缓存 */
    private void addFieldToRedis(Map<String, Object> paramMap) {
        Long engineNodeId = Long.valueOf(paramMap.get("nodeId").toString());
        listDbMapper.findListDbByIds(paramMap);
    }

    /**
     * 根据节点编号获取黑白名单节点 （测试通过）
     * @param   paramMap 包含一下参数：<br>
     * nodeId | Long | 必需 | 节点编号
     * @return
     * NodeListDb: 黑白名单节点信息
     */
    public NodeListDb queryNodeListDbByNodeId(Map<String, Object> paramMap) {
        return nodeListDbMapper.findByNodeId(paramMap);
    }

    /**
     * 根据节点编号获取黑白名单节点
     * @param
     * @return
     * dbName: 数据库名
     */
    private String getDbName() {
        ResourceBundle resource = ResourceBundle.getBundle("jdbc");
        String mysqlUrl = resource.getString("mysql.url");
        String[] aArray = mysqlUrl.split("/");
        String[] bArray = aArray[3].split("\\?");
        String dbName = bArray[0];
        return dbName;
    }
    /**  */
    public boolean findByQueryKey(Map<String, Object> paramMap , Long userId , Long organId) {
        String strlistDbIds = null;
        if(!paramMap.get("nodeId").equals("0")) {
            //根据节点编号获取节点信息
            NodeListDb nodeListDb = nodeListDbMapper.findByNodeId(paramMap);
            //获取内部黑白名单库编号(逗号分割)
            strlistDbIds = nodeListDb.getInsideListdbs();
            String[] arraylistDBIds = null;
            Integer matchs = Integer.valueOf(0);
            Integer revMatchs = Integer.valueOf(0);
            if(strlistDbIds.length() <= 0) {
                return false;
            }
            //切割内部黑白名单库编号放入数组
            arraylistDBIds = strlistDbIds.split(",");

            for(int i = 0; i < arraylistDBIds.length; i++) {
                HashMap param1 = new HashMap();
                param1.put("userId", userId);
                Integer listDbId = Integer.valueOf(Integer.valueOf(arraylistDBIds[i]).intValue());
                param1.put("listDbId", listDbId);
                new ListDb();//？
                //根据id获取黑白名单信息
                ListDb listDb = listDbMapper.findListDbById(param1);
                paramMap.put("listDb", listDb);
                //获取黑白名单类型
                String listType = listDb.getListType();
                //切割查询主键
                String[] queryKeyArray = listDb.getQueryField().split(",");
                if(queryKeyArray.length > 0) {
                    //获取查询字段间逻辑 and:1，or:0
                    Integer queryType = listDb.getQueryType();
                    //获取检索匹配类型 精确匹配:1，模糊匹配:0
                    Integer matchType = listDb.getMatchType();
                    //查询条件
                    String queryKey = "";
                    String revQueryKey = "";
                    String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
                    paramMap.put("tableName", tableName);//表名
                    paramMap.put("schemaName", getDbName());//数据库名
                    //获取表字段信息
                    List columnList = tblColumnMapper.getColumnList(paramMap);
                    Integer loc = Integer.valueOf(0);
                    int j = 0;

                    label100:
                    while(true) {
                        if(j >= queryKeyArray.length) {
                            paramMap.put("queryKey", queryKey);
                            paramMap.put("revQueryKey", revQueryKey);
                            break;
                        }

                        HashMap param = new HashMap();
                        param.put("id", queryKeyArray[j]);
                        param.put("userId", userId);
                        param.put("engineId", (Object)null);
                        //获取字段信息
                        Field field = fieldMapper.findByFieldId(param);
                        String fieldEn = field.getEnName();
                        Iterator iterator = columnList.iterator();

                        while(true) {
                            while(true) {
                                String colName;
                                String paramValue;
                                do {
                                    String colComment;
                                    do {
                                        do {
                                            do {
                                                if(!iterator.hasNext()) {
                                                    ++j;
                                                    continue label100;
                                                }
                                                //获取表列信息
                                                TblColumn tblColumn = (TblColumn)iterator.next();
                                                colComment = tblColumn.getColComment();//字段描述
                                                colName = tblColumn.getColName();//字段名
                                                paramValue = (String)paramMap.get(fieldEn);
                                            } while(!colName.startsWith("t"));
                                        } while(!queryKeyArray[j].equals(colComment));
                                    } while(paramValue == null);
                                } while(paramValue.equals(""));

                                loc = Integer.valueOf(loc.intValue() + 1);
                                if(matchType.intValue() == 1) {
                                    if(loc.intValue() > 1 && queryType.intValue() == 1) {
                                        queryKey = queryKey + " and ";
                                    } else if(loc.intValue() > 1 && queryType.intValue() == 0) {
                                        queryKey = queryKey + " or ";
                                    }

                                    queryKey = queryKey + colName + " = \'" + paramValue + "\'";
                                } else if(matchType.intValue() == 0) {
                                    if(loc.intValue() > 1 && queryType.intValue() == 1) {
                                        queryKey = queryKey + " and ";
                                    } else if(loc.intValue() > 1 && queryType.intValue() == 0) {
                                        queryKey = queryKey + " or ";
                                        revQueryKey = revQueryKey + " + ";
                                    }

                                    queryKey = queryKey + colName + " like " + "\'%" + paramValue + "%\'";
                                    revQueryKey = revQueryKey + "max(instr(\'" + paramValue + "\'," + colName + "))";
                                }
                            }
                        }
                    }
                }

                matchs = Integer.valueOf(matchs.intValue() + custListMapper.findByQueryKey(paramMap).intValue());
                if(!paramMap.get("revQueryKey").equals("")) {
                    revMatchs = custListMapper.revFindByQueryKey(paramMap);
                }

                if(matchs.intValue() + revMatchs.intValue() > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean importExcel(String url, Map<String, Object> paramMap) {
        FileInputStream is = null;
        Workbook Workbook = null;

        try {
            is = new FileInputStream(url);
            Workbook = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList custList = new ArrayList();

        for(int i = 0; i < Workbook.getNumberOfSheets(); i++) {
            Sheet Sheet = Workbook.getSheetAt(i);
            if(Sheet != null) {
                CustList custInfo;
                String checkColStr;
                for(int j = 1; j <= Sheet.getLastRowNum(); j++) {
                    Row iterator = Sheet.getRow(j);
                    if(iterator != null) {
                        custInfo = new CustList();
                        custInfo.setUserId((Long)paramMap.get("userId"));
                        checkColStr = (String)paramMap.get("checkCol");

                        for(int n = 0; n <= iterator.getLastCellNum(); n++) {
                            Cell cell = iterator.getCell(n);
                            String cellStr = ExcelUtil.getCellValue(cell).trim();
                            switch(n) {
                                case 0:
                                    custInfo.setT0(cellStr);
                                    checkColStr = checkColStr.replace("\'t0\'", "\'" + cellStr + "\'");
                                    break;
                                case 1:
                                    custInfo.setT1(cellStr);
                                    checkColStr = checkColStr.replace("\'t1\'", "\'" + cellStr + "\'");
                                    break;
                                case 2:
                                    custInfo.setT2(cellStr);
                                    checkColStr = checkColStr.replace("\'t2\'", "\'" + cellStr + "\'");
                                    break;
                                case 3:
                                    custInfo.setT3(cellStr);
                                    checkColStr = checkColStr.replace("\'t3\'", "\'" + cellStr + "\'");
                                    break;
                                case 4:
                                    custInfo.setT4(cellStr);
                                    checkColStr = checkColStr.replace("\'t4\'", "\'" + cellStr + "\'");
                                    break;
                                case 5:
                                    custInfo.setT5(cellStr);
                                    checkColStr = checkColStr.replace("\'t5\'", "\'" + cellStr + "\'");
                                    break;
                                case 6:
                                    custInfo.setT6(cellStr);
                                    checkColStr = checkColStr.replace("\'t6\'", "\'" + cellStr + "\'");
                                    break;
                                case 7:
                                    custInfo.setT7(cellStr);
                                    checkColStr = checkColStr.replace("\'t7\'", "\'" + cellStr + "\'");
                                    break;
                                case 8:
                                    custInfo.setT8(cellStr);
                                    checkColStr = checkColStr.replace("\'t8\'", "\'" + cellStr + "\'");
                                    break;
                                case 9:
                                    custInfo.setT9(cellStr);
                                    checkColStr = checkColStr.replace("\'t9\'", "\'" + cellStr + "\'");
                                    break;
                                case 10:
                                    custInfo.setT10(cellStr);
                                    checkColStr = checkColStr.replace("\'t10\'", "\'" + cellStr + "\'");
                                    break;
                                case 11:
                                    custInfo.setT11(cellStr);
                                    checkColStr = checkColStr.replace("\'t11\'", "\'" + cellStr + "\'");
                                    break;
                                case 12:
                                    custInfo.setT12(cellStr);
                                    checkColStr = checkColStr.replace("\'t12\'", "\'" + cellStr + "\'");
                                    break;
                                case 13:
                                    custInfo.setT13(cellStr);
                                    checkColStr = checkColStr.replace("\'t13\'", "\'" + cellStr + "\'");
                                    break;
                                case 14:
                                    custInfo.setT14(cellStr);
                                    checkColStr = checkColStr.replace("\'t14\'", "\'" + cellStr + "\'");
                                    break;
                                case 15:
                                    custInfo.setT15(cellStr);
                                    checkColStr = checkColStr.replace("\'t15\'", "\'" + cellStr + "\'");
                                    break;
                                case 16:
                                    custInfo.setT16(cellStr);
                                    checkColStr = checkColStr.replace("\'t16\'", "\'" + cellStr + "\'");
                                    break;
                                case 17:
                                    custInfo.setT17(cellStr);
                                    checkColStr = checkColStr.replace("\'t17\'", "\'" + cellStr + "\'");
                                    break;
                                case 18:
                                    custInfo.setT18(cellStr);
                                    checkColStr = checkColStr.replace("\'t18\'", "\'" + cellStr + "\'");
                                    break;
                                case 19:
                                    custInfo.setT19(cellStr);
                                    checkColStr = checkColStr.replace("\'t19\'", "\'" + cellStr + "\'");
                            }
                        }

                        custInfo.setCheckCol(checkColStr);
                        custList.add(custInfo);
                    }
                }

                if(custList.size() > 0) {
                    paramMap.put("custList", custList);
                    ArrayList dupCustList = new ArrayList();
                    Iterator iterator = custList.iterator();

                    while(iterator.hasNext()) {
                        custInfo = (CustList)iterator.next();
                        checkColStr = custInfo.getCheckCol();
                        paramMap.put("checkCol", checkColStr);
                        if(custListMapper.checkDupCustList(paramMap) > 0) {
                            dupCustList.add(custInfo);
                        } else {
                            custInfo.setTableName(String.valueOf(paramMap.get("tableName")));
                            custInfo.setUserId((Long)paramMap.get("userId"));
                            custInfo.setNickName(String.valueOf(paramMap.get("nickName")));
                            custListMapper.importOneCustList(custInfo);
                        }
                    }

                    paramMap.put("dupCustList", dupCustList);
                }
            }
        }

        return true;
    }

    /**
     * 根据节点编号获取查询主键 （测试通过）
     * @param   nodeId | 必需 | 节点编号 <br>
     * @param   userId | 必需 | 用户id
     * @return
     * queryFields: 查询主键(以逗号分割)
     */
    public String querySearchKeyIdListByNodeId(String nodeId , Long userId) {
        Map param = new HashMap();
        param.put("userId", userId);
        param.put("nodeId", nodeId);
        //根据nodeId获取节点信息
        NodeListDb nodeDbList = nodeListDbMapper.findByNodeId(param);
        String strkeyIds = "";
        //获取内部黑白名单库编号(逗号分割)
        String strlistDbIds = nodeDbList.getInsideListdbs();
        String[] arraylistDBIds = null;
        if(strlistDbIds.length() > 0) {
            //切割内部黑白名单库编号
            arraylistDBIds = strlistDbIds.split(",");
            for(int i = 0; i < arraylistDBIds.length; i++) {
                Integer id = Integer.valueOf(Integer.valueOf(arraylistDBIds[i]).intValue());
                param.put("listDbId", id);
                //根据id查找节点信息
                ListDb keyIdList = listDbMapper.findListDbById(param);
                //获取查询主键(字段编号以逗号分割)
                String str = keyIdList.getQueryField();
                if(str.length() > 0) {
                    strkeyIds = strkeyIds + str + ",";
                }
            }
        }
        //分割查询主键
        String[] queryField = strkeyIds.subSequence(0, strkeyIds.length() - 1).toString().split(",");
        ArrayList queryField2 = new ArrayList();
        //查询主键去重
        for(int i = 0; i < queryField.length; i++) {
            if(!queryField2.contains(queryField[i])) {
                queryField2.add(queryField[i]);
            }
        }
        //以逗号分割新获取的查询主键
        String queryFields = "";
        if(queryField2 != null && queryField2.size() > 0) {
            for(int i = 0; i < queryField2.size(); i++) {
                queryFields = queryFields + (String)queryField2.get(i) + ',';
            }
            queryFields = queryFields.substring(0, queryFields.length() - 1);
        }
        return queryFields;
    }

    /**
     * 根据id获取黑白名单 （测试通过）
     * @param   paramMap 包含一下参数： <br>
     * userId | Long | 必需 | 用户id <br>
     * listDbIds | List | 必需 | 黑白名单id集合
     * @return
     * List<ListDb>: 黑白名单集合
     */
    public List<ListDb> queryListDbByIds(Map<String, Object> paramMap) {
        return listDbMapper.findListDbByIds(paramMap);
    }

    /**
     * 根据名单库名称验证名单库是否唯一 （测试通过）
     * @param   paramMap  包含一下参数： <br>
     * userId | Long | 必需 | 用户id <br>
     * listType | String | 必需 | 黑白名单类型 <br>
     * listName | String | 必需 | 名单库名称 <br>
     * id | Long | 必需 | 名单库id
     * @return
     * count: 名单数量
     */
    public Integer isExists(Map<String, Object> paramMap) {
        return listDbMapper.isExists(paramMap);
    }

    /**
     * 根据id获取名单库维护字段maintenance 字段id逗号分隔 （测试通过）
     * @param   paramMap  包含一下参数： <br>
     * userId | Long | 必需 | 用户id <br>
     * listDbIds | List | 必需 | 黑白名单id集合
     * @return
     * maintenance: 名单库表中列字段  字段id逗号分隔
     */
    public String findFieldsByListDbIds(Map<String, Object> paramMap) {
        return listDbMapper.findFieldsByListDbIds(paramMap);
    }

    /**
     * 根据字段id获取查询主键名 （测试通过）
     * @param   queryField | 必需 | 字段id
     * @return
     * queryFieldCn: 查询主键名
     */
    @Override
    public String getQueryFieldCn(String queryField) {
        return listDbMapper.getQueryFieldCn(queryField);
    }

    @Override
    public NodeListDb queryDbListByNodeId(Long nodeId) {
        return nodeListDbMapper.findDbListByNodeId(nodeId);
    }
}
