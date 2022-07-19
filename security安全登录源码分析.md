# security安全登录源码分析

登录请求过来之后，会经过一系列的filter过滤器完成操作，关于安全登录的过滤器主要分为下列两种：

1、认证过滤器： 认证的过程是识别用户身份是否合法

2、授权过滤器：授权是给予已经通过认证的用户功能权限的过程,授权是在认证之后发生的.

认证和授权都是基于filter完成的。

### 认证过滤器

`UsernamePasswordAuthenticationFilter` 继承了 `AbstractAuthenticationProcessingFilter`

`AbstractAuthenticationProcessingFilter`其中主要有

1、`RequestMatcher`请求路劲配置

2、`AuthenticationManager`  身份管理器 

3、`AuthenticationSuccessHandler` 成功后的处理器

4、`AuthenticationFailureHandler` 失败后的处理器

![调用图.png](security%E5%AE%89%E5%85%A8%E7%99%BB%E5%BD%95%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%202e8fe568fc9c4623a9f15ce348b2ebb0/%E8%B0%83%E7%94%A8%E5%9B%BE.png)

```java
private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
				//1、首选与RequestMatcher配置的路径进行匹配，如果匹配就说明需要认证
        //一般配置的路径就是new AntPathRequestMatcher("/login", "POST");
        if (!this.requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
        } else {
            try {
								//2、当路径匹配成功之后，就会到身份认证管理器中，从request中获取用户的登录信息，最后
                //生成Authentication 
                Authentication authenticationResult = this.attemptAuthentication(request, response);
                if (authenticationResult == null) {
                    return;
                }

                this.sessionStrategy.onAuthentication(authenticationResult, request, response);
                if (this.continueChainBeforeSuccessfulAuthentication) {
                    chain.doFilter(request, response);
                }
								//上述所有检验成功之后 ，会将authenticationResult 保存到SecurityContext 中，默认重定向指定的页面 ，如果我们返回json就实现AuthenticationSuccessHandler的onAuthenticationSuccess()方法
                this.successfulAuthentication(request, response, chain, authenticationResult);
            } catch (InternalAuthenticationServiceException var5) {
                this.logger.error("An internal error occurred while trying to authenticate the user.", var5);
								//校验失败后的处理               
								this.unsuccessfulAuthentication(request, response, var5);
            } catch (AuthenticationException var6) {
                this.unsuccessfulAuthentication(request, response, var6);
            }

        }
    }
```

