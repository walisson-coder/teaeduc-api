package com.api.teaeduc.dtos.paginacao;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListWrapper<T> {
	
	private int totalResults;
	private int page;
	private int pages;
	private List<T> list;
}
