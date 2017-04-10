package com.citi.nio.channel;

import java.nio.channels.ByteChannel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.junit.Test;

public class ChannelTest {
	@Test
	public void test_Channel() {
		// ByteChannel
       ReadableByteChannel newChannel = Channels.newChannel(System.in);
	}
}
