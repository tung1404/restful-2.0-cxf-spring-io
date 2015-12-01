package vn.store.service;

import java.util.Collection;

import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.store.jpa.domain.User;
import vn.store.service.repositories.UserRepository;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;

	public Collection<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User getUser(Integer id) {
		return userRepository.findOne(id);
	}
	
	public User addUser(User user) {
		return userRepository.save(user);
	}
	
	public User login(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Transactional(rollbackFor = { ServiceException.class })
	public void updateUser(User user) throws Exception {
		try {
			userRepository.save(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException("Update has error", e);
		}
	}
}
