package engine;

import com.zw.rule.engine.po.EngineVersion;
import com.zw.rule.engine.service.EngineService;
import com.zw.rule.knowledge.service.RuleFieldService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/5/17.
 */
public class EngineServiceTest {
    ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath*:/spring/spring-context.xml");
    @Test
    public void getFieldListTest() throws Exception {
        EngineService engineService = (EngineService) cxt.getBean("engineServiceImpl");
        EngineVersion engineVersion=new EngineVersion();
        engineVersion.setVerId(92L);
        Long userId=111L;
        Long orgId=28L;
       // engineService.getEngineByField(engineVersion,userId,orgId);
    }
}
