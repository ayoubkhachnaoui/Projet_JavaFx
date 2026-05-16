package org.example.projet_java_fx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
        private static final String URL = "jdbc:sqlite:gestion_etudiants.db";
        private static Connection connection = null;

        public static Connection getConnection() throws SQLException {
                if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(URL);
                        initializeDatabase();
                }
                return connection;
        }

    private static void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            // Users table
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL)");

            // Students table
            statement.execute("CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom TEXT NOT NULL," +
                    "prenom TEXT NOT NULL," +
                    "cin TEXT UNIQUE NOT NULL," +
                    "email TEXT NOT NULL," +
                    "telephone TEXT NOT NULL," +
                    "date_naissance TEXT NOT NULL," +
                    "niveau TEXT NOT NULL," +
                    "filiere TEXT NOT NULL," +
                    "groupe TEXT NOT NULL)");

            // Modules table
            statement.execute("CREATE TABLE IF NOT EXISTS modules (" +
                    "code TEXT PRIMARY KEY," +
                    "nom TEXT NOT NULL," +
                    "coefficient REAL NOT NULL," +
                    "enseignant TEXT NOT NULL)");

            // Grades table
            statement.execute("CREATE TABLE IF NOT EXISTS grades (" +
                    "student_id INTEGER," +
                    "module_code TEXT," +
                    "note_cc REAL NOT NULL," +
                    "note_examen REAL NOT NULL," +
                    "PRIMARY KEY (student_id, module_code)," +
                    "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (module_code) REFERENCES modules(code) ON DELETE CASCADE)");

            // Seed default admin if not exists[INFO] ------------------------------------------------------------------------
