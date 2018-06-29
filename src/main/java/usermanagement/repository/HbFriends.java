package usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import usermanagement.entity.Friends;

@Repository
public interface HbFriends extends JpaRepository<Friends, Long> {

	@Query(value = "SELECT * FROM `friends` WHERE `login_user_id` = :userid", nativeQuery = true)
	List<Friends> findByUserId(@Param("userid")String userid);

	@Query(value = "SELECT * FROM `friends` WHERE `login_user_id` = :loginUserid AND `friend_user_id` = :userid OR `login_user_id` = :userid AND `friend_user_id` = :loginUserid", nativeQuery = true)
	public Friends findByBothId(@Param("loginUserid")String loginUserid,@Param("userid")String userid);

}
