package org.researchbucks.util.jwt;

import org.researchbucks.model.Respondent;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RespondentDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        try {
            Respondent respondent = userRepository.findByEmail(email);
            if(respondent == null) throw new UsernameNotFoundException(CommonMessages.INVALID_RESPONDENT);
            return new RespondentDetails(respondent);
        } catch (Exception e) {
            throw new UsernameNotFoundException(CommonMessages.INVALID_RESPONDENT);
        }
    }
}
