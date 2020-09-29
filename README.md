# CrashCatch
Android异常捕获测试Demo  
## Java层
采用UncaughtThreadExceptionHandler + Bugly实现程序crash的捕获与日志上报；  
## JNI层
1、采用ExceptionCheck机制进行处理；  
2、采用信号量机制进行处理；  
