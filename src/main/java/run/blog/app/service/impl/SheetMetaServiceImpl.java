package run.blog.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import run.blog.app.exception.NotFoundException;
import run.blog.app.model.entity.SheetMeta;
import run.blog.app.repository.SheetMetaRepository;
import run.blog.app.repository.SheetRepository;
import run.blog.app.service.SheetMetaService;

/**
 * Sheet meta service implementation class.
 *
 * @author ryanwang
 * @author ikaisec
 * @date 2019-08-04
 */
@Slf4j
@Service
public class SheetMetaServiceImpl extends BaseMetaServiceImpl<SheetMeta> implements SheetMetaService {

    private final SheetMetaRepository sheetMetaRepository;

    private final SheetRepository sheetRepository;

    public SheetMetaServiceImpl(SheetMetaRepository sheetMetaRepository,
            SheetRepository sheetRepository) {
        super(sheetMetaRepository);
        this.sheetMetaRepository = sheetMetaRepository;
        this.sheetRepository = sheetRepository;
    }

    @Override
    public void validateTarget(@NotNull Integer sheetId) {
        sheetRepository.findById(sheetId)
                .orElseThrow(() -> new NotFoundException("查询不到该页面的信息").setErrorData(sheetId));
    }
}
