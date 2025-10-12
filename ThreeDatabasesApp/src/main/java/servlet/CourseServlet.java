package servlet;

import com.google.gson.Gson;
import ejb.CourseEJB;
import model.Course;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/courses/*")
public class CourseServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private CourseEJB courseEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            List<Course> students = courseEJB.findAll();
            out.print(gson.toJson(students));
        } else {
            Long id = Long.valueOf(path.substring(1));
            Course c = courseEJB.findById(id);
            if (c != null) {
                out.print(gson.toJson(c));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        Long teacherId = Long.valueOf(req.getParameter("teacherId"));
        Course c = new Course();
        c.setName(name);
        c.setTeacherId(teacherId);
        courseEJB.create(c);

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(c));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        String name = req.getParameter("name");
        Long teacherId = Long.valueOf(req.getParameter("teacherId"));
        Course c = new Course();
        c.setId(id);
        c.setName(name);
        c.setTeacherId(teacherId);
        courseEJB.update(c);

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(c));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // e.g. /5
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course ID");
            return;
        }

        try {
            long id = Long.parseLong(pathInfo.substring(1)); // remove the slash
            courseEJB.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID format");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting course");
        }
    }
}
