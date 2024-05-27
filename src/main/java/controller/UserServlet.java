package controller;

import model.User;
import service.IUserService;
import service.UserServiceIMPL;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet ", value = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;


    public void init() {
        userService = new UserServiceIMPL();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "SEARCH":
                    String keyword = request.getParameter("keyword");
                    List<User> userList = userService.findByCountry(keyword);
                    request.setAttribute("listUser", userList);
                    request.setAttribute("keyword", keyword);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("views/list.jsp");
                    dispatcher.forward(request, response);
                    break;
                case "SortAsc":
                    List<User> userListAsc = userService.sortByNameAsc();
                    request.setAttribute("listUser", userListAsc);
                    RequestDispatcher dispatcherAsc = request.getRequestDispatcher("views/list.jsp");
                    dispatcherAsc.forward(request, response);
                    break;
                case "SortDesc":
                    List<User> userListDesc = userService.sortByNameDesc();
                    request.setAttribute("listUser", userListDesc);
                    RequestDispatcher dispatcherDesc = request.getRequestDispatcher("views/list.jsp");
                    dispatcherDesc.forward(request, response);
                    break;
                case "permision":
                    addUserPermision(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                case "SEARCH":
                    String keyword = request.getParameter("keyword");
                    List<User> userList = userService.findByCountry(keyword);
                    request.setAttribute("listUser", userList);
                    request.setAttribute("keyword", keyword);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("views/list.jsp");
                    dispatcher.forward(request, response);
                    break;
                case "SortAsc":
                    List<User> userListAsc = userService.sortByNameAsc();
                    request.setAttribute("listUser", userListAsc);
                    RequestDispatcher dispatcherAsc = request.getRequestDispatcher("views/list.jsp");
                    dispatcherAsc.forward(request, response);
                    break;
                case "SortDesc":
                    List<User> userListDesc = userService.sortByNameDesc();
                    request.setAttribute("listUser", userListDesc);
                    RequestDispatcher dispatcherDesc = request.getRequestDispatcher("views/list.jsp");
                    dispatcherDesc.forward(request, response);
                    break;


            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userService.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userService.getUserById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/edit.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User newUser = new User(name, email, country);
        userService.insertUserStore(newUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/create.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User book = new User(id, name, email, country);
        userService.updateUser(book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);

        List<User> listUser = userService.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/list.jsp");
        dispatcher.forward(request, response);
    }

    private void addUserPermision(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User("TuanAnh", " kien@gmail.com ", "spanish");
        int[] permision = {1, 2, 4};
        userService.addUserTransaction(user, permision);
        response.sendRedirect("/users?action=users" );

    }

    public void destroy() {
    }


}