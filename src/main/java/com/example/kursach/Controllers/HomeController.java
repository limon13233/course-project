package com.example.kursach.Controllers;


import com.example.kursach.Models.*;
import com.example.kursach.Repositories.*;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.hibernate.exception.JDBCConnectionException;

import javax.validation.Valid;
import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

@Controller
public class HomeController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepositorie userRepositorie;
    @Autowired
    BooksRepositorie booksRepositorie;
    @Autowired
    ProviderRepositorie providerRepositorie;
    @Autowired
    MembershipRepositorie membershipRepositorie;
    @Autowired
    BooksOrderRepositorie booksOrderRepositorie;
    @Autowired
    OrderRepositorie orderRepositorie;
    @Autowired
    IssuebookRepositorie issuebookRepositorie;
    @Autowired
    LibraryfundsRepositorie libraryfundsRepositorie;

    String message;

    @GetMapping("/")
    public String index(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User employee = userRepositorie.findByusername(auth.getName());
        if(employee==null)
        {
            return("redirect:/login");
        }
        if(employee.getRoles().contains(Role.USER))
        {
            return("redirect:/profile");
        }
        if(employee.getRoles().contains(Role.ADMIN))
        {
            Iterable<User> users = userRepositorie.findAll();
            model.addAttribute("list_employee", users);
            model.addAttribute("listRoles", Role.values());
            return("redirect:/backup");
        }
        if(employee.getRoles().contains(Role.EMPLOYEE))
        {
            return("redirect:/Add");
        }

        Iterable<User> users = userRepositorie.findAll();
        model.addAttribute("list_employee", users);
        model.addAttribute("listRoles", Role.values());
        return ("index");
    }
    @GetMapping("/editrole")
    public String edit_role(Model model)
    {

        Iterable<User> users = userRepositorie.findAll();
        model.addAttribute("list_employee", users);
        model.addAttribute("listRoles", Role.values());
        return ("editrole");
    }
    @PostMapping("/editrole")
    public String edit_role(Model model,@RequestParam String[] roles,@RequestParam Long id)
    {
        User user = userRepositorie.findById(id).orElseThrow();
        user.getRoles().clear();

        for(String role: roles){
            user.getRoles().add(Role.valueOf(role));
        }

        userRepositorie.save(user);
        return("redirect:/editrole");
    }
    @GetMapping("/Add")
    public String Add(User user,Books books,Provider provider,Membership membership)
    {
        return ("Add");
    }
    @PostMapping("/Addbooks")
    public String Addbooks(
            @Valid Books books, BindingResult bindingResult,User user,Provider provider,Membership membership)
    {
        if (bindingResult.hasErrors())
            return ("Add");
        try {
            booksRepositorie.insertbook(books.getnamebooks(), books.getgenre(), books.getyearrelese(), books.getauthor());
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        return ("redirect:/Add");
    }

    @PostMapping("/Addemployee")
    public String Addemployee(
            @Valid User user,BindingResult bindingResult,Books books,Provider provider,Membership membership)
    {
        if (bindingResult.hasErrors()) {

            return ("Add");
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.EMPLOYEE));
        userRepositorie.save(user);
        return ("redirect:/Add");
    }
    @PostMapping("/Adduser")
    public String Adduser(
            @Valid User user, BindingResult bindingResult, Books books,Provider provider,Membership membership)
    {
        if (bindingResult.hasErrors())
            return ("Add");
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        userRepositorie.save(user);
        return ("redirect:/Add");
    }
    @PostMapping("/Addprovider")
    public String Addprovider(
            @Valid Provider provider, BindingResult bindingResult, Books books,User user,Membership membership)
    {
        if (bindingResult.hasErrors())
            return ("Add");
        providerRepositorie.save(provider);
        return ("redirect:/Add");
    }
    @PostMapping("/Addmembership")
    public String Addmembership(
            @Valid Membership membership, BindingResult bindingResult, Books books, User user,Provider provider)
    {
        if (bindingResult.hasErrors())
            return ("Add");
        membership.setDateregistration(Calendar.getInstance().getTime());
        membershipRepositorie.save(membership);
        return ("redirect:/Add");
    }


    @GetMapping("/OrderCreate")
    public String createOrder(Model model)
    {
        Iterable<Books> books= booksRepositorie.findAll();
        Iterable<Provider> pr= providerRepositorie.findAll();
        model.addAttribute("providers",pr);
        model.addAttribute("list_books",books);
        return ("OrderCreate");
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String crOrder(@RequestParam String provider1, @RequestParam String[] namesbooks, @RequestParam int[] amount)
    {

        order norder = new order();
        for (String name: namesbooks
             ) {
            BooksOrder cart = new BooksOrder();
            cart.setBooks(booksRepositorie.findBynamebooks(name));
            cart.setOrder(norder);
            cart.setAmount(amount[Arrays.asList(namesbooks).indexOf(name)]);
            booksOrderRepositorie.save(cart);
        }


        norder.setdateregistration(Calendar.getInstance().getTime());
        norder.setProvider(providerRepositorie.findBynameprovider(provider1));
        norder.setStatus(false);
        orderRepositorie.save(norder);
        return("redirect:/");
    }
    @GetMapping("/AddBookUser")
    public String AddBookUser(Model model)
    {
        Iterable<Books> books= booksRepositorie.findAll();
        ArrayList<Books> b = new ArrayList<>();
        for (Books books1:books
             ) {
            for (LibraryFund lf: books1.getLibraryFunds()
                 ) {
                if(lf.getAmount()>0)
                {
                    b.add(books1);
                    break;
                }
            }
        }
        Iterable<User> users= userRepositorie.findAll();
        model.addAttribute("user",users);
        model.addAttribute("book",b);
        return ("AddUserBook");
    }
    @PostMapping("/AddBookUser")
    public String AddBookUserPost(@RequestParam(name = "book") String cb,@RequestParam(name = "user") String user)
    {
        Books b = booksRepositorie.findBynamebooks(cb);
        IssueBook ib = new IssueBook();
        ib.setBooks2(b);
        ib.setData(Calendar.getInstance().getTime());
        ib.setStatus(false);
        User us = userRepositorie.findByFIO(user.split(" ")[0],user.split(" ")[1],user.split(" ")[2]);
        List<IssueBook> of = us.getIssuebooks();
        of.add(ib);
        us.setIssuebooks(of);
        ib.setUser(us);
        issuebookRepositorie.save(ib);
        userRepositorie.save(us);
        return ("redirect:/AddBookUser");
    }
    @GetMapping("/AddMeembershipUser")
    public String AddMembershipUser(Model model)
    {
        Iterable<Membership> ms= membershipRepositorie.findAll();
        Iterable<User> users= userRepositorie.findAll();
        model.addAttribute("user",users);
        model.addAttribute("membership",ms);
        return ("AddMeembershipUser");
    }
    @PostMapping("/AddMeembershipUser")
    public String AddMembershipUser1(@RequestParam(name = "membership") String cb,@RequestParam(name = "user") String user)
    {
       User us = userRepositorie.findByFIO(user.split(" ")[0],user.split(" ")[1],user.split(" ")[2]);
        Membership membership = membershipRepositorie.findByperiod(cb);
        membership.getUsers().add(us);
        us.setMembership(membership);
        userRepositorie.save(us);
        membershipRepositorie.save(membership);
        return ("redirect:/AddMeembershipUser");
    }
    @GetMapping("/Delete")
    public String Delete(Model model, User user,Books books,Provider provider,Membership membership)
    {
        Iterable<User> users = userRepositorie.findAll();
        Iterable<Membership> ms = membershipRepositorie.findAll();
        Iterable<Provider> pr = providerRepositorie.findAll();
        ArrayList<User> employee = new ArrayList<User>();
        ArrayList<User> client = new ArrayList<User>();
        Iterable<Books> book = booksRepositorie.findAll();

        for (User us:users) {
            if(us.getRoles().contains(Role.EMPLOYEE))
            {
                employee.add(us);
            }
            else
            {
                client.add(us);
            }
        }

        model.addAttribute("list_employee", employee);
        model.addAttribute("list_client", client);
        model.addAttribute("list_book", book);
        model.addAttribute("list_membership", ms);
        model.addAttribute("list_provider", pr);
        model.addAttribute("message",message);
        return ("Delete");
    }


    @GetMapping("/Dellemployee/{id}")
    public String Dellemployee(@PathVariable long id)
    {
        userRepositorie.deleteById(id);
        return ("redirect:/Delete");
    }

    @GetMapping("/Dellbook/{id}")
    public String Dellbook(@PathVariable long id)
    {
        libraryfundsRepositorie.deleteCBin(id);
        issuebookRepositorie.deleteCBin(id);
        booksRepositorie.deleteById(id);

        return ("redirect:/Delete");
    }
    @GetMapping("/Dellmembership/{id}")
    public String Dellmembership(@PathVariable long id)
    {
        membershipRepositorie.deleteById(id);

        return ("redirect:/Delete");
    }
    @PostMapping("/Dellprovider/{id}")
    public String Dellprovider(@PathVariable long id,Model model)
    {
        message = providerRepositorie.deleteById(id);
        model.addAttribute("message", message);
        return ("redirect:/Delete");
    }
    @GetMapping("/Statistic")
    public String Statistic(Model model)
    {
        return ("Statistic");
    }
    @GetMapping("/api/user/")
    public ResponseEntity<ArrayList<ForStatistic>> userallapi() {

        ArrayList<ForStatistic> a = new ArrayList<>();
        Iterable<Books> cb=booksRepositorie.findAll();

        for (Books cop:cb
             ) {
            ForStatistic fs = new ForStatistic();
            fs.setBook(issuebookRepositorie.countBybooks2_id(cop.getId()));
            fs.setName(cop.getnamebooks());
            a.add(fs);
        }
        return ResponseEntity.ok().body(a);
    }
    @PostMapping("/BackupExport")
    public String backup()
            throws IOException, InterruptedException {
        String command = String.format("mysqldump -u%s --password=%s --add-drop-table --column-statistics=0 --databases %s -r %s",
                "root", "", "kursach", "D:\\OSPanel\\bec.sql");
        Process process = Runtime.getRuntime().exec(command);
        //System.out.println(process.exitValue());
        int processComplete = process.waitFor();
        if(processComplete==0)
        {
        return("redirect:/");}
        else
        { return("Static");
        }
    }
    @PostMapping("/BackupImport")
    public String backupImport()
            throws IOException, InterruptedException {
        String[] command = new String[]{
                "mysql",
                "-u" + "root",
                "--password=" + "",
                "-e",
                " source " + "D:\\OSPanel\\bec.sql",
                "kursach"
        };
        Process runtimeProcess = Runtime.getRuntime().exec(command);
        int processComplete = runtimeProcess.waitFor();
        if(processComplete==0)
        {
            return("redirect:/");}
        else
        {
            return("Static");
        }
    }
    @GetMapping("/ViewOrder")
    public String ViewOrder(Model model)
    {
        Iterable<order> orders = orderRepositorie.findAll();
        ArrayList<order> complitedorders = new ArrayList<order>();
        ArrayList<order> notcomlitedorders = new ArrayList<order>();
        for (order o:orders) {
            if(o.isStatus())
            {
                complitedorders.add(o);
            }
            else
            {
                notcomlitedorders.add(o);
            }
        }
        model.addAttribute("list_corders", complitedorders);
        model.addAttribute("list_ncorders", notcomlitedorders);
        return("ViewOrders");
    }
    @PostMapping("/ViewOrder")
    public String ViewOrder1(@RequestParam long id)
    {
        order o = orderRepositorie.findById(id).orElseThrow();
        o.setStatus(true);
        orderRepositorie.save(o);
        for (BooksOrder bo:o.getBooksorders()
             ) {
            LibraryFund lf = libraryfundsRepositorie.findBybooks1_id(bo.getBooks().getId());
            lf.setAmount(bo.getAmount());
            libraryfundsRepositorie.save(lf);
        }

        return("redirect:/ViewOrder");
    }
    @GetMapping("/profile")
    public String profile(Model model,User user)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = userRepositorie.findByusername(auth.getName());
        model.addAttribute("user_p",user);
        ArrayList<IssueBook> issueBookArrayList = new ArrayList<>();
        for (IssueBook is:user.getIssuebooks()
             ) {
            if(!is.isStatus())
            {
                issueBookArrayList.add(is);
            }

        }
        model.addAttribute("user",user);
        model.addAttribute("list_issue",issueBookArrayList);
        model.addAttribute("list_book",booksRepositorie.findAll());

        return("ClientPage");
    }
    @PostMapping("/profile")
    public String profile1(@Valid User user,BindingResult bindingResult,Model model)
    {
        if(bindingResult.hasErrors())
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            user = userRepositorie.findByusername(auth.getName());
            model.addAttribute("user_p",user);
            ArrayList<IssueBook> issueBookArrayList = new ArrayList<>();
            for (IssueBook is:user.getIssuebooks()
            ) {
                if(!is.isStatus())
                {
                    issueBookArrayList.add(is);
                }

            }
            model.addAttribute("user",user);
            model.addAttribute("list_issue",issueBookArrayList);
            model.addAttribute("list_book",booksRepositorie.findAll());

            return("ClientPage");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User employee = userRepositorie.findByusername(auth.getName());
        employee.setSurname(user.getSurname());
        employee.setName(user.getName());
        employee.setMiddleName(user.getMiddleName());
        employee.setSerial_passport(user.getSerial_passport());
        employee.setNumber_passport(user.getNumber_passport());
        employee.setBirthday(user.getBirthday());
        employee.setPhone(user.getPhone());
        userRepositorie.save(employee);
        return("redirect:/profile");
    }
    @GetMapping("/filter")
    public String EmployeeFilter(Model model,
                                 @RequestParam(name = "search") String name) {

        List<User> appliances = userRepositorie.findBysurnameContains(name);
        model.addAttribute("searchRes", appliances);
        return ("indexFilter");
    }
    @GetMapping("/edit")
    public String Edit(Model model,User user,Books books,Provider provider,Membership membership) {
        Iterable<User> users = userRepositorie.findAll();
        Iterable<Membership> ms = membershipRepositorie.findAll();
        Iterable<Provider> pr = providerRepositorie.findAll();
        ArrayList<User> employee = new ArrayList<User>();
        ArrayList<User> client = new ArrayList<User>();
        Iterable<Books> book = booksRepositorie.findAll();

        for (User us:users) {
            if(us.getRoles().contains(Role.EMPLOYEE))
            {
                employee.add(us);
            }
            else
            {
                client.add(us);
            }
        }

        model.addAttribute("list_employee", employee);
        model.addAttribute("list_client", client);
        model.addAttribute("list_book", book);
        model.addAttribute("list_membership", ms);
        model.addAttribute("list_provider", pr);
        return ("Edit");
    }

    @PostMapping("/edit_user")
    public String Edit(@Valid User user,BindingResult bindingResult,Model model,@RequestParam long id)
    {

        if(bindingResult.hasErrors()) {

            return("Edit");
        }
        User employee = userRepositorie.findById(id).orElseThrow();
        employee.setSurname(user.getSurname());
        employee.setName(user.getName());
        employee.setMiddleName(user.getMiddleName());
        employee.setSerial_passport(user.getSerial_passport());
        employee.setNumber_passport(user.getNumber_passport());
        employee.setBirthday(user.getBirthday());
        employee.setPhone(user.getPhone());
        employee.setPassword(employee.getPassword());
        employee.setUsername(employee.getUsername());
        employee.setActive(employee.getActive());
        userRepositorie.save(employee);
        return("redirect:/edit");

    }

    @GetMapping("/registration")
    public String reg(User user)
    {
        return("registration");
    }
    @PostMapping("/registration")
    public String reg1(User employee,
                      Model model) {

        if(userRepositorie.findByusername(employee.getUsername()) != null) {
            model.addAttribute("error", "Логин занят!");
            return ("registration");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setActive(true);
        employee.setRoles(Collections.singleton(Role.USER));

        userRepositorie.save(employee);

        return ("redirect:/login");
    }

    @GetMapping("/backup")
    public String backup(User user)
    {
        return("Backup");
    }

}
