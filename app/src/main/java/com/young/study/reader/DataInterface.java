package com.young.study.reader;

import org.htmlparser.util.ParserException;

import com.young.study.bean.Novels;

/**
 * Created by edz on 2017/8/10.
 */

public interface DataInterface {

    public Novels getSortKindNovels(String url) throws ParserException;

    public DataInterface select(String url);

    public NovelDetail getNovelDetail(String url) throws ParserException;

    public String getChapterContent(String url) throws ParserException;
}
