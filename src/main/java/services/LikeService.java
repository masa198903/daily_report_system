package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import constants.JpaConst;
import models.Like;

public class LikeService extends ServiceBase {
    /**
     * 画面から入力された日報の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param rv 日報の登録内容
     */
    public void create(Like like) {
            createInternal(like);
    }

    private void createInternal(Like like) {

        em.getTransaction().begin();
        em.persist(like);
        em.getTransaction().commit();
        em.close();

    }

    public Like findOneInternal(int id) {
        return em.find(Like.class, id);
    }

    public void destroy(Like like) {
        em.getTransaction().begin();
        Like l = ((LikeService) em).findOneInternal(like.getId());
        em.remove(l);
        em.getTransaction().commit();
        em.close();

    }

    public List<Like> getLikedReport(EmployeeView employee) {

        List<Like> likes = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_LIKE, Like.class) // 全likedReportを取得
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee)) // employee
                .getResultList();
        return likes;
    }
}
