# spring-security-integration
Spring Security Integration -- 方便 Spring Security 集成


- security-core 核心业务逻辑
- security-browser 浏览器安全相关
- security-app App安全相关
- security-demo Demo 小样

-----
## Spring Security 简明架构

Spring Security 主要涉及2大核心功能：

### Authentication and Access Control

- authentication - who are you? （你是谁）- 认证
- access control or authorization - what are you allowed to do? (你能干什么) - 授权

#### 1 Authentication 认证

认证的核心接口是 认证管理器`AuthenticationManager`, 它只有一个方法：

```java
public interface AuthenticationManager {

  Authentication authenticate(Authentication authentication)
    throws AuthenticationException;

}
```

一个认证管理器可以通过 `authenticate()` 处理三种情况，这三种结果涵盖了所有出现的认证情况：

1. 认证成功，返回一个认证成功的对象 `Authentication`(属性authenticated=true，在未认证之前authenticated=false)，即代表输入是一个合法的身份
2.  抛出一个认证异常`AuthenticationException`， 代表能够确定的输入非法的各种情况，该异常后续会被认证流程的其他类处理转译为用户可读的结果，所有我们无需处理
3.  如果认证管理器也无法判断的话就返回`null`, 因为可能还有其它的认证器来处理



`AuthenticationManager` 最常用的实现类是`ProviderManager`, 它维护了一个链表的``AuthenticationProvider`的实例,  `AuthenticationProvider` 有点类似于`AuthenticationManager` 认证管理器，但是它添加了一个`supports()`方法，用于判断当前输入的被认证对象是否能够被这个认证器所处理：

```java
public interface AuthenticationProvider {

	Authentication authenticate(Authentication authentication)
			throws AuthenticationException;

