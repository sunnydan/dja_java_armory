package com.hilla.daniel.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hilla.daniel.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long>, UserDetailsService {

	List<User> findAll();

	User findByUsername(String username);

	List<User> findByPermissionLevel(int permissionLevel);

	public default void saveUser(User user, BCryptPasswordEncoder bCryptPasswordEncoder, int permissionLevel) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setPermissionLevel(permissionLevel);
		save(user);
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
		for (GrantedAuthority i : authorities) {
			System.out.println(i);
		}
		return authorities;
	}

}
