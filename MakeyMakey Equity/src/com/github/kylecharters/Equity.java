package com.github.kylecharters;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JFrame;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

public class Equity implements KeyListener{
	public static void main(String[] args){
		new Equity();
	}
	
	public Equity(){
		loadSounds();
		window();
	}
	
	private String letters = "abcd";
	private int categories = 6;
	
	
	private HashMap<Integer, ArrayList<Music>> sounds = new HashMap<Integer, ArrayList<Music>>();
	private HashMap<Integer, Integer> catAmounts = new HashMap<Integer, Integer>();
	
	public void window(){
		JFrame window = new JFrame("Audio");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(200, 200);
		window.setAlwaysOnTop(true);
		window.setAutoRequestFocus(true);
		window.addKeyListener(this);
		window.setVisible(true);
	}
	
	/*
	 * SOUNDS UTIL
	 */
	
	public void loadSounds(){
		TinySound.init();
		
		for(int i = 1; i <= categories; i++){ // c = amount of files ; iterate through all categories
			ArrayList<Music> temp = new ArrayList<Music>();
			
			int fileAmount = 0;
			for(int k = 0; k < letters.length(); k++){
				File file = new File(Integer.toString(i)+letters.charAt(k)+".wav");
				
				if(file.exists() && !file.isDirectory() && file != null){
					Music sound = TinySound.loadMusic(file);
					temp.add(sound);
					sound.play(false);
					
					fileAmount++;
					
					System.out.println("File " + i + ": " + letters.charAt(k) + " Found");
				}else break;
			}
			sounds.put(i, temp);
			catAmounts.put(i, fileAmount);
			
			System.out.println(fileAmount + " files under category " + i);
		}
	}
	
	public boolean soundsDone(int cat){
		for(Music m : getSounds(cat))
			if(m.playing())
				return false;
		
		return true;
	}
	
	public void playSound(int cat, int num){
		getSound(cat, num).play(false);
		System.out.println("Played Sound "+ cat + ": " +  letters.charAt(num));
	}
	
	public void playRandom(int cat){
		Random random = new Random();
		playSound(cat, random.nextInt(catAmounts.get(cat)));
	}
	
	public void stopSound(int cat, int num){
		getSound(cat, num).stop();
		System.out.println("Stopped Sound "+num);
	}
	
	public void stopSounds(int cat){
		for(Music m : sounds.get(cat)){
			m.stop();
		}
	}
	
	public void stopAllSounds(){
		for(Entry<Integer, ArrayList<Music>> i : sounds.entrySet()){
			for(Music i2 : i.getValue()){
				i2.stop();
			}
		}
	}
	
	public ArrayList<Music> getSounds(int cat){
		return sounds.get(cat);
	}
	
	public Music getSound(int cat, int num){
		return sounds.get(cat).get(num);
	}
	
	/*
	 * INPUT PROCESSING
	 */
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP && soundsDone(1)){
			stopSounds(1);
			playRandom(1);
		}else if(key == KeyEvent.VK_DOWN && soundsDone(2)){
			stopSounds(2);
			playRandom(2);
		}else if(key == KeyEvent.VK_SPACE && soundsDone(3)){
			stopSounds(3);
			playRandom(3);
		}else if(key == KeyEvent.VK_W && soundsDone(4)){
			stopSounds(4);
			playRandom(4);
		}else if(key == KeyEvent.VK_A && soundsDone(5)){
			stopSounds(5);
			playRandom(5);
		}else if(key == KeyEvent.VK_S && soundsDone(6)){
			stopSounds(6);
			playRandom(6);
		}
	}
	
	public void keyReleased(KeyEvent e){
	}
	
	public void keyTyped(KeyEvent e){
		
	}
}