	boolean supports(Class<?> authentication);

}
```

一个认证提供器 `ProviderManager`能够支持多种不同的认证方法，如果不支持所认证的对象那就会跳过它。一个`ProviderManager` 有一个可选的 `parent`(双亲认证提供器)，如果所有的认证对象它都不支持的话，`parent` 就会用来做最后的认证。如果`parent ProviderManager`不可用话就会抛出一个 认证异常`AuthenticationException`。

通常在一个应用程序中，我们会将受保护的资源按权限进行分组，比如 Web 系统中，只允许`/api/**`的请求进入，`/resources/**`访问静态资源，在这种情况下，每组资源都对应一个 `AuthenticationManager`, 所有的认证器都共享一个全局的认证器作为所有认证器的顶级双亲。

![](https://github.com/spring-guides/top-spring-security-architecture/raw/master/images/authentication.png)



Spring Boot 提供了一个默认配置的全局认证管理器（Global AuthenticationManager）通过`@Autowired`自动注入``AuthenticationManagerBuilder` `可以对其进行配置，一般情况已经足够使用。

```java
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

   ... // 其他配置

  @Autowired
  public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) {
    builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
      .password("secret").roles("USER");
  }

}
```

以上是通过自动注入一个`AuthenticationManagerBuilder` 来对这个全局的认证器进行配置，通常一个简单的web 应用这么配置是可以的，但是当我们有许多认证规则的时候，配置全局认证器并不是一个很好的解决方案，这时我们就可以单独去配置每一个`AuthenticationManager`:

```java
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

   ... // 其他配置

  @Override
  public void configure(AuthenticationManagerBuilder builder) {
    builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
      .password("secret").roles("USER");
  }

}
```

通过重写`WebSecurityConfigurerAdapter`的`configure(AuthenticationManagerBuilder builder)`来配置一个新的认证器，此认证器是上面全局认证器的下级，它的作用域仅限于当前这个配置类所对应的规则，或者也可以称为`Local AuthenticationManager `。



#### 2 Authorization 授权

当认证成功之后，接下来的一步就是授权了，授权最核心的类是`AccessDecisionManager`。Spring Security 提供了三种实现类，这三种实现分别都维护了各自的一个链表的`AccessDecisionVoter`，非常类似于 ``ProviderManager` 来代理一个链表的`AuthenticationProvider`一样。

这里的`AccessDecisionVoter` 我们可以简单称它为“投票器”，就像投票人一样，如果觉得你有授权就会投票赞成一样，很形象。

`AccessDecisionManager`我们可以通过字面意思将它理解为访问决策管理器，它的作用就是来决定访问者是否真的有权限访问对应资源，它的三种实现机制分别是：

-   AffirmativeBased：只要有一个投票器通过则代表授权成功 （`默认配置`）
-  	ConsensusBased：少数服从多数原则，即多数AccessDecisionVoter通过则代表授权成功
-  	UnanimousBased：全部赞同才代表授权成功

我们可以来看一下这些投票者的接口`AccessDecisionVoter`:
```java
int ACCESS_GRANTED = 1; //授权成功
int ACCESS_ABSTAIN = 0; //弃权
int ACCESS_DENIED = -1; //否决

boolean supports(ConfigAttribute attribute);

boolean supports(Class<?> clazz);

int vote(Authentication authentication, S object,
        Collection<ConfigAttribute> attributes);
```

`vote()`方法即代表投票，Authentication 代表认证通过的凭证，存储着一些身份信息， S object 这个 object 是一个泛型的参数，代表着一切要被访问的资源（一个静态资源或一个方法等等），`ConfigAttribute` 指代访问这些资源的属性，该接口只有一个方法`getAttribute()`返回一个字符串，这个字符串以某种形式代表了可以访问一个资源的规则。最常见的是代表角色权限的字符串，比如在 Spring Security 中默认的以 `ROLE_`为前缀的角色，这些属性通常以特殊的格式存在，或者是一个`SpEL` 表达式，如`isFullyAuthenticated() && hasRole('FOO')`。如果要自定义能够解析的 SpEL 的话，可以实现 `SecurityExpressionRoot`或`SecurityExpressionHandler`



### Web Security - Web 安全

我们都知道在一个请求到达 Servlet 真正执行之前，会经过一系列的过滤器，然后才会执行 `service()`方法，Spring Security 就是主要以 `Filter`（过滤器） 的方式在 Web 应用程序中起作用。

![](https://github.com/spring-guides/top-spring-security-architecture/raw/master/images/security-filters.png)

Spring Security 以一个单独的 Filter 注册在 Web 容器内部，这个特殊的 Filter 就是`FilterChainProxy`, 它自身维护了一个内部的过滤器链，用来做权限相关的处理，也就是上图中的 Spring Security Filters。在一个 Spring Boot 项目中，`FilterChainProxy`是作为一个`@Bean`默认加载在`ApplicationContext`中的，其在 Filter 中的执行顺序由`SecurityProperties.DEFAULT_FILTER_ORDER`决定。

对于 Spring Security 来说，可以存在多个过滤器链，每一个过滤器链匹配一个或一组请求，如下图`/foo/**`，`/bar/**`，`/**`分别对应一组 Filter Chain

![](https://github.com/spring-guides/top-spring-security-architecture/raw/master/images/security-filters-dispatch.png)

配置自定义的过滤器链也比较简单，通常设置一个`WebSecurityConfigurerAdapter`作为配置类即可，如下：

```java
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class ApplicationConfigurerAdapter extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/foo/**")
     ...    
  }
}
```

以上配置类将会使 Spring Security 创建一个新的 Filter Chain 并且它将会在`BASIC_AUTH_ORDER`之前执行。

许多应用程序对于不同的资源由完全不一样的访问权限设置，比如一个网站对于浏览器 UI 的访问可能是基于 cookie 认证的，认证成功后会进行页面跳转，而对于 API 的访问则是基于 token 的访问规则，认证失败则返回一个 401 的权限错误。对于每种不同的访问规则，可以配置单独的``WebSecurityConfigurerAdapter` `处理。

一个普通的 Spring Boot 应用内部默认配置了多个过滤器链。在`/**`的默认过滤器链中包含了认证逻辑，授权规则，异常处理，Session 处理，Http Header 设置等等 11 个默认的 Filter，一般情况下用户无需关心。