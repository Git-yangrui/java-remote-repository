package com.newtouch.workStealingmodel;

import java.util.concurrent.BlockingDeque;


import com.newtouch.productandconsumer.Channel;

public interface  WorkStealingEnableChannel<P> extends Channel<P>{
	P take(BlockingDeque<P> preferredDeque) throws Exception;
}
