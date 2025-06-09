package org.researchbucks.AdminService_API.util.jwt;

import org.researchbucks.AdminService_API.model.Admin;
import org.researchbucks.AdminService_API.repository.AdminRepository;
import org.researchbucks.AdminService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Admin admin = adminRepository.findByEmail(email);
            if(admin == null) throw new UsernameNotFoundException(CommonMessages.INVALID_ADMIN);
            return new AdminDetails(admin);
        } catch (Exception e) {
            throw new UsernameNotFoundException(CommonMessages.INVALID_ADMIN);
        }
    }
}
