/*
 * Minio Java Example, (C) 2016 Minio, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.minio.photoapiservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.MinioException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;

public class AlbumDao {
	private static final String ALBUMS = "liu";
	private static final String IP = "http://localhost";

	private static final String ACCESSKEY = "B7XIE72VDKNSINN898D3";
	private static final String SECRETKEY = "gCCpGe2DH7rk7i8j9kKxR6hKwnFwKzC4WXD5LFoF";

	public List<Album> listAlbums(String minioBucket)
			throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException, MinioException {

		List<Album> list = new ArrayList<Album>();
		// Initialize minio client object.
		MinioClient minioClient = new MinioClient(IP, 9000, ACCESSKEY, SECRETKEY);

		// List all objects.
		Iterable<Result<Item>> myObjects = minioClient.listObjects(minioBucket);

		// Iterate over each elements and set album url.
		for (Result<Item> result : myObjects) {
			Item item = result.get();
			System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());

			// Generate a presigned URL which expires in a day
			String url = minioClient.presignedGetObject(minioBucket, item.objectName(), 60 * 60 * 24);

			// Create a new Album Object
			Album album = new Album();

			// Set the presigned URL in the album object
			album.setUrl(url);

			// Add the album object to the list holding Album objects
			list.add(album);

		}

		// Return list of albums.
		return list;
	}

	public Album getPhoto(String name) throws InvalidEndpointException, InvalidPortException, InvalidKeyException,
			InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, NoResponseException,
			ErrorResponseException, InternalException, IOException, XmlPullParserException, InvalidExpiresRangeException {
		MinioClient minioClient = new MinioClient(IP, 9000, ACCESSKEY, SECRETKEY);
//		String objectUrl = minioClient.getObjectUrl(ALBUMS, "a.jpg");
		String presignedGetObject = minioClient.presignedGetObject(ALBUMS, name);
		// minioClient.
		System.out.println(presignedGetObject);
		return new Album(presignedGetObject, null);
	}

	/**
	 * @Description: 添加bucket
	 * @return
	 * @throws InvalidPortException
	 * @throws InvalidEndpointException
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws InternalException
	 * @throws ErrorResponseException
	 * @throws NoResponseException
	 * @throws InsufficientDataException
	 * @throws NoSuchAlgorithmException
	 * @throws RegionConflictException
	 * @throws InvalidBucketNameException
	 * @throws InvalidKeyException
	 */
	public Map<String, String> insertBucket(String bucketName)
			throws InvalidEndpointException, InvalidPortException, InvalidKeyException, InvalidBucketNameException,
			RegionConflictException, NoSuchAlgorithmException, InsufficientDataException, NoResponseException,
			ErrorResponseException, InternalException, IOException, XmlPullParserException {
		MinioClient minioClient = new MinioClient(IP, 9000, ACCESSKEY, SECRETKEY);
		minioClient.makeBucket(bucketName);
		return null;
	}

	public Map<String, String> uploadFile(String bucketName, String objectName, String file)
			throws InvalidEndpointException, InvalidPortException, InvalidKeyException, InvalidBucketNameException,
			RegionConflictException, NoSuchAlgorithmException, InsufficientDataException, NoResponseException,
			ErrorResponseException, InternalException, IOException, XmlPullParserException, InvalidArgumentException {
		MinioClient minioClient = new MinioClient(IP, 9000, ACCESSKEY, SECRETKEY);
		minioClient.putObject(bucketName, objectName, file);
		return null;
	}
}
