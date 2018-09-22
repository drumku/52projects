package cn.itcast.heimatravel.web.servlet;

import cn.itcast.heimatravel.domain.Catagory;
import cn.itcast.heimatravel.service.CatagoryService;
import cn.itcast.heimatravel.service.Impl.CatagoryServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/catagory/*")
public class CatagoryServlet extends BaseServlet {
    public void findcata(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CatagoryService catagoryService=new CatagoryServiceImpl();
        List<Catagory> findcata = catagoryService.findcata();
        WriteValue(findcata,response);
    }
}
