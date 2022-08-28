public class MysqlSink extends RichSinkFunction<Person> {
    private PreparedStatement ps = null;
    private Connection connection = null;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/flinkdb";
    String username = "root";
    String password = "root";
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = getConn();
        ps = connection.prepareStatement("select * from person;");
    }
    private Connection getConn() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void invoke(Person p, Context context) throws Exception {
        ps.setString(1,p.getName());
        ps.setInt(2,p.getAge());
        ps.executeUpdate();
    }

    @Override
    public void close() throws Exception {
        super.close();
        if(connection != null){
            connection.close();
        }
        if (ps != null){
            ps.close();
        }
    }
}
