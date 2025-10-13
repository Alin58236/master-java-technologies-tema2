package servlet;

import ejb.CourseEJB;
import ejb.TeacherEJB;
import model.Course;
import model.Teacher;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/page/courses")
public class CoursePageServlet extends HttpServlet {

    @EJB
    private CourseEJB courseEJB;
    @EJB
    private TeacherEJB teacherEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Course> courses = courseEJB.findAll();
        List<Teacher> teachers = teacherEJB.findAll();
        Map<Long, String> teacherNames = new HashMap<>();

        for (Teacher t : teachers) {
            teacherNames.put(t.getId(), t.getName());
        }

        req.setAttribute("courses", courses);
        req.setAttribute("teacherNames", teacherNames);

        // forward către JSP
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/courses.jsp");
        dispatcher.forward(req, resp);
    }
}
