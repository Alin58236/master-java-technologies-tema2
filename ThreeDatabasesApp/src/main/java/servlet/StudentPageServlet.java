package servlet;

import ejb.StudentEJB;
import model.Student;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/page/students")
public class StudentPageServlet extends HttpServlet {
    @EJB
    private StudentEJB studentEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = studentEJB.findAll();
        req.setAttribute("students", students);

        req.getRequestDispatcher("/WEB-INF/jsp/students.jsp").forward(req, resp);
    }
}
