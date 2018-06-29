package usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import usermanagement.entity.User;
import usermanagement.entity.UserAddress;


@Repository
public interface HbUserAddressRepository extends JpaRepository<UserAddress, Long> {

	List<UserAddress> findAllById(Long id);

	@Query(value = "select * from address where `address` like %:address% group by `user_id`", nativeQuery=true)
	public List<UserAddress> findAdd(@Param("address") String address);

	List<UserAddress> findByUser(User user);

}
