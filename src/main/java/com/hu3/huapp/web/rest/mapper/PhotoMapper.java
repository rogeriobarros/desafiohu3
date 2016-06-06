package com.hu3.huapp.web.rest.mapper;

import com.hu3.huapp.domain.*;
import com.hu3.huapp.web.rest.dto.PhotoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Photo and its DTO PhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhotoMapper {

    PhotoDTO photoToPhotoDTO(Photo photo);

    List<PhotoDTO> photosToPhotoDTOs(List<Photo> photos);

    Photo photoDTOToPhoto(PhotoDTO photoDTO);

    List<Photo> photoDTOsToPhotos(List<PhotoDTO> photoDTOs);
}
