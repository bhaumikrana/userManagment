package usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import usermanagement.entity.Request;

@Repository
public interface HbRequest extends JpaRepository<Request, Long> {

	List<Request> findByGetRequestUserId(String id);

	@Query(value = "SELECT id FROM request WHERE  `get_request_user_id` = :fuserId AND `send_request_user_id` = :userId",nativeQuery=true)
	public Long getId(@Param("fuserId")String fuserId, @Param("userId")String userId);

	List<Request> findBySendRequestUserId(String id);

}
