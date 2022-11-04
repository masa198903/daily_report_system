package actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import actions.views.EmployeeConverter;
import actions.views.ReportConverter;
import constants.AttributeConst;
import constants.ForwardConst;
import models.Employee;
import models.Like;
import models.Report;
import services.EmployeeService;
import services.LikeService;
//import services.EmployeeService;
import services.ReportService;

/**
 * Servlet implementation class LikeAction
 */
@WebServlet("/like")
public class LikeAction extends ActionBase {
    private ReportService repservice;
    private EmployeeService empservice;
    private LikeService likeservice;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        repservice = new ReportService();
        empservice = new EmployeeService();
        likeservice = new LikeService();

        //メソッドを実行
        invoke();

        repservice.close();
        empservice.close();
        likeservice.close();
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        Employee employee = EmployeeConverter.toModel(getSessionScope(AttributeConst.LOGIN_EMP));
        String rvid = request.getParameter("id"); // <input>から送られてきた"id"を文字列として取得
        Report report = ReportConverter.toModel(repservice.findOne(toNumber(rvid)));

        Like like = new Like(); //パラメータの値をもとに日報情報のインスタンスを作成する

        like.setEmployee(employee); // Likeテーブルのカラムにログインした従業員データをセット
        like.setReport(report); // Likeテーブルのカラムに <input>から送られてきた"id"と同じ id のレポートをセット

        likeservice.create(like); //日報情報登録

        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
    }

    public void destroy() throws ServletException, IOException {

        Employee employee = EmployeeConverter.toModel(getSessionScope(AttributeConst.LOGIN_EMP));
        String rvid = request.getParameter("id"); // <input>から送られてきた"id"を文字列として取得
        Report report = ReportConverter.toModel(repservice.findOne(toNumber(rvid))); // id をもとにレポートデータを取得

        Like like = likeservice.getLike(employee, report);

        likeservice.destroy(like); //日報情報削除

        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
    }
}
