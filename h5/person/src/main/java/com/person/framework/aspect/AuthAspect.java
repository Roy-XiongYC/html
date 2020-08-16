package com.person.framework.aspect;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.person.framework.constant.MessageConstant;
import com.person.framework.exception.AuthException;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.tag.ShiroTag;

/**
 * 权限控制切入点，如果做了权限标记的执行操作
 * @author 
 *
 */
public class AuthAspect {
	@Autowired
	private IMessageService msgService;
	
	private Logger log = LoggerFactory.getLogger(getClass());
    
    public Object doAuthResource(JoinPoint  point) throws Throwable {
           String temp = point.getStaticPart().toShortString();   //获取execution 定义切入点
            String longTemp = point.getStaticPart().toLongString();  //获取execution 定义切入点,包括参数等信息
            point.getStaticPart().toString(); 
            String classType = point.getTarget().getClass().getName(); // 获取类名(全路径 )
            String methodName = temp.substring(10, temp.length() - 1); //获取方法名: 类名.方法名
            Class<?> className = Class. forName(classType);
            //日志动作 ,方法参数的获取
            @SuppressWarnings( "rawtypes") 
            Class[] args = new Class[point.getArgs(). length]; 
            String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(" ) + 1, 
                    longTemp.length() - 2)).split( ","); 
            for ( int i = 0; i < args. length; i++) { 
                if (sArgs[i].endsWith( "String[]")) { 
                    args[i] = Array.newInstance(Class.forName("java.lang.String" ), 
                            1).getClass(); 
                } else if (sArgs[i].endsWith( "Long[]")) { 
                    args[i] = Array.newInstance(Class.forName("java.lang.Long" ), 1).getClass(); 
                } else if (sArgs[i].endsWith( "BigDecimal[]")) { 
                    args[i] = Array.newInstance(Class.forName("java.math.BigDecimal" ), 1).getClass();
                } else if (sArgs[i].endsWith( "Object[]")) { 
                    args[i] = Array.newInstance(Class.forName("java.lang.Object" ), 1).getClass(); 
                } else if (sArgs[i].indexOf( ".") == -1) { 
                    if (sArgs[i].equals( "int")) { 
                        args[i] = int. class; 
                    } else if (sArgs[i].equals( "char")) { 
                        args[i] = char. class; 
                    } else if (sArgs[i].equals( "float")) { 
                        args[i] = float. class; 
                    } else if (sArgs[i].equals( "boolean")) { 
                        args[i] = boolean. class; 
                    } else if (sArgs[i].equals( "long")) { 
                        args[i] = long. class; 
                    } 
                } else { 
                    args[i] = Class. forName(sArgs[i]); 
                } 
            }
            
            Method method = className.getMethod(methodName.substring(methodName.indexOf("." ) + 1,methodName.indexOf("(")), args);
            // 如果该方法写了权限注解才做操作     cheakAuth 验证权限
            if (method.isAnnotationPresent(PerAuth.class)) { 
            	PerAuth authAnnotation = method.getAnnotation(PerAuth.class );
                if(!checkAuth(authAnnotation.resourceId())){
                	log.error("checkAuth is not");
                	throw new AuthException(msgService.message(MessageConstant.C00000));
                }
            }
       return point.getTarget();
    }
    
    public boolean checkAuth(String resourceId) throws JspException, IOException{
    	boolean result = false;
    	if (StringUtil.isNullOrBlank(resourceId)) {
			ShiroTag tag = new ShiroTag();
			result = tag.checkAuth(resourceId);
		}
        return result;
    }
	
}
