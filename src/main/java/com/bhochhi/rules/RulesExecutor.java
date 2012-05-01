package com.bhochhi.rules;

import java.io.ByteArrayInputStream;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.bhochhi.rules.model.Message;
import com.bhochhi.util.PackageStore;

@Path("/execute")
public class RulesExecutor {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Path("/{packageName}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response execute(@PathParam("packageName") String pkg,
			String xmlString) {
		Message fact = marshallXMLToMessage(xmlString);
		try {
			KnowledgeBase kbase = readKnowledgeBase(pkg);
			StatefulKnowledgeSession ksession = kbase
					.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
					.newFileLogger(ksession, "message");
			ksession.insert(fact);
			System.out.println("**************No of rules fired: "
					+ ksession.fireAllRules());
			System.out.println(ksession.getFactCount());
			logger.close();
			ksession.dispose();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return Response.ok(fact).build();
	}

	private Message marshallXMLToMessage(String xmlStr) {
		Message mess = new Message();
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(new ByteArrayInputStream(xmlStr
					.getBytes("UTF-8")));
			NodeList facts = doc.getElementsByTagName("message");
			for (int i = 0; i < facts.getLength(); i++) { // for now there will
															// be only on fact
				Element fact = (Element) facts.item(0);
				mess.setClient(fact.getElementsByTagName("client").item(0)
						.getTextContent());
				mess.setCompetitor(fact.getElementsByTagName("competitor")
						.item(0).getTextContent());
				mess.setProductCategory(fact
						.getElementsByTagName("productCategory").item(0)
						.getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return mess;
		}
		return mess;
	}

	private URL getPackageUri(String pkg) throws Exception {
		return new URL(PackageStore.getStore().get(pkg));
	}

	private KnowledgeBase readKnowledgeBase(String pkg) throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		// later this url will be extracted from xml or properties list.
		URL url = getPackageUri(pkg);
		UrlResource resource = (UrlResource) ResourceFactory
				.newUrlResource(url);
		resource.setBasicAuthentication("enabled");
		resource.setUsername("");
		resource.setPassword("");
		kbuilder.add(resource, ResourceType.PKG); 
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}
