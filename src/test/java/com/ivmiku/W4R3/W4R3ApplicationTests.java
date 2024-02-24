package com.ivmiku.W4R3;

import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.mapper.VideoMapper;
import com.ivmiku.W4R3.pojo.User;
import com.ivmiku.W4R3.pojo.Video;
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
	@Test
	public void test1() {
		Video video = videoMapper.selectById("1");
		System.out.println(video.getTitle());
		User u = user.selectById("1755469837687713793");
		System.out.println(u.getUsername());
	}

}
