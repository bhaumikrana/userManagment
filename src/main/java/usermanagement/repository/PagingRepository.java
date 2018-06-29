package usermanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import usermanagement.entity.User;
import usermanagement.entity.UserAddress;

@Repository
public interface PagingRepository extends PagingAndSortingRepository<User,Long>{
	
//	@Query( "select * from Userlist " )
//	Page<User> findAllCustom( Pageable pageable );
}
