package sy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.dao.CustomerDao;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired 
	private CustomerDao customerDao;

	@RequestMapping("/{id}")
	public String getCustomer(@PathVariable String id, HttpServletRequest request) {
		request.setAttribute("customer", customerDao.queryById(id));
		return "showCustomer";
	}

}
