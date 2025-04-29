package com.ftn.event_hopper.mapper.blocking;

import com.ftn.event_hopper.dtos.blocking.CreatedBlockDTO;
import com.ftn.event_hopper.dtos.blocking.GetBlockDTO;

import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.mapper.users.AccountDTOMapper;
import com.ftn.event_hopper.models.blocks.Block;

import com.ftn.event_hopper.models.users.Account;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlockDTOMapper {
    private final ModelMapper modelMapper;
    private AccountDTOMapper accountDTOMapper;

    public BlockDTOMapper(ModelMapper modelMapper, AccountDTOMapper accountDTOMapper) {
        this.modelMapper = modelMapper;
        this.accountDTOMapper = accountDTOMapper;
        configureMappings();
    }


    public void configureMappings() {
        Converter<Account, SimpleAccountDTO> converter = context ->
                accountDTOMapper.fromAccountToSimpleDTO(context.getSource());

        modelMapper.typeMap(Block.class, GetBlockDTO.class)
                .addMappings(mapper -> mapper.using(converter)
                        .map(Block::getWho, GetBlockDTO::setWho));

        modelMapper.typeMap(Block.class, GetBlockDTO.class)
                .addMappings(mapper -> mapper.using(converter)
                        .map(Block::getBlocked, GetBlockDTO::setBlocked));
    }

    public GetBlockDTO fromBlockToGetBlockDTO(Block block) {
        return modelMapper.map(block, GetBlockDTO.class);
    }

    public List<GetBlockDTO> fromBlockListToGetBlockDTOList(List<Block> blocks){
        return blocks.stream()
                .map(this::fromBlockToGetBlockDTO)
                .collect(Collectors.toList());
    }

    public CreatedBlockDTO fromBlockToCreatedBlockDTO(Block block) {
        return modelMapper.map(block, CreatedBlockDTO.class);
    }

}
