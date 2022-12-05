package com.example.kursach.Controllers;


import com.example.kursach.Models.*;
import com.example.kursach.Repositories.*;
import com.sun.istack.Nullable;
import org.hibernate.MappingException;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    CopyBooksRepositorie copyBooksRepositorie;
    @Autowired
    BooksOrderRepositorie booksOrderRepositorie;
    @Autowired
    OrderRepositorie orderRepositorie;
    @Autowired
    IssuebookRepositorie issuebookRepositorie;

    @GetMapping("/")
    public String index(Model model)
    {
        Iterable<User> users = userRepositorie.findAll();
        model.addAttribute("list_employee", users);
        model.addAttribute("listRoles", Role.values());
        return ("index");
    }
    @PostMapping("/")
    public String index(Model model,@RequestParam String[] roles,@RequestParam Long id)
    {
        User user = userRepositorie.findById(id).orElseThrow();
        user.getRoles().clear();

        for(String role: roles){
            user.getRoles().add(Role.valueOf(role));
        }

        userRepositorie.save(user);
        return("redirect:/");
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
        booksRepositorie.save(books);
        CopyBook cp = new CopyBook();
        cp.setBooks(books);
        copyBooksRepositorie.save(cp);
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
        orderRepositorie.save(norder);
        return("index");
    }
    @GetMapping("/AddBookUser")
    public String AddBookUser(Model model)
    {
        Iterable<CopyBook> books= copyBooksRepositorie.findAll();
        Iterable<User> users= userRepositorie.findAll();
        model.addAttribute("user",users);
        model.addAttribute("book",books);
        return ("AddUserBook");
    }
    @PostMapping("/AddBookUser")
    public String AddBookUserPost(@RequestParam(name = "book") String cb,@RequestParam(name = "user") String user)
    {
        Books b = booksRepositorie.findBynamebooks(cb);
        CopyBook cb1= copyBooksRepositorie.findBybook_id(b.getId());
        IssueBook ib = new IssueBook();
        ib.setCopybook(cb1);
        ib.setData(Calendar.getInstance().getTime());
        issuebookRepositorie.save(ib);
        User us = userRepositorie.findByFIO(user.split(" ")[0],user.split(" ")[1],user.split(" ")[2]);
        List<IssueBook> of = us.getIssuebooks();
        of.add(ib);
        us.setIssuebooks(of);
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
        CopyBook cp= copyBooksRepositorie.findBybook_id(id);
        issuebookRepositorie.deleteCBin(cp.getId());
        copyBooksRepositorie.deleteById(cp.getId());
        booksRepositorie.deleteById(id);

        return ("redirect:/Delete");
    }
    @GetMapping("/Dellmembership/{id}")
    public String Dellmembership(@PathVariable long id)
    {
        membershipRepositorie.deleteById(id);

        return ("redirect:/Delete");
    }
    @GetMapping("/Dellprovider/{id}")
    public String Dellprovider(@PathVariable long id)
    {
        providerRepositorie.deleteById(id);

        return ("redirect:/Delete");
    }
    @GetMapping("/Statistic")
    public String Statistic(Model model)
    {
        return ("Statistic");
    }
    @GetMapping("/api/user/")
    public ResponseEntity<ArrayList<ForStatistic>> userallapi() {

        ForStatistic fs = new ForStatistic();
        fs.setBook(4);
        fs.setName("Гари потер");
        ForStatistic fs1 = new ForStatistic();
        fs1.setBook(40);
        fs1.setName("Гари");
        ForStatistic fs2 = new ForStatistic();
        fs2.setBook(14);
        fs2.setName("потер");
        ArrayList<ForStatistic> a = new ArrayList<>();
        a.add(fs);
        a.add(fs1);
        a.add(fs2);
        return ResponseEntity.ok().body(a);}
}
