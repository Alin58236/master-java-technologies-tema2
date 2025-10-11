package servlet;

import ejb.CourseEJB;
import model.Course;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import org.json.JSONObject;
import java.util.List;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {

    @EJB
    private CourseEJB courseEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Course> courses = courseEJB.findAll();
        resp.setContentType("application/json");
        resp.getWriter().write(new JSONObject().put("courses", courses).toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JSONObject json = new JSONObject(req.getReader().lines().reduce("", (a, b) -> a + b));

        Course c = new Course();
        c.setName(json.getString("name"));
        c.setTeacherId(json.getLong("teacher_id"));
        courseEJB.create(c);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Course created");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JSONObject json = new JSONObject(req.getReader().lines().reduce("", (a, b) -> a + b));

        Long id = json.getLong("id");
        Course c = courseEJB.findById(id);
        if (c == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Course not found");
            return;
        }

        if (json.has("name")) c.setName(json.getString("name"));
        if (json.has("teacher_id")) c.setTeacherId(json.getLong("teacher_id"));

        courseEJB.update(c);
        resp.getWriter().write("Course updated");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing id parameter");
            return;
        }

        Long id = Long.parseLong(idParam);
        courseEJB.delete(id);
        resp.getWriter().write("Course deleted");
    }
}
