package com.ftn.event_hopper.services.blocking;

import com.ftn.event_hopper.dtos.blocking.CreateBlockDTO;
import com.ftn.event_hopper.dtos.blocking.CreatedBlockDTO;
import com.ftn.event_hopper.dtos.blocking.GetBlockDTO;
import com.ftn.event_hopper.mapper.blocking.BlockDTOMapper;
import com.ftn.event_hopper.models.blocks.Block;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.repositories.blocking.BlockingRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BlockingService {

    @Autowired
    private BlockingRepository blockingRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BlockDTOMapper blockDTOMapper;

    public List<GetBlockDTO> findAll(){
        List<Block> blocks = blockingRepository.findAll();
        return blockDTOMapper.fromBlockListToGetBlockDTOList(blocks);
    }

    public GetBlockDTO findById(UUID id){
        Block block = blockingRepository.findById(id).orElse(null);
        return blockDTOMapper.fromBlockToGetBlockDTO(block);
    }

    public CreatedBlockDTO create(CreateBlockDTO createBlockDTO){

        Block block = new Block();

        Account who = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        block.setWho(who);

        Account blocked = accountRepository.findById(createBlockDTO.getBlocked()).orElse(null);
        block.setBlocked(blocked);

        block.setTimestamp(LocalDateTime.now());
        this.save(block);

        return blockDTOMapper.fromBlockToCreatedBlockDTO(block);
    }

    public void delete(UUID id) {
        Block block = blockingRepository.findById(id).orElse(null);
        if(block != null) {
            blockingRepository.delete(block);
        }
    }

    public Block save(Block block){
        return blockingRepository.save(block);
    }




}
