package org.capcaval.ccoutils.lang;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class SystemClassLoader extends URLClassLoader{
	public SystemClassLoader(){
		super(new URL[0]);
		this.initSystemURLList();
	}

	public SystemClassLoader(URL... urlList){
		super(new URL[0]);
		this.initSystemURLList();
		
		this.addURL(urlList);
	}

	public SystemClassLoader(String... urlList){
		super(new URL[0]);
		this.initSystemURLList();
		
		this.addURL(urlList);
	}

	
	protected void initSystemURLList() {
		URLClassLoader ucl = (URLClassLoader)ClassLoader.getSystemClassLoader();
		this.addURL(ucl.getURLs());
	}

	public SystemClassLoader(URLClassLoader ucl){
		super(ucl.getURLs());
		this.initSystemURLList();
	}
	
	@Override
	public void addURL(URL url){
		super.addURL(url);
	}
	
	public void addURL(URL... urlList){
		for(URL url : urlList){
			super.addURL(url);}
	}
	
	public void addURL(String... urlStringList){
		for(String urlStr : urlStringList){
			try {
				File file = new File(urlStr);
				
				if(file.exists() == false){
					throw new RuntimeException("[ccOutils] SystemClassLoader : Unable to load the file " + file); 
				}
				// the file is correct let add it
				super.addURL(file.toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString(){
		return Arrays.toString(this.getURLs());
	}
}