package org.researchbucks.ResearcherService_API.util.jwt;

import org.researchbucks.ResearcherService_API.model.Researcher;
import org.researchbucks.ResearcherService_API.repository.ResearcherRepository;
import org.researchbucks.ResearcherService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResearcherDetailsService implements UserDetailsService {

    @Autowired
    private ResearcherRepository researcherRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Researcher researcher = researcherRepository.findResearcherByEmail(email);
            if(researcher == null) throw new UsernameNotFoundException(CommonMessages.INVALID_RESEARCHER);
            return new ResearcherDetails(researcher);
        } catch (Exception e) {
            throw new UsernameNotFoundException(CommonMessages.INVALID_RESEARCHER);
        }
    }
}
