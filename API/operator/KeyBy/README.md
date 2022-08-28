# 什么是KeyBy
KeyBy通俗理解就是将流进一步切分为更细、互不相连的分块，其中 键值 相同的被归到同一个分块，其底层原理是Hash。

# 不可以作为key的type
- 数组
- POJO(Plain OrdinaryJava Object)，普通的Java对象。
