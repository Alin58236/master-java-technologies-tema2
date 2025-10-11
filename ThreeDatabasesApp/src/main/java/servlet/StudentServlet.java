package servlet;

import ejb.StudentEJB;
import model.Student;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @EJB
    private StudentEJB studentEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Student> students = studentEJB.findAll();
        resp.setContentType("application/json");
        resp.getWriter().write(new JSONObject().put("students", students).toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String body = req.getReader().lines().reduce("", (acc, line) -> acc + line);
        JSONObject json = new JSONObject(body);

        Student s = new Student();
        s.setName(json.getString("name"));
        s.setEmail(json.getString("email"));
        studentEJB.create(s);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Student created");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String body = req.getReader().lines().reduce("", (acc, line) -> acc + line);
        JSONObject json = new JSONObject(body);

        Long id = json.getLong("id");
        Student s = studentEJB.findById(id);
        if (s == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Student not found");
            return;
        }

        if (json.has("name")) s.setName(json.getString("name"));
        if (json.has("email")) s.setEmail(json.getString("email"));

        studentEJB.update(s);
        resp.getWriter().write("Student updated");
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

        Long id = Long.valueOf(idParam);
        studentEJB.delete(id);
        resp.getWriter().write("Student deleted");
    }
}
