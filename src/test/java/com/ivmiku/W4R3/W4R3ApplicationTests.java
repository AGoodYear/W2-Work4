package com.ivmiku.W4R3;

import com.ivmiku.W4R3.controller.ActionController;
import com.ivmiku.W4R3.entity.ActionInput;
import com.ivmiku.W4R3.entity.CommentLike;
import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.mapper.VideoMapper;
import com.ivmiku.W4R3.entity.User;
import com.ivmiku.W4R3.entity.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class W4R3ApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private UserMapper user;
	@Autowired
	private ActionController actionController;
	@Test
	public void test1() {
		Video video = videoMapper.selectById("1");
		System.out.println(video.getTitle());
		User u = user.selectById("1755469837687713793");
		System.out.println(u.getUsername());
	}

//	@Test
//	public void testLike() {
//		ActionInput actionInput = new ActionInput();
//		actionInput.setAction_type("1");
//		actionInput.setComment_id("2");
//		System.out.println(actionController.Like(actionInput));
//
//	}

}
