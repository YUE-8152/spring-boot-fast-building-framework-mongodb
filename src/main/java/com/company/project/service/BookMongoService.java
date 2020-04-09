package com.company.project.service;

import com.company.project.model.Book;

import java.util.List;

public interface BookMongoService {
    /**
     * 保存对象
     *
     * @param book
     * @return
     */
    String insertBook(Book book);

    /**
     * 查询所有
     *
     * @return
     */
    List<Book> findAll();

    /***
     * 根据id查询
     * @param id
     * @return
     */
    Book getBookById(String id);

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    Book getBookByName(String name);

    /**
     * 更新对象
     *
     * @param book
     * @return
     */
    String updateBook(Book book);

    /***
     * 删除对象
     * @param book
     * @return
     */
    String deleteBook(Book book);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    String deleteBookById(String id);

    /**
     * 模糊查询
     *
     * @param search
     * @return
     */
    List<Book> findByLikes(String search);
}
