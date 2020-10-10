package Spring.hellospring.repository;

import Spring.hellospring.domain.Member;
import Spring.hellospring.repository.MemberRepository;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    //datasource 주입받아야함 (스프링한테 주입받아야함)
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        //아래와 같이 하면 계속 새로운 커넥션 주어진다.
        //dataSource.getConnection(); //데이터베이스와 연결되는 소켓?얻을 수 있다
        //여기에 sql넘겨서 db에 전달해주면 된다.
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //결과 받는것
        try {
            conn = getConnection(); //커넥션 가지고옴
            //커넥션에서 preparestatement에서 sql넣음
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //return_generated_keys? db에 insert하면 아이디값 얻을 떄 사용

            pstmt.setString(1, member.getName()); //?란에 값을 넣는다
            pstmt.executeUpdate(); //db에 insert 쿼리 날라간다.
            rs = pstmt.getGeneratedKeys(); //return generated랑 매칭

            if (rs.next()) { //값이 있으면 getLong으로 꺼낸다
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?"; //sql 날려서 가져옴
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name")); return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member); //멤버 담고
            }
            return members; //멤버 반환
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally { close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        //스프링 프레임워크롤 통해 데이터베이스커넥션을 쓸 때는 Utils를 통해 획득해야한다
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        //닫을 때도 Utils를 통해야한다.
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}

