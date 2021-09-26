// tag::comment[]
/*******************************************************************************
 * Copyright (c) 2017, 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
// end::comment[]
package io.openliberty.guides.rest;

import java.util.List;
import java.util.Properties;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import javax.ws.rs.QueryParam;

@Path("paths")
public class PropertiesResource {

  String fileName = "/projects/jaxrs-app/src/main/java/io/openliberty/guides/rest/PropertiesResource.java";

  String skeleton = "\n\t@Path(\"<PATH>\") \n"
      + "\t@GET \n"
      + "\t@Produces(MediaType.APPLICATION_JSON) \n"
      + "\tpublic ResponseItem get_<METHOD_NAME>() { \n" 
      + "\t  return new ResponseItem(\"<PATH>\", \"<DESCRIPTION>\"); \n"
      + "\t}\n";

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String createPath(PathDetails pathDetails) {
    try {
      System.out.println("Enter create with " + pathDetails);
      File sourcePath = new File(fileName);
      List<String> lines = Files.readAllLines(sourcePath.toPath());
      lines.add(lines.size() - 1,
          skeleton.replaceAll("<PATH>", pathDetails.getPath())
              .replace("<METHOD_NAME>", pathDetails.getPath().replaceAll("\\W", "_"))
              .replaceAll("<DESCRIPTION>", pathDetails.getDescription()));
      Files.write(sourcePath.toPath(), lines);
      buildAndDeployApiProject();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      return "FAILED";
    }
    return "SUCCESS";
  }

  private synchronized void buildAndDeployApiProject() {
    String API_PROJECT_DEPLOY_SCRIPT = "/opt/ibm/deploy_project.sh";
    String[] commands = { API_PROJECT_DEPLOY_SCRIPT };
    try {
      ProcessBuilder pb = new ProcessBuilder(commands);
      Process p = pb.start();
      p.waitFor();
      int exitStatus = p.exitValue();
      if (exitStatus == 0) {
        System.out.println("Deploy executed successfully.");
      } else {
        System.out.println("Deploy execution failed !!!.");
      }
    } catch (IOException | InterruptedException e) {
      System.out.println("Error while executing deploy script !!!.");
      e.printStackTrace();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<PathDetails> getAllPaths() {
    List<PathDetails> pathList = new ArrayList<>();
    try {
      File logpath = new File(fileName);
      List<String> lines = Files.readAllLines(logpath.toPath());
      for (String line : lines) {
        if (line != null && line.trim().startsWith("@Path")) {
          String path = line.split("\"")[1];
          if (path.trim().length() > 0) {
            pathList.add(new PathDetails(path, "Dummy Description"));
          }
        }
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return pathList;
  }
}