[INFO] Total time:  07:29 min
[INFO] Finished at: 2026-05-16T08:56:16+01:00
[INFO] ------------------------------------------------------------------------
PS C:\Users\Ayoub\Desktop\Projet_java_Fx> .\mvnw.cmd clean javafx:run
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------< org.example:Projet_java_Fx >---------------------
[INFO] Building Projet_java_Fx 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ Projet_java_Fx ---
[INFO] Deleting C:\Users\Ayoub\Desktop\Projet_java_Fx\target
[INFO] 
[INFO] >>> javafx-maven-plugin:0.0.8:run (default-cli) > process-classes @ Projet_java_Fx >>>
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ Projet_java_Fx ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 10 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.13.0:compile (default-compile) @ Projet_java_Fx ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 21 source files with javac [debug target 17 module-path] to target\classes
[INFO] /C:/Users/Ayoub/Desktop/Projet_java_Fx/src/main/java/org/example/projet_java_fx/controllers/StatsController.java: C:\Users\Ayoub\Desktop\Projet_java_Fx\src\main\java\org\example\projet_java_fx\controllers\StatsController.java uses unchecked or unsafe operations.
[INFO] /C:/Users/Ayoub/Desktop/Projet_java_Fx/src/main/java/org/example/projet_java_fx/controllers/StatsController.java: Recompile with -Xlint:unchecked for details.
[INFO]
[INFO] <<< javafx-maven-plugin:0.0.8:run (default-cli) < process-classes @ Projet_java_Fx <<<
[INFO]
[INFO]
[INFO] --- javafx-maven-plugin:0.0.8:run (default-cli) @ Projet_java_Fx ---
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Terminate batch job (Y/N)? y
PS C:\Users\Ayoub\Desktop\Projet_java_Fx> .\mvnw.cmd clean javafx:run
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------< org.example:Projet_java_Fx >---------------------
[INFO] Building Projet_java_Fx 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ Projet_java_Fx ---
[INFO] Deleting C:\Users\Ayoub\Desktop\Projet_java_Fx\target
[INFO] 
[INFO] >>> javafx-maven-plugin:0.0.8:run (default-cli) > process-classes @ Projet_java_Fx >>>
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ Projet_java_Fx ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 10 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.13.0:compile (default-compile) @ Projet_java_Fx ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 21 source files with javac [debug target 17 module-path] to target\classes
[INFO] /C:/Users/Ayoub/Desktop/Projet_java_Fx/src/main/java/org/example/projet_java_fx/controllers/StatsController.java: C:\Users\Ayoub\Desktop\Projet_java_Fx\src\main\java\org\example\projet_java_fx\controllers\StatsController.java uses unchecked or unsafe operations.
[INFO] /C:/Users/Ayoub/Desktop/Projet_java_Fx/src/main/java/org/example/projet_java_fx/controllers/StatsController.java: Recompile with -Xlint:unchecked for details.
[INFO]
[INFO] <<< javafx-maven-plugin:0.0.8:run (default-cli) < process-classes @ Projet_java_Fx <<<
[INFO]
[INFO]
[INFO] --- javafx-maven-plugin:0.0.8:run (default-cli) @ Projet_java_Fx ---
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
javafx.fxml.LoadException: 
/C:/Users/Ayoub/Desktop/Projet_java_Fx/target/classes/org/example/projet_java_fx/student-view.fxml:10

        at javafx.fxml/javafx.fxml.FXMLLoader.constructLoadException(FXMLLoader.java:2714)
        at javafx.fxml/javafx.fxml.FXMLLoader$ValueElement.processAttribute(FXMLLoader.java:944)
        at javafx.fxml/javafx.fxml.FXMLLoader$InstanceDeclarationElement.processAttribute(FXMLLoader.java:981)
        at javafx.fxml/javafx.fxml.FXMLLoader$Element.processStartElement(FXMLLoader.java:230)
        at javafx.fxml/javafx.fxml.FXMLLoader$ValueElement.processStartElement(FXMLLoader.java:755)
        at javafx.fxml/javafx.fxml.FXMLLoader.processStartElement(FXMLLoader.java:2845)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:2641)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:2555)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3368)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3324)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3292)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3264)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3240)
        at javafx.fxml/javafx.fxml.FXMLLoader.load(FXMLLoader.java:3233)
        at org.example.projet_java_fx@1.0-SNAPSHOT/org.example.projet_java_fx.controllers.MainController.loadView(MainController.java:77)
        at org.example.projet_java_fx@1.0-SNAPSHOT/org.example.projet_java_fx.controllers.MainController.showStudents(MainController.java:57)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at com.sun.javafx.reflect.Trampoline.invoke(MethodUtil.java:77)
        at jdk.internal.reflect.GeneratedMethodAccessor2.invoke(Unknown Source)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at javafx.base/com.sun.javafx.reflect.MethodUtil.invoke(MethodUtil.java:275)
        at javafx.fxml/com.sun.javafx.fxml.MethodHelper.invoke(MethodHelper.java:84)
        at javafx.fxml/javafx.fxml.FXMLLoader$MethodHandler.invoke(FXMLLoader.java:1854)
        at javafx.fxml/javafx.fxml.FXMLLoader$ControllerMethodEventHandler.handle(FXMLLoader.java:1724)
        at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
        at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
        at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics/javafx.scene.Node.fireEvent(Node.java:8792)
        at javafx.controls/javafx.scene.control.Button.fire(Button.java:203)
        at javafx.controls/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:208)
        at javafx.controls/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
        at javafx.base/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
        at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
        at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
        at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics/javafx.scene.Scene$MouseHandler.process(Scene.java:3897)
        at javafx.graphics/javafx.scene.Scene.processMouseEvent(Scene.java:1878)
        at javafx.graphics/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2623)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:411)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:301)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:399)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:450)
        at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:424)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:449)
        at javafx.graphics/com.sun.glass.ui.View.handleMouseEvent(View.java:557)
        at javafx.graphics/com.sun.glass.ui.View.notifyMouse(View.java:943)
        at javafx.graphics/com.sun.glass.ui.win.WinApplication._runLoop(Native Method)
        at javafx.graphics/com.sun.glass.ui.win.WinApplication.lambda$runLoop$3(WinApplication.java:184)
        at java.base/java.lang.Thread.run(Thread.java:840)
Caused by: java.lang.reflect.InvocationTargetException
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
        at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499)
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480)
        at javafx.fxml/javafx.fxml.FXMLLoader$ValueElement.processAttribute(FXMLLoader.java:939)
        ... 71 more
Caused by: java.lang.Error: Unresolved compilation problems:
        The import org.example.projet_java_fx.models.Student cannot be resolved
        The import org.example.projet_java_fx.utils.NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type

        at org.example.projet_java_fx@1.0-SNAPSHOT/org.example.projet_java_fx.controllers.StudentController.<init>(StudentController.java:8)
        ... 77 more
