# 什么是sink
在流计算系统中，sink可以理解为是对流数据进行处理后“输出”，形式可以为文件、套接字、外部系统、或者打印出结果等。

# sink相关注意事项
- 在flink中，以write开头相关的方法主要用于debug,不在flink的检查点范围中，不一定满足exactly-once语义。
- flink中的StreamingFileSink 以及addSink()方法可以保证exactly-once语义。
