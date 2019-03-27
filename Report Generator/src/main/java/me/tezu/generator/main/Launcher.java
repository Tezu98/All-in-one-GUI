package me.tezu.generator.main;

import java.awt.EventQueue;

import me.tezu.delay.DelayDB;
import me.tezu.generator.Handler;

public class Launcher {
	public static void main(String[] args){
		
		final Handler handler = new Handler();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					handler.getDisplay().initialize();
					System.setOut(handler.getDisplay().getCon());
					System.setErr(handler.getDisplay().getCon());
					DelayDB db = new DelayDB();
					
					db.test();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					
				}
			}
		});
		
	}
}
