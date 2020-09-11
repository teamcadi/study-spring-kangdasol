package Spring.domain;

public class Member {

    private long id; //시스템이 정하는 아이디 (데이터 구분위해 시스템이 저장하는 id)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
