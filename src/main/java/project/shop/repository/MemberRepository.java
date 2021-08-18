package project.shop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.shop.domain.Address;
import project.shop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /*
    * Member 전체 조회
    * */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /*
    * name, city 파라미터 넘어올 경우 Member 조건 조회
    * */
    public List<Member> findAll(String name, String city) {

        String jpql = "select m from Member m join m.address adr";
        boolean isFirstCondition = true;

        if (StringUtils.hasText(name)) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like '%'||:name||'%'";
        }

        if (StringUtils.hasText(city)) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " adr.city like '%'||:city||'%'";
        }


        TypedQuery<Member> query = em.createQuery(jpql, Member.class);

        if (StringUtils.hasText(name)) {
            query = query.setParameter("name", name);
        }
        if (StringUtils.hasText(city)) {
            query = query.setParameter("city", city);
        }
        return query.getResultList();
    }

    public Member findByName(String name) {

        if (name == null) {
            return em.createQuery(
                    "select m from Member m", Member.class)
                    .getSingleResult();
        }

        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public void delete(Long id) {
        Member member = findOne(id);
        em.remove(member);
    }
}
