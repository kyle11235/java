package com.oracle.solrgo.controller;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.oracle.solrgo.model.Document;
import com.oracle.solrgo.model.Response;
import com.oracle.solrgo.service.DocumentService;

@Path("/document")
@Singleton
public class DocumentController {

	private static Logger logger = Logger.getLogger(DocumentController.class);
	private DocumentService documentService = new DocumentService();

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDocuments(@QueryParam("q") String q) {
		logger.debug(q);
		Response response = new Response();
		List<Document> documents = documentService.getDocuments(q);
		response.setDocuments(documents);
		return response;
	}

}
