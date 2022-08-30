# DAG
DAG(Directed acyclic graph),中文释义有向无环图。

# flink如何生成图
flink有一组api叫Gelly,专门用于处理图的问题。
Gelly提供了很多图生成器(generator),它们有以下特点：
- 并行处理，以便生成大的数据集。
- 无论并行度如何，生成的图都相同，不缩放。
- 使用的算子数量尽可能少。

以下代码可反映生成图的大致流程：
```java
ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

boolean wrapEndpoints = false;

int parallelism = 4;

Graph<LongValue, NullValue, NullValue> graph = new GridGraph(env)
    .addDimension(2, wrapEndpoints)
    .addDimension(4, wrapEndpoints)
    .setParallelism(parallelism)
    .generate();
```


# 以DAG形式编排使用所有支持的算子
- 算子是逻辑图的结点，一个算子执行一个操作。这个要求的目标就是这个 逻辑图 要转换为 有向无环图 。
