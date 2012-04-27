package com.telogical.ksp;

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

import com.telogical.ksp.model.KSPFactType;
import com.telogical.ksp.util.PackageStore;

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
		KSPFactType fact = marshallXMLToKSP(xmlString);	
		try {
			KnowledgeBase kbase = readKnowledgeBase(pkg);
			StatefulKnowledgeSession ksession = kbase
					.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
					.newFileLogger(ksession, "ksp");
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

	private KSPFactType marshallXMLToKSP(String xmlStr) {
		KSPFactType ksp = new KSPFactType();
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			NodeList facts = doc.getElementsByTagName("kspFactType");
			for (int i = 0; i < facts.getLength(); i++) { // for now there will	be only on fact
				Element fact = (Element) facts.item(0);
				ksp.setClient(fact.getElementsByTagName("client").item(0)
						.getTextContent());
				ksp.setCompetitor(fact.getElementsByTagName("competitor").item(0)
						.getTextContent());
				ksp.setProductCategory(fact
						.getElementsByTagName("productCategory").item(0)
						.getTextContent());
				ksp.setCompetitorProductSubCategory(fact
						.getElementsByTagName("competitorProductSubCategory")
						.item(0).getTextContent());
				ksp.setCompetitorProductType(fact
						.getElementsByTagName("competitorProductType").item(0)
						.getTextContent());
				ksp.setCompetitorAttribute1(fact
						.getElementsByTagName("competitorAttribute1").item(0)
						.getTextContent());
				ksp.setCompetitorAttribute2(fact
						.getElementsByTagName("competitorAttribute2").item(0)
						.getTextContent());
				ksp.setClientProductSubCategory(fact
						.getElementsByTagName("clientProductSubCategory")
						.item(0).getTextContent());
				ksp.setClientProductType(fact
						.getElementsByTagName("clientProductType").item(0)
						.getTextContent());
				ksp.setClientAttribute1(fact
						.getElementsByTagName("clientAttribute1").item(0)
						.getTextContent());
				ksp.setClientAttribute2(fact
						.getElementsByTagName("clientAttribute2").item(0)
						.getTextContent());
				ksp.setLocationType(fact.getElementsByTagName("locationType")
						.item(0).getTextContent());
				ksp.setLocation(fact.getElementsByTagName("location").item(0)
						.getTextContent());
//				ksp.setKspTitle(fact.getElementsByTagName("kspTitle").item(0)
//						.getTextContent());
//				ksp.setKspBody(fact.getElementsByTagName("kspBody").item(0)
//						.getTextContent());
//				ksp.setKspBullets(fact.getElementsByTagName("kspBullets")
//						.item(0).getTextContent());
//				ksp.setKspPriority(fact.getElementsByTagName("kspPriority")
//						.item(0).getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ksp;
		}
		return ksp;
	}

	private URL getPackageUri(String pkg) throws Exception{
		return new URL(PackageStore.getStore().get(pkg));
	}
	
	private KnowledgeBase readKnowledgeBase(String pkg) throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		// later this url will be extracted from xml or properties list.
		URL url = getPackageUri(pkg);
		UrlResource resource = (UrlResource) ResourceFactory.newUrlResource(url);
		resource.setBasicAuthentication("enabled");
		resource.setUsername("");
		resource.setPassword("");
		kbuilder.add(resource, ResourceType.PKG); // .newClassPathResource("Sample.drl")
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
