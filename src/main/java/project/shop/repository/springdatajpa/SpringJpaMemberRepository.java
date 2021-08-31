package project.shop.repository.springdatajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.shop.domain.Member;

import java.util.List;

public interface SpringJpaMemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join m.address adr where m.name like '%'||:name||'%' and adr.city like '%'||:city||'%'")
    List<Member> findByNameAndCity(@Param("name") String name, @Param("city") String city);

    @Query("select m from Member m join m.address adr where adr.city like '%'||:city||'%'")
    List<Member> findByCity(@Param("city") String city);

    @Query("select m from Member m where m.name like '%'||:name||'%'")
    List<Member> findByName(@Param("name") String name);

    @Query("select m from Member m where m.loginId = :loginId")
    Member findByLoginId(@Param("loginId") String loginId);

}


