package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectTests {

    @Test
    // @Test를 적용하는 메서드는 반드시 public으로 선언되어야 하고, 파라미터나 리턴 타입 없이 작성한다.
    public void test1() {
        int v1 = 10;
        int v2 = 10;

        // assertEquals() ? 같다고 확신한다. 두 변수의 내용이 같아야만 테스트가 성공한다.
        Assertions.assertEquals(v1, v2);
    }

    // 자바 코드를 이용해서 설치된 MariaDB와 연결을 확인하는 용도
    @Test
    public void testConnection() throws Exception {

        // Class.forName(): JDBC 드라이버 클래스를 메모리상으로 로딩하는 역할.
        Class.forName("org.mariadb.jdbc.Driver");

        // Connection connection: java.sql 패키지의 Connection 인터페이스 타입의 변수. 데이터 베이스와 네트워크 연결을 의미한다.
        // DriverManager.getConnection(): 데이터베이스 내에 있는 여러 정보들을 통해서 특정한 데이터베이스에 연결을 시도한다.
        // -'jdbc:mariadb://localhost:3306/webdb': jdbc 프로토콜을 이용한다는 의미. 네트워크 연결 정보, 연결하려는 데이터베이스 정보를 의미한다.
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/webdb",
                "webuser",
                "webuser");

        // 데이터베이스와 정상적으로 연결이 된다면 Connection 타입의 객체는 null이 아니라는 것을 확신한다는 의미이다.
        Assertions.assertNotNull(connection);

        // 데이터베이스와의 연결을 종료한다. JDBC 프로그램은 데이터베이스와 연결을 잠깐씩 맺고 종료하는 방식으로 처리된다.
        // 작업이 완료되면 데이터베이스와의 연결을 종료해주어야만 한다.
        connection.close();
    }

    @Test
    public void testHikariCP() throws Exception {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("webuser");
        config.setPassword("webuser");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();

        System.out.println(connection);

        connection.close();

    }

}
