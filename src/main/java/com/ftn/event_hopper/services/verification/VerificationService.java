package com.ftn.event_hopper.services.verification;

import com.ftn.event_hopper.models.verification.VerificationToken;
import com.ftn.event_hopper.models.verification.VerificationTokenState;
import com.ftn.event_hopper.repositories.verification.VerificationTokenRepository;
import com.ftn.event_hopper.services.emails.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService; // Assuming you have an EmailService set up

    public boolean sendVerificationEmail(String email) {
        VerificationToken verificationToken = new VerificationToken(email);
        tokenRepository.save(verificationToken);

        String verificationLink = "http://localhost:4200/verify-email/" + verificationToken.getToken();
        String emailBody = "Click the link below to verify your profile. This link will expire in 24 hours:\n\n" + verificationLink;

        return emailService.sendVerificationEmail(email, "Verify Your Profile", emailBody);
    }

    public VerificationTokenState verifyToken(String token) {
        Optional<VerificationToken> tokenOpt = tokenRepository.findByToken(token);
        if(tokenOpt.isEmpty()) {
            return VerificationTokenState.MISSING;
        }
        if(tokenOpt.get().getExpirationTime().isBefore(LocalDateTime.now())) {
            return VerificationTokenState.EXPIRED;
        }
        return VerificationTokenState.ACCEPTED;
    }

    public String getEmailByToken(String token) {
        Optional<VerificationToken> tokenOpt = tokenRepository.findByToken(token);
        if(tokenOpt.isEmpty()) {
            return null;
        }
        return tokenOpt.get().getEmail();
    }

}

