package com.hilla.daniel.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.hilla.daniel.models.User;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long>, UserDetailsService {

	List<User> findAll();

	User findByUsername(String username);

	User findOneById(int id);

	List<User> findByPermissionLevel(int permissionLevel);

	public default Page<User> usersPerPage(int pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber, 5, Sort.Direction.ASC, "username");
		return findAll(pageRequest);
	}

	public default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthorities(user));
	}

	public default List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (user.getPermissionLevel() >= 2) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		if (user.getPermissionLevel() == 3) {
			authorities.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
		}
		return authorities;
	}

}
