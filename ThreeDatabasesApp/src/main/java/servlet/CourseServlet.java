package servlet;

import ejb.CourseEJB;
import model.Course;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;
@WebServlet("/courses/*")
public class CourseServlet extends HttpServlet {

    @EJB
    private CourseEJB courseEJB; // EJB cu metode findAll, find, create, update, delete

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();
        PrintWriter out = resp.getWriter();

        if (path == null || path.equals("/")) {
            List<Course> courses = courseEJB.findAll();
            out.print("[");
            for (int i = 0; i < courses.size(); i++) {
                Course c = courses.get(i);
                out.print("{\"id\":" + c.getId() + ",\"name\":\"" + c.getName() + "\",\"teacherId\":" + c.getTeacherId() + "}");
                if (i < courses.size() - 1) out.print(",");
            }
            out.print("]");
        } else {
            Long id = Long.valueOf(path.substring(1));
            Course c = courseEJB.findById(id);
            if (c != null) {
                out.print("{\"id\":" + c.getId() + ",\"name\":\"" + c.getName() + "\",\"teacherId\":" + c.getTeacherId() + "}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // poți citi parametrii direct din request, de exemplu:
        String name = req.getParameter("name");
        Long teacherId = Long.valueOf(req.getParameter("teacherId"));
        Course c = new Course(name, teacherId);
        courseEJB.create(c);

        resp.setContentType("application/json");
        resp.getWriter().print("{\"id\":" + c.getId() + ",\"name\":\"" + c.getName() + "\",\"teacherId\":" + c.getTeacherId() + "}");
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        String name = req.getParameter("name");
        Long teacherId = Long.valueOf(req.getParameter("teacherId"));
        Course c = new Course(name, teacherId);
        c.setId(id);
        courseEJB.update(c);

        resp.setContentType("application/json");
        resp.getWriter().print("{\"id\":" + c.getId() + ",\"name\":\"" + c.getName() + "\",\"teacherId\":" + c.getTeacherId() + "}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        courseEJB.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
