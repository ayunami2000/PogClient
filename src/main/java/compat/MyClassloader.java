package compat;

import java.net.URL;
import java.net.URLClassLoader;

public class MyClassloader extends URLClassLoader {

    public MyClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public void addURL(URL url) {
        super.addURL(url);
    }
}
