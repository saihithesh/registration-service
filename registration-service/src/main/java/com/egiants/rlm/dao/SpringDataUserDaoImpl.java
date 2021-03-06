package com.egiants.rlm.dao;

import java.util.Date;
import java.util.List;
import com.egiants.rlm.dao.config.DynamoDbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.egiants.rlm.entity.User;

@Repository
public class SpringDataUserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	DynamoDbConfig dynamoDbConfig;

	@Override
	public List<User> getUsers() {

		Iterable<User> users = this.userRepository.findAll();

		return (List<User>) users;
	}

	@Override
	public User getUser(String emailId) {
		// TODO: add Resource not found exception if user is null
		return this.userRepository.findOne(emailId);

	}

	@Override
	public User createUser(User user) {

			Date newDate = new Date();

			user.setCreatedDate(newDate);
			user.setLastModifiedDate(newDate);
			return this.userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {

		return customUpdate(user);

	}

	@Override
	public void deleteUser(String emailId) {

		this.userRepository.delete(emailId);
	}

	public User customUpdate(User user) {

		User old = userRepository.findOne(user.getEmailId());

		/* not changing EmailId, CreatedBy, UUID, CreatedDate */

		old.setFirstName(user.getFirstName());
		old.setLastName(user.getLastName());
		old.setRole(user.getRole());
		old.setLastModifiedBy(user.getLastModifiedBy());

		Date newDate = new Date();

		old.setLastModifiedDate(newDate);

		return this.userRepository.save(old);

	}

}