javafx.fxml.LoadException: 
/C:/Users/Ayoub/Desktop/Projet_java_Fx/target/classes/org/example/projet_java_fx/student-view.fxml:10

        at javafx.fxml/javafx.fxml.FXMLLoader.constructLoadException(FXMLLoader.java:2714)
        at javafx.fxml/javafx.fxml.FXMLLoader$ValueElement.processAttribute(FXMLLoader.java:944)
        at javafx.fxml/javafx.fxml.FXMLLoader$InstanceDeclarationElement.processAttribute(FXMLLoader.java:981)
        at javafx.fxml/javafx.fxml.FXMLLoader$Element.processStartElement(FXMLLoader.java:230)
        at javafx.fxml/javafx.fxml.FXMLLoader$ValueElement.processStartElement(FXMLLoader.java:755)
        at javafx.fxml/javafx.fxml.FXMLLoader.processStartElement(FXMLLoader.java:2845)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:2641)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:2555)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3368)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3324)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3292)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3264)
        at javafx.fxml/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3240)
        at javafx.fxml/javafx.fxml.FXMLLoader.load(FXMLLoader.java:3233)
        at org.example.projet_java_fx@1.0-SNAPSHOT/org.example.projet_java_fx.controllers.MainController.loadView(MainController.java:77)
        at org.example.projet_java_fx@1.0-SNAPSHOT/org.example.projet_java_fx.controllers.MainController.showStudents(MainController.java:57)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at com.sun.javafx.reflect.Trampoline.invoke(MethodUtil.java:77)
        at jdk.internal.reflect.GeneratedMethodAccessor2.invoke(Unknown Source)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at javafx.base/com.sun.javafx.reflect.MethodUtil.invoke(MethodUtil.java:275)
        at javafx.fxml/com.sun.javafx.fxml.MethodHelper.invoke(MethodHelper.java:84)
        at javafx.fxml/javafx.fxml.FXMLLoader$MethodHandler.invoke(FXMLLoader.java:1854)
        at javafx.fxml/javafx.fxml.FXMLLoader$ControllerMethodEventHandler.handle(FXMLLoader.java:1724)
        at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
        at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
        at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics/javafx.scene.Node.fireEvent(Node.java:8792)
        at javafx.controls/javafx.scene.control.Button.fire(Button.java:203)
        at javafx.controls/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:208)
        at javafx.controls/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
        at javafx.base/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
        at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
        at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
        at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
        at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics/javafx.scene.Scene$MouseHandler.process(Scene.java:3897)
        at javafx.graphics/javafx.scene.Scene.processMouseEvent(Scene.java:1878)
        at javafx.graphics/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2623)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:411)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:301)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:399)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:450)
        at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:424)
        at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:449)
        at javafx.graphics/com.sun.glass.ui.View.handleMouseEvent(View.java:557)
        at javafx.graphics/com.sun.glass.ui.View.notifyMouse(View.java:943)
        at javafx.graphics/com.sun.glass.ui.win.WinApplication._runLoop(Native Method)
        at javafx.graphics/com.sun.glass.ui.win.WinApplication.lambda$runLoop$3(WinApplication.java:184)
        at java.base/java.lang.Thread.run(Thread.java:840)
Caused by: java.lang.reflect.InvocationTargetException
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
        at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499)
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480)
        at javafx.fxml/javafx.fxml.FXMLLoader$ValueElement.processAttribute(FXMLLoader.java:939)
        ... 71 more
Caused by: java.lang.Error: Unresolved compilation problems:
        The import org.example.projet_java_fx.models.Student cannot be resolved
        The import org.example.projet_java_fx.utils.NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        Student cannot be resolved to a type
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        NotificationUtils cannot be resolved
        Student cannot be resolved to a type
        Student cannot be resolved to a type

        at org.example.projet_java_fx@1.0-S
            statement.execute("INSERT OR IGNORE INTO users (username, password, role) VALUES ('ayoub', 'khachnaouiayoub', 'ADMIN')");
            statement.execute("INSERT OR IGNORE INTO users (username, password, role) VALUES ('teacher', 'teacher123', 'ENSEIGNANT')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
