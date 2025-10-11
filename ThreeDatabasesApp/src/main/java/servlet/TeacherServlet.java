package servlet;

import ejb.TeacherEJB;
import model.Teacher;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import org.json.JSONObject;
import java.util.List;

@WebServlet("/teachers")
public class TeacherServlet extends HttpServlet {

    @EJB
    private TeacherEJB teacherEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println(" TeacherServlet GET TEACHERS");

        List<Teacher> teachers = teacherEJB.findAll();
        resp.setContentType("application/json");

        System.out.println(" TeacherServlet AFTER GET TEACHERS");
        resp.getWriter().write(new JSONObject().put("teachers", teachers).toString());
        System.out.println(" AFTER RESPONSE");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JSONObject json = new JSONObject(req.getReader().lines().reduce("", (a, b) -> a + b));

        Teacher t = new Teacher();
        t.setName(json.getString("name"));
        t.setDepartment(json.getString("department"));
        teacherEJB.create(t);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Teacher created");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JSONObject json = new JSONObject(req.getReader().lines().reduce("", (a, b) -> a + b));

        Long id = json.getLong("id");
        Teacher t = teacherEJB.findById(id);
        if (t == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Teacher not found");
            return;
        }

        if (json.has("name")) t.setName(json.getString("name"));
        if (json.has("department")) t.setDepartment(json.getString("department"));

        teacherEJB.update(t);
        resp.getWriter().write("Teacher updated");
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
        teacherEJB.delete(id);
        resp.getWriter().write("Teacher deleted");
    }
}
