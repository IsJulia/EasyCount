# Reduce释义
其功能类似于累加器，将当前值与上一个被Reduce的值整合到一起形成新值。可以用如下代码辅助理解reduce的原理：
```java
keyedStream.reduce(new ReduceFunction<Integer>() {
    @Override
    public Integer reduce(Integer value1, Integer value2)
    throws Exception {
        return value1 + value2;
    }
});
```
