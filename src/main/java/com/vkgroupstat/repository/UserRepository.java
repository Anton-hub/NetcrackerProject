package com.vkgroupstat.repository;

import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.User;
import com.vkgroupstat.vkconnection.GroupCollector;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepository {

	private final MongoTemplate mongoTemplate;

	public UserRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public User save(User user) {
		return mongoTemplate.save(user, "user");
	}

	public void delete(User user) {
		mongoTemplate.remove(user, "user");
	}

	public User refresh(User user, Integer vkUser)  {
		user.setUserId(vkUser);
		save(user);
		return user;
	}
	public User findByEmail(String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		return mongoTemplate.findOne(query, User.class);
	}
	public User findByuserId(Integer userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		return mongoTemplate.findOne(query, User.class);
	}
}
//@Repository
//public interface UserRepository extends MongoRepository<User, String> {
//	public User findByuserId(Integer groupName);
//	User findByEmail(String email);
//
//}
