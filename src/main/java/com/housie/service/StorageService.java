package com.housie.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Service
public class StorageService {

    private static final Logger logger = LogManager.getLogger(StorageService.class);

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final SimpleDateFormat stringDateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Autowired
    MongoTemplate primaryMongoTemplate;


    private static String idKeyName = "_id";
    private static String createDate = "createDate";
    private static String updateDate = "updateDate";
    private static String createdAt = "createdAt";
    private static String updatedAt = "updatedAt";


    public Document saveDocument(String collectionName, String jsonDoc) {
        return this.saveDocument(collectionName, jsonDoc, primaryMongoTemplate);
    }

    public Document saveDocument(String collectionName, String jsonDoc, MongoTemplate mongoTemplate) {

        Document doc = Document.parse(jsonDoc);
        doc.append(createDate, formatter.format(new Date()));
        doc.append(updateDate, formatter.format(new Date()));
        doc.append(createdAt, new Date());
        doc.append(updatedAt, new Date());


        mongoTemplate.insert(doc, collectionName);

        System.out.println(" Insert Success ... " + doc.getObjectId(idKeyName));
        appendDocId(doc);
        return doc;

    }

    public boolean upsertDocument(String collectionName, Query query, JSONObject jsonObject, Set<String> toBeUpdatedKeys) {
        return this.upsertDocument(collectionName, query, jsonObject, toBeUpdatedKeys, primaryMongoTemplate);
    }

    public boolean upsertDocument(String collectionName, Query query, JSONObject jsonObject, Set<String> toBeUpdatedKeys, MongoTemplate mongoTemplate) {

        DBObject dbDoc = BasicDBObject.parse(jsonObject.toString());
        Update update = fromDBObjectExcludeNullFields(dbDoc, toBeUpdatedKeys);

        update.setOnInsert(createDate, formatter.format(new Date()));
        update.setOnInsert(createdAt, new Date());
        update.set(updateDate, formatter.format(new Date()));
        update.set(updatedAt, new Date());


        UpdateResult updateResult = mongoTemplate.upsert(query, update, collectionName);

        if (updateResult.wasAcknowledged()) {
            logger.info(" UPSERT Success Matched-Count : {} , Modified-Count : {}", updateResult.getMatchedCount(), updateResult.getModifiedCount());
            return true;
        } else {
            logger.info(" UPSERT ERROR ... ");
        }
        return false;
    }

    public Query getUpsertQueries(JSONObject jsonBody, Set<String> upsertQueryKeys) {
        Query query = new Query();
        for (String upsertKey : upsertQueryKeys) {
            if (jsonBody.has(upsertKey)) {
                query = query.addCriteria(Criteria.where(upsertKey).is(jsonBody.get(upsertKey)));
            }
        }
        return query;
    }


    public Document getDocumentById(String collectionName, String id) {
        return this.getDocumentById(collectionName, id, primaryMongoTemplate);
    }

    public Document getDocumentById(String collectionName, String id, MongoTemplate mongoTemplate) {
        // TODO Auto-generated method stub

        FindIterable<Document> docIt = mongoTemplate.getCollection(collectionName)
                .find(and(eq(idKeyName, new ObjectId(id))));

        return docIt.first();
    }


    public Document updateDocById(String collectionName, JSONObject jsonObj, String id, MongoTemplate mongoTemplate) {

        Document doc = Document.parse(jsonObj.toString());
        updateDateTypeValues(doc);
        doc.append(updateDate, formatter.format(new Date()));
        UpdateResult result = mongoTemplate.getCollection(collectionName).replaceOne(and(eq(idKeyName, new ObjectId(id))), doc);
        //Check if updated...
        if (result.getMatchedCount() == 1) {
            appendDocId(doc);
            return doc;
        } else {
            return null;
        }

    }

    public Document updateDocumentById(String collectionName, Document document, String id) {
        return this.updateDocumentById(collectionName, document, id, primaryMongoTemplate);
    }

    public Document updateDocumentById(String collectionName, Document document, String id, MongoTemplate mongoTemplate) {

        //doc.append(ownerKeyName,GenericFilter.threadLocalValue.get().getOwnerName() );
        document.append(updateDate, formatter.format(new Date()));
        document.append(updatedAt, new Date());
        UpdateResult result = mongoTemplate.getCollection(collectionName).replaceOne(and(eq(idKeyName, new ObjectId(id))), document);
        //Check if updated...
        if (result.getMatchedCount() == 1) {
            appendDocId(document);
            return document;
        } else {
            return null;
        }

    }

    private void updateDateTypeValues(Document document) {
        try {
            if (document.containsKey(createdAt)) {
                document.append(createdAt, stringDateFormater.parse(document.getString(createdAt)));
            }

            if (document.containsKey(updatedAt)) {
                document.append(updatedAt, stringDateFormater.parse(document.getString(updatedAt)));
            }
        } catch (Exception e) {
            logger.error("Exception in updateDateTypeValues  for DOC  {} ", document.toString(), e);
        }
    }

    public void appendDocId(Document doc) {
        String docId = doc.getObjectId("_id").toString();
        doc.remove(idKeyName);
        // This is being done to change the format of Object Id in response.
        doc.append(idKeyName, docId);
    }


    public static Update fromDBObjectExcludeNullFields(DBObject object, Set<String> toBeUpdatedKeys) {
        Update update = new Update();
        for (String key : toBeUpdatedKeys) {
            if (object.containsField(key)) {
                Object value = object.get(key);
                if (value != null) {
                    update.set(key, value);
                }
            }
        }
        return update;
    }

    public ArrayList<Document> getDocumentsByCollection(String collectionName) {
        return this.getDocumentsByCollection(collectionName, primaryMongoTemplate);
    }

    public ArrayList<Document> getDocumentsByCollection(String collectionName, MongoTemplate mongoTemplate) {

        ArrayList<Document> listDoc = new ArrayList<Document>();
        FindIterable<Document> documentIterator;
        documentIterator = mongoTemplate.getCollection(collectionName).find();

        for (Document doc : documentIterator) {
            appendDocId(doc);
            listDoc.add(doc);
        }
        return listDoc;
    }
}
