/**
 * 
 */
package vn.store.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.store.jpa.domain.User;


/**
 * @author siva
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmailAndPassword(String email, String password);
}
