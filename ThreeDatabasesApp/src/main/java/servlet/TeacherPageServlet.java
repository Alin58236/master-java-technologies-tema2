package servlet;

import ejb.StudentEJB;
import ejb.TeacherEJB;
import model.Teacher;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/page/teachers")
public class TeacherPageServlet extends HttpServlet {

    @EJB
    private TeacherEJB teacherEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Teacher> teachers = teacherEJB.findAll();
        req.setAttribute("teachers", teachers);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp");
        dispatcher.forward(req, resp);  // Forward către JSP
    }
}
