package com.laker.admin.module.remote.fegin;

import com.laker.admin.framework.fegin.LakerFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@LakerFeignClient(url ="https://api.github.com")
public interface GitHub {
  @GetMapping("/repos/{owner}/{repo}/contributors")
  List<Contributor> contributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
  @PostMapping("/repos/{owner}/{repo}/issues")
  void createIssue(Issue issue, @PathVariable("owner") String owner, @PathVariable("repo") String repo);

}