# yunCloud
集成学习
1、全局异常的处理方案：
    1、定义自定义异常
    2、定义接口的返回结果类---
    3、定义全局异常处理类，使用注解@RestControllerAdvice 标记该类为异常处理类，在类中定义各种异常的处理方法 eg: yunCloudExceptionHandle()  处理自定义异常

2、过滤器中的异常处理
   方案1：捕获过滤器中异常，然后请求转发到一个异常接口中去，
         eg:@RequestMapping(value = "/401")
                        public Object notLogin(HttpServletRequest request) {
                        
                            // 取出错误信息//
                            Exception errorMessage = (Exception) request.getAttribute("errorMessage");
                        
                            String message = errorMessage.getMessage();
                        
                            System.out.println(message);
                        
                            return "未登录";
                        }
         
             @Override
        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
    
            HttpServletRequest request = (HttpServletRequest) req;
            try {
                int i = 0 / 0;
            } catch (Exception e) {
    
                // 将错误信息封装在request中
                request.setAttribute("errorMessage", e);
                // 请求转发
                request.getRequestDispatcher("/401").forward(req, resp);
            }
            filterChain.doFilter(req, resp);
        }

3、实现jwt登录的过程
   1、添加自定义fileter  拦截login登录请求。 
   2、 从request中获取用户的相关信息（JwtTokenProvider）   
   3、定义manager 鉴权 通过JwtTokenProvider 中生成的JWTToken ，去查询库中是否存在改用户，然后检查密码是否正确，是否登录过期等、
   4、 校验成功之后，走success处理，并将jwtToken写入到session中   
    