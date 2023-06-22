//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package resources_DISABLED;

import compat.MyClassloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Resources_DISABLED {
    /*
    private static MyClassloader classLoader = new MyClassloader(new URL[0], ClassLoader.getSystemClassLoader());;

    public Resources() {
    }

    private static void collectURL(ResourceURLFilter f, Set<URL> s, URL u) {
        if (f == null || f.accept(u)) {
            s.add(u);
        }

    }

    private static void iterateFileSystem(File r, ResourceURLFilter f, Set<URL> s) throws MalformedURLException, IOException {
        File[] files = r.listFiles();
        File[] var7 = files;
        int var6 = files.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            File file = var7[var5];
            if (file.isDirectory()) {
                iterateFileSystem(file, f, s);
            } else if (file.isFile()) {
                collectURL(f, s, file.toURI().toURL());
            }
        }

    }

    private static void iterateJarFile(File file, ResourceURLFilter f, Set<URL> s) throws MalformedURLException, IOException {
        JarFile jFile = new JarFile(file);
        Enumeration je = jFile.entries();

        while(je.hasMoreElements()) {
            JarEntry j = (JarEntry)je.nextElement();
            if (!j.isDirectory()) {
                collectURL(f, s, new URL("jar", "", file.toURI() + "!/" + j.getName()));
            }
        }

        jFile.close();
    }

    private static void iterateEntry(File p, ResourceURLFilter f, Set<URL> s) throws MalformedURLException, IOException {
        if (p.isDirectory()) {
            iterateFileSystem(p, f, s);
        } else if (p.isFile() && p.getName().toLowerCase().endsWith(".jar")) {
            iterateJarFile(p, f, s);
        }

    }

    public static Set<URL> getResourceURLs() throws IOException, URISyntaxException {
        return getResourceURLs((ResourceURLFilter)null);
    }

    public static Set<URL> getResourceURLs(ResourceURLFilter filter) throws IOException, URISyntaxException {
        Set<URL> collectedURLs = new HashSet();
        URLClassLoader ucl = (URLClassLoader)classLoader;
        URL[] var6;
        int var5 = (var6 = ucl.getURLs()).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            URL url = var6[var4];
            iterateEntry(new File(url.toURI()), filter, collectedURLs);
        }

        return collectedURLs;
    }
    */
}
