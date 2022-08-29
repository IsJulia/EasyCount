# 设置算子并发度
在flink中，可通过调用setParallelism()方法设置算子并发度。该方法的其他信息如下：

- 该方法源自包：java.lang.Object
org.apache.flink.api.common.operators.Operator<OUT>
- 关于方法的具体描述
```java
public void setParallelism(int parallelism)
Sets the parallelism for this contract instance. The parallelism denotes how many parallel instances of the user function will be spawned during the execution.
Parameters:
parallelism - The number of parallel instances to spawn. Set this value to ExecutionConfig.PARALLELISM_DEFAULT to let the system decide on its own.
```
- flink源码
 源码在flink/flink-java/src/main/java/org/apache/flink/api/java/operators/Operator.java 路径下。
  
  ```java
    /**
     * Sets the parallelism for this operator. The parallelism must be 1 or more.
     *
     * @param parallelism The parallelism for this operator. A value equal to {@link
     *     ExecutionConfig#PARALLELISM_DEFAULT} will use the system default.
     * @return The operator with set parallelism.
     */
    public O setParallelism(int parallelism) {
        OperatorValidationUtils.validateParallelism(parallelism);

        this.parallelism = parallelism;

        @SuppressWarnings("unchecked")
        O returnType = (O) this;
        return returnType;
    }
  ```
