package org.qenherkhopeshef.jsesh.prepare;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * Process lex files.
 * @goal lex
 * @phase generate-sources
 * @author rosmord
 */

public class LexMojo extends AbstractMojo{

	/**
	 * The output directory.
	 * @parameter  default-value="${project.build.directory}"
	 * @readonly
	 */
	private String outputDirName;
	
	/**
	 * @parameter default-value="${project}";
	 */
	private MavenProject project;
	
	/**
	 * @parameter expression="${cup.lexerPackage}"
	 * @required
	 */
	private String lexerPackage;
	
	/**
	 * Path to lexicon definition file.
	 * @parameter expression="${lex.lexDef}" 
	 * @required
	 */
	private String lexDef;

	public void execute() throws MojoExecutionException, MojoFailureException {
		String[] args= {lexDef};
		try {
			File sourceFile= new File(lexDef);
			// JLex is supposed to create a file in the SAME folder as the source.
			// We will need to move it.
			String resultPath= lexDef+ ".java";
			// The file created by JLex
			File tempResult= new File(resultPath);
			// Compute the resulting file name
			int endPos= tempResult.getName().lastIndexOf(".l.java");
			if (endPos == -1)
				throw new MojoExecutionException("lex file names must end in '.l' "+ lexDef);
			
			String finalResultName= tempResult.getName().substring(0, endPos) + ".java";
			File finalResultFile= new File(createPackage(lexerPackage), finalResultName);
			
			// Avoid unnecessary processing
			if (finalResultFile.lastModified() > sourceFile.lastModified())
				return;
			JLex.Main.main(args);		
			
			if (tempResult.exists()) {			
				tempResult.renameTo(finalResultFile);
				project.addCompileSourceRoot(getJavaSourcePath().getAbsolutePath());
			}
		} catch (IOException e) {
			throw new MojoExecutionException("error in jlex", e);
		}
		
	}

	/**
	 * Create a package and returns the corresponding file.
	 * @param packageName
	 * @return
	 */
	private File createPackage(String packageName) {
		File result= getJavaSourcePath();
		// ok. Now use the package name...
		String[] folders = packageName.trim().split("\\.");
		for (String folder : folders) {
			result= new File(result, folder);
		}
		result.mkdirs();
		return result;		
	}
	
	private File getJavaSourcePath() {
		File result= new File(outputDirName);
		result= new File(result, "generated-sources");
		result= new File(result, "lex");
		return result;
	}
}
