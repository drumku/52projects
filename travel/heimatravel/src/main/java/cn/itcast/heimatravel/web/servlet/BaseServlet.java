package cn.itcast.heimatravel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        int i = requestURI.lastIndexOf("/" )+ 1;
        String substring = requestURI.substring(i);

        try {
            Method method = this.getClass().getMethod(substring,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void WriteValue(Object object,HttpServletResponse response){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("text/html;charset=utf-8");
        try {
            objectMapper.writeValue(response.getWriter(),object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
