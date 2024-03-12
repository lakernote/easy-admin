package com.laker.admin.module.remote.fegin;

import lombok.Data;

import java.util.List;

@Data
public  class Issue {
  String title;
  String body;
  List<String> assignees;
  int milestone;
  List<String> labels;
}