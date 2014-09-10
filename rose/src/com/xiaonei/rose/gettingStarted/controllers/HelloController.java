package com.xiaonei.rose.gettingStarted.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONObject;

import com.cantellow.lib.model.User;

//在控制器上标注@Path设置"u"，自定义映射规则(默认是/hello，现改为/h)
@Path("h")
public class HelloController {

	public String world(Invocation inv) {
		inv.addModel("now", new Date());
		return "hello-world";
	}

	//
	public String test() {
		// 返回@开始的字符串，表示将紧跟@之后的字符串显示在页面上
		return "@" + new java.util.Date();
	}

	// 返回一个velocity页面
	public String velocity() {
		// 返回一个普通字符串，表示要从webapp/views/目录下找第一个以user-velocity.开始的页面
		// 运行本程序时，请在webapp/views/目录下创建一个名为user-velocity.vm的文件，写上写文本字符
		return "user-velocity";
	}

	// 返回一个jsp页面
	public String jsp() {
		// 在webapp/views/目录下创建user-jsp.jsp的文件即可 (UTF-8的)。
		return "user-jsp";
	}

	// 在页面渲染业务数据
	public String render(Invocation inv) {
		// 在vm/jsp中可以使用$now渲染这个值
		inv.addModel("now", new java.util.Date());
		// 在vm/jsp中可以使用$user.id, $user.name渲染user的值
		inv.addModel("user", new User(1 + "", "aldo.wang")); // id=1,
																// name=qieqie.wang
		return "user-render";
	}

	// 重定向(Redirect) lib 为工程别名
	public String redirect() {
		// 以r:开始表示重定向
		return "r:/lib/h/test"; // 或 r:http://127.0.0.1:8080/lib/h/test
	}

	// 转发(Forward)
	public String forward() {
		// 大多数情况下，以/开始即是转发(除非存在webapp/user/test文件)
		return "/h/test";
	}

	public String forward2() {
		// a:开始表示转发到同一个控制器的acton方法forward()，更多参数有m:/module:,c:/controller:
		return "a:forward?note=可以带参数";
	}

	/**
	 * 自定义方法映射 http://127.0.0.1/user/listByGroup?groupId=123
	 **/
	@Get("listByGroup")
	public String listByGroup(@Param("groupId") String groupId) {
		System.out.println(groupId);
		return "@${groupId}";
	}

	// 使用正则表达式自定义方法映射......http://127.0.0.1:8080/lib/h/listByGroup2-1122
	// @Get("listByGroup2{groupId}")
	@Get("listByGroup2{groupId:\\d+}")
	public String listByGroup2(@Param("groupId") int groupId) {
		// return "@string-${groupId}";
		return "user-jsp";
	}

	/**
	 * 获取request请求参数
	 * **/
	// http://127.0.0.1:8080/lib/h/param1?name=rose
	public String param1(@Param("name") String name) {
		return "@" + name;
	}

	// http://127.0.0.1:8080/lib/h/param2?name=rose

	public String param2(Invocation inv) {
		return "@" + inv.getRequest().getParameter("name");
	}

	// http://127.0.0.1:8080/lib/h/param3/rose
	@Get("param3/{name}")
	public String param3(Invocation inv, @Param("name") String name) {
		// request.getParameter()也能获取@ReqMapping中定义的参数
		return "@method.name=" + name + "; request.param.name="
				+ inv.getRequest().getParameter("name");
	}

	/*
	 * 数组参数 http://127.0.0.1:8080/lib/h/array?id=1&id=2&id=3
	 * http://127.0.0.1/usre/array?id=1,2,3,4
	 */
	public String array(@Param("id") int[] idArray) {
		return "@" + Arrays.toString(idArray);
	}

	/*
	 * Map参数 http://127.0.0.1:8080/lib/h/keyOfMap?map:1=paoding&map:2=rose
	 */
	public String keyOfMap(@Param("map") Map<Integer, String> map) {
		return "@" + Arrays.toString(map.keySet().toArray());
	}

	/*
	 * http://127.0.0.1:8080/lib/h/valueOfMap?map:1=paoding&map:2=rose
	 */
	public String valueOfMap(@Param("map") Map<Integer, String> map) {
		return "@" + Arrays.toString(map.values().toArray(new String[0]));
	}

	/*
	 * http://127.0.0.1:8080/lib/h/map?map:1=paoding&map:2=rose
	 */
	public String map(@Param("map") Map<Integer, String> map) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue())
					.append("<br>");
		}
		return "@" + sb;
	}

	/**
	 * 表单提交
	 * **/
	@Post
	public String adduser(User user) {
		// return "@ test";
		return "@" + user.getId() + "=" + user.getName();
	}

	/*
	 * 返回json http://127.0.0.1/user/json?id=1 http://127.0.0.1/user/json?id=2
	 */

	public Object json(@Param("id") String id) {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("name", "rose");
		json.put("text", "可以有中文");
		// rose将调用json.toString()渲染
		return json;
	}

	// 把JSONObject放到方法中，Rose将帮忙创建实例
	public Object json2(JSONObject json, @Param("id") String id) {
		json.put("id", id);
		json.put("name", "rose");
		json.put("text", "可以有中文");
		// rose将调用json.toString()渲染
		return json;
	}

	/**
	 * 返回xml http://127.0.0.1/user/xml
	 * 
	 * @param inv
	 * @return
	 */
	public Object xml(Invocation inv) {
		User user = new User();
		user.setId(1 + "09");
		user.setName("aldo");
		inv.addModel("user", user);
		// rose将调用user-xml.xml或.vm或.jsp渲染页面(按字母升序顺序优先: jsp, vm, xml)
		// 使用user-xml.xml的，默认contentType是text/xml;charset=UTF-8，语法同velocity
		// 使用user-xml.jsp或user-xml.vm的可在本方法中标注@HttpFeatures(contentType="xxx")改变
		// jsp的也可通过<%@ page contentType="text/html;charset=UTF-8" %>改变
		return "user-xml";
	}

}
