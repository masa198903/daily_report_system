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
        String rvid = request.getParameter("id");
        Report report = ReportConverter.toModel(repservice.findOne(toNumber(rvid)));

        //パラメータの値をもとに日報情報のインスタンスを作成する
        Like like = new Like();

        like.setEmployee(employee);
        like.setReport(report);

        //日報情報登録
        likeservice.create(like);

//        rv.setReportDate(toLocalDate(getRequestParam(AttributeConst.REP_DATE))); 要らない
//        rv.setTitle(getRequestParam(AttributeConst.REP_TITLE));
//        rv.setContent(getRequestParam(AttributeConst.REP_CONTENT));

//        long reportsCount = repservice.countAll();

//        putRequestScope(AttributeConst.REP_COUNT, 1); //全ての日報データの件数
//        forward(ForwardConst.FW_REP_INDEX);
        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
    }

    public void destroy() throws ServletException, IOException {
        Employee employee = EmployeeConverter.toModel(getSessionScope(AttributeConst.LOGIN_EMP));
        String rvid = request.getParameter("id");
        Report report = ReportConverter.toModel(repservice.findOne(toNumber(rvid)));

        //パラメータの値をもとに日報情報のインスタンスを作成する
        Like like = new Like();

        like.setEmployee(employee);
        like.setReport(report);

        //日報情報登録
        likeservice.destroy(like);

//        putRequestScope(AttributeConst.REP_COUNT, 1); //全ての日報データの件数
//        forward(ForwardConst.FW_REP_INDEX);
        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
    }
}
