package usermanagement.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import usermanagement.entity.User;


@Repository
public interface HbUserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public List<User> findByFirstNameOrLastNameOrEmailOrCityLike(String word, String word2, String word3, String word4);
	
//	public SQLQuery createSQLQuery(String sqlString)  {
//		
//	String sql = "SELECT * FROM EMPLOYEE WHERE id = :employee_id";
//	SQLQuery query = session.createSQLQuery(sql);
//	query.addEntity(Employee.class);
//	query.setParameter("employee_id", 10);
//	List results = query.list();
//	@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
//	  User findByEmailAddress(String emailAddress);
//	
//	@Query("From User u WHERE u.email = :email")
//	public User findByEmailQuery(@Param("email") String email); 
//	
//	@Query("Select u From User u WHERE u.firstName = :firstName")
//	public User findByFirstname(@Param("firstName") String firstName); 
//	
//	@Query(value =  "select u userlist u WHERE u.First_Name = :firstName", nativeQuery = true)
//	public User findByFirstnameQuery(@Param("firstName") String firstName); 
	
//	@Query("SELECT * FROM Userlist where firstName LIKE  :firstName")
//	List<User> findByQuery(@Param("firstName") String firstName);
	
//	@Query("SELECT u FROM USERLIST u WHERE u.firstName LIKE  '%:firstName%'")
//	public List<User> findByQuery(@Param("firstName") String firstName);
	
	@Query(value =  "SELECT * FROM `userlist` WHERE `city` like %:city% or `first_name` like  %:first_name% or `last_name` like %:last_name% or `email` like %:email%", nativeQuery = true)
	public List<User> findByQuery(@Param("city") String city,@Param("first_name") String first_name,@Param("last_name") String last_name,@Param("email") String email);

	public User findById(String id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE USERLIST SET `password`= :password, `confirm_password`= :confirm_password WHERE `id`= :id", nativeQuery=true)
	public void upadtePassword(@Param("password") String password,@Param("confirm_password") String confirm_password,@Param("id") long ld);

	@Query(value =  "SELECT * FROM `userlist` WHERE `first_name` like  %:first_name% or `last_name` like %:last_name%", nativeQuery = true)
	public List<User> findByName(@Param("first_name") String first_name,@Param("last_name") String last_name);
	
	@Query(value =  "SELECT * FROM `userlist` WHERE `first_name` like  :first_name% or `last_name` like :last_name%", nativeQuery = true)
	public List<User> findByFirstNameLastName(@Param("first_name") String first_name,@Param("last_name") String last_name);

	public List<User> findByFirstName(String string); 

//	public List<User> findByFirstNameLike(String searchstr); 
}

