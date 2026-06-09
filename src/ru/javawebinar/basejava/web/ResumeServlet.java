package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    Storage storage = Config.get().getStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(
                "<html>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "border:1px solid black }\n" +
                "</style>\n" +
                "<body>\n" +
                "<h2>Список соискателей</h2>\n" +
                "<table style= width:100%>\n" +
                "    <tr>\n" +
                "        <th>ID</th>\n" +
                "        <th>Name</th>\n" +
                "    </tr>\n");
        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "<tr>\n" +
                    "     <td>" + resume.getUuid() + "</td>\n" +
                    "     <td>" + resume.getFullName() + "</td>\n" +
                    "</tr>\n");
        }
        writer.write("</table>\n" +
                     "</body>\n" +
                     "</html>\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
