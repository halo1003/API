package com.busbooking.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.entities.User;
import com.busbooking.repo.UserRepository;
import com.busbooking.service.JwtService;
import com.busbooking.service.UserService;
import com.busbooking.validator.UserValidator;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

//	@RequestMapping(method = RequestMethod.GET, value = "/users")
//    @ResponseBody
//    public List<User> search(@RequestParam(value = "search") String search) {
//        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
//        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
//        Matcher matcher = pattern.matcher(search + ",");
//        while (matcher.find()) {
//            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
//        }
//         
//        Specification<User> spec = builder.build();
//        return userRepository.findAll(spec);
//    }

	/* ---------------- GET ALL USER ------------------------ */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser() {
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}

	/* ---------------- GET USER BY ID ------------------------ */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		Optional<User> user = userService.findById(id);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
	}

	/* ---------------- GET USER BY TOKEN ------------------------ */
	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
	public ResponseEntity<Object> loadUserByToken(@PathVariable("token") String token) {
		User _user = userService.loadUserByToken(token);
		if (_user != null) {
			return new ResponseEntity<Object>(userService.loadUserByToken(token), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
	}

	/* ---------------- PAGEABLE USER BY NAME ------------------------ */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Object> listUser(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
			@RequestParam(name = "e", required = false, defaultValue = "name") String element) {
		Sort sortable = null;

		if (sort.equals("asc")) {
			sortable = Sort.by(element).ascending();
		}
		if (sort.equals("desc")) {
			sortable = Sort.by(element).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		return new ResponseEntity<Object>(userService.findUser(pageable), HttpStatus.OK);
	}

	/* ---------------- CREATE NEW USER ------------------------ */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody User user, BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("Somethings are wrong. Please try again !", HttpStatus.BAD_REQUEST);
		}
		user.setRoles(new HashSet<>(userService.findByIdRole(1)));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (userService.add(user)) {
			return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Create faild!", HttpStatus.BAD_REQUEST);
		}
	}

	/* ---------------- UPDATE USER BY ADMIN------------------------ */
	@RequestMapping(value = "/{id}/updatebyadmin", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUserbyAdmin(@PathVariable("id") int id, @RequestBody User user) {
		Optional<User> userData = userService.findById(id);
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setName(user.getName());
			_user.setPassword(user.getPassword());
			_user.setAge(user.getAge());
//			_user.setActive(user.isActive());
			_user.setEmail(user.getEmail());
			_user.setPhone(user.getPhone());
			userService.add(_user);
			return new ResponseEntity<>("Updated!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Update faild", HttpStatus.NOT_FOUND);
		}
	}

	/* ---------------- UPDATE USER BY CUSTOMER------------------------ */
	@RequestMapping(value = "/{id}/updatebycustomer", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUserbyCustomer(@PathVariable("id") int id, @RequestBody User user) {
		Optional<User> userData = userService.findById(id);
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setName(user.getName());
//			_user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			_user.setAge(user.getAge());
//			_user.setActive(user.isActive());
			_user.setEmail(user.getEmail());
			_user.setPhone(user.getPhone());
			userService.add(_user);
			return new ResponseEntity<>("Updated!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Update faild", HttpStatus.NOT_FOUND);
		}
	}

	/* ---------------- UPDATE ROLES USER ------------------------ */
	@RequestMapping(value = "/{id}/setrole", method = RequestMethod.PUT)
	public ResponseEntity<String> setRoles(@PathVariable("id") int id, @RequestBody User user) {
		Optional<User> userData = userService.findById(id);
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setRoles(user.getRoles());
			userService.add(_user);
			return new ResponseEntity<>("Updated!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Update faild", HttpStatus.NOT_FOUND);
		}
	}

	/* ---------------- DELETE USER ------------------------ */
	@RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable int id) {
		Optional<User> user = userService.findById(id);
		if (user != null) {
			userService.deleteById(id);
			return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Deleted faild!", HttpStatus.NOT_FOUND);

	}

	/* ---------------- LOGIN ------------------------------ */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User user) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			if (userService.checkLogin(user)) {
				result = jwtService.generateTokenLogin(user.getUsername());
				httpStatus = HttpStatus.OK;
			} else {
				result = "Wrong username and password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

}
