 StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 添加自定义数据源
        DataStreamSource<Person> data = env.addSource(new DataSource());
        data.print().setParallelism(2);
        data.addSink(new MysqlSink());
        // 提交执行任务
env.execute("MySource");
