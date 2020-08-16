package com.person.framework.shiro;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.UserResource;
import com.person.web.admin.model.SysUser;
import com.person.web.admin.service.ISysGroupUserService;
import com.person.web.admin.service.ISysUserService;

public class ShiroRealm extends AuthorizingRealm {

    /**
     * 为当限前登录的用户授予角色和 权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        // 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String currentUsername = (String) super
                .getAvailablePrincipal(principals);


        // 从数据库中获取当前登录用户的详细信息
        Criteria<SysUser> param = new Criteria<SysUser>();
        param.addParam("userName", currentUsername);
        SysUser user = userService.loginSys(param);
        if (null != user) {
            // 实体类User中包含有用户组的实体类信息
            user = UserResource.getUserById(user.getUserId());
        } else {
            throw new AuthorizationException();
        }
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        if (!StringUtil.isNullOrBlank(user.getGroupId())) {
            simpleAuthorInfo.addRole(user.getGroupId());
        }
        // 目前未用到多角色   与多参数
//		List<String> roleList = new ArrayList<String>(); // 控制菜单
//		List<String> permissionList = new ArrayList<String>(); //控制按钮
//		simpleAuthorInfo.addRoles(roleList);
//		simpleAuthorInfo.addStringPermissions(permissionList);

        // 若该方法什么都不做直接返回null的话,就会导致任何用户访问自动跳转到unauthorizedUrl指定的地址
        // 详见applicationContext.xml中的<bean id="shiroFilter">的配置
        return simpleAuthorInfo;
    }

    /**
     * 验证当前登录的用户 登陆验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        // 获取基于用户名和密码的令牌
        // 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        // 两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        System.out.println("验证当前Subject时获取到token为"
                + ReflectionToStringBuilder.toString(token,
                ToStringStyle.MULTI_LINE_STYLE));
        Criteria<SysUser> param = new Criteria<SysUser>();
        param.addParam("userName", token.getUsername());
        SysUser user = userService.loginSys(param);
        if (null != user) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                    user.getUserName(), user.getPassword(), user.getUserName());
            this.setSession("currentUser", user);
            // shiro 版本的 全局变量
//			AppAdminUserInfo applicationUserInfo = new AppAdminUserInfo(user.getUserId(),user.getUserName(),user.getGroupName(), user.getIp());
//			AppAdminContext.setUserInfo(applicationUserInfo); 
            return authcInfo;
        } else {
            return null;
        }
        // 此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息
        // 说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)
        // 这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证
        // if("jadyer".equals(token.getUsername())){
        // AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("jadyer",
        // "jadyer", this.getName());
        // this.setSession("currentUser", "jadyer");
        // return authcInfo;
        // }else if("玄玉".equals(token.getUsername())){
        // AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("玄玉",
        // "xuanyu", this.getName());
        // this.setSession("currentUser", "玄玉");
        // return authcInfo;
        // }
        // 没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
        // return null;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out
                    .println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysGroupUserService groupUserService;
}
