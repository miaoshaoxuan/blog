package run.blog.app.service.impl;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Service;
import run.blog.app.service.TraceService;

import java.util.List;

/**
 * @author johnniang
 * @date 19-6-18
 */
@Service
public class TraceServiceImpl implements TraceService {

    private final HttpTraceRepository httpTraceRepository;

    public TraceServiceImpl(HttpTraceRepository httpTraceRepository) {
        this.httpTraceRepository = httpTraceRepository;
    }

    @Override
    public List<HttpTrace> listHttpTraces() {
        return httpTraceRepository.findAll();
    }
}
