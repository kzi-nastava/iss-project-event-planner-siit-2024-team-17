package com.ftn.event_hopper.repositories.invitations;

import com.ftn.event_hopper.models.invitations.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
}
