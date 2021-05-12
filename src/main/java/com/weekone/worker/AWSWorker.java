package com.weekone.worker;

import java.io.File;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class AWSWorker {
	Logger log = LoggerFactory.getLogger(AWSWorker.class);
	@Value("${aws.s3.bucket}")
	private String bucketName;
	@Value("${aws.access_key_id}")
	private String access_key;
	@Value("${aws.secret_access_key}")
	private String secret_key;
	@Value("${uploading_folder}")
	private String uploading_folder;

	@Autowired
	private AmazonS3 amazons3;

	public String uploadImageToS3(String imageURl) {


		for (Bucket bucket : amazons3.listBuckets()) {
			log.info(" bucket names >>>" + bucket.getName());

		}
		Date date = new Date(); 
		long timeMilli = date.getTime();
		String fileName = uploading_folder + timeMilli;

		amazons3.putObject(new PutObjectRequest(bucketName, fileName, new File(imageURl))
				.withCannedAcl(CannedAccessControlList.PublicRead));
		URL s3Url = amazons3.getUrl(bucketName, fileName);
		log.info("s3url >>>>>>>>" + s3Url);
		return s3Url.toString();
	}


}
