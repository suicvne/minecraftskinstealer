Most of this application was compiled against JDK 8 under OS X 10.8.3. While I don't believe I've bundled code specific to JDK 8, I have not tested this under other versions so I cannot guarantee compatibility.


===FOR OS X DISTRIBUTION===
I have included the app bundler and the build.xml already contains references to it. As far as I know, netbeans will NOT autocompile a .app on build so it's best to do the following steps!

-Right click and run a "Clean and Build" in Netbeans. This will produce the needed .jar file in the dist folder.
-Open a terminal, and cd into the project's directory, should be "Minecraft Skin Stealer"
-run "ant bundle-application"
-The dist folder should contain the application.