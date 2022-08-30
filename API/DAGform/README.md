# DAG
DAG(Directed acyclic graph),中文释义有向无环图。

# flink如何生成图
flink有一组api叫Gelly,专门用于处理图的问题。

## Gelly中的图
- 图是顶点和边的数据集。其中，每个顶点和边都有唯一的id，也 可能 会有自己的value值（若不赋值就是NullValue）
- Gelly为操作图提供了很多属性方法，如get顶点、边、相应id值、入度、出度等等。
- 图的转换提供了map、translate、filter、join、reverse、undirected、union、difference、intersect等方法。
- 支持对图进行增加\删除边、结点的操作
- 检验图的有效性
- 其他方法

## 如何建图？
有以下几种方法：但本质上都是给定边和（或）顶点
- 给定边数据集（或外加顶点数据集）
```java
ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

DataSet<Vertex<String, Long>> vertices = ...;

DataSet<Edge<String, Double>> edges = ...;

Graph<String, Long, Double> graph = Graph.fromDataSet(vertices, edges, env);
```

- 给定以 二值元组 形式呈现的边数据集.
其中第一个值默认为源id，第二个值为目标id，边和点的value值默认为NullValue
```java
 ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

DataSet<Tuple2<String, String>> edges = ...;

Graph<String, NullValue, NullValue> graph = Graph.fromTuple2DataSet(edges, env);
```

- 给定以 三值元组 形式呈现的边数据集（或加上以 二值元组 形式呈现的点数据集）。
其中，三值元组会被转换成边，二值元组会被转换为点，边和点对应的id和value值都有
```java
ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

DataSet<Tuple2<String, Long>> vertexTuples = env.readCsvFile("path/to/vertex/input").types(String.class, Long.class);

DataSet<Tuple3<String, String, Double>> edgeTuples = env.readCsvFile("path/to/edge/input").types(String.class, String.class, Double.class);

Graph<String, Long, Double> graph = Graph.fromTupleDataSet(vertexTuples, edgeTuples, env);
```

- 给定边和可选顶点的 集合
```java
ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

List<Vertex<Long, Long>> vertexList = new ArrayList...;

List<Edge<Long, String>> edgeList = new ArrayList...;

Graph<Long, Long, String> graph = Graph.fromCollection(vertexList, edgeList, env);
```

## Gelly中的generator
Gelly提供了很多图生成器(generator),它们有以下特点：
- 并行处理，以便生成大的数据集。
- 无论并行度如何，生成的图都相同，不缩放。
- 使用的算子数量尽可能少。

以下代码可反映使用生成器生成网格图的大致流程：
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
算子是逻辑图的结点，一个算子执行一个操作。这个要求的目标就是这个 逻辑图 要转换为 有向无环图 。
## 如何转换
在org.apache.flink.api.dag对类Transformation的描述中，介绍了图的转换思路：
- 转换就是对源数据流在底层进行转换，并创建新的数据流。
- 以map为例，就是在底层创建了一个转换树。当要执行流程序时，通过流图生成器将图转换为流图。
- 在运行时，某种转换可能只是一个逻辑上的概念，而不一定会对应物理层面的转换，比如 union, split/select data stream, partitioning。
以下是一个转换的例子：
```
 Source              Source
      +                   +
      |                   |
      v                   v
  Rebalance          HashPartition
      +                   +
      |                   |
      |                   |
      +------>Union<------+
                +
                |
                v
              Split
                +
                |
                v
              Select
                +
                v
               Map
                +
                |
                v
              Sink
```
              
              转换为DAG:

```
 Source              Source
   +                   +
   |                   |
   |                   |
   +------->Map<-------+
             +
             |
             v
            Sink
```
