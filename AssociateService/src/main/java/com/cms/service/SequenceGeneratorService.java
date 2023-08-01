package com.cms.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cms.model.DatabaseSequence;

@Service
public class SequenceGeneratorService {

	@Autowired
	private MongoOperations mongoOperations;

	public long generateSequence(String seqName) {

		DatabaseSequence counter = mongoOperations.findAndModify(

				query(where("_id").is(seqName)),

				new Update().inc("seq", 1),

				options().returnNew(true),

				DatabaseSequence.class);

		if (counter == null) {

			counter = new DatabaseSequence();

			counter.setId(seqName);

			counter.setSeq(100);

			mongoOperations.insert(counter);

		}

		return counter.getSeq();

	}
}