- 源码分析
    
    （1）、路径匹配的源码
    
    ```java
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
            if (this.requiresAuthenticationRequestMatcher.matches(request)) {
                return true;
            } else {
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace(LogMessage.format("Did not match request to %s", this.requiresAuthenticationRequestMatcher));
                }
    
                return false;
            }
        }
    ```
    
    （2）、从请求中获取用户信息封装成Authentication
    
    ```java
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            if (this.postOnly && !request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            } else {
                String username = this.obtainUsername(request);
                username = username != null ? username : "";
                username = username.trim();
                String password = this.obtainPassword(request);
                password = password != null ? password : "";
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
    ```
    
    （3）、将Authentication（UsernamePasswordAuthenticationToken ）提交至认证管理器AuthenticationManager进行认证，这里使用的是AuthenticationManager 的实现类`ProviderManager`
    
    ```java
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            Class<? extends Authentication> toTest = authentication.getClass();
            AuthenticationException lastException = null;
            AuthenticationException parentException = null;
            Authentication result = null;
            Authentication parentResult = null;
            int currentPosition = 0;
            int size = this.providers.size();
            Iterator var9 = this.getProviders().iterator();
    
            while(var9.hasNext()) {
    						//Spring Security支持多种认证方式,因此ProviderManager维护着一个List 列表,存放多种认证方式
    						//最终实际的认证工作是由AuthenticationProvider完成的
                AuthenticationProvider provider = (AuthenticationProvider)var9.next();
                if (provider.supports(toTest)) {
                    if (logger.isTraceEnabled()) {
                        Log var10000 = logger;
                        String var10002 = provider.getClass().getSimpleName();
                        ++currentPosition;
                        var10000.trace(LogMessage.format("Authenticating request with %s (%d/%d)", var10002, currentPosition, size));
                    }
    
                    try {
    										//此处应用到的AuthenticationProvider 是它的实现类  DaoAuthenticationProvider  完成
                        //DaoAuthenticationProvider  继承了抽象类AbstractUserDetailsAuthenticationProvider
    										result = provider.authenticate(authentication);
                        if (result != null) {
                            this.copyDetails(authentication, result);
                            break;
                        }
                    } catch (InternalAuthenticationServiceException | AccountStatusException var14) {
                        this.prepareException(var14, authentication);
                        throw var14;
                    } catch (AuthenticationException var15) {
                        lastException = var15;
                    }
                }
            }
    
            if (result == null && this.parent != null) {
                try {
                    parentResult = this.parent.authenticate(authentication);
                    result = parentResult;
                } catch (ProviderNotFoundException var12) {
                } catch (AuthenticationException var13) {
                    parentException = var13;
                    lastException = var13;
                }
            }
    
            if (result != null) {
                if (this.eraseCredentialsAfterAuthentication && result instanceof CredentialsContainer) {
                    ((CredentialsContainer)result).eraseCredentials();
                }
    
                if (parentResult == null) {
                    this.eventPublisher.publishAuthenticationSuccess(result);
                }
    
                return result;
            } else {
                if (lastException == null) {
                    lastException = new ProviderNotFoundException(this.messages.getMessage("ProviderManager.providerNotFound", new Object[]{toTest.getName()}, "No AuthenticationProvider found for {0}"));
                }
    
                if (parentException == null) {
                    this.prepareException((AuthenticationException)lastException, authentication);
                }
    
                throw lastException;
            }
        }
    ```
    
    （4）、AbstractUserDetailsAuthenticationProvider的authenticate方法
    
    ```java
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication, () -> {
                return this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports", "Only UsernamePasswordAuthenticationToken is supported");
            });
            String username = this.determineUsername(authentication);
            boolean cacheWasUsed = true;
            UserDetails user = this.userCache.getUserFromCache(username);
            if (user == null) {
                cacheWasUsed = false;
    
                try {
    								//关键在此处 通过用户名获取业务系统中的用户信息
                    user = this.retrieveUser(username, (UsernamePasswordAuthenticationToken)authentication);
                } catch (UsernameNotFoundException var6) {
                    this.logger.debug("Failed to find user '" + username + "'");
                    if (!this.hideUserNotFoundExceptions) {
                        throw var6;
                    }
    
                    throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                }
    
                Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
            }
    
            try {
    						//校验用户是否锁定、可用等情况
                this.preAuthenticationChecks.check(user);
                //校验用户密码是否正确
                this.additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken)authentication);
            } catch (AuthenticationException var7) {
                if (!cacheWasUsed) {
                    throw var7;
                }
    
                cacheWasUsed = false;
                user = this.retrieveUser(username, (UsernamePasswordAuthenticationToken)authentication);
                this.preAuthenticationChecks.check(user);
                this.additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken)authentication);
            }
    
            this.postAuthenticationChecks.check(user);
            if (!cacheWasUsed) {
                this.userCache.putUserInCache(user);
            }
    
            Object principalToReturn = user;
            if (this.forcePrincipalAsString) {
                principalToReturn = user.getUsername();
            }
    
            return this.createSuccessAuthentication(principalToReturn, authentication, user);
        }
    ```
    
    （5）、`DaoAuthenticationProvider`中的`retrieveUser`方法
    
    ```java
    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
            this.prepareTimingAttackProtection();
    
            try {
              //使用了接口   UserDetailsService，我们要实现从业务系统中获取用户信息，就需要实现该接口的这loadUserByUsername方法
                UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
                if (loadedUser == null) {
                    throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
                } else {
                    return loadedUser;
                }
            } catch (UsernameNotFoundException var4) {
                this.mitigateAgainstTimingAttack(authentication);
                throw var4;
            } catch (InternalAuthenticationServiceException var5) {
                throw var5;
            } catch (Exception var6) {
                throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
            }
        }
    ```