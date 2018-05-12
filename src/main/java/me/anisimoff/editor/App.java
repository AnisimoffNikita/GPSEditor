package me.anisimoff.editor;

import me.anisimoff.editor.Model.DatabaseModel;
import me.anisimoff.editor.Presenter.SimplePresenter;
import me.anisimoff.editor.Utils.Utils;
import me.anisimoff.editor.View.Editor;
import me.anisimoff.editor.View.View;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

class App {

    public static void main(String[] args) {
        App app = new App();
        app.startApp();
    }

    private void startApp() {
        SwingUtilities.invokeLater(() -> {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }



//            try
//            {
//                File myJar = new File("/home/nikita/Projects/labs/Software-Engineering/Module/out/artifacts/Module_main_jar/Module_main.jar");
//                URL url = myJar.toURI().toURL();
//
//                Class[] parameters = new Class[]{URL.class};
//
//                URLClassLoader sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
//                Class sysClass = URLClassLoader.class;
//
//                Method method = sysClass.getDeclaredMethod("addURL", parameters);
//                method.setAccessible(true);
//                method.invoke(sysLoader,new Object[]{ url });
//
//                Constructor cs = ClassLoader.getSystemClassLoader().loadClass("SimpleModule").getConstructor();
//                Module instance = (Module)cs.newInstance();
//                instance.action(new Route());
//
//            }
//            catch(Exception ex)
//            {
//                System.err.println(ex.getMessage());
//            }


            Utils.createConfigDir();
            View view = new Editor();
            view.setPresenter(new SimplePresenter(view, new DatabaseModel()));

        });
    }
}
