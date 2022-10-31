package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "likes")
@NamedQueries({
    @NamedQuery(
        name = "getAlllikes",
        query = "SELECT m FROM Like AS m ORDER BY m.id DESC"
    ),
    @NamedQuery(
            name = "like.getAllMylikes",
            query = "SELECT m FROM Like AS m WHERE m.employee = :" + JpaConst.JPQL_PARM_EMPLOYEE + " ORDER BY m.id DESC"),
})
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Like {

    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * いいねした従業員
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * いいねをされた日報
     */
    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;
}