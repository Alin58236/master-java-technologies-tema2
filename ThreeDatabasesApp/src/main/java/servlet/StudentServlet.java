package servlet;

import com.google.gson.Gson;
import ejb.StudentEJB;
import model.Student;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private StudentEJB studentEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            List<Student> students = studentEJB.findAll();
            out.print(gson.toJson(students));
        } else {
            Long id = Long.valueOf(path.substring(1));
            Student s = studentEJB.findById(id);
            if (s != null) {
                out.print(gson.toJson(s));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        Student s = new Student();
        s.setName(name);
        s.setEmail(email);
        studentEJB.create(s);

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(s));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        Student s = new Student();
        s.setId(id);
        s.setName(name);
        s.setEmail(email);
        studentEJB.update(s);

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(s));
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        studentEJB.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
