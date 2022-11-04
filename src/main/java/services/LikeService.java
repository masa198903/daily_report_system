package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import constants.JpaConst;
import models.Employee;
import models.Like;
import models.Report;

public class LikeService extends ServiceBase {
    /**
     * 画面から入力された日報の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param rv 日報の登録内容
     */
//    public void create(Like like) {
//            createInternal(like);
//    }

    public void create(Like like) {
        em.getTransaction().begin();
        em.persist(like); // DBに保存
        em.getTransaction().commit(); // 保存を確定
        em.close();
    }

//    public Like findOneInternal(int id) {
//        return em.find(Like.class, id);
//    }

    public void destroy(Like like) {
        em.getTransaction().begin();
        em.remove(like);
        em.getTransaction().commit();
        em.close();

    }

    public List<Like> getLikedReport(EmployeeView employee) {

        List<Like> likes = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_LIKE, Like.class) // 全likedReportを取得
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee)) // employee
                .getResultList();
        return likes;
    }

    public Like getLike(Employee employee, Report report) {

        Like like = em.createNamedQuery(JpaConst.Q_REP_GET__LIKE, Like.class) // 条件付きでlikedReportを取得
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, employee) // employee
                .setParameter(JpaConst.JPQL_PARM_REPORT, report) // report
                .getSingleResult();
        return like;
    }

    public long countNumOfEmp() {
        long likes_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT__LIKE, Long.class)
                .getSingleResult();
        return likes_count;
    }


}
