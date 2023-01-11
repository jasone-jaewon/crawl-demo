package com.hyundai.crawldemo.application.port;

import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import java.net.URI;
import java.util.List;

public interface HtmlAlphanumericService {
  Alphanumeric getAlphanumericFromUris(List<URI> uris);
}
