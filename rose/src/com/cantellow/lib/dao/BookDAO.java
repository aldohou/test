package com.cantellow.lib.dao;

import java.util.List;

import com.cantellow.lib.model.Book;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

//必须是接口，并且以大写DAO结尾
//必须标注@DAO，DAO中有一个catalog属性，对于大部分人来说，这个都是没用的
@DAO
public interface BookDAO {

    /**
     * @author xuze(cantellow)
     * @time Aug 10, 2011 10:17:52 AM
     * @param
     * @do 根据BookID从数据库查询出Book
     * @Modify
     */
    @SQL("select id, name, price, author, create_time from book where id = :1")
    public Book get(long bookId);

    /**
     * @author xuze(cantellow)
     * @time Aug 10, 2011 3:08:02 PM
     * @param
     * @do 从数据库中查询出书名一样的书，一般来说书名可能重复，但是书名和作者不能同事重复
     * @Modify
     */
    @SQL("select id, name, price, author, create_time from book where name = :1")
    public List<Book> getBooksByName(String name);

    /**
     * @author xuze(cantellow)
     * @time Aug 10, 2011 10:18:16 AM
     * @param
     * @do 查询前几行的数据
     * @Modify
     */
    @SQL("select id, name, price, author, create_time from book order by id desc limit :1")
    public List<Book> find(int limit);

    @SQL("select id, name, price, author, create_time from book order by id desc where id < :1 limit :2")
    public List<Book> find(long bookId, int limit);

    @SQL("update book set name=:1.name, price=:1.price, author=:1.author where id=:1.id")
    public void update(Book book);

    @SQL("insert into book (name, price, author) values (:1.name, :1.price, :1.author)")
    public Identity save(Book book);

    @SQL("delete from book where id = :1")
    public void delete(long bookId);
}
