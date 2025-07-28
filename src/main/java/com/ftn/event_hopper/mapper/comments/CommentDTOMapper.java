package com.ftn.event_hopper.mapper.comments;

import com.ftn.event_hopper.dtos.comments.GetCommentDTO;
import com.ftn.event_hopper.dtos.comments.SimpleCommentAuthorDTO;
import com.ftn.event_hopper.dtos.comments.SimpleCommentDTO;
import com.ftn.event_hopper.dtos.comments.UpdatedCommentDTO;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.users.EventOrganizer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CommentDTOMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public CommentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {

        modelMapper.typeMap(Comment.class, SimpleCommentDTO.class)
                .addMappings(mapper -> mapper.using(accountConverter)
                    .map(Comment::getAuthor, SimpleCommentDTO::setAuthor));

    }

    public SimpleCommentDTO fromCommentToSimplecommentDTO(Comment comment) {
        return modelMapper.map(comment, SimpleCommentDTO.class);
    }

    public Collection<SimpleCommentDTO> fromCommentListToSimplecommentDTOCollection(Collection<Comment> source) {
        return source.stream()
                .map(this::fromCommentToSimplecommentDTO)
                .toList();
    }

    private final Converter<EventOrganizer, SimpleCommentAuthorDTO> accountConverter = context -> {
        EventOrganizer source = context.getSource();

        SimpleCommentAuthorDTO simpleCommentAuthorDTO = new SimpleCommentAuthorDTO();
        simpleCommentAuthorDTO.setId(source.getId());
        simpleCommentAuthorDTO.setProfilePicture(source.getProfilePicture());
        simpleCommentAuthorDTO.setName(source.getName());
        simpleCommentAuthorDTO.setSurname(source.getSurname());

        return simpleCommentAuthorDTO;
    };

    public GetCommentDTO fromCommentToGetCommentDTO(Comment comment) {
        return modelMapper.map(comment, GetCommentDTO.class);
    }

    public List<GetCommentDTO> fromCommentListToGetCommentDTOCollection(Collection<Comment> source) {
        return source.stream()
                .map(this::fromCommentToGetCommentDTO)
                .toList();
    }

    public UpdatedCommentDTO fromCommentToUpdatedCommentDTO(Comment comment) {
        return modelMapper.map(comment, UpdatedCommentDTO.class);
    }
}
