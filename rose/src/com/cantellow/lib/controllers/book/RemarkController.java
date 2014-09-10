package com.cantellow.lib.controllers.book;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cantellow.lib.controllers.AdminRequired;
import com.cantellow.lib.controllers.LoginRequired;
import com.cantellow.lib.dao.RemarkDAO;
import com.cantellow.lib.model.Remark;
import com.cantellow.lib.model.User;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 
 * @author xuze(cantellow)
 * @Email ze.xu@renren-inc.com
 * @time Aug 10, 2011 5:59:01 PM
 * @do 书本评论控制器
 * @Modify
 */
@LoginRequired
@Path("{id:[0-9]+}/remark")
public class RemarkController {

    @Autowired
    private RemarkDAO remarkDao;

    /**
     * @author xuze(cantellow)
     * @time Aug 12, 2011 12:35:31 PM
     * @param
     * @do 增加一个评论
     * @Modify
     */
    @Post("add")
    public String add(final Invocation inv, final Remark remark) {
        if (StringUtils.isEmpty(remark.getEssay())) {
            inv.addModel("book", inv.getRequest().getSession().getAttribute("book"));
            inv.addModel("remarks", inv.getRequest().getSession().getAttribute("remarks"));
            inv.addModel("remark_error", "评论内容不能为空");
            return "/views/one_book";
        }
        final User user = (User) inv.getRequest().getSession().getAttribute("loginUser");
        remark.setUserName(user.getLoginName());
        this.remarkDao.save(remark);
        return "r:/lib/book/" + remark.getBookId();
    }

    /**
     * @author xuze(cantellow)
     * @time Aug 10, 2011 6:01:53 PM
     * @param
     * @do 清除所有评论
     * @Modify
     */
    @Post("deleteAll")
    @AdminRequired
    public String clear(@Param("bookId") final long bookId) {
        this.remarkDao.deleteByBook(bookId);
        return "r:/lib/book/" + bookId;
    }

    /**
     * @author xuze(cantellow)
     * @time Aug 10, 2011 6:02:04 PM
     * @param
     * @do 删除指定ID的评论
     * @Modify
     */
    @Post("{remarkId}/delete")
    @AdminRequired
    public String delete(@Param("bookId") final long bookId,
            @Param("remarkId") final String remarkId) {
        this.remarkDao.delete(Long.parseLong(remarkId));
        return "r:/lib/book/" + bookId;
    }
}
