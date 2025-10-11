package servlet;

import ejb.TeacherEJB;
import model.Teacher;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/teachers/*")
public class TeacherServlet extends HttpServlet {

    @EJB
    private TeacherEJB teacherEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            List<Teacher> teachers = teacherEJB.findAll();
            out.print("[");
            for (int i = 0; i < teachers.size(); i++) {
                Teacher t = teachers.get(i);
                out.print("{\"id\":" + t.getId() + ",\"name\":\"" + t.getName() + "\"}");
                if (i < teachers.size() - 1) out.print(",");
            }
            out.print("]");
        } else {
            Long id = Long.valueOf(path.substring(1));
            Teacher t = teacherEJB.findById(id);
            if (t != null) {
                out.print("{\"id\":" + t.getId() + ",\"name\":\"" + t.getName() + "\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        Teacher t = new Teacher();
        t.setName(name);
        teacherEJB.create(t);

        resp.setContentType("application/json");
        resp.getWriter().print("{\"id\":" + t.getId() + ",\"name\":\"" + t.getName() + "\"}");
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        String name = req.getParameter("name");
        Teacher t = new Teacher();
        t.setId(id);
        t.setName(name);
        teacherEJB.update(t);

        resp.setContentType("application/json");
        resp.getWriter().print("{\"id\":" + t.getId() + ",\"name\":\"" + t.getName() + "\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        teacherEJB.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
