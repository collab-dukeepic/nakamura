package org.sakaiproject.nakamura.basiclti;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.sakaiproject.nakamura.api.basiclti.VirtualToolDataProvider;
import org.sakaiproject.nakamura.util.ExtendedJSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletException;

@SlingServlet(methods = { "GET" }, generateService = true, paths = { "/var/basiclti/cletools" })
public class BasicLTICLEToolPropertyServlet extends SlingSafeMethodsServlet {

  @Reference
  protected transient VirtualToolDataProvider virtualToolDataProvider;

  private static final long serialVersionUID = 4209601992662802279L;

  private static final Logger LOG = LoggerFactory
      .getLogger(BasicLTICLEToolPropertyServlet.class);

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    LOG.debug("doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)");
    ExtendedJSONWriter ejw = new ExtendedJSONWriter(response.getWriter());
    try {
      ejw.object();
      ejw.key("toolList").value(virtualToolDataProvider.getSupportedVirtualToolIds());
      ejw.endObject();
    } catch (JSONException e) {
      final Writer trace = new StringWriter();
      final PrintWriter pw = new PrintWriter(trace);
      e.printStackTrace(pw);
      response.sendError(500, trace.toString());
    }
  }

}
