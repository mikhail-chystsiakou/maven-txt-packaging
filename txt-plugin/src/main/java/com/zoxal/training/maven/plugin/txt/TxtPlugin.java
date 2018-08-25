package com.zoxal.training.maven.plugin.txt;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.*;

/**
 * Creates hello-world txt file in target directory
 *
 * @author Mike
 * @version 08/25/2018
 */
@Mojo( name = "txt")
public class TxtPlugin extends AbstractMojo {
    @Component
    private MavenProject project;

    @Parameter(property = "project.build.directory", readonly = true)
    private String outputDirectory;

    @Parameter(property = "project.build.finalName", readonly = true)
    private String finalName;

    public void execute() throws MojoExecutionException, MojoFailureException {
        String targetFileName = outputDirectory + File.separator + finalName + ".txt";
        File buildTarget = new File(targetFileName);
        try {
            if (!buildTarget.createNewFile()) {
                throw new MojoExecutionException("Failed to create target file " + targetFileName);
            }
            FileWriter fos = new FileWriter(buildTarget);
            fos.write("Hello world!");
            fos.close();
        } catch (IOException e) {
            throw new MojoExecutionException("Got IO errors", e);
        }
        project.getArtifact().setFile(buildTarget);
        getLog().info("Created file " + targetFileName);
    }
}
