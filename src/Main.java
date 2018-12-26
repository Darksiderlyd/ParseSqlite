import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Main {


    public static void main(String[] args) {
        writeManualToFile(selectAll(Consts.ONESTEPKILLTABLE).toString(),Consts.OUTPATNONESTEPKILLKING);
        writeManualToFile(selectAll(Consts.OPENBOOKTABLE).toString(),Consts.OUTPATNOPENBOOK);
        writeManualToFile(selectAll(Consts.POLGARTABLE).toString(),Consts.OUTPATNPOLGARSUBJECT);
    }

    private static void writeManualToFile(String content,String path) {
        FileWriter writer;
        try {
            writer = new FileWriter(path);

            writer.write(content);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("数据写入文件失败");
        }
    }

    private static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:/Users/yaodonglv/Desktop/XQS文档/practice.db";
            connection = DriverManager.getConnection(url);
            System.out.println("数据连接成功");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("数据连接失败");
        }

        return connection;
    }


    private static StringBuilder selectAll(String tableName) {
        String sql = "Select *from " + tableName;

        Connection connect = connect();
        StringBuilder content = new StringBuilder();
        try {
            Statement statement = connect.createStatement();//得到Statement实例
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                content.append("[Title \"").append(rs.getString(tableName.equals(Consts.OPENBOOKTABLE) ? Consts.OPENBOOKTITLE : Consts.COMMONCLASSTITLE).replace("\n", "")).append("\"]").append("\n").append(rs.getString(Consts.COMMONMANUAL)).append("\n\n");
            }
            System.out.println(content);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据查询出错");
        }
        return content;
    }

}
