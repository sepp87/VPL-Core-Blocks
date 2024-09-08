package vpllib;

import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import vplcore.Config;
import vplcore.graph.util.BlockLoader;
import vplcore.Util;

/**
 *
 * @author joostmeulenkamp
 */
public class Main {

    public static void main(String[] args) {
        loadStaticFieldsAsBlocks();
        loadStaticMethodsAsBlocks();
        JsonParser parser = new JsonParser();
        getClassNamesFromJarFile(new File("/Users/joostmeulenkamp/Offline/Dev/VPL-Core-Blocks/build/VPL-Core-Blocks.jar"));
    }

    /**
     * Retrieve all blocks from external libraries
     */
    public static void loadExternalBlocks() {
        File dir = new File(Config.get().libraryDirectory());
        File[] libraries = Util.getFilesByExtensionFrom(dir, ".jar");

        for (File lib : libraries) {
            List<String> classNames = getClassNamesFromJarFile(lib);

        }

    }

    private static List<String> getClassNamesFromJarFile(File file) {
        List<String> result = new ArrayList<>();
        try (JarFile jarFile = new JarFile(file)) {

            Enumeration<JarEntry> e = jarFile.entries();
            String packageName = jarFile.getManifest().getMainAttributes().getValue("package");
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    if (className.startsWith(packageName)) {
                        result.add(className);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BlockLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static void loadStaticMethodsAsBlocks() {
        List<Method> methods = getStaticMethodsFromClass(Math.class);
        for (Method m : methods) {

//            System.out.println(m.getName());
//            for (Parameter p : m.getParameters()) {
//
//                System.out.println("\t" + p.isNamePresent() + " " + p.getType().getSimpleName());
//
//            }
        }
    }

    public static void loadStaticFieldsAsBlocks() {
        List<Field> fields = getStaticFieldsFromClass(Math.class);
        for (Field f : fields) {
            try {

                System.out.println(f.get(null));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static List<Method> getStaticMethodsFromClass(Class<?> c) {
        List<Method> result = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            if (Modifier.isStatic(m.getModifiers())) {
                result.add(m);
            }
        }
        return result;
    }

    public static List<Field> getStaticFieldsFromClass(Class<?> c) {
        List<Field> result = new ArrayList<>();
        Field[] fields = c.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (Modifier.isStatic(f.getModifiers())) {
                result.add(f);
            }
        }
        return result;
    }
}
