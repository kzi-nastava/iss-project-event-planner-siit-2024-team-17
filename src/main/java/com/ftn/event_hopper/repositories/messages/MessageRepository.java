package com.ftn.event_hopper.repositories.messages;

import com.ftn.event_hopper.models.messages.Message;
import com.ftn.event_hopper.models.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("""
        SELECT DISTINCT CASE
            WHEN m.from.id = :accountId THEN m.to
            ELSE m.from
        END
        FROM Message m
        WHERE m.from.id = :accountId OR m.to.id = :accountId
    """)
    List<Account> findDistinctCommunicatedUsers(UUID accountId);

    @Query("""
    SELECT m
    FROM Message m
    WHERE\s
        (m.from.id = :firstAccountId AND m.to.id = :secondAccountId)
        OR\s
        (m.from.id = :secondAccountId AND m.to.id = :firstAccountId)
    ORDER BY m.timestamp DESC
""")
    Message findNewestMessageContentBetweenAccounts(UUID firstAccountId, UUID secondAccountId);

    @Query("""
    SELECT m
    FROM Message m
    WHERE\s
        (m.from.id = :firstAccount AND m.to.id = :secondAccount)
        OR\s
        (m.from.id = :secondAccount AND m.to.id = :firstAccount)
    ORDER BY m.timestamp ASC
""")
    List<Message> findMessagesBetweenAccounts(UUID firstAccount, UUID secondAccount);

}
