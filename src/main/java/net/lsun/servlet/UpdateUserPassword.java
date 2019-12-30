package net.lsun.servlet;

import net.lsun.bean.User;
import net.lsun.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UpdatePassword", value = "/update_password")
public class UpdateUserPassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();

        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        if (user == null) {
            printWriter.write("{\"success\":false,\"msg\":\"请重新登录后重试!\",\"data\":{}}");
            return;
        }

        String oldpassword = request.getParameter("oldpassword");

        UserDao userDao = UserDao.getInstance();
        User temp = userDao.login(user.getPhone(), oldpassword);

        if (temp == null) {
            printWriter.write("{\"success\":false,\"msg\":\"当前密码错误!\",\"data\":{}}");
        } else {
            int id = user.getId();
            String password = request.getParameter("password");
            userDao.updatePassword(id, password);
            printWriter.write("{\"success\":true,\"msg\":\"修改成功.\",\"data\":{}}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
