package servlet;

import com.google.gson.Gson;
import ejb.TeacherEJB;
import model.Teacher;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/teachers/*")
public class TeacherServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private TeacherEJB teacherEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            List<Teacher> teachers = teacherEJB.findAll();
            out.print(gson.toJson(teachers));
        } else {
            Long id = Long.valueOf(path.substring(1));
            Teacher t = teacherEJB.findById(id);
            if (t != null) {
                out.print(gson.toJson(t));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String department = req.getParameter("department");
        Teacher t = new Teacher();
        t.setName(name);
        t.setDepartment(department);
        teacherEJB.create(t);

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(t));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        String name = req.getParameter("name");
        String department = req.getParameter("department");
        Teacher t = new Teacher();
        t.setId(id);
        t.setName(name);
        t.setDepartment(department);
        teacherEJB.update(t);

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(t));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        teacherEJB.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
