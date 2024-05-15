package com.example.demo.userDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Account;
import com.example.demo.repository.AccoutRepository;
import com.example.demo.userDetail.CustomUserDetail;
@Component
public class UserDetailServiceIplmm implements UserDetailsService {
	@Autowired
	AccoutRepository accoutRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account acc = accoutRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));;
		return new CustomUserDetail(acc);
	}

}
