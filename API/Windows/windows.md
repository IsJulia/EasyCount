# Windows含义
Windows是流处理的核心，它能将无限的数据划分成有限大小的“桶”。
- 从窗口内的元素看，可将窗口划分为keyed和non-keyed两种。
- 从窗口移动方式看，可划分为tumbling、sliding、session三种。

# 基于处理时间的滚动窗口 使用
```java
DataStream<T> input = ...;

input
    .keyBy(<key selector>)
    .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
    .<windowed transformation>(<window function>);

```
