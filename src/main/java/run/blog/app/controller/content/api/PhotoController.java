package run.blog.app.controller.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.blog.app.model.dto.PhotoDTO;
import run.blog.app.model.params.PhotoQuery;
import run.blog.app.service.PhotoService;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Content photo controller.
 *
 * @author ryanwang
 * @date 2019-09-21
 */
@RestController("ApiContentPhotoController")
@RequestMapping("/api/content/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * List all photos
     *
     * @param sort sort
     * @return all of photos
     */
    @GetMapping(value = "latest")
    public List<PhotoDTO> listPhotos(@SortDefault(sort = "updateTime", direction = Sort.Direction.DESC) Sort sort) {
        return photoService.listDtos(sort);
    }

    @GetMapping
    public Page<PhotoDTO> pageBy(@PageableDefault(sort = "updateTime", direction = DESC) Pageable pageable,
            PhotoQuery photoQuery) {
        return photoService.pageDtosBy(pageable, photoQuery);
    }
